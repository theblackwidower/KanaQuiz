package com.noprestige.kanaquiz.questions;

import java.util.concurrent.ArrayBlockingQueue;

class QuestionRecord extends ArrayBlockingQueue<String>
{
    QuestionRecord(int capacity)
    {
        super(capacity);
    }

    public boolean add(Question question)
    {
        if (contains(question.getDatabaseKey()))
            return false;
        else
        {
            add(question.getDatabaseKey());
            if (remainingCapacity() == 0)
                remove();
            return true;
        }
    }
}
