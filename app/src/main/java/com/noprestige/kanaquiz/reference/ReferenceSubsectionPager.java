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

package com.noprestige.kanaquiz.reference;

import android.content.Context;

import com.noprestige.kanaquiz.R;
import com.noprestige.kanaquiz.options.OptionsControl;
import com.noprestige.kanaquiz.questions.QuestionManagement;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

class ReferenceSubsectionPager extends FragmentStateAdapter
{
    private int questionTypeRef;
    private List<Integer> tabList;
    Context context;

    private static List<String> pageIds;

    ReferenceSubsectionPager(Fragment refPage, int questionTypeRef)
    {
        //ref: https://www.reddit.com/r/androiddev/comments/kqz3sg/error_fragmentmanager_is_already_executing/gi6ipn4
        super(refPage);
        this.questionTypeRef = questionTypeRef;
        context = refPage.getContext();

        tabList = new ArrayList<>(3);

        if (questionTypeRef == R.string.vocabulary)
        {
            boolean isFullReference = OptionsControl.getBoolean(R.string.prefid_full_reference);
            for (int i = 1; i <= QuestionManagement.getVocabulary().getCategoryCount(); i++)
                if (isFullReference || QuestionManagement.getVocabulary().getPref(i))
                    tabList.add(i);
        }
        else if (questionTypeRef == R.string.kanji)
        {
            boolean isFullReference = OptionsControl.getBoolean(R.string.prefid_full_reference);
            for (int i = 0; i < QuestionManagement.getKanjiFileCount(); i++)
                if (isFullReference || QuestionManagement.getKanji(i).anySelected())
                    tabList.add(i);
        }
        else if (OptionsControl.getBoolean(R.string.prefid_full_reference))
        {
            tabList.add(R.string.base_form_title);
            tabList.add(R.string.diacritics_title);
            tabList.add(R.string.digraphs_title);
            if (questionTypeRef == R.string.katakana)
                tabList.add(R.string.extended_katakana_title);
        }
        else
        {
            QuestionManagement questions;

            if (questionTypeRef == R.string.hiragana)
                questions = QuestionManagement.getHiragana();
            else if (questionTypeRef == R.string.katakana)
                questions = QuestionManagement.getKatakana();
            else
                throw new IllegalArgumentException("questionTypeRef '" + questionTypeRef + "' is invalid.");

            if (questions.anyMainKanaSelected())
                tabList.add(R.string.base_form_title);
            if (questions.diacriticsSelected())
                tabList.add(R.string.diacritics_title);
            if (questions.digraphsSelected())
                tabList.add(R.string.digraphs_title);
            if (questions.extendedKatakanaSelected())
                tabList.add(R.string.extended_katakana_title);
        }
    }

    @Override
    public Fragment createFragment(int position)
    {
        if (questionTypeRef == R.string.vocabulary)
            return ReferenceSubsectionVocab
                    .newInstance(QuestionManagement.getVocabulary().getPrefId(tabList.get(position)));
        else
            return ReferenceSubsectionPage.newInstance(questionTypeRef, tabList.get(position));
    }

    private static final int KANJI_LOCALE_SHIFT = Integer.SIZE; // = Long.SIZE / 2

    @Override
    public long getItemId(int position)
    {
        if (questionTypeRef == R.string.vocabulary)
        {
            if (pageIds == null)
                pageIds = new ArrayList<>();
            String prefId = QuestionManagement.getVocabulary().getPrefId(tabList.get(position));
            if (!pageIds.contains(prefId))
                pageIds.add(prefId);
            return pageIds.indexOf(prefId);
        }
        else if (questionTypeRef == R.string.kanji)
            //should clear out all pages if locale changes
            return ((long) Locale.getDefault().hashCode() << KANJI_LOCALE_SHIFT) + tabList.get(position);
        else
            return tabList.get(position);
    }

    // should result in a binary value of 32 ones and 32 zeroes
    private static final long KANJI_LOCALE_MASK = -(1L << KANJI_LOCALE_SHIFT);

    //ref: https://stackoverflow.com/a/57691487/3582371
    @Override
    public boolean containsItem(long itemId)
    {
        if (questionTypeRef == R.string.vocabulary)
            if (itemId < pageIds.size())
            {
                int setNumber = QuestionManagement.getVocabulary().getSetNumber(pageIds.get((int) itemId));
                if (setNumber == 0)
                    return false;
                else
                    return tabList.contains(setNumber);
            }
            else
                return false;
        else if (questionTypeRef == R.string.kanji)
            if ((itemId & KANJI_LOCALE_MASK) == ((long) Locale.getDefault().hashCode() << KANJI_LOCALE_SHIFT))
                return tabList.contains((int) (itemId & ~KANJI_LOCALE_MASK));
            else
                return false;
        else
            return tabList.contains((int) itemId);
    }

    @Override
    public int getItemCount()
    {
        return tabList.size();
    }

    public CharSequence getPageTitle(int position)
    {
        if (questionTypeRef == R.string.vocabulary)
            return QuestionManagement.getVocabulary().getSetTitle(tabList.get(position)).toString();
        else if (questionTypeRef == R.string.kanji)
            return QuestionManagement.getKanjiTitle(tabList.get(position));
        else
            return context.getResources().getString(tabList.get(position));
    }
}
