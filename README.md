# KanaQuiz

[![GitHub release](https://img.shields.io/github/release/theblackwidower/KanaQuiz.svg)](https://github.com/theblackwidower/KanaQuiz/releases/)
[![GitHub license](https://img.shields.io/github/license/theblackwidower/KanaQuiz.svg)](https://github.com/theblackwidower/KanaQuiz/blob/master/LICENSE)

[![GitHub issues](https://img.shields.io/github/issues/theblackwidower/KanaQuiz.svg)](https://github.com/theblackwidower/KanaQuiz/issues/)
[![GitHub pull requests](https://img.shields.io/github/issues-pr/theblackwidower/KanaQuiz.svg)](https://github.com/theblackwidower/KanaQuiz/pulls/)
[![GitHub commit activity](https://img.shields.io/github/commit-activity/4w/theblackwidower/KanaQuiz.svg)](https://github.com/theblackwidower/KanaQuiz/graphs/commit-activity)

[![Codacy Badge](https://api.codacy.com/project/badge/Grade/b0af5712d54745ada2893d82c55a680b)](https://www.codacy.com/app/theblackwidower/KanaQuiz?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=theblackwidower/KanaQuiz&amp;utm_campaign=Badge_Grade)
[![Build Status](https://semaphoreci.com/api/v1/theblackwidower/kanaquiz/branches/master/shields_badge.svg)](https://semaphoreci.com/theblackwidower/kanaquiz)
[![codecov](https://codecov.io/gh/theblackwidower/KanaQuiz/branch/master/graph/badge.svg)](https://codecov.io/gh/theblackwidower/KanaQuiz)

Are you trying to learn Japanese, but can't seem to remember the pronunciation of the basic Hiragana or Katakana character set?

This application is here to help. It will test your knowledge, and drill the basics into your head. You can select which of the twenty different groups you wish to test yourself on. Allowing you to customize based on your skill level. Because we can't learn all this stuff at once. Records your daily progress, so you can know how much you've improved over time. Also includes a reference screen that can display all kana you're currently working on, just to remind you. 

Open source and ad free.

This application is in a state of constant development. Any additional feature requests can be sent via email, or submitted as an issue here, on GitHub. If they are appropriate, they will likely be added to the next release.

[<img src="https://f-droid.org/badge/get-it-on.png"
     alt="Get it on F-Droid"
     height="80">](https://f-droid.org/packages/com.noprestige.kanaquiz/)
[<img src="https://play.google.com/intl/en_us/badges/images/generic/en-play-badge.png"
     alt="Get it on Google Play"
     height="80">](https://play.google.com/store/apps/details?id=com.noprestige.kanaquiz)

## Feature set

  * Supports Hiragana and Katakana character sets
  * Ten different character groupings for each set, allowing a gradual progression through the characters
  * Can answer questions by entering them using the on-screen keyboard, or as multiple choice questions
  * Built-in kana reference sheet
  * Supports both digraphs (ひゃ, みゅ, リュ) and diacritics (が, ぴ)
  * User can select app behaviour on an incorrect answer from three different options: retry until the correct answer is given, show the correct answer and move on, or hide the correct answer and move on
  * Support for the three major romaji systems: Revised Hepburn, Nihon-shiki, and Kunrei-shiki
  * A daily log and line graph will show your progress over time
  * Dynamic AI chooses the kana you have the most trouble with
  * Vocabulary support with Kanji option
  * Enhanced multiple choice answer selection

## Upcoming features

_NB: The following features have no planned release date, or guarantee of release._

  * Multiple fonts
  * Obsolete kana option
  * Finer question selection feature
  * User-made question lists
  * Furigana option
  * Export logs and preferences
  * Pronunciation Guide
  * Number questions
  * Reverse questions mode (questions in English, answers in Japanese)
  * Proper kanji support with kanji questions
  * Support for kun'yomi and on'yomi-based kanji questions
  * Limitation alerts, either by time, or number of questions
  * Interface themes of various colours and tones (including a dark theme)

## Code Analysis, Continuous Integration and Test Coverage

| Code Analysis Service | Badge |
| --------------------- |:-----:|
| Code Climate          | [![Maintainability](https://api.codeclimate.com/v1/badges/e0d6b8024f0ffa7682c9/maintainability)](https://codeclimate.com/github/theblackwidower/KanaQuiz/maintainability) |
| Codacy                | [![Codacy Badge](https://api.codacy.com/project/badge/Grade/b0af5712d54745ada2893d82c55a680b)](https://www.codacy.com/app/theblackwidower/KanaQuiz?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=theblackwidower/KanaQuiz&amp;utm_campaign=Badge_Grade) |

| CI Server | Badge&nbsp;with&nbsp;status | My Review |
| --------- |:---------------------------:| --------- |
| Travis CI | master:<br />[![Build Status](https://travis-ci.org/theblackwidower/KanaQuiz.svg?branch=master)](https://travis-ci.org/theblackwidower/KanaQuiz)<br />develop:<br />[![Build Status](https://travis-ci.org/theblackwidower/KanaQuiz.svg?branch=master)](https://travis-ci.org/theblackwidower/KanaQuiz) | ★★★☆☆<br />Mostly automated. Handles Android pretty well, but emulation support is limited to Android 5.1 (API 22), at the latest. |
| Circle CI | [![CircleCI](https://circleci.com/gh/theblackwidower/KanaQuiz.svg?style=shield)](https://circleci.com/gh/theblackwidower/KanaQuiz) | ★☆☆☆☆<br />Pretty good, but does not handle Android emulation. The only way to run Instrumented Tests is to take advantage of Firebase Test Lab remote testing, which has problems with automated result collecting, unless you're willing to pay for a results bucket. |
| Codeship  | master:<br />[![Codeship Status for theblackwidower/KanaQuiz](https://app.codeship.com/projects/7db68d50-ff29-0135-aeeb-56b253369268/status?branch=master)](https://app.codeship.com/projects/279635)<br />develop:<br />[![Codeship Status for theblackwidower/KanaQuiz](https://app.codeship.com/projects/7db68d50-ff29-0135-aeeb-56b253369268/status?branch=develop)](https://app.codeship.com/projects/279635) | ★★★★☆<br />Highly customizable. Build scripts can be written like any Linux Bash script. Even includes a special debug mode, allowing direct access to build environments through SSH, where one can experiment with build commands in the environment they run. However, it currently seems to have a problem with Android emulators, where builds will occasionally fail to install, but work on retry. Don't know what's going on. |
| Semaphore | master:<br />[![Build Status](https://semaphoreci.com/api/v1/theblackwidower/kanaquiz/branches/master/shields_badge.svg)](https://semaphoreci.com/theblackwidower/kanaquiz)<br />develop:<br />[![Build Status](https://semaphoreci.com/api/v1/theblackwidower/kanaquiz/branches/develop/shields_badge.svg)](https://semaphoreci.com/theblackwidower/kanaquiz) | ★★★★★<br />Highly customizable. Much like Codeship, build scripts can be written like any Linux Bash script, and it's very user friendly. Includes a special 'Launch SSH' feature to allow direct access to build environments through SSH. Also, unlike Codeship, it automatically runs builds on pull requests. |

| Coverage Reporter | Badge&nbsp;with&nbsp;percentage | My Review |
| ----------------- |:-------------------------------:| --------- |
| Coveralls         | master:<br />[![Coverage Status](https://coveralls.io/repos/github/theblackwidower/KanaQuiz/badge.svg?branch=master)](https://coveralls.io/github/theblackwidower/KanaQuiz?branch=master)<br />develop:<br />[![Coverage Status](https://coveralls.io/repos/github/theblackwidower/KanaQuiz/badge.svg?branch=develop)](https://coveralls.io/github/theblackwidower/KanaQuiz?branch=develop) | ☆☆☆☆☆<br />Only reports on unit tests, because they will not accept more than one report, or provide utilities for merging. |
| Codecov           | master:<br />[![codecov](https://codecov.io/gh/theblackwidower/KanaQuiz/branch/master/graph/badge.svg)](https://codecov.io/gh/theblackwidower/KanaQuiz)<br />develop:<br />[![codecov](https://codecov.io/gh/theblackwidower/KanaQuiz/branch/develop/graph/badge.svg)](https://codecov.io/gh/theblackwidower/KanaQuiz) | ★★★★★<br />Best report interface. Submission utility intuitively collects and submits reports with a single command, and site itself is intuitive and complete. |
| Code Climate      | [![Test Coverage](https://api.codeclimate.com/v1/badges/e0d6b8024f0ffa7682c9/test_coverage)](https://codeclimate.com/github/theblackwidower/KanaQuiz/test_coverage) | ★★★☆☆<br />Bundled with code analysis service. Submission utility can convert reports to JSON, and merge as many JSON reports as you like. |
| Codacy            | [![Codacy Badge](https://api.codacy.com/project/badge/Coverage/b0af5712d54745ada2893d82c55a680b)](https://www.codacy.com/app/theblackwidower/KanaQuiz?utm_source=github.com&utm_medium=referral&utm_content=theblackwidower/KanaQuiz&utm_campaign=Badge_Coverage) | ★★★☆☆<br />Bundled with code analysis service. Submission utility allows you to submit multiple reports, so it's ideal for parallel testing. |

## Branching Model

I'm using the `develop` branch for main development and experimentation, as well as readme and privacy policy updates; and the `master` branch for releases.

Mainly, the branching model I'm currently experimenting with is inspired by [this blog post](https://nvie.com/posts/a-successful-git-branching-model/). But I am altering it for my own purposes.

## Translations

This application is currently only available in English, Spanish, and Catalan.

If you are multi-lingual, and wish to assist this project by volunteering translation services, you can submit the changes in a pull request, or via email.

If you wish to submit a translation as a pull request, please use Android Studio's built-in Translations Editor. 

If you don't wish to go through the hassle of installing Android Studio, you can use [this strings.xml file](https://gist.github.com/theblackwidower/206876858d2bc5a81f9014267750d8fd) as a template, and place it in a new directory called `/app/src/main/res/values-[language code]/` with the [ISO 639-1 language code](https://en.wikipedia.org/wiki/List_of_ISO_639-1_codes). The various string tags contain elements that need to be translated. Be sure to place the name of the language you're translating as well as your own name, in the field marked `translator_credit`. You can also place the URL to your GitHub, Twitter, or other social media profile or personal/professional website in the field marked `translator_credit_url`.

If you don't wish to bother with pull requests, using the template listed above you can also submit your translation through email to [theblackwidower@gmail.com](mailto:theblackwidower@gmail.com?subject=KanaQuiz%20Translation), with the subject "KanaQuiz Translation".

I'll also ask, if possible, for any translators to also translate the vocabulary and kanji files, and insert them into a language-specific directory (`app/src/main/res/xml-[language code]/`), so they'll be automatically included as part of the translation package.

The vocabulary file ([vocabulary.xml](https://github.com/theblackwidower/KanaQuiz/blob/develop/app/src/main/res/xml/vocabulary.xml)) just needs to have it's English answers replaced.

The kanji files however, are a bit more complex. There are many more kanji to tranlate, and all three can just be translated and copied over. However, if you do not wish to translate all three files ([kanji_1.xml](https://github.com/theblackwidower/KanaQuiz/blob/develop/app/src/main/res/xml/kanji_1.xml), [kanji_2.xml](https://github.com/theblackwidower/KanaQuiz/blob/develop/app/src/main/res/xml/kanji_2.xml), [kanji_3.xml](https://github.com/theblackwidower/KanaQuiz/blob/develop/app/src/main/res/xml/kanji_3.xml)), just take the base kanji file ([kanji.xml](https://github.com/theblackwidower/KanaQuiz/blob/develop/app/src/main/res/xml/kanji.xml)) and remove the QuestionFile elements of the files you do not wish to translate. All together, these files should be copied into the new folder.

If you require any assistance, please do not be afraid to email me. I appreciate the assistance from anyone wishing to volunteer their skills to help with this project.

## Themes

I'm open to people offering to design new themes. If you wish to do so, there are several key files to be aware of.

The themes themselves need to be designed in the styles.xml files (`app/src/main/res/values-v21/styles.xml` and `app/src/main/res/values/styles.xml`). Be sure to add any new colours you use to the colors.xml file. (`app/src/main/res/values/colors.xml`)

Add an appropriate title, and related prefId to the appropriate section in `app/src/main/res/values/strings.xml`, and link to those string resources when you add the theme to the chooser in `app/src/main/res/layout/theme_chooser_dialog.xml`.

Finally, to make the whole thing work, be sure to add your theme to the if-else chain in the getThemeId method in `app/src/main/java/com/noprestige/kanaquiz/themes/ThemeManager.java`.

You can submit the finished product via pull request.
