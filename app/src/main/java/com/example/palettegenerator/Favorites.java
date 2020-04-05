package com.example.palettegenerator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewParent;
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

    private void loadFavorites() {
        Map<String,?> keys = sharedPreferences.getAll();

        for(Map.Entry<String,?> entry : keys.entrySet()){
            System.out.println(entry.getValue().toString());
            addFavorite(entry);
        }

    }

    private void addFavorite(Map.Entry<String, ?> favorite) {
        List<String> colorHexCodes = Arrays.asList(favorite.getValue().toString().split("\\s*,\\s*"));
        LinearLayout horizontalFavoriteLayout = new LinearLayout(this);
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

        /*
        Button viewButton = new Button(this);
        viewButton.setText("View");
        viewButton.setTextSize(12);
        viewButton.setGravity(Gravity.CENTER);
        viewButton.setLayoutParams(buttonParams);
        horizontalFavoriteLayout.addView(viewButton);
        */

        TextView textView1 = new TextView(this);
        textView1.setBackgroundColor(Color.parseColor(colorHexCodes.get(0)));
        textView1.setLayoutParams(textViewParams);
        textView1.setGravity(Gravity.CENTER);
        horizontalFavoriteLayout.addView(textView1);

        TextView textView2 = new TextView(this);
        textView2.setBackgroundColor(Color.parseColor(colorHexCodes.get(1)));
        textView2.setLayoutParams(textViewParams);
        textView2.setGravity(Gravity.CENTER);
        horizontalFavoriteLayout.addView(textView2);

        TextView textView3 = new TextView(this);
        textView3.setBackgroundColor(Color.parseColor(colorHexCodes.get(2)));
        textView3.setLayoutParams(textViewParams);
        textView3.setGravity(Gravity.CENTER);
        horizontalFavoriteLayout.addView(textView3);

        TextView textView4 = new TextView(this);
        textView4.setBackgroundColor(Color.parseColor(colorHexCodes.get(3)));
        textView4.setLayoutParams(textViewParams);
        textView4.setGravity(Gravity.CENTER);
        horizontalFavoriteLayout.addView(textView4);

        TextView textView5 = new TextView(this);
        textView5.setBackgroundColor(Color.parseColor(colorHexCodes.get(4)));
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
    }

    /**
     * Convert dp to pixels. Utility function.
     * @param dp
     * @return int
     */
    private int getPixels(int dp) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }
}
