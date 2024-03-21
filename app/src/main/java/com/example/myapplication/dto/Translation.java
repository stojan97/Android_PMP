package com.example.myapplication.dto;

import android.view.View;

import com.example.myapplication.activitiy.Language;

/**
 * Translation DTO used for storing id and text of the Tags.
 */
public class Translation {
    private final int id;

    private String englishWord;

    private String macedonianWord;

    /**
     * Constructor.
     * Creates translation in memory from the dictionary by generating new id's .
     *
     * @param englishWord    the english translation
     * @param macedonianWord the macedonian translation
     */
    public Translation(String englishWord, String macedonianWord) {
        this.id = View.generateViewId();
        this.englishWord = englishWord;
        this.macedonianWord = macedonianWord;
    }

    /**
     * Gets the translation for the specified language.
     *
     * @param language the language
     * @return the relevant translation
     */
    public String getTranslationForLanguage(Language language) {
        return language == Language.ENGLISH ? englishWord : macedonianWord;
    }

    /**
     * Gets the inverse translation for the specified language.
     *
     * @param language the language
     * @return the inverse translation
     */
    public String getInverseTranslationForLanguage(Language language) {
        return language == Language.ENGLISH ? macedonianWord : englishWord;
    }

    public int getId() {
        return id;
    }

    public String getEnglishWord() {
        return englishWord;
    }

    public String getMacedonianWord() {
        return macedonianWord;
    }

    public void setEnglishWord(String englishWord) {
        this.englishWord = englishWord;
    }

    public void setMacedonianWord(String macedonianWord) {
        this.macedonianWord = macedonianWord;
    }


}
