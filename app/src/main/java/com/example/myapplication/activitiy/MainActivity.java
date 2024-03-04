package com.example.myapplication.activitiy;

import android.app.Dialog;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SearchView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.myapplication.R;
import com.example.myapplication.dto.Tag;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import com.example.myapplication.utils.DimensionConvertor;


/**
 * Main activity of the Android App.
 */
public class MainActivity extends AppCompatActivity {

    private static final String EMPTY_TEXT = "";
    List<Tag> tags = new ArrayList<>();
    private float DENSITY = -1;

    public void onSaveButton(View view) {
        TextInputEditText textInputEditText = findViewById(R.id.new_tag_text);
        String tagText = Objects.requireNonNull(textInputEditText.getText()).toString();

        if (tagText.isEmpty()) {
            return;
        }

        LinearLayout scrollLayout = findViewById(R.id.scrollLayout);
        textInputEditText.setText(EMPTY_TEXT);
        scrollLayout.addView(createTag(tagText), 1);
    }

    public void onClearTags(View view) {
        LinearLayout scrollLayout = findViewById(R.id.scrollLayout);
        tags.forEach(tag -> scrollLayout.removeView(findViewById(tag.getId())));
        tags = new ArrayList<>();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DENSITY = super.getResources().getDisplayMetrics().density;
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(super.findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initListeners();
        initScrollLayout();
    }

    private void initListeners() {
        SearchView searchView = super.findViewById(R.id.searchView);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String searchText) {

                String searchTextLowerCase = searchText.toLowerCase();

                for (Tag tag : tags) {
                    int visible = tag.getLabel().toLowerCase().contains(searchTextLowerCase) ? View.VISIBLE : View.GONE;
                    findViewById(tag.getId()).setVisibility(visible);
                }

                return false;
            }
        });
    }

    private void initScrollLayout() {

        LinearLayout scrollLayout = super.findViewById(R.id.scrollLayout);

        List<String> tagNames = Arrays.asList(
                "AndroidFP",
                "Deitel",
                "Google",
                "iPhoneFP",
                "JavaFP",
                "JavaHTP",
                "Facebook0",
                "Facebook1",
                "Facebook2",
                "Facebook3"
        );

        tagNames.forEach(tagName -> scrollLayout.addView(createTag(tagName)));
    }

    private LinearLayout createTag(String tagLabel) {

        int tagId = View.generateViewId();
        tags.add(new Tag(tagId, tagLabel));

        LinearLayout.LayoutParams tagLayoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                DimensionConvertor.dpToPixel(DENSITY, 70)
        );
        tagLayoutParams.topMargin = DimensionConvertor.dpToPixel(DENSITY, 40);

        LinearLayout tagLayout = new LinearLayout(this);
        tagLayout.setId(tagId);
        tagLayout.setLayoutParams(tagLayoutParams);
        tagLayout.setOrientation(LinearLayout.HORIZONTAL);


        int buttonColor = ContextCompat.getColor(this, R.color.button_background);
        int buttonTextColor = ContextCompat.getColor(this, R.color.black);

        Button tagTextButton = new Button(this);
        int labelWidth = DimensionConvertor.dpToPixel(DENSITY, 220);
        int labelHeight = ViewGroup.LayoutParams.MATCH_PARENT;
        LinearLayout.LayoutParams tagLabelButtonParams = new LinearLayout.LayoutParams(
                labelWidth,
                labelHeight
        );

        tagLabelButtonParams.setMarginStart(DimensionConvertor.dpToPixel(DENSITY, 20));
        // set button colors
        tagTextButton.setBackgroundTintList(ColorStateList.valueOf(buttonColor));
        tagTextButton.setTextColor(buttonTextColor);

        tagTextButton.setLayoutParams(tagLabelButtonParams);
        tagTextButton.setClickable(false);
        tagTextButton.setAllCaps(false);
        tagTextButton.setText(tagLabel);

        tagLayout.addView(tagTextButton);

        Button tagEditButton = new Button(this);
        LinearLayout.LayoutParams tagEditButtonParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        );
        tagEditButtonParams.setMarginStart(DimensionConvertor.dpToPixel(DENSITY, 10));
        tagEditButtonParams.setMarginEnd(DimensionConvertor.dpToPixel(DENSITY, 20));

        // set button colors
        tagEditButton.setBackgroundTintList(ColorStateList.valueOf(buttonColor));
        tagEditButton.setTextColor(buttonTextColor);

        tagEditButton.setLayoutParams(tagEditButtonParams);
        tagEditButton.setClickable(true);
        tagEditButton.setText(R.string.edit);
        tagEditButton.setAllCaps(false);
        tagEditButton.setOnClickListener(view -> showDialog(tagTextButton));

        tagLayout.addView(tagEditButton);

        return tagLayout;
    }

    private void showDialog(Button tagTextButton) {
        Dialog dialog = new Dialog(this);
        dialog.setTitle(R.string.edit_tag_text);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog);

        EditText text = dialog.findViewById(R.id.edit_text_dialog);
        text.setText(tagTextButton.getText());
        Button dismissButton = dialog.findViewById(R.id.button7);
        dismissButton.setOnClickListener(v -> dialog.dismiss());
        Button confirmButton = dialog.findViewById(R.id.button6);
        confirmButton.setOnClickListener(v -> {
            tagTextButton.setText(text.getText());
            dialog.dismiss();
        });
        dialog.show();
    }


}