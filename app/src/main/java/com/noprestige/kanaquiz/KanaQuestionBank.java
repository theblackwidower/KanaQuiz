package com.noprestige.kanaquiz;

import java.util.ArrayList;
import java.util.Random;
import java.util.TreeMap;

class KanaQuestionBank extends TreeMap<Integer, KanaQuestion>
{
    private KanaQuestion currentQuestion;
    private String[] fullAnswerList = null;
    private Random rng = new Random();
    private int maxValue = 0;

    private static final int MAX_MULTIPLE_CHOICE_ANSWERS = 6;

    private QuestionRecord previousQuestions = null;

    KanaQuestionBank()
    {
        super();
    }

    void newQuestion() throws NoQuestionsException
    {
        if (this.size() > 1)
        {
            if (previousQuestions == null)
                previousQuestions = new QuestionRecord(Math.min(this.size(), OptionsControl.getInt(R.string.prefid_repetition)));
            do
                currentQuestion = this.get(this.floorKey(rng.nextInt(maxValue)));
            while (!previousQuestions.add(currentQuestion));
        }
        else
            throw new NoQuestionsException();
    }

    String getCurrentKana()
    {
        return currentQuestion.getKana();
    }

    boolean checkCurrentAnswer(String response)
    {
        return currentQuestion.checkAnswer(response);
    }

    String fetchCorrectAnswer()
    {
        return currentQuestion.fetchCorrectAnswer();
    }

    boolean addQuestions(KanaQuestion[] questions)
    {
        previousQuestions = null;
        fullAnswerList = null;
        for (KanaQuestion question : questions)
        {
            this.put(maxValue, question);
            // Fetches the percentage of times the user got a kana right,
            // The 1.05f is to invert the value so we get the number of times they got it wrong,
            // and add 5% so any kana the user got perfect will still appear in the quiz.
            // Times 100f to get the percentage.
            maxValue += (int) Math.ceil((1.05f - LogDatabase.DAO.getKanaPercentage(question.getKana())) * 100f);
        }
        return true;
    }

    boolean addQuestions(KanaQuestionBank questions)
    {
        previousQuestions = null;
        fullAnswerList = null;

        for (Integer key : questions.keySet())
            this.put(maxValue + key, questions.get(key));
        maxValue += questions.maxValue;

        return true;
    }

    String[] getPossibleAnswers()
    {
        return getPossibleAnswers(MAX_MULTIPLE_CHOICE_ANSWERS);
    }

    String[] getPossibleAnswers(int maxChoices)
    {
        if (fullAnswerList == null)
        {
            ArrayList<String> answers = new ArrayList<>();
            for (KanaQuestion question : this.values())
                if (!answers.contains(question.fetchCorrectAnswer()))
                    answers.add(question.fetchCorrectAnswer());
            fullAnswerList = new String[answers.size()];
            answers.toArray(fullAnswerList);
            QuestionManagement.gojuonSort(fullAnswerList);
        }

        if (fullAnswerList.length <= maxChoices)
            return fullAnswerList;
        else
        {
            TreeMap<Integer, String> weightedAnswerList = new TreeMap<>();
            int answerListMaxValue = 0;
            for (String answer : fullAnswerList)
            {
                if (!answer.equals(fetchCorrectAnswer()))
                {
                    weightedAnswerList.put(answerListMaxValue, answer);
                    // Max value of 24 to prevent integer overflow,
                    // since LOGbase2( Integer.MAX_VALUE / 102 ) ~= 24 (rounded down)
                    // where 102 is the number of unique correct answers in Hiragana and Katakana classes
                    answerListMaxValue += Math.pow(2, Math.min(LogDatabase.DAO.getIncorrectAnswerCount(fetchCorrectAnswer(), answer), 24));
                }
            }

            ArrayList<String> possibleAnswerStrings = new ArrayList<>();

            possibleAnswerStrings.add(fetchCorrectAnswer());

            while (possibleAnswerStrings.size() < maxChoices)
            {
                String choice = weightedAnswerList.get(weightedAnswerList.floorKey(rng.nextInt(answerListMaxValue)));
                if (!possibleAnswerStrings.contains(choice))
                    possibleAnswerStrings.add(choice);
            }

            String[] returnValue = new String[possibleAnswerStrings.size()];
            possibleAnswerStrings.toArray(returnValue);
            QuestionManagement.gojuonSort(returnValue);
            return returnValue;
        }
    }
}
