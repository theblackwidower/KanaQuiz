package com.noprestige.kanaquiz;

class KanaQuestion
{
    private String kana;
    private String romanji;

    KanaQuestion(char kana, String romanji)
    {
        setKana(kana);
        setRomanji(romanji);
    }

    private void setRomanji(String romanji)
    {
        this.romanji = romanji.trim().toLowerCase();
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
        return (romanji.equals(response.trim().toLowerCase()));
    }

    String fetchCorrectAnswer()
    {
        return romanji;
    }
}
