package com.example.palettegenerator;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Favorites extends AppCompatActivity {

    private LinearLayout verticalFavoritesLayout;
    private Button returnButton;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        verticalFavoritesLayout = findViewById(R.id.verticalFavoritesLayout);
        returnButton = findViewById(R.id.returnButton);
        sharedPreferences = getSharedPreferences("PaletteGeneratorSharedPreferences", MODE_PRIVATE);

        loadFavorites();

        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * Load favorites.
     * Iterate over sharedPreferences favorites call method to display them.
     */
    private void loadFavorites() {
        Map<String,?> keys = sharedPreferences.getAll();
        for(Map.Entry<String,?> entry : keys.entrySet()){
            displayFavorite(entry);
        }

    }

    /**
     * Display favorite.
     * Creates views and displays the palette data in sharedPreferences.
     * @param favorite Map.Entry<String, ?>
     */
    private void displayFavorite(final Map.Entry<String, ?> favorite) {
        final List<String> colorHexCodes = Arrays.asList(favorite.getValue().toString().split("\\s*,\\s*"));
        final LinearLayout horizontalFavoriteLayout = new LinearLayout(this);
        horizontalFavoriteLayout.setOrientation(LinearLayout.HORIZONTAL);
        horizontalFavoriteLayout.setTag(favorite.getKey());
        horizontalFavoriteLayout.setBackgroundColor(Color.parseColor("#DEE3E6"));


        TableLayout.LayoutParams layoutParams = new TableLayout.LayoutParams(
                TableLayout.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.MATCH_PARENT);
        layoutParams.setMargins(0, getPixels(5), 0, getPixels(5));
        horizontalFavoriteLayout.setLayoutParams(layoutParams);

        TableLayout.LayoutParams buttonParams = new TableLayout.LayoutParams(
                getPixels(50),
                getPixels(90),
                1f);
        buttonParams.setMargins(0, getPixels(10), 0, getPixels(10));

        TableLayout.LayoutParams textViewParams = new TableLayout.LayoutParams(
                TableLayout.LayoutParams.WRAP_CONTENT,
                getPixels(90),
                1f);
        textViewParams.setMargins(0, getPixels(10), 0, getPixels(10));

        TextView textView1 = new TextView(this);
        textView1.setBackgroundColor(Color.parseColor(colorHexCodes.get(0)));
        textView1.setText(colorHexCodes.get(0));
        textView1.setLayoutParams(textViewParams);
        textView1.setGravity(Gravity.CENTER);
        horizontalFavoriteLayout.addView(textView1);

        TextView textView2 = new TextView(this);
        textView2.setBackgroundColor(Color.parseColor(colorHexCodes.get(1)));
        textView2.setText(colorHexCodes.get(1));
        textView2.setLayoutParams(textViewParams);
        textView2.setGravity(Gravity.CENTER);
        horizontalFavoriteLayout.addView(textView2);

        TextView textView3 = new TextView(this);
        textView3.setBackgroundColor(Color.parseColor(colorHexCodes.get(2)));
        textView3.setText(colorHexCodes.get(2));
        textView3.setLayoutParams(textViewParams);
        textView3.setGravity(Gravity.CENTER);
        horizontalFavoriteLayout.addView(textView3);

        TextView textView4 = new TextView(this);
        textView4.setBackgroundColor(Color.parseColor(colorHexCodes.get(3)));
        textView4.setText(colorHexCodes.get(3));
        textView4.setLayoutParams(textViewParams);
        textView4.setGravity(Gravity.CENTER);
        horizontalFavoriteLayout.addView(textView4);

        TextView textView5 = new TextView(this);
        textView5.setBackgroundColor(Color.parseColor(colorHexCodes.get(4)));
        textView5.setText(colorHexCodes.get(4));
        textView5.setLayoutParams(textViewParams);
        textView5.setGravity(Gravity.CENTER);
        horizontalFavoriteLayout.addView(textView5);

        Button deleteButton = new Button(this);
        deleteButton.setText("Delete");
        deleteButton.setTextSize(12);
        deleteButton.setGravity(Gravity.CENTER);
        deleteButton.setLayoutParams(buttonParams);
        horizontalFavoriteLayout.addView(deleteButton);

        verticalFavoritesLayout.addView(horizontalFavoriteLayout);

        /*
        ============================================================================================
        ======================================== LISTENERS =========================================
        ============================================================================================
         */

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userConfirmDeletion(horizontalFavoriteLayout, favorite.getKey());
            }
        });
    }

    /**
     * Confirm user deletion.
     * Alert dialog popup.
     * @param horizontalFavoriteLayout favorite entry view
     * @param key sharedPreferences entry key
     */
    public void userConfirmDeletion(final LinearLayout horizontalFavoriteLayout, final String key) {
        // Gnarly passing final variables around but hey what can ya do
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                deleteFavorite(horizontalFavoriteLayout, key);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
            }
        });
        builder.setMessage("Are you sure you want to delete this palette?");
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /**
     * Delete favorite.
     * Remove favorite entry views and remove entry from sharedPreferences.
     * @param horizontalFavoriteLayout favorite entry view
     * @param key sharedPreferences entry key
     */
    public void deleteFavorite(LinearLayout horizontalFavoriteLayout, String key) {
        horizontalFavoriteLayout.setVisibility(View.GONE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(key);
        editor.apply();
    }

    /**
     * Convert dp to pixels. Utility function.
     * @param dp int
     * @return pixels int
     */
    private int getPixels(int dp) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }
}

