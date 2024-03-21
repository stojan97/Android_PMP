package com.example.myapplication.activitiy;

import android.app.Dialog;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.myapplication.R;
import com.example.myapplication.dto.Language;
import com.example.myapplication.dto.Translation;
import com.example.myapplication.service.DictionaryService;
import com.example.myapplication.service.impl.DictionaryServiceImpl;
import com.example.myapplication.utils.DimensionConvertor;
import com.google.android.material.button.MaterialButton;

import java.util.List;


/**
 * Main activity of the Android App.
 */
public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private List<Translation> translations;

    private Language language;

    private final DictionaryService dictionaryService;

    public MainActivity() {
        this.dictionaryService = new DictionaryServiceImpl();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(TAG, "MainActivity created");

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        translations = dictionaryService.getDictionaryTranslations();
        language = Language.ENGLISH;

        initCallbacks();
        initTranslations();
    }

    /**
     * When language is switched we should operate on the GUI with the selected language.
     *
     * @param view the view
     */
    public void onLanguageSwitch(View view) {
        language = language == Language.ENGLISH ? Language.MACEDONIAN : Language.ENGLISH;

        // swap languages
        TextView firstLanguage = findViewById(R.id.textView2);
        TextView secondLanguage = findViewById(R.id.textView3);
        int englishLang = R.string.english_lang;
        int macedonianLang = R.string.macedonian_lang;
        firstLanguage.setText(language == Language.ENGLISH ? englishLang : macedonianLang);
        secondLanguage.setText(language == Language.ENGLISH ? macedonianLang : englishLang);

        // swap buttons
        translations.forEach(this::swapButtonText);
    }

    private void swapButtonText(Translation translation) {

        LinearLayout wordLayout = findViewById(translation.getId());
        Button firstButton = (Button) wordLayout.getChildAt(0);
        Button secondButton = (Button) wordLayout.getChildAt(1);

        firstButton.setText(translation.getTranslationForLanguage(language));
        secondButton.setText(translation.getInverseTranslationForLanguage(language));
    }

    private void initCallbacks() {

        Button addNewTranslationButton = findViewById(R.id.addNewTranslation);
        addNewTranslationButton.setOnClickListener(view -> displayAddWordCallback());

        SearchView searchView = findViewById(R.id.searchView);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String searchText) {

                String searchTextLowerCase = searchText.toLowerCase();

                for (Translation translation : translations) {
                    String languageTranslation = translation.getTranslationForLanguage(language);
                    int visible = languageTranslation.toLowerCase().contains(searchTextLowerCase) ? View.VISIBLE : View.GONE;
                    findViewById(translation.getId()).setVisibility(visible);
                }

                return false;
            }
        });
    }

    private void initTranslations() {

        LinearLayout translationsLayout = super.findViewById(R.id.translations);
        translations.forEach(translation -> translationsLayout.addView(createTranslation(translation)));
    }

    private void displayAddWordCallback() {
        // Use AlertDialog suggested from Android
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.add_word_dialog);

        EditText firstWord = dialog.findViewById(R.id.edit_text_dialog1);
        EditText secondWord = dialog.findViewById(R.id.edit_text_dialog2);

        Button dismissButton = dialog.findViewById(R.id.addWordDismissButton);
        dismissButton.setOnClickListener(v -> dialog.dismiss());

        Button addWordButton = dialog.findViewById(R.id.addWordButton);
        addWordButton.setOnClickListener(v -> {
            addNewTranslation(firstWord.getText().toString(), secondWord.getText().toString());
            dialog.dismiss();
        });
        dialog.show();
    }

    private void addNewTranslation(String firstWord, String secondWord) {

        if (firstWord.isEmpty() || secondWord.isEmpty()) {
            return;
        }

        Translation translation = new Translation(firstWord, secondWord);
        translations.add(0, translation);
        LinearLayout translationsLayout = findViewById(R.id.translations);
        translationsLayout.addView(createTranslation(translation), 1);
        dictionaryService.updateDictionaryTranslations(translations);
    }

    private LinearLayout createTranslation(Translation translation) {

        String translationForLanguage = translation.getTranslationForLanguage(language);
        String inverseTranslationForLanguage = translation.getInverseTranslationForLanguage(language);

        LinearLayout wordLayout = new LinearLayout(this);

        LinearLayout.LayoutParams wordLayoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                DimensionConvertor.dpToPixel(80)
        );

        wordLayoutParams.topMargin = DimensionConvertor.dpToPixel(40);

        wordLayout.setId(translation.getId());
        wordLayout.setLayoutParams(wordLayoutParams);
        wordLayout.setOrientation(LinearLayout.HORIZONTAL);

        // Prepare colors

        int whiteColor = ContextCompat.getColor(this, R.color.white);
        int blackColor = ContextCompat.getColor(this, R.color.black);

        // First Word Button
        MaterialButton firstWordButton = new MaterialButton(this);

        LinearLayout.LayoutParams firstButtonParams = new LinearLayout.LayoutParams(
                DimensionConvertor.dpToPixel(130),
                ViewGroup.LayoutParams.MATCH_PARENT
        );

        firstButtonParams.setMarginStart(DimensionConvertor.dpToPixel(5));
        // set button colors
        firstWordButton.setBackgroundTintList(ColorStateList.valueOf(whiteColor));
        firstWordButton.setTextColor(blackColor);

        firstWordButton.setLayoutParams(firstButtonParams);
        firstWordButton.setClickable(false);
        firstWordButton.setAllCaps(false);
        firstWordButton.setText(translationForLanguage);

        wordLayout.addView(firstWordButton);

        // Second Word Button
        MaterialButton secondWordButton = new MaterialButton(this);

        LinearLayout.LayoutParams secondWordParams = new LinearLayout.LayoutParams(
                DimensionConvertor.dpToPixel(130),
                ViewGroup.LayoutParams.MATCH_PARENT
        );

        secondWordParams.setMarginStart(DimensionConvertor.dpToPixel(10));
        secondWordParams.setMarginEnd(DimensionConvertor.dpToPixel(20));

        secondWordButton.setLayoutParams(secondWordParams);
        secondWordButton.setBackgroundTintList(ColorStateList.valueOf(whiteColor));
        secondWordButton.setTextColor(blackColor);

        secondWordButton.setClickable(false);
        secondWordButton.setAllCaps(false);
        secondWordButton.setText(inverseTranslationForLanguage);

        wordLayout.addView(secondWordButton);

        // Edit Button
        ImageButton editButton = new ImageButton(this);

        LinearLayout.LayoutParams editButtonParams = new LinearLayout.LayoutParams(
                DimensionConvertor.dpToPixel(0),
                ViewGroup.LayoutParams.MATCH_PARENT
        );

        editButtonParams.topMargin = DimensionConvertor.dpToPixel(6);
        editButtonParams.setMarginEnd(DimensionConvertor.dpToPixel(10));
        editButtonParams.bottomMargin = DimensionConvertor.dpToPixel(6);
        editButtonParams.weight = 1;
        editButton.setLayoutParams(editButtonParams);
        editButton.setImageResource(android.R.drawable.ic_menu_edit);
        editButton.setBackgroundColor(whiteColor);
        editButton.setImageTintList(ColorStateList.valueOf(blackColor));
        editButton.setOnClickListener(view -> displayDialogCallback(translation, firstWordButton, secondWordButton));
        wordLayout.addView(editButton);

        // Remove Button
        ImageButton removeButton = new ImageButton(this);

        LinearLayout.LayoutParams removeButtonParams = new LinearLayout.LayoutParams(
                DimensionConvertor.dpToPixel(0),
                ViewGroup.LayoutParams.MATCH_PARENT
        );

        removeButtonParams.topMargin = DimensionConvertor.dpToPixel(6);
        removeButtonParams.setMarginEnd(DimensionConvertor.dpToPixel(5));
        removeButtonParams.bottomMargin = DimensionConvertor.dpToPixel(6);
        removeButtonParams.weight = 1;
        removeButton.setLayoutParams(removeButtonParams);
        removeButton.setImageResource(android.R.drawable.ic_delete);
        removeButton.setBackgroundColor(whiteColor);
        removeButton.setImageTintList(ColorStateList.valueOf(blackColor));
        removeButton.setOnClickListener(view -> onRemoveWordTranslation(translation));

        wordLayout.addView(removeButton);

        return wordLayout;
    }

    private void displayDialogCallback(Translation translation, Button firstWord, Button secondWord) {
        // Use AlertDialog suggested from Android
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.edit_word_dialog);

        EditText text1 = dialog.findViewById(R.id.edit_text_dialog1);
        text1.setText(translation.getEnglishWord());

        EditText text2 = dialog.findViewById(R.id.edit_text_dialog2);
        text2.setText(translation.getMacedonianWord());

        Editable firstWordText = language == Language.ENGLISH ? text1.getText() : text2.getText();
        Editable secondWordText = language == Language.ENGLISH ? text2.getText() : text1.getText();

        // confirm and dismiss callbacks
        Button confirmButton = dialog.findViewById(R.id.button6);
        confirmButton.setOnClickListener(v -> {
            firstWord.setText(firstWordText);
            secondWord.setText(secondWordText);
            translation.setEnglishWord(text1.getText().toString());
            translation.setMacedonianWord(text2.getText().toString());

            dictionaryService.updateDictionaryTranslations(translations);
            dialog.dismiss();
        });

        Button dismissButton = dialog.findViewById(R.id.button7);
        dismissButton.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }

    private void onRemoveWordTranslation(Translation translation) {
        LinearLayout translationsLayout = findViewById(R.id.translations);
        translationsLayout.removeView(findViewById(translation.getId()));
        translations.remove(translation);
        dictionaryService.updateDictionaryTranslations(translations);
    }

}