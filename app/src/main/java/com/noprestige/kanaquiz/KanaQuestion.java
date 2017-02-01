package com.noprestige.kanaquiz;

class KanaQuestion
{
    private char kana;
    private String romanji;

    KanaQuestion(char kana, String romanji)
    {
        super();
        setKana(kana);
        setRomanji(romanji);
    }

    private void setRomanji(String romanji)
    {
        this.romanji = romanji.trim().toLowerCase();
    }

    private void setKana(char kana)
    {
        this.kana = kana;
    }

    char getKana()
    {
        return kana;
    }

    boolean checkAnswer(String response)
    {
        return (romanji.equals(response.trim().toLowerCase()));
    }
}
