package com.noprestige.kanaquiz.questions;

import org.junit.Assert;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;

public class QuestionRecordTest
{
    @Test
    public void testRecord()
    {
        QuestionRecord recordToTest = new QuestionRecord(6);

        KanaQuestion question1 = new KanaQuestion("T", "test");
        KanaQuestion question2 = new KanaQuestion("L", "run");
        KanaQuestion question3 = new KanaQuestion("J", "swing");
        KanaQuestion question4 = new KanaQuestion("Q", "flex");
        KanaQuestion question5 = new KanaQuestion("N", "squid");
        KanaQuestion question6 = new KanaQuestion("R", "iron");

        //add three questions to the record
        Assert.assertThat(recordToTest.add(question1), is(true));
        Assert.assertThat(recordToTest.add(question2), is(true));
        Assert.assertThat(recordToTest.add(question3), is(true));

        //questions already in the record can't be added
        Assert.assertThat(recordToTest.add(question2), is(false));
        Assert.assertThat(recordToTest.add(question3), is(false));

        //add two more questions
        Assert.assertThat(recordToTest.add(question4), is(true));
        Assert.assertThat(recordToTest.add(question5), is(true));

        //question1 should still be in the record
        Assert.assertThat(recordToTest.add(question1), is(false));
        //adding another question
        Assert.assertThat(recordToTest.add(question6), is(true));
        //question1 should now be pushed out, and can be readded
        Assert.assertThat(recordToTest.add(question1), is(true));
        Assert.assertThat(recordToTest.add(question2), is(true));
        Assert.assertThat(recordToTest.add(question3), is(true));
    }
}
