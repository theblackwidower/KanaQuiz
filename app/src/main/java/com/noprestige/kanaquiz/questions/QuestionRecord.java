package com.noprestige.kanaquiz.questions;

import java.util.concurrent.ArrayBlockingQueue;

class QuestionRecord extends ArrayBlockingQueue<Question>
{
    QuestionRecord(int capacity)
    {
        super(capacity);
    }

    @Override
    public boolean add(Question question)
    {
        if (contains(question))
            return false;
        else
        {
            super.add(question);
            if (remainingCapacity() == 0)
                remove();
            return true;
        }
    }
}
