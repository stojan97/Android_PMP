package com.example.myapplication.service;

import com.example.myapplication.dto.Translation;

import java.util.List;


/**
 * Dictionary service that provides logic for retrieving and updating the dictionary
 * from Internal Storage.
 */
public interface DictionaryService {

    /**
     * Get all dictionary translations from internal storage.
     *
     * @return the translations
     */
    List<Translation> getDictionaryTranslations();

    /**
     * Updates the translations in the internal storage.
     *
     * @param translations the update translations
     */
    void updateDictionaryTranslations(List<Translation> translations);
}
