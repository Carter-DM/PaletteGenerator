package com.example.palettegenerator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private TextView colorTextView1;
    private TextView colorTextView2;
    private TextView colorTextView3;
    private TextView colorTextView4;
    private TextView colorTextView5;

    private ToggleButton lockButton1;
    private ToggleButton lockButton2;
    private ToggleButton lockButton3;
    private ToggleButton lockButton4;
    private ToggleButton lockButton5;

    private Button newPaletteButton;
    private Button savePaletteButton;
    private Button favoritesButton;

    private RadioButton randomRadioButton;
    private RadioButton warmRadioButton;
    private RadioButton coolRadioButton;

    private SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        colorTextView1 = findViewById(R.id.colorTextView1);
        colorTextView2 = findViewById(R.id.colorTextView2);
        colorTextView3 = findViewById(R.id.colorTextView3);
        colorTextView4 = findViewById(R.id.colorTextView4);
        colorTextView5 = findViewById(R.id.colorTextView5);

        lockButton1 = findViewById(R.id.lockButton1);
        lockButton2 = findViewById(R.id.lockButton2);
        lockButton3 = findViewById(R.id.lockButton3);
        lockButton4 = findViewById(R.id.lockButton4);
        lockButton5 = findViewById(R.id.lockButton5);

        newPaletteButton = findViewById(R.id.newPaletteButton);
        savePaletteButton = findViewById(R.id.savePaletteButton);
        favoritesButton = findViewById(R.id.favoritesButton);

        randomRadioButton = findViewById(R.id.randomRadioButton);
        warmRadioButton = findViewById(R.id.warmRadioButton);
        coolRadioButton = findViewById(R.id.coolRadioButton);

        sharedPreferences = getSharedPreferences("PaletteGeneratorSharedPreferences", MODE_PRIVATE);

        setTextViewColors();

        /*
        ============================================================================================
        ======================================== LISTENERS =========================================
        ============================================================================================
         */

        newPaletteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTextViewColors();
            }
        });

        savePaletteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveNewFavorite();
            }
        });

        favoritesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFavorites();
            }
        });
    }

    /**
     * Set Text View Colors. Sets the main palette colors.
     */
    private void setTextViewColors() {
        int colorTextView1ColorInt;
        int colorTextView2ColorInt;
        int colorTextView3ColorInt;
        int colorTextView4ColorInt;
        int colorTextView5ColorInt;

        if (randomRadioButton.isChecked()) {
            colorTextView1ColorInt = generateRandomColor();
            colorTextView2ColorInt = generateRandomColor();
            colorTextView3ColorInt = generateRandomColor();
            colorTextView4ColorInt = generateRandomColor();
            colorTextView5ColorInt = generateRandomColor();
        }
        else if (warmRadioButton.isChecked()) {
            colorTextView1ColorInt = generateWarmColor();
            colorTextView2ColorInt = generateWarmColor();
            colorTextView3ColorInt = generateWarmColor();
            colorTextView4ColorInt = generateWarmColor();
            colorTextView5ColorInt = generateWarmColor();
        }
        else {
            colorTextView1ColorInt = generateCoolColor();
            colorTextView2ColorInt = generateCoolColor();
            colorTextView3ColorInt = generateCoolColor();
            colorTextView4ColorInt = generateCoolColor();
            colorTextView5ColorInt = generateCoolColor();
        }

        if (!lockButton1.isChecked()) {
            colorTextView1.setBackgroundColor(colorTextView1ColorInt);
            colorTextView1.setText(getHexValue(colorTextView1ColorInt));
        }

        if (!lockButton2.isChecked()) {
            colorTextView2.setBackgroundColor(colorTextView2ColorInt);
            colorTextView2.setText(getHexValue(colorTextView2ColorInt));
        }

        if (!lockButton3.isChecked()) {
            colorTextView3.setBackgroundColor(colorTextView3ColorInt);
            colorTextView3.setText(getHexValue(colorTextView3ColorInt));
        }

        if (!lockButton4.isChecked()) {
            colorTextView4.setBackgroundColor(colorTextView4ColorInt);
            colorTextView4.setText(getHexValue(colorTextView4ColorInt));
        }

        if (!lockButton5.isChecked()) {
            colorTextView5.setBackgroundColor(colorTextView5ColorInt);
            colorTextView5.setText(getHexValue(colorTextView5ColorInt));
        }
    }

    /**
     * Generates random HSL(V) color.
     * @return int color
     */
    private int generateRandomColor() {
        Random random = new Random();
        final float hue = random.nextFloat() * 360;
        final float saturation = random.nextFloat();
        final float luminance = 0.9f;
        return Color.HSVToColor(new float[] {hue, saturation, luminance});
    }

    /**
     * Generates random warm HSL(V) color.
     * Hue is in the range of 0-60 || 300-360 (Just eyeballed the values)
     * @return int color
     */
    private int generateWarmColor() {
        Random random = new Random();
        float hue = random.nextBoolean() ? random.nextFloat() * 60 : 300 + random.nextFloat() * (360 - 300);
        final float saturation = random.nextFloat();
        final float luminance = 0.9f;
        return Color.HSVToColor(new float[] {hue, saturation, luminance});
    }

    /**
     * Generates random cool HSL(V) color.
     * Hue is in the range of 80-300 (Just eyeballed the values)
     * @return int color
     */
    private int generateCoolColor() {
        Random random = new Random();
        final float hue = 80 + random.nextFloat() * (300 - 80);
        final float saturation = random.nextFloat();
        final float luminance = 0.9f;
        return Color.HSVToColor(new float[] {hue, saturation, luminance});
    }

    /**
     * Coverts HSL(V) int to Hex string. Utility function.
     * @param intColor HSL(V) int
     * @return String hex
     */
    private String getHexValue(int intColor) {
        return String.format("#%06X", (0xFFFFFF & intColor));
    }

    /**
     * Save color hex codes to sharedPreferences.
     * Id is based on length of sharedPreferences.
     */
    private void saveNewFavorite() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        int idNum = sharedPreferences.getAll().size()+1;
        String colorHexCodes =
                colorTextView1.getText().toString() + ", " +
                        colorTextView2.getText().toString() + ", " +
                        colorTextView3.getText().toString() + ", " +
                        colorTextView4.getText().toString() + ", " +
                        colorTextView5.getText().toString();
        System.out.println(colorHexCodes);
        editor.putString("fav" + String.valueOf(idNum), colorHexCodes);
        editor.apply();

        Context context = getApplicationContext();
        CharSequence text = "Palette Saved!";
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    /**
     * Open Favorites activity.
     */
    private void openFavorites() {
        Intent intent = new Intent(this, Favorites.class);
        startActivity(intent);
    }
}
