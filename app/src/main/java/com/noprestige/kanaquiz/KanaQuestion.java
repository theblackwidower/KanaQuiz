package com.noprestige.kanaquiz;

class KanaQuestion
{
    private String kana;
    private String[] romanji;

    KanaQuestion(char kana, String romanji)
    {
        setKana(kana);
        setRomanji(romanji);
    }
    KanaQuestion(char kana, String[] romanji)
    {
        setKana(kana);
        setRomanji(romanji);
    }

    private void setRomanji(String romanji)
    {
        this.romanji = new String[] {romanji};
    }
    private void setRomanji(String[] romanji)
    {
        this.romanji = romanji;
    }

    private void setKana(char kana)
    {
        setKana(Character.toString(kana));
    }
    private void setKana(String kana)
    {
        this.kana = kana.trim();
    }

    String getKana()
    {
        return kana;
    }

    boolean checkAnswer(String response)
    {
        boolean isCorrect = false;
        for (String correctAnswer : romanji)
        {
            if (correctAnswer.trim().toLowerCase().equals(response.trim().toLowerCase()))
                isCorrect = true;
        }
        return isCorrect;
    }

    String fetchCorrectAnswer()
    {
        return romanji[0];
    }
}
