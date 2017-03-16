package com.noprestige.kanaquiz;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

class KanaQuestionBank extends ArrayList<KanaQuestion>
{
    private KanaQuestion currentQuestion;
    private Random rng = new Random();

    //this number represents any given sequence of questions in which there are no repeats
    //can't be anything less than 2
    private static final int NO_REPEAT_RECORD = 3;
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
                previousQuestions = new QuestionRecord((this.size() < NO_REPEAT_RECORD)?this.size():NO_REPEAT_RECORD);

            do {
                currentQuestion = this.get(rng.nextInt(this.size()));
            } while (!previousQuestions.add(currentQuestion));
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

    boolean addQuestions(KanaQuestion[] questions1, KanaQuestion[] questions2, KanaQuestion[] questions3)
    {
        previousQuestions = null;
        return (super.addAll(Arrays.asList(questions1)) &&
                super.addAll(Arrays.asList(questions2)) &&
                super.addAll(Arrays.asList(questions3)));
    }

    boolean addQuestions(KanaQuestionBank questions)
    {
        previousQuestions = null;
        return super.addAll(questions);
    }
}
