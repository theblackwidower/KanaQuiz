package com.noprestige.kanaquiz;

public final class RomajiConversion
{
    private RomajiConversion() {}

    @SuppressWarnings("AssignmentToForLoopParameter")
    static String convertKana(String kana)
    {
        StringBuilder romaji = new StringBuilder();

        //prevent StringIndexOutOfBoundsException
        kana += '\u0000';

        for (int i = 0; i < kana.length(); i++)
            switch (kana.charAt(i))
            {
                case '\u0000':
                    i++;
                    break;
                case 'ッ':
                case 'っ':
                case 'ー':
                    romaji.append(kana.charAt(i));
                    break;
                case 'あ':
                case 'ア':
                    romaji.append('a');
                    break;
                case 'い':
                    romaji.append('i');
                    break;
                case 'イ':
                    i++;
                    if (kana.charAt(i) == 'ィ')
                        romaji.append("yi");
                    else if (kana.charAt(i) == 'ェ')
                        romaji.append("ye");
                    else
                    {
                        i--;
                        romaji.append('i');
                    }
                    break;
                case 'う':
                    romaji.append('u');
                    break;
                case 'ウ':
                    i++;
                    if (kana.charAt(i) == 'ァ')
                        romaji.append("wa");
                    else if (kana.charAt(i) == 'ィ')
                        romaji.append("wi");
                    else if (kana.charAt(i) == 'ゥ')
                        romaji.append("wu");
                    else if (kana.charAt(i) == 'ェ')
                        romaji.append("we");
                    else if (kana.charAt(i) == 'ォ')
                        romaji.append("wo");
                    else
                    {
                        i--;
                        romaji.append('u');
                    }
                    break;
                case 'ヴ':
                    i++;
                    if (kana.charAt(i) == 'ァ')
                        romaji.append("va");
                    else if (kana.charAt(i) == 'ィ')
                        if (kana.charAt(i + 1) == 'ェ')
                        {
                            i++;
                            romaji.append("vye");
                        }
                        else
                            romaji.append("vi");
                    else if (kana.charAt(i) == 'ェ')
                        romaji.append("ve");
                    else if (kana.charAt(i) == 'ォ')
                        romaji.append("vo");
                    else
                    {
                        i--;
                        romaji.append("vu");
                    }
                    break;
                case 'え':
                case 'エ':
                    romaji.append('e');
                    break;
                case 'お':
                case 'オ':
                    romaji.append('o');
                    break;
                case 'が':
                case 'ガ':
                    romaji.append("ga");
                    break;
                case 'か':
                case 'カ':
                    romaji.append("ka");
                    break;
                case 'ぎ':
                    i++;
                    if (kana.charAt(i) == 'ゃ')
                        romaji.append("gya");
                    else if (kana.charAt(i) == 'ゅ')
                        romaji.append("gyu");
                    else if (kana.charAt(i) == 'ょ')
                        romaji.append("gyo");
                    else
                    {
                        i--;
                        romaji.append("gi");
                    }
                    break;
                case 'ギ':
                    i++;
                    if (kana.charAt(i) == 'ェ')
                        romaji.append("gye");
                    else if (kana.charAt(i) == 'ャ')
                        romaji.append("gya");
                    else if (kana.charAt(i) == 'ュ')
                        romaji.append("gyu");
                    else if (kana.charAt(i) == 'ョ')
                        romaji.append("gyo");
                    else
                    {
                        i--;
                        romaji.append("gi");
                    }
                    break;
                case 'き':
                    i++;
                    if (kana.charAt(i) == 'ゃ')
                        romaji.append("kya");
                    else if (kana.charAt(i) == 'ゅ')
                        romaji.append("kyu");
                    else if (kana.charAt(i) == 'ょ')
                        romaji.append("kyo");
                    else
                    {
                        i--;
                        romaji.append("ki");
                    }
                    break;
                case 'キ':
                    i++;
                    if (kana.charAt(i) == 'ェ')
                        romaji.append("kye");
                    else if (kana.charAt(i) == 'ャ')
                        romaji.append("kya");
                    else if (kana.charAt(i) == 'ュ')
                        romaji.append("kyu");
                    else if (kana.charAt(i) == 'ョ')
                        romaji.append("kyo");
                    else
                    {
                        i--;
                        romaji.append("ki");
                    }
                    break;
                case 'ぐ':
                    romaji.append("gu");
                    break;
                case 'グ':
                    i++;
                    if (kana.charAt(i) == 'ァ')
                        romaji.append("gwa");
                    else if (kana.charAt(i) == 'ィ')
                        romaji.append("gwi");
                    else if (kana.charAt(i) == 'ェ')
                        romaji.append("gwe");
                    else if (kana.charAt(i) == 'ォ')
                        romaji.append("gwo");
                    else
                    {
                        i--;
                        romaji.append("gu");
                    }
                    break;
                case 'く':
                    romaji.append("ku");
                    break;
                case 'ク':
                    i++;
                    if (kana.charAt(i) == 'ァ')
                        romaji.append("kwa");
                    else if (kana.charAt(i) == 'ィ')
                        romaji.append("kwi");
                    else if (kana.charAt(i) == 'ェ')
                        romaji.append("kwe");
                    else if (kana.charAt(i) == 'ォ')
                        romaji.append("kwo");
                    else
                    {
                        i--;
                        romaji.append("ku");
                    }
                    break;
                case 'げ':
                case 'ゲ':
                    romaji.append("ge");
                    break;
                case 'け':
                case 'ケ':
                    romaji.append("ke");
                    break;
                case 'ご':
                case 'ゴ':
                    romaji.append("go");
                    break;
                case 'こ':
                case 'コ':
                    romaji.append("ko");
                    break;
                case 'さ':
                case 'サ':
                    romaji.append("sa");
                    break;
                case 'ざ':
                case 'ザ':
                    romaji.append("za");
                    break;
                case 'じ':
                    i++;
                    if (kana.charAt(i) == 'ゃ')
                        romaji.append("ja");
                    else if (kana.charAt(i) == 'ゅ')
                        romaji.append("ju");
                    else if (kana.charAt(i) == 'ょ')
                        romaji.append("jo");
                    else
                    {
                        i--;
                        romaji.append("ji");
                    }
                    break;
                case 'ジ':
                    i++;
                    if (kana.charAt(i) == 'ェ')
                        romaji.append("je");
                    else if (kana.charAt(i) == 'ャ')
                        romaji.append("ja");
                    else if (kana.charAt(i) == 'ュ')
                        romaji.append("ju");
                    else if (kana.charAt(i) == 'ョ')
                        romaji.append("jo");
                    else
                    {
                        i--;
                        romaji.append("ji");
                    }
                    break;
                case 'し':
                    i++;
                    if (kana.charAt(i) == 'ゃ')
                        romaji.append("sha");
                    else if (kana.charAt(i) == 'ゅ')
                        romaji.append("shu");
                    else if (kana.charAt(i) == 'ょ')
                        romaji.append("sho");
                    else
                    {
                        i--;
                        romaji.append("shi");
                    }
                    break;
                case 'シ':
                    i++;
                    if (kana.charAt(i) == 'ェ')
                        romaji.append("she");
                    else if (kana.charAt(i) == 'ャ')
                        romaji.append("sha");
                    else if (kana.charAt(i) == 'ュ')
                        romaji.append("shu");
                    else if (kana.charAt(i) == 'ョ')
                        romaji.append("sho");
                    else
                    {
                        i--;
                        romaji.append("shi");
                    }
                    break;
                case 'す':
                    romaji.append("su");
                    break;
                case 'ス':
                    i++;
                    if (kana.charAt(i) == 'ィ')
                        romaji.append("si");
                    else
                    {
                        i--;
                        romaji.append("su");
                    }
                    break;
                case 'ず':
                    romaji.append("zu");
                    break;
                case 'ズ':
                    i++;
                    if (kana.charAt(i) == 'ィ')
                        romaji.append("zi");
                    else
                    {
                        i--;
                        romaji.append("zu");
                    }
                    break;
                case 'せ':
                case 'セ':
                    romaji.append("se");
                    break;
                case 'ぜ':
                case 'ゼ':
                    romaji.append("ze");
                    break;
                case 'そ':
                case 'ソ':
                    romaji.append("so");
                    break;
                case 'ぞ':
                case 'ゾ':
                    romaji.append("zo");
                    break;
                case 'だ':
                case 'ダ':
                    romaji.append("da");
                    break;
                case 'た':
                case 'タ':
                    romaji.append("ta");
                    break;
                case 'ち':
                    i++;
                    if (kana.charAt(i) == 'ゃ')
                        romaji.append("cha");
                    else if (kana.charAt(i) == 'ゅ')
                        romaji.append("chu");
                    else if (kana.charAt(i) == 'ょ')
                        romaji.append("cho");
                    else
                    {
                        i--;
                        romaji.append("chi");
                    }
                    break;
                case 'チ':
                    i++;
                    if (kana.charAt(i) == 'ェ')
                        romaji.append("che");
                    else if (kana.charAt(i) == 'ャ')
                        romaji.append("cha");
                    else if (kana.charAt(i) == 'ュ')
                        romaji.append("chu");
                    else if (kana.charAt(i) == 'ョ')
                        romaji.append("cho");
                    else
                    {
                        i--;
                        romaji.append("chi");
                    }
                    break;
                case 'ぢ':
                    i++;
                    if (kana.charAt(i) == 'ゃ')
                        romaji.append("ja");
                    else if (kana.charAt(i) == 'ゅ')
                        romaji.append("ju");
                    else if (kana.charAt(i) == 'ょ')
                        romaji.append("jo");
                    else
                    {
                        i--;
                        romaji.append("ji");
                    }
                    break;
                case 'ヂ':
                    i++;
                    if (kana.charAt(i) == 'ィ')
                        romaji.append("di");
                    else if (kana.charAt(i) == 'ャ')
                        romaji.append("ja");
                    else if (kana.charAt(i) == 'ュ')
                        romaji.append("ju");
                    else if (kana.charAt(i) == 'ョ')
                        romaji.append("jo");
                    else
                    {
                        i--;
                        romaji.append("ji");
                    }
                    break;
                case 'つ':
                    romaji.append("tsu");
                    break;
                case 'ツ':
                    i++;
                    if (kana.charAt(i) == 'ァ')
                        romaji.append("tsa");
                    else if (kana.charAt(i) == 'ィ')
                        romaji.append("tsi");
                    else if (kana.charAt(i) == 'ェ')
                        romaji.append("tse");
                    else if (kana.charAt(i) == 'ォ')
                        romaji.append("tso");
                    else
                    {
                        i--;
                        romaji.append("tsu");
                    }
                    break;
                case 'づ':
                case 'ヅ':
                    romaji.append("zu");
                    break;
                case 'で':
                    romaji.append("de");
                    break;
                case 'デ':
                    i++;
                    if (kana.charAt(i) == 'ィ')
                        romaji.append("di");
                    else if ((kana.charAt(i) == 'ゥ') || (kana.charAt(i) == 'ュ'))
                        romaji.append("du");
                    else
                    {
                        i--;
                        romaji.append("de");
                    }
                    break;
                case 'て':
                    romaji.append("te");
                    break;
                case 'テ':
                    i++;
                    if (kana.charAt(i) == 'ィ')
                        romaji.append("ti");
                    else if ((kana.charAt(i) == 'ゥ') || (kana.charAt(i) == 'ュ'))
                        romaji.append("tu");
                    else
                    {
                        i--;
                        romaji.append("te");
                    }
                    break;
                case 'ど':
                    romaji.append("do");
                    break;
                case 'ド':
                    if (kana.charAt(i + 1) == 'ゥ')
                        i++;
                    romaji.append("do");
                    break;
                case 'と':
                    romaji.append("to");
                    break;
                case 'ト':
                    if (kana.charAt(i + 1) == 'ゥ')
                        i++;
                    romaji.append("to");
                    break;
                case 'な':
                case 'ナ':
                    romaji.append("na");
                    break;
                case 'に':
                    i++;
                    if (kana.charAt(i) == 'ゃ')
                        romaji.append("nya");
                    else if (kana.charAt(i) == 'ゅ')
                        romaji.append("nyu");
                    else if (kana.charAt(i) == 'ょ')
                        romaji.append("nyo");
                    else
                    {
                        i--;
                        romaji.append("ni");
                    }
                    break;
                case 'ニ':
                    i++;
                    if (kana.charAt(i) == 'ェ')
                        romaji.append("nye");
                    else if (kana.charAt(i) == 'ャ')
                        romaji.append("nya");
                    else if (kana.charAt(i) == 'ュ')
                        romaji.append("nyu");
                    else if (kana.charAt(i) == 'ョ')
                        romaji.append("nyo");
                    else
                    {
                        i--;
                        romaji.append("ni");
                    }
                    break;
                case 'ぬ':
                case 'ヌ':
                    romaji.append("nu");
                    break;
                case 'ね':
                case 'ネ':
                    romaji.append("ne");
                    break;
                case 'の':
                case 'ノ':
                    romaji.append("no");
                    break;
                case 'ば':
                case 'バ':
                    romaji.append("ba");
                    break;
                case 'は':
                case 'ハ':
                    romaji.append("ha");
                    break;
                case 'ぱ':
                case 'パ':
                    romaji.append("pa");
                    break;
                case 'び':
                    i++;
                    if (kana.charAt(i) == 'ゃ')
                        romaji.append("bya");
                    else if (kana.charAt(i) == 'ゅ')
                        romaji.append("byu");
                    else if (kana.charAt(i) == 'ょ')
                        romaji.append("byo");
                    else
                    {
                        i--;
                        romaji.append("bi");
                    }
                    break;
                case 'ビ':
                    i++;
                    if (kana.charAt(i) == 'ェ')
                        romaji.append("bye");
                    else if (kana.charAt(i) == 'ャ')
                        romaji.append("bya");
                    else if (kana.charAt(i) == 'ュ')
                        romaji.append("byu");
                    else if (kana.charAt(i) == 'ョ')
                        romaji.append("byo");
                    else
                    {
                        i--;
                        romaji.append("bi");
                    }
                    break;
                case 'ひ':
                    i++;
                    if (kana.charAt(i) == 'ゃ')
                        romaji.append("hya");
                    else if (kana.charAt(i) == 'ゅ')
                        romaji.append("hyu");
                    else if (kana.charAt(i) == 'ょ')
                        romaji.append("hyo");
                    else
                    {
                        i--;
                        romaji.append("hi");
                    }
                    break;
                case 'ヒ':
                    i++;
                    if (kana.charAt(i) == 'ェ')
                        romaji.append("hye");
                    else if (kana.charAt(i) == 'ャ')
                        romaji.append("hya");
                    else if (kana.charAt(i) == 'ュ')
                        romaji.append("hyu");
                    else if (kana.charAt(i) == 'ョ')
                        romaji.append("hyo");
                    else
                    {
                        i--;
                        romaji.append("hi");
                    }
                    break;
                case 'ぴ':
                    i++;
                    if (kana.charAt(i) == 'ゃ')
                        romaji.append("pya");
                    else if (kana.charAt(i) == 'ゅ')
                        romaji.append("pyu");
                    else if (kana.charAt(i) == 'ょ')
                        romaji.append("pyo");
                    else
                    {
                        i--;
                        romaji.append("pi");
                    }
                    break;
                case 'ピ':
                    i++;
                    if (kana.charAt(i) == 'ェ')
                        romaji.append("pye");
                    else if (kana.charAt(i) == 'ャ')
                        romaji.append("pya");
                    else if (kana.charAt(i) == 'ュ')
                        romaji.append("pyu");
                    else if (kana.charAt(i) == 'ョ')
                        romaji.append("pyo");
                    else
                    {
                        i--;
                        romaji.append("pi");
                    }
                    break;
                case 'ぶ':
                case 'ブ':
                    romaji.append("bu");
                    break;
                case 'ふ':
                    romaji.append("fu");
                    break;
                case 'フ':
                    i++;
                    if (kana.charAt(i) == 'ァ')
                        romaji.append("fa");
                    else if (kana.charAt(i) == 'ィ')
                        if (kana.charAt(i + 1) == 'ェ')
                        {
                            i++;
                            romaji.append("fye");
                        }
                        else
                            romaji.append("fi");
                    else if (kana.charAt(i) == 'ェ')
                        romaji.append("fe");
                    else if (kana.charAt(i) == 'ォ')
                        romaji.append("fo");
                    else
                    {
                        i--;
                        romaji.append("fu");
                    }
                    break;
                case 'ぷ':
                case 'プ':
                    romaji.append("pu");
                    break;
                case 'べ':
                case 'ベ':
                    romaji.append("be");
                    break;
                case 'へ':
                case 'ヘ':
                    romaji.append("he");
                    break;
                case 'ぺ':
                case 'ペ':
                    romaji.append("pe");
                    break;
                case 'ぼ':
                case 'ボ':
                    romaji.append("bo");
                    break;
                case 'ほ':
                    romaji.append("ho");
                    break;
                case 'ホ':
                    i++;
                    if (kana.charAt(i) == 'ゥ')
                        romaji.append("hu");
                    else
                    {
                        i--;
                        romaji.append("ho");
                    }
                    break;
                case 'ぽ':
                case 'ポ':
                    romaji.append("po");
                    break;
                case 'ま':
                case 'マ':
                    romaji.append("ma");
                    break;
                case 'み':
                    i++;
                    if (kana.charAt(i) == 'ゃ')
                        romaji.append("mya");
                    else if (kana.charAt(i) == 'ゅ')
                        romaji.append("myu");
                    else if (kana.charAt(i) == 'ょ')
                        romaji.append("myo");
                    else
                    {
                        i--;
                        romaji.append("mi");
                    }
                    break;
                case 'ミ':
                    i++;
                    if (kana.charAt(i) == 'ェ')
                        romaji.append("mye");
                    else if (kana.charAt(i) == 'ャ')
                        romaji.append("mya");
                    else if (kana.charAt(i) == 'ュ')
                        romaji.append("myu");
                    else if (kana.charAt(i) == 'ョ')
                        romaji.append("myo");
                    else
                    {
                        i--;
                        romaji.append("mi");
                    }
                    break;
                case 'む':
                case 'ム':
                    romaji.append("mu");
                    break;
                case 'め':
                case 'メ':
                    romaji.append("me");
                    break;
                case 'も':
                case 'モ':
                    romaji.append("mo");
                    break;
                case 'や':
                case 'ヤ':
                    romaji.append("ya");
                    break;
                case 'ゆ':
                case 'ユ':
                    romaji.append("yu");
                    break;
                case 'よ':
                case 'ヨ':
                    romaji.append("yo");
                    break;
                case 'ラ':
                    i++;
                    if (kana.charAt(i) == '゚')
                        romaji.append("la");
                    else
                    {
                        i--;
                        romaji.append("ra");
                    }
                    break;
                case 'ら':
                    romaji.append("ra");
                    break;
                case 'リ':
                    i++;
                    if (kana.charAt(i) == '゚')
                        romaji.append("li");
                    else if (kana.charAt(i) == 'ェ')
                        romaji.append("rye");
                    else if (kana.charAt(i) == 'ャ')
                        romaji.append("rya");
                    else if (kana.charAt(i) == 'ュ')
                        romaji.append("ryu");
                    else if (kana.charAt(i) == 'ョ')
                        romaji.append("ryo");
                    else
                    {
                        i--;
                        romaji.append("ri");
                    }
                    break;
                case 'り':
                    i++;
                    if (kana.charAt(i) == 'ゃ')
                        romaji.append("rya");
                    else if (kana.charAt(i) == 'ゅ')
                        romaji.append("ryu");
                    else if (kana.charAt(i) == 'ょ')
                        romaji.append("ryo");
                    else
                    {
                        i--;
                        romaji.append("ri");
                    }
                    break;
                case 'ル':
                    i++;
                    if (kana.charAt(i) == '゚')
                        romaji.append("lu");
                    else
                    {
                        i--;
                        romaji.append("ru");
                    }
                    break;
                case 'る':
                    romaji.append("ru");
                    break;
                case 'レ':
                    i++;
                    if (kana.charAt(i) == '゚')
                        romaji.append("le");
                    else
                    {
                        i--;
                        romaji.append("re");
                    }
                    break;
                case 'れ':
                    romaji.append("re");
                    break;
                case 'ロ':
                    i++;
                    if (kana.charAt(i) == '゚')
                        romaji.append("lo");
                    else
                    {
                        i--;
                        romaji.append("ro");
                    }
                    break;
                case 'ろ':
                    romaji.append("ro");
                    break;
                case 'わ':
                case 'ワ':
                    romaji.append("wa");
                    break;
                case 'ゐ':
                case 'ヰ':
                    romaji.append("wi");
                    break;
                case 'ゑ':
                case 'ヱ':
                    romaji.append("we");
                    break;
                case 'を':
                case 'ヲ':
                    romaji.append("wo");
                    break;
                case 'ん':
                case 'ン':
                    romaji.append("n");
                    break;
            }

        for (int i = 0; i < romaji.length(); i++)
        {
            switch (romaji.charAt(i))
            {
                case 'ッ':
                    if (((i + 1) < romaji.length()) &&
                            ((romaji.charAt(i + 1) == 'd') || (romaji.charAt(i + 1) == 'g') ||
                                    (romaji.charAt(i + 1) == 'f')))
                        romaji.setCharAt(i, romaji.charAt(i + 1));
                    //fallthrough intentional to prevent repeating code
                case 'っ':
                    if (((i + 1) < romaji.length()) &&
                            ((romaji.charAt(i + 1) == 'p') || (romaji.charAt(i + 1) == 't') ||
                                    (romaji.charAt(i + 1) == 'k') || (romaji.charAt(i + 1) == 's')))
                        romaji.setCharAt(i, romaji.charAt(i + 1));
                    break;
                case 'ー':
                    if ((i > 0) && ((romaji.charAt(i - 1) == 'a') || (romaji.charAt(i - 1) == 'i') ||
                            (romaji.charAt(i - 1) == 'u') || (romaji.charAt(i - 1) == 'e') ||
                            (romaji.charAt(i - 1) == 'o')))
                        romaji.setCharAt(i, romaji.charAt(i - 1));
            }
        }

        return romaji.toString();
    }
}
