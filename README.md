# KanaQuiz

[![GitHub release](https://img.shields.io/github/release/theblackwidower/KanaQuiz.svg)](https://github.com/theblackwidower/KanaQuiz/releases/)
[![GitHub license](https://img.shields.io/github/license/theblackwidower/KanaQuiz.svg)](https://github.com/theblackwidower/KanaQuiz/blob/master/LICENSE)

[![GitHub commits](https://img.shields.io/github/commits-since/theblackwidower/KanaQuiz/latest.svg)](https://github.com/theblackwidower/KanaQuiz/commits/master)
[![GitHub issues](https://img.shields.io/github/issues/theblackwidower/KanaQuiz.svg)](https://github.com/theblackwidower/KanaQuiz/issues/)
[![GitHub commit activity](https://img.shields.io/github/commit-activity/4w/theblackwidower/KanaQuiz.svg)](https://github.com/theblackwidower/KanaQuiz/graphs/commit-activity)

[![Codacy Badge](https://api.codacy.com/project/badge/Grade/b0af5712d54745ada2893d82c55a680b)](https://www.codacy.com/app/theblackwidower/KanaQuiz?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=theblackwidower/KanaQuiz&amp;utm_campaign=Badge_Grade)
[![Build Status](https://semaphoreci.com/api/v1/theblackwidower/kanaquiz/branches/master/shields_badge.svg)](https://semaphoreci.com/theblackwidower/kanaquiz)
[![codecov](https://codecov.io/gh/theblackwidower/KanaQuiz/branch/master/graph/badge.svg)](https://codecov.io/gh/theblackwidower/KanaQuiz)

Are you trying to learn Japanese, but can't seem to remember the pronunciation of the basic Hiragana or Katakana character set?

This application is here to help. It will test your knowledge, and drill the basics into your head. You can select which of the twenty different groups you wish to test yourself on. Allowing you to customize based on your skill level. Because we can't learn all this stuff at once. Records your daily progress, so you can know how much you've improved over time. Also includes a reference screen that can display all kana you're currently working on, just to remind you. 

Open source and ad free.

This application is in a state of constant development. Any additional feature requests can be sent via email, or submitted as an issue here, on GitHub. If they are appropriate, they will likely be added to the next release.

Currently available on [Google Play™](https://play.google.com/store/apps/details?id=com.noprestige.kanaquiz).

## Feature set

  * Supports Hiragana and Katakana character sets
  * Ten different character groupings for each set, allowing a gradual progression through the characters
  * Can answer questions by entering them using the on-screen keyboard, or as multiple choice questions
  * Built-in kana reference sheet
  * Supports both digraphs (ひゃ, みゅ, リュ) and diacritics (が, ぴ)
  * User can select app behaviour on an incorrect answer from three different options: retry until the correct answer is given, show the correct answer and move on, or hide the correct answer and move on
  * Support for the three major romanji systems: Revised Hepburn, Nihon-shiki, and Kunrei-shiki
  * A daily log will show your progress over time
  * Dynamic AI chooses the kana you have the most trouble with

## Upcoming features

_NB: The following features have no planned release date, or guarantee of release._

  * Kanji and vocabulary support
  * Multiple fonts
  * Obsolete kana option
  * Improved multiple choice answer selection

## Code Analysis, Continuous Integration and Test Coverage

| Code Analysis Service | Badge |
| --------------------- |:-----:|
| Code Climate          | [![Maintainability](https://api.codeclimate.com/v1/badges/e0d6b8024f0ffa7682c9/maintainability)](https://codeclimate.com/github/theblackwidower/KanaQuiz/maintainability) |
| Codacy                | [![Codacy Badge](https://api.codacy.com/project/badge/Grade/b0af5712d54745ada2893d82c55a680b)](https://www.codacy.com/app/theblackwidower/KanaQuiz?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=theblackwidower/KanaQuiz&amp;utm_campaign=Badge_Grade) |

| CI Server | Badge&nbsp;with&nbsp;status | My Review |
| --------- |:---------------------------:| --------- |
| Travis CI | [![Build Status](https://travis-ci.org/theblackwidower/KanaQuiz.svg?branch=master)](https://travis-ci.org/theblackwidower/KanaQuiz) | ★★★☆☆<br />Mostly automated. Handles Android pretty well, but emulation support is limited to Android 5.1 (API 22), at the latest. |
| Circle CI | [![CircleCI](https://circleci.com/gh/theblackwidower/KanaQuiz.svg?style=shield)](https://circleci.com/gh/theblackwidower/KanaQuiz) | ★☆☆☆☆<br />Pretty good, but does not handle Android emulation. The only way to run Instrumented Tests is to take advantage of Firebase Test Lab remote testing, which has problems with automated result collecting, unless you're willing to pay for a results bucket. |
| Codeship  | [![Codeship Status for theblackwidower/KanaQuiz](https://app.codeship.com/projects/7db68d50-ff29-0135-aeeb-56b253369268/status?branch=master)](https://app.codeship.com/projects/279635) | ★★★★☆<br />Highly customizable. Build scripts can be written like any Linux Bash script. Even includes a special debug mode, allowing direct access to build environments through SSH, where one can experiment with build commands in the environment they run. However, it currently seems to have a problem with Android emulators, where builds will occasionally fail to install, but work on retry. Don't know what's going on. |
| Semaphore | [![Build Status](https://semaphoreci.com/api/v1/theblackwidower/kanaquiz/branches/master/shields_badge.svg)](https://semaphoreci.com/theblackwidower/kanaquiz) | ★★★★★<br />Highly customizable. Much like Codeship, build scripts can be written like any Linux Bash script, and it's very user friendly. Includes a special 'Launch SSH' feature to allow direct access to build environments through SSH. Also, unlike Codeship, it automatically runs builds on pull requests. |

| Coverage Reporter | Badge&nbsp;with&nbsp;percentage | My Review |
| ----------------- |:-------------------------------:| --------- |
| Coveralls         | [![Coverage Status](https://coveralls.io/repos/github/theblackwidower/KanaQuiz/badge.svg?branch=master)](https://coveralls.io/github/theblackwidower/KanaQuiz?branch=master) | ☆☆☆☆☆<br />Only reports on unit tests, because they will not accept more than one report, or provide utilities for merging. |
| Codecov           | [![codecov](https://codecov.io/gh/theblackwidower/KanaQuiz/branch/master/graph/badge.svg)](https://codecov.io/gh/theblackwidower/KanaQuiz) | ★★★★★<br />Best report interface. Submission utility intuitively collects and submits reports with a single command, and site itself is intuitive and complete. |
| Code Climate      | [![Test Coverage](https://api.codeclimate.com/v1/badges/e0d6b8024f0ffa7682c9/test_coverage)](https://codeclimate.com/github/theblackwidower/KanaQuiz/test_coverage) | ★★★☆☆<br />Bundled with code analysis service. Submission utility can convert reports to JSON, and merge as many JSON reports as you like. |
| Codacy            | [![Codacy Badge](https://api.codacy.com/project/badge/Coverage/b0af5712d54745ada2893d82c55a680b)](https://www.codacy.com/app/theblackwidower/KanaQuiz?utm_source=github.com&utm_medium=referral&utm_content=theblackwidower/KanaQuiz&utm_campaign=Badge_Coverage) | ★★★☆☆<br />Bundled with code analysis service. Submission utility allows you to submit multiple reports, so it's ideal for parallel testing. |

## Translations

This application is currently only available for English language speakers.

If you are multi-lingual, and wish to assist this project by volunteering translation services, you can submit the changes in a pull request, or via email.

If you wish to submit a translation as a pull request, please use Android Studio's built-in Translations Editor. 

If you don't wish to go through the hassle of installing Android Studio, you can use [this strings.xml file](https://gist.github.com/theblackwidower/206876858d2bc5a81f9014267750d8fd) as a template, and place it in a new directory called `/app/src/main/res/values-xx/` where `xx` is the language's locale code. The various string tags contain elements that need to be translated. Be sure to place the name of the language you're translating as well as your own name, in the field marked `translator_credit`. You can also place the URL to your GitHub, Twitter, or other social media profile or personal/professional website in the field marked `translator_credit_url`.

If you don't wish to bother with pull requests, using the template listed above you can also submit your translation through email to [theblackwidower@gmail.com](mailto:theblackwidower@gmail.com?subject=KanaQuiz%20Translation), with the subject "KanaQuiz Translation".

If you require any assistance, please do not be afraid to email me. I appreciate the assistance from anyone wishing to volunteer their skills to help with this project.
