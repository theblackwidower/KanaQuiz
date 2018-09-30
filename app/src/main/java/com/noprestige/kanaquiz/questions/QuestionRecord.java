package com.noprestige.kanaquiz.questions;

import java.util.concurrent.ArrayBlockingQueue;

class QuestionRecord extends ArrayBlockingQueue<KanaQuestion>
{
    QuestionRecord(int capacity)
    {
        super(capacity);
    }

    @Override
    public boolean add(KanaQuestion question)
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
