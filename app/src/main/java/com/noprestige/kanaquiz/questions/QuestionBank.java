/*
 *    Copyright 2021 T Duke Perry
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.noprestige.kanaquiz.questions;

import com.noprestige.kanaquiz.R;
import com.noprestige.kanaquiz.logs.LogDao;
import com.noprestige.kanaquiz.logs.LogTypeConversion;
import com.noprestige.kanaquiz.options.OptionsControl;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.concurrent.ArrayBlockingQueue;

import static androidx.room.util.StringUtil.EMPTY_STRING_ARRAY;

public class QuestionBank extends WeightedList<Question>
{
    private Question currentQuestion;
    private Set<String> fullKanaAnswerList = new TreeSet<>(new GojuonOrder());
    private Map<String, WeightedList<String>> weightedAnswerListCache;

    private Set<String> basicAnswerList = new TreeSet<>(new GojuonOrder());
    private Set<String> diacriticAnswerList = new TreeSet<>(new GojuonOrder());
    private Set<String> digraphAnswerList = new TreeSet<>(new GojuonOrder());
    private Set<String> diacriticDigraphAnswerList = new TreeSet<>(new GojuonOrder());

    private String[] currentPossibleAnswers;

    private Set<String> wordAnswerList = new TreeSet<>();
    private Set<String> yomiAnswerList = new TreeSet<>();

    private static final int MAX_MULTIPLE_CHOICE_ANSWERS = 6;

    private QuestionRecord previousQuestions;
    private int maxKanaAnswerWeight = -1;
    private int maxWordAnswerWeight = -1;
    private int maxYomiAnswerWeight = -1;

    private boolean isReset = true;

    public QuestionType getCurrentQuestionType()
    {
        return currentQuestion.getType();
    }

    public void newQuestion()
    {
        if (count() > 0)
        {
            if (previousQuestions == null)
            {
                previousQuestions =
                        new QuestionRecord(Math.min(count(), OptionsControl.getInt(R.string.prefid_repetition)));
                isReset = false;
            }
            do
                currentQuestion = getRandom();
            while (!previousQuestions.add(currentQuestion));
            currentPossibleAnswers = null;
        }
    }

    static class QuestionRecord extends ArrayBlockingQueue<String>
    {
        QuestionRecord(int capacity)
        {
            super(capacity);
        }

        public boolean add(Question question)
        {
            char prefix = LogTypeConversion.toCharFromType(question.getType());
            if (contains(prefix + question.getDatabaseKey()))
                return false;
            else
            {
                add(prefix + question.getDatabaseKey());
                if (remainingCapacity() == 0)
                    remove();
                return true;
            }
        }
    }

    public String getCurrentQuestionText()
    {
        return currentQuestion.getQuestionText();
    }

    public String getCurrentQuestionKey()
    {
        return currentQuestion.getDatabaseKey();
    }

    public boolean checkCurrentAnswer(String response)
    {
        return currentQuestion.checkAnswer(response);
    }

    public String fetchCorrectAnswer()
    {
        return currentQuestion.fetchCorrectAnswer();
    }

    public boolean loadQuestion(String questionKey, QuestionType type)
    {
        if (previousQuestions == null)
        {
            previousQuestions =
                    new QuestionRecord(Math.min(count(), OptionsControl.getInt(R.string.prefid_repetition)));
            isReset = false;
        }

        Collection<Question> questions = values();

        for (Question thisQuestion : questions)
        {
            if (thisQuestion.getDatabaseKey().equals(questionKey) && (thisQuestion.getType() == type))
            {
                currentQuestion = thisQuestion;
                previousQuestions.add(thisQuestion);
                currentPossibleAnswers = null;
                return true;
            }
        }
        return false;
    }

    public boolean addQuestions(Question[] questions)
    {
        if (questions != null)
        {
            boolean returnValue = true;
            for (Question question : questions)
                // if any one of the additions fail, the method returns false
                returnValue = addQuestion(question) && returnValue;
            return returnValue;
        }
        return false;
    }

    private Set<String> kanjiQuestionTypePref;

    public boolean addQuestion(Question question)
    {
        if (!isReset)
        {
            weightedAnswerListCache = null;
            previousQuestions = null;
            maxKanaAnswerWeight = -1;
            maxWordAnswerWeight = -1;
            maxYomiAnswerWeight = -1;
            isReset = true;
        }
        if (question != null)
        {
            if (question.getClass().equals(KanjiQuestion.class))
            {
                boolean returnValue = true;
                if (kanjiQuestionTypePref == null)
                    kanjiQuestionTypePref = OptionsControl.getStringSet(R.string.prefid_kanji_question_type);
                if (kanjiQuestionTypePref.contains("meaning"))
                    returnValue = addQuestionInternal(question) && returnValue;
                if (kanjiQuestionTypePref.contains("kunyomi"))
                    returnValue = addQuestionInternal(((KanjiQuestion) question).getKunYomiQuestion()) && returnValue;
                if (kanjiQuestionTypePref.contains("onyomi"))
                    returnValue = addQuestionInternal(((KanjiQuestion) question).getOnYomiQuestion()) && returnValue;
                return returnValue;
            }
            else
                return addQuestionInternal(question);
        }
        return false;
    }

    private boolean addQuestionInternal(Question question)
    {
        // Fetches the percentage of times the user got a question right,
        Float percentage = LogDao.getQuestionPercentage(question.getDatabaseKey(), question.getType());
        if (percentage == null)
            percentage = 0.1f;
        // The 1f is to invert the value so we get the number of times they got it wrong,
        // Times 100f to get the percentage.
        int weight = (int) Math.ceil((1f - percentage) * 100f);
        // Setting weight to never get lower than 2,
        // so any question the user got perfect will still appear in the quiz.
        if (weight < 2)
            weight = 2;
        // if any aspect of the addition fails, the method returns false
        boolean returnValue = add(weight, question);
        QuestionType type = question.getType();
        if ((type == QuestionType.VOCABULARY) || (type == QuestionType.KANJI))
            returnValue = wordAnswerList.add(question.fetchCorrectAnswer()) && returnValue;
        else if ((type == QuestionType.KUN_YOMI) || (type == QuestionType.ON_YOMI))
            returnValue = yomiAnswerList.add(question.fetchCorrectAnswer()) && returnValue;
        else if (type == QuestionType.KANA)
        {
            returnValue = fullKanaAnswerList.add(question.fetchCorrectAnswer()) && returnValue;
            // Storing answers in specialized answer lists for more specialized answer selection
            returnValue = getSpecialList((KanaQuestion) question).add(question.fetchCorrectAnswer()) && returnValue;
        }

        return returnValue;
    }

    private Set<String> getSpecialList(KanaQuestion question)
    {
        if (question.isDiacritic() && question.isDigraph())
            return diacriticDigraphAnswerList;
        else if (question.isDiacritic())
            return diacriticAnswerList;
        else if (question.isDigraph())
            return digraphAnswerList;
        else
            return basicAnswerList;
    }

    public boolean addQuestions(QuestionBank questions)
    {
        weightedAnswerListCache = null;
        previousQuestions = null;
        maxKanaAnswerWeight = -1;
        maxWordAnswerWeight = -1;
        maxYomiAnswerWeight = -1;
        isReset = true;
        fullKanaAnswerList.addAll(questions.fullKanaAnswerList);
        basicAnswerList.addAll(questions.basicAnswerList);
        diacriticAnswerList.addAll(questions.diacriticAnswerList);
        digraphAnswerList.addAll(questions.digraphAnswerList);
        diacriticDigraphAnswerList.addAll(questions.diacriticDigraphAnswerList);
        wordAnswerList.addAll(questions.wordAnswerList);
        yomiAnswerList.addAll(questions.yomiAnswerList);
        return merge(questions);
    }

    private int getMaxKanaAnswerWeight()
    {
        if (maxKanaAnswerWeight < 0)
        {
            maxKanaAnswerWeight = getMaxAnswerWeight(fullKanaAnswerList);
            isReset = false;
        }
        return maxKanaAnswerWeight;
    }

    private int getMaxWordAnswerWeight()
    {
        if (maxWordAnswerWeight < 0)
        {
            maxWordAnswerWeight = getMaxAnswerWeight(wordAnswerList);
            isReset = false;
        }
        return maxWordAnswerWeight;
    }

    private int getMaxYomiAnswerWeight()
    {
        if (maxYomiAnswerWeight < 0)
        {
            maxYomiAnswerWeight = getMaxAnswerWeight(yomiAnswerList);
            isReset = false;
        }
        return maxYomiAnswerWeight;
    }

    private int getMaxAnswerWeight(Set<String> list)
    {
        // Max value is to prevent integer overflow in the weighted answer list. Number is chosen so if every
        // answer had this count (which is actually impossible because of the range restriction, but better to
        // err on the side of caution) the resultant calculations of 2^count would not add up to an integer
        // overflow.
        return (int) Math.floor(Math.log((double) Integer.MAX_VALUE / (list.size() - 1)) / Math.log(2));
    }

    public String[] getPossibleAnswers()
    {
        if (currentPossibleAnswers == null)
        {
            QuestionType type = getCurrentQuestionType();
            if ((type == QuestionType.VOCABULARY) || (type == QuestionType.KANJI))
                currentPossibleAnswers = getPossibleWordAnswers(MAX_MULTIPLE_CHOICE_ANSWERS);
            else if ((type == QuestionType.KUN_YOMI) || (type == QuestionType.ON_YOMI))
                currentPossibleAnswers = getPossibleYomiAnswers(MAX_MULTIPLE_CHOICE_ANSWERS);
            else if (type == QuestionType.KANA)
                currentPossibleAnswers = getPossibleKanaAnswers(MAX_MULTIPLE_CHOICE_ANSWERS);
        }
        return currentPossibleAnswers;
    }


    public String[] getPossibleWordAnswers(int maxChoices)
    {
        if (wordAnswerList.size() <= maxChoices)
            return wordAnswerList.toArray(EMPTY_STRING_ARRAY);
        else
        {
            if (weightedAnswerListCache == null)
            {
                weightedAnswerListCache = new TreeMap<>();
                isReset = false;
            }
            char prefix = LogTypeConversion.toCharFromType(currentQuestion.getType());
            if (!weightedAnswerListCache.containsKey(prefix + currentQuestion.getDatabaseKey()))
            {
                Map<String, Integer> answerCounts = new TreeMap<>();
                for (String answer : wordAnswerList)
                {
                    if (!answer.equals(fetchCorrectAnswer()))
                    {
                        //fetch all data
                        int count = LogDao.getIncorrectAnswerCount(currentQuestion.getDatabaseKey(),
                                getCurrentQuestionType(), answer);
                        answerCounts.put(answer, count);
                    }
                }

                WeightedList<String> weightedAnswerList =
                        generateWeightedAnswerList(answerCounts, getMaxWordAnswerWeight());

                weightedAnswerListCache.put(prefix + currentQuestion.getDatabaseKey(), weightedAnswerList);
            }

            String[] possibleAnswerStrings = weightedAnswerListCache.get(prefix + currentQuestion.getDatabaseKey())
                    .getRandom(new String[maxChoices - 1]);

            possibleAnswerStrings = Arrays.copyOf(possibleAnswerStrings, maxChoices);
            possibleAnswerStrings[maxChoices - 1] = fetchCorrectAnswer();

            Arrays.sort(possibleAnswerStrings);

            return possibleAnswerStrings;
        }
    }

    public String[] getPossibleYomiAnswers(int maxChoices)
    {
        if (yomiAnswerList.size() <= maxChoices)
            return yomiAnswerList.toArray(EMPTY_STRING_ARRAY);
        else
        {
            if (weightedAnswerListCache == null)
            {
                weightedAnswerListCache = new TreeMap<>();
                isReset = false;
            }
            char prefix = LogTypeConversion.toCharFromType(currentQuestion.getType());
            if (!weightedAnswerListCache.containsKey(prefix + currentQuestion.getDatabaseKey()))
            {
                Map<String, Integer> answerCounts = new TreeMap<>();
                for (String answer : yomiAnswerList)
                {
                    if (!answer.equals(fetchCorrectAnswer()))
                    {
                        //fetch all data
                        int count = LogDao.getIncorrectAnswerCount(currentQuestion.getDatabaseKey(),
                                getCurrentQuestionType(), answer);
                        answerCounts.put(answer, count);
                    }
                }

                WeightedList<String> weightedAnswerList =
                        generateWeightedAnswerList(answerCounts, getMaxYomiAnswerWeight());

                weightedAnswerListCache.put(prefix + currentQuestion.getDatabaseKey(), weightedAnswerList);
            }

            String[] possibleAnswerStrings = weightedAnswerListCache.get(prefix + currentQuestion.getDatabaseKey())
                    .getRandom(new String[maxChoices - 1]);

            possibleAnswerStrings = Arrays.copyOf(possibleAnswerStrings, maxChoices);
            possibleAnswerStrings[maxChoices - 1] = fetchCorrectAnswer();

            GojuonOrder.sort(possibleAnswerStrings);

            return possibleAnswerStrings;
        }
    }

    public String[] getPossibleKanaAnswers(int maxChoices)
    {
        if (fullKanaAnswerList.size() <= maxChoices)
            return fullKanaAnswerList.toArray(EMPTY_STRING_ARRAY);
        else
        {
            if (weightedAnswerListCache == null)
            {
                weightedAnswerListCache = new TreeMap<>();
                isReset = false;
            }
            char prefix = LogTypeConversion.toCharFromType(currentQuestion.getType());
            if (!weightedAnswerListCache.containsKey(prefix + currentQuestion.getDatabaseKey()))
            {
                Map<String, Integer> answerCounts = new TreeMap<>();
                for (String answer : fullKanaAnswerList)
                {
                    if (!answer.equals(fetchCorrectAnswer()))
                    {
                        //fetch all data
                        int count = LogDao.getIncorrectAnswerCount(currentQuestion.getDatabaseKey(),
                                getCurrentQuestionType(), answer);
                        if (getSpecialList((KanaQuestion) currentQuestion).contains(answer))
                            count += 2;
                        answerCounts.put(answer, count);
                    }
                }

                WeightedList<String> weightedAnswerList =
                        generateWeightedAnswerList(answerCounts, getMaxKanaAnswerWeight());

                weightedAnswerListCache.put(prefix + currentQuestion.getDatabaseKey(), weightedAnswerList);
            }

            String[] possibleAnswerStrings = weightedAnswerListCache.get(prefix + currentQuestion.getDatabaseKey())
                    .getRandom(new String[maxChoices - 1]);

            possibleAnswerStrings = Arrays.copyOf(possibleAnswerStrings, maxChoices);
            possibleAnswerStrings[maxChoices - 1] = fetchCorrectAnswer();

            GojuonOrder.sort(possibleAnswerStrings);

            return possibleAnswerStrings;
        }
    }

    private static WeightedList<String> generateWeightedAnswerList(Map<String, Integer> answerCounts,
            int maxAnswerWeight)
    {
        int maxCount = 0;
        int minCount = Integer.MAX_VALUE;

        Map<String, Float> newAnswerCount = new TreeMap<>();

        for (String answer : answerCounts.keySet())
        {
            int count = answerCounts.get(answer);
            maxCount = Math.max(maxCount, count);
            minCount = Math.min(minCount, count);
            newAnswerCount.put(answer, (float) count);
        }

        // Because of the inherent properties of the power function used later, subtracting the same amount
        // from each count so the lowest count is zero will not change the relative weight of each item. And
        // doing so will also save the space I need to prevent overflow issues.
        if (minCount > 0)
        {
            maxCount -= minCount;
            Map<String, Float> editedCount = new TreeMap<>();
            for (String answer : newAnswerCount.keySet())
            {
                float newCount = newAnswerCount.get(answer) - minCount;
                editedCount.put(answer, newCount);
            }
            newAnswerCount = editedCount;
        }

        if (maxCount > maxAnswerWeight)
        {
            float controlFactor = (float) maxAnswerWeight / maxCount;
            Map<String, Float> editedCount = new TreeMap<>();
            for (String answer : newAnswerCount.keySet())
            {
                float newCount = newAnswerCount.get(answer) * controlFactor;
                editedCount.put(answer, newCount);
            }
            newAnswerCount = editedCount;
        }

        WeightedList<String> weightedAnswerList = new WeightedList<>();
        for (String answer : newAnswerCount.keySet())
            weightedAnswerList.add(Math.pow(2, newAnswerCount.get(answer)), answer);

        return weightedAnswerList;
    }
}
