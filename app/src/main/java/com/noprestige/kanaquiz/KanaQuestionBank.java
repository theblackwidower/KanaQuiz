package com.noprestige.kanaquiz;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

class KanaQuestionBank extends ArrayList<KanaQuestion>
{
    private int currentQuestionNumber;
    private Random rng = new Random();

    KanaQuestionBank()
    {
        super();
    }

    void newQuestion() throws NoQuestionsException
    {
        if (this.size() > 1)
        {
            int newQuestionNumber;
            do {
                newQuestionNumber = rng.nextInt(this.size());
            } while (currentQuestionNumber == newQuestionNumber);
            currentQuestionNumber = newQuestionNumber;
        }
        else
            throw new NoQuestionsException();
    }

    char getCurrentKana()
    {
        return (this.get(currentQuestionNumber)).getKana();
    }

    boolean checkCurrentAnswer(String response)
    {
        return (this.get(currentQuestionNumber)).checkAnswer(response);
    }

    boolean addQuestions(KanaQuestion[] questions)
    {
        return super.addAll(Arrays.asList(questions));
    }

    boolean addQuestions(KanaQuestionBank questions)
    {
        return super.addAll(questions);
    }

    static KanaQuestionBank merge(KanaQuestionBank questions1, KanaQuestionBank questions2)
    {
        KanaQuestionBank newBank = new KanaQuestionBank();
        newBank.addAll(questions1);
        newBank.addAll(questions2);
        return newBank;
    }
}