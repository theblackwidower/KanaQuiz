package com.noprestige.kanaquiz.questions;

import com.noprestige.kanaquiz.R;
import com.noprestige.kanaquiz.logs.LogDao;
import com.noprestige.kanaquiz.options.OptionsControl;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class KanaQuestionBank extends WeightedList<KanaQuestion>
{
    private KanaQuestion currentQuestion;
    private Set<String> fullAnswerList = new TreeSet<>(new GojuonOrder());
    private Map<String, WeightedList<String>> weightedAnswerListCache;

    private Set<String> basicAnswerList = new TreeSet<>(new GojuonOrder());
    private Set<String> diacriticAnswerList = new TreeSet<>(new GojuonOrder());
    private Set<String> digraphAnswerList = new TreeSet<>(new GojuonOrder());
    private Set<String> diacriticDigraphAnswerList = new TreeSet<>(new GojuonOrder());

    private static final int MAX_MULTIPLE_CHOICE_ANSWERS = 6;

    private QuestionRecord previousQuestions;
    private int maxAnswerWeight = -1;

    KanaQuestionBank() {}

    public void newQuestion() throws NoQuestionsException
    {
        if (this.count() > 0)
        {
            if (previousQuestions == null)
                previousQuestions =
                        new QuestionRecord(Math.min(this.count(), OptionsControl.getInt(R.string.prefid_repetition)));
            do
                currentQuestion = this.getRandom();
            while (!previousQuestions.add(currentQuestion));
        }
        else
            throw new NoQuestionsException();
    }

    public String getCurrentKana()
    {
        return currentQuestion.getKana();
    }

    public boolean checkCurrentAnswer(String response)
    {
        return currentQuestion.checkAnswer(response);
    }

    public String fetchCorrectAnswer()
    {
        return currentQuestion.fetchCorrectAnswer();
    }

    public boolean addQuestions(KanaQuestion[] questions)
    {
        weightedAnswerListCache = null;
        previousQuestions = null;
        maxAnswerWeight = -1;
        if (questions != null)
        {
            boolean returnValue = true;
            for (KanaQuestion question : questions)
            {
                // Fetches the percentage of times the user got a kana right,
                Float percentage = LogDao.getKanaPercentage(question.getKana());
                if (percentage == null)
                    percentage = 0.1f;
                // The 1f is to invert the value so we get the number of times they got it wrong,
                // Times 100f to get the percentage.
                int weight = (int) Math.ceil((1f - percentage) * 100f);
                // Setting weight to never get lower than 2,
                // so any kana the user got perfect will still appear in the quiz.
                if (weight < 2)
                    weight = 2;
                // if any one of the additions fail, the method returns false
                returnValue =
                        this.add(weight, question) && fullAnswerList.add(question.fetchCorrectAnswer()) && returnValue;
                // Storing answers in specialized answer lists for more specialized answer selection
                returnValue = (question.isDiacritic() ?
                        (question.isDigraph() ? diacriticDigraphAnswerList.add(question.fetchCorrectAnswer()) :
                                diacriticAnswerList.add(question.fetchCorrectAnswer())) :
                        (question.isDigraph() ? digraphAnswerList.add(question.fetchCorrectAnswer()) :
                                basicAnswerList.add(question.fetchCorrectAnswer()))) && returnValue;
            }
            return returnValue;
        }
        return false;
    }

    public boolean addQuestions(KanaQuestionBank questions)
    {
        weightedAnswerListCache = null;
        previousQuestions = null;
        maxAnswerWeight = -1;
        this.fullAnswerList.addAll(questions.fullAnswerList);
        this.basicAnswerList.addAll(questions.basicAnswerList);
        this.diacriticAnswerList.addAll(questions.diacriticAnswerList);
        this.digraphAnswerList.addAll(questions.digraphAnswerList);
        this.diacriticDigraphAnswerList.addAll(questions.diacriticDigraphAnswerList);
        return this.merge(questions);
    }

    private int getMaxAnswerWeight()
    {
        if (maxAnswerWeight < 0)
        {
            // Max value is to prevent integer overflow in the weighted answer list. Number is chosen so if every
            // answer had this count (which is actually impossible because of the range restriction, but better to
            // err on the side of caution) the resultant calculations of 2^count would not add up to an integer
            // overflow.
            maxAnswerWeight = (int) Math.floor(Math.log(Integer.MAX_VALUE / (fullAnswerList.size() - 1)) / Math.log(2));
        }
        return maxAnswerWeight;
    }

    public String[] getPossibleAnswers()
    {
        return getPossibleAnswers(MAX_MULTIPLE_CHOICE_ANSWERS);
    }

    public String[] getPossibleAnswers(int maxChoices)
    {
        if (fullAnswerList.size() <= maxChoices)
        {
            String[] answerArray = new String[fullAnswerList.size()];
            fullAnswerList.toArray(answerArray);
            return answerArray;
        }
        else
        {
            if (weightedAnswerListCache == null)
                weightedAnswerListCache = new TreeMap<>();
            if (!weightedAnswerListCache.containsKey(getCurrentKana()))
            {
                int maxCount = 0;
                int minCount = Integer.MAX_VALUE;
                Map<String, Float> answerCounts = new TreeMap<>();
                for (String answer : fullAnswerList)
                {
                    if (!answer.equals(fetchCorrectAnswer()))
                    {
                        //fetch all data
                        int count = LogDao.getIncorrectAnswerCount(getCurrentKana(), answer);
                        maxCount = Math.max(maxCount, count);
                        minCount = Math.min(minCount, count);
                        answerCounts.put(answer, (float) count);
                    }
                }

                // Because of the inherent properties of the power function used later, subtracting the same amount
                // from each count so the lowest count is zero will not change the relative weight of each item. And
                // doing so will also save the space I need to prevent overflow issues.
                if (minCount > 0)
                {
                    maxCount -= minCount;
                    for (String answer : answerCounts.keySet())
                    {
                        float newCount = answerCounts.remove(answer) - minCount;
                        answerCounts.put(answer, newCount);
                    }
                }

                if (maxCount > getMaxAnswerWeight())
                {
                    float controlFactor = getMaxAnswerWeight() / maxCount;
                    for (String answer : answerCounts.keySet())
                    {
                        float newCount = answerCounts.remove(answer) * controlFactor;
                        answerCounts.put(answer, newCount);
                    }
                }

                WeightedList<String> weightedAnswerList = new WeightedList<>();
                for (String answer : answerCounts.keySet())
                    weightedAnswerList.add(Math.pow(2, answerCounts.get(answer)), answer);

                weightedAnswerListCache.put(getCurrentKana(), weightedAnswerList);
            }

            String[] possibleAnswerStrings = new String[maxChoices - 1];
            weightedAnswerListCache.get(getCurrentKana()).getRandom(possibleAnswerStrings);

            possibleAnswerStrings = Arrays.copyOf(possibleAnswerStrings, maxChoices);
            possibleAnswerStrings[maxChoices - 1] = fetchCorrectAnswer();

            GojuonOrder.sort(possibleAnswerStrings);

            return possibleAnswerStrings;
        }
    }
}
