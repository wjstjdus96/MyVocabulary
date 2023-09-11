package com.example.myvocabulary;

public class Word {
    public String word;
    public String meaning;

    public Word() {
        // Default constructor required for calls to DataSnapshot.getValue(Words.class)
    }

    public Word(String word, String meaning) {
        this.word = word;
        this.meaning = meaning;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getMeaning() {
        return meaning;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }

    @Override
    public String toString() {
        return "Words{" +
                "word='" + word + '\'' +
                ", meaning='" + meaning + '\'' +
                '}';
    }

}
