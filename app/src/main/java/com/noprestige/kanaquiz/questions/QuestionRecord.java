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
        if (super.contains(question))
            return false;
        else
        {
            super.add(question);
            if (super.remainingCapacity() == 0)
                super.remove();
            return true;
        }
    }
}
