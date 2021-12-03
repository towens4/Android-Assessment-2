package com.holmesglen.lab11themeandsharedpreferences;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;

// In Theme Example 2, theme related color are defined in layout resource file for each element.
// When switching to a different theme, activity is recreated.
// Example 2 also demonstrated how to store data in Shared Preferences
public class ThemeExample2 extends AppCompatActivity {
    private String TAG = this.getClass().getSimpleName();
    private String isDarkThemeKey = "is_dark_theme_key";
    private int activeThemeResId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // load and set theme
        loadAndSetTheme();
        // set layout
        setContentView(R.layout.activity_theme_example2);
        // set action bar background color
        setActionBarStyle();

        // button to switch theme
        findViewById(R.id.btnLight).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(activeThemeResId == R.style.DarkTheme)
                {

                    switchAndSaveThemeSetting();
                }
            }
        });

        findViewById(R.id.btnDark).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(activeThemeResId == R.style.AppTheme)
                {

                    switchAndSaveThemeSetting();
                }
            }
        });

    }

    // for app action bar, if dark theme, color to be set accordingly.
    // if light theme, system default setting is good enough
    private void setActionBarStyle(){
        if (activeThemeResId == R.style.DarkTheme) {
            ActionBar actionBar;
            actionBar = getSupportActionBar();
            actionBar.setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(this, R.color.colorActionBarBackgroundDarkTheme)));
        }
    }


    private void loadAndSetTheme(){
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        boolean isDark = sharedPref.getBoolean(isDarkThemeKey, false);
        activeThemeResId = isDark ? R.style.DarkTheme : R.style.AppTheme;
        setTheme(activeThemeResId);
    }


    private void switchAndSaveThemeSetting() {SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        if (activeThemeResId == R.style.DarkTheme) {
            editor.putBoolean(isDarkThemeKey, false);
        } else {
            editor.putBoolean(isDarkThemeKey, true);
        }
        editor.commit();


        recreate();
    }
}
