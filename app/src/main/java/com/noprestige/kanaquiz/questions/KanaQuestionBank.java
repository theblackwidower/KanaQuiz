package com.noprestige.kanaquiz.questions;

import com.noprestige.kanaquiz.logs.LogDatabase;
import com.noprestige.kanaquiz.R;
import com.noprestige.kanaquiz.options.OptionsControl;

import java.util.TreeSet;

public class KanaQuestionBank extends WeightedList<KanaQuestion>
{
    private KanaQuestion currentQuestion;
    private String[] fullAnswerList = null;

    private static final int MAX_MULTIPLE_CHOICE_ANSWERS = 6;

    private QuestionRecord previousQuestions = null;

    KanaQuestionBank()
    {
        super();
    }

    public void newQuestion() throws NoQuestionsException
    {
        if (this.count() > 1)
        {
            if (previousQuestions == null)
                previousQuestions = new QuestionRecord(Math.min(this.count(), OptionsControl.getInt(R.string.prefid_repetition)));
            do
                currentQuestion = this.getRandom();
            while (!previousQuestions.add(currentQuestion));
        }
        else
            throw new NoQuestionsException();
    }

    public String getCurrentKana()
    {
        return currentQuestion.getKana();
    }

    public boolean checkCurrentAnswer(String response)
    {
        return currentQuestion.checkAnswer(response);
    }

    public String fetchCorrectAnswer()
    {
        return currentQuestion.fetchCorrectAnswer();
    }

    public boolean addQuestions(KanaQuestion[] questions)
    {
        previousQuestions = null;
        fullAnswerList = null;
        for (KanaQuestion question : questions)
        {
            // Fetches the percentage of times the user got a kana right,
            // The 1.05f is to invert the value so we get the number of times they got it wrong,
            // and add 5% so any kana the user got perfect will still appear in the quiz.
            // Times 100f to get the percentage.
            this.add((1.05f - LogDatabase.DAO.getKanaPercentage(question.getKana())) * 100f, question);
        }
        return true;
    }

    public boolean addQuestions(KanaQuestionBank questions)
    {
        previousQuestions = null;
        fullAnswerList = null;

        return this.merge(questions);
    }

    public String[] getPossibleAnswers()
    {
        return getPossibleAnswers(MAX_MULTIPLE_CHOICE_ANSWERS);
    }

    public String[] getPossibleAnswers(int maxChoices)
    {
        if (fullAnswerList == null)
        {
            TreeSet<String> answers = new TreeSet<>(new GojuonOrder());
            for (KanaQuestion question : this.values())
                answers.add(question.fetchCorrectAnswer());
            fullAnswerList = new String[answers.size()];
            answers.toArray(fullAnswerList);
        }

        if (fullAnswerList.length <= maxChoices)
            return fullAnswerList;
        else
        {
            WeightedList<String> weightedAnswerList = new WeightedList<>();
            for (String answer : fullAnswerList)
            {
                if (!answer.equals(fetchCorrectAnswer()))
                {
                    // Max value of 24 to prevent integer overflow,
                    // since LOGbase2( Integer.MAX_VALUE / 102 ) ~= 24 (rounded down)
                    // where 102 is the number of unique correct answers in Hiragana and Katakana classes
                    weightedAnswerList.add(
                            Math.pow(2, Math.min(LogDatabase.DAO.getIncorrectAnswerCount(fetchCorrectAnswer(), answer), 24)),
                            answer);
                }
            }

            TreeSet<String> possibleAnswerStrings = new TreeSet<>(new GojuonOrder());

            possibleAnswerStrings.add(fetchCorrectAnswer());

            while (possibleAnswerStrings.size() < maxChoices)
                possibleAnswerStrings.add(weightedAnswerList.getRandom());

            String[] returnValue = new String[possibleAnswerStrings.size()];
            possibleAnswerStrings.toArray(returnValue);
            return returnValue;
        }
    }
}
