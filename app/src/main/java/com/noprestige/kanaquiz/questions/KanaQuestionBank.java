package com.noprestige.kanaquiz.questions;

import com.noprestige.kanaquiz.R;
import com.noprestige.kanaquiz.logs.LogDao;
import com.noprestige.kanaquiz.options.OptionsControl;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class KanaQuestionBank extends WeightedList<KanaQuestion>
{
    private KanaQuestion currentQuestion;
    private Set<String> fullAnswerList = new TreeSet<>(new GojuonOrder());
    private Map<String, WeightedList<String>> weightedAnswerListCache = new TreeMap<>();

    private static final int MAX_MULTIPLE_CHOICE_ANSWERS = 6;

    private QuestionRecord previousQuestions;

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
        previousQuestions = null;
        if (questions != null)
            for (KanaQuestion question : questions)
            {
                // Fetches the percentage of times the user got a kana right,
                Float percentage = LogDao.getKanaPercentage(question.getKana());
                if (percentage == null)
                    percentage = 0.1f;
                // The 1.05f is to invert the value so we get the number of times they got it wrong,
                // and add 5% so any kana the user got perfect will still appear in the quiz.
                // Times 100f to get the percentage.
                this.add((1.05f - percentage) * 100f, question);
                fullAnswerList.add(question.fetchCorrectAnswer());
            }
        return true;
    }

    public boolean addQuestions(KanaQuestionBank questions)
    {
        previousQuestions = null;
        this.fullAnswerList.addAll(questions.fullAnswerList);
        return this.merge(questions);
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
            if (!weightedAnswerListCache.containsKey(getCurrentKana()))
            {
                WeightedList<String> weightedAnswerList = new WeightedList<>();
                for (String answer : fullAnswerList)
                {
                    if (!answer.equals(fetchCorrectAnswer()))
                    {
                        // Max value of 24 to prevent integer overflow,
                        // since LOGbase2( Integer.MAX_VALUE / 102 ) ~= 24 (rounded down)
                        // where 102 is the number of unique correct answers in Hiragana and Katakana classes
                        weightedAnswerList.add(Math
                                        .pow(2, Math.min(LogDao.getIncorrectAnswerCount(getCurrentKana(), answer), 24)),
                                answer);
                    }
                }
                weightedAnswerListCache.put(getCurrentKana(), weightedAnswerList);
            }

            TreeSet<String> possibleAnswerStrings = new TreeSet<>(new GojuonOrder());

            possibleAnswerStrings.add(fetchCorrectAnswer());

            WeightedList<String> weightedAnswerList = weightedAnswerListCache.get(getCurrentKana());

            while (possibleAnswerStrings.size() < maxChoices)
                possibleAnswerStrings.add(weightedAnswerList.getRandom());

            String[] returnValue = new String[possibleAnswerStrings.size()];
            possibleAnswerStrings.toArray(returnValue);
            return returnValue;
        }
    }
}
