package com.example.myapplication.service.impl;

import com.example.myapplication.DictionaryApplication;
import com.example.myapplication.dto.Translation;
import com.example.myapplication.infrastructure.FileService;
import com.example.myapplication.infrastructure.impl.FileServiceImpl;
import com.example.myapplication.service.DictionaryService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Dictionary service that provides logic for retrieving and updating the dictionary
 * from Internal Storage.
 */
public class DictionaryServiceImpl implements DictionaryService {

    private final FileService fileService;

    public DictionaryServiceImpl() {
        this.fileService = new FileServiceImpl();
    }

    @Override
    public List<Translation> getDictionaryTranslations() {

        List<String> lines = fileService.readLinesFromFile(DictionaryApplication.getDictionaryFilePath());

        return lines.stream()
                .map(this::toTranslation)
                .collect(Collectors.toList());
    }

    @Override
    public void updateDictionaryTranslations(List<Translation> translations) {
        List<String> fileFormatTranslations = translations.stream()
                .map(this::toFileFormat)
                .collect(Collectors.toList());

        fileService.writeLinesToFile(DictionaryApplication.getDictionaryFilePath(), fileFormatTranslations);
    }

    private Translation toTranslation(String lineFormat) {
        String[] split = lineFormat.split(",");
        return new Translation(split[0], split[1]);
    }

    private String toFileFormat(Translation translation) {
        return String.format(
                "%s,%s",
                translation.getEnglishWord(),
                translation.getMacedonianWord()
        );
    }

}
