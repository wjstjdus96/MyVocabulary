package com.example.myvocabulary;

public class Dictionary {

    private String id;
    private String word;
    private String meaning;


    public Dictionary(String id, String word, String meaning) {
        this.id = id;
        this.word = word;
        this.meaning = meaning;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
}
