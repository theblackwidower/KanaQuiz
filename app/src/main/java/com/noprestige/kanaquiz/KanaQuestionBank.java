package com.noprestige.kanaquiz;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

class KanaQuestionBank extends ArrayList<KanaQuestion>
{
    private KanaQuestion currentQuestion;
    private Random rng = new Random();

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
        return super.addAll(Arrays.asList(questions));
    }

    boolean addQuestions(KanaQuestion[] questions1, KanaQuestion[] questions2)
    {
        previousQuestions = null;
        return (super.addAll(Arrays.asList(questions1)) &&
                super.addAll(Arrays.asList(questions2)));
    }

    boolean addQuestions(KanaQuestionBank questions)
    {
        previousQuestions = null;
        return super.addAll(questions);
    }

    String[] getPossibleAnswers()
    {
        //TODO: Randomize so we select a limited number from a larger selection.
        ArrayList<String> possibleAnswerStrings = new ArrayList<>();

        for (int i = 0; i < size(); i++)
            if (!possibleAnswerStrings.contains(get(i).fetchCorrectAnswer()))
                possibleAnswerStrings.add(get(i).fetchCorrectAnswer());

        String[] returnValue = new String[possibleAnswerStrings.size()];
        possibleAnswerStrings.toArray(returnValue);
        return returnValue;
    }
}
