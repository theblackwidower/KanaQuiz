package com.noprestige.kanaquiz;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

class KanaQuestionBank extends ArrayList<KanaQuestion>
{
    private KanaQuestion currentQuestion;
    private ArrayList<String> fullAnswerList = null;
    private Random rng = new Random();

    private static final int MAX_MULTIPLE_CHOICE_ANSWERS = 6;

    private QuestionRecord previousQuestions = null;

    KanaQuestionBank()
    {
        super();
    }

    void newQuestion() throws NoQuestionsException
    {
        //TODO: Weight question choice by kana records
        if (this.size() > 1)
        {
            if (previousQuestions == null)
                previousQuestions = new QuestionRecord(Math.min(this.size(), OptionsControl.getInt(R.string.prefid_repetition)));
            do
                currentQuestion = this.get(rng.nextInt(this.size()));
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
        return super.addAll(Arrays.asList(questions));
    }

    boolean addQuestions(KanaQuestion[] questions1, KanaQuestion[] questions2)
    {
        previousQuestions = null;
        fullAnswerList = null;
        return (super.addAll(Arrays.asList(questions1)) &&
                super.addAll(Arrays.asList(questions2)));
    }

    boolean addQuestions(KanaQuestionBank questions)
    {
        previousQuestions = null;
        fullAnswerList = null;
        return super.addAll(questions);
    }

    String[] getPossibleAnswers()
    {
        return getPossibleAnswers(MAX_MULTIPLE_CHOICE_ANSWERS);
    }

    String[] getPossibleAnswers(int maxChoices)
    {
        if (fullAnswerList == null)
        {
            fullAnswerList = new ArrayList<>();
            for (int i = 0; i < size(); i++)
                if (!fullAnswerList.contains(get(i).fetchCorrectAnswer()))
                    fullAnswerList.add(get(i).fetchCorrectAnswer());
        }

        //TODO: Weight displayed choices by incorrect answer records

        ArrayList<String> possibleAnswerStrings = new ArrayList<>();

        possibleAnswerStrings.add(fetchCorrectAnswer());

        while (possibleAnswerStrings.size() < Math.min(fullAnswerList.size(), maxChoices))
        {
            int rand = rng.nextInt(fullAnswerList.size());
            if (!possibleAnswerStrings.contains(fullAnswerList.get(rand)))
                possibleAnswerStrings.add(fullAnswerList.get(rand));
        }

        Collections.shuffle(possibleAnswerStrings);

        String[] returnValue = new String[possibleAnswerStrings.size()];
        possibleAnswerStrings.toArray(returnValue);
        return returnValue;
    }
}
