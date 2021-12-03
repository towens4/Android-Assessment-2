package com.holmesglen.lab11themeandsharedpreferences;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private int activeThemeResId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme);
        setContentView(R.layout.activity_main);

        activeThemeResId = R.style.AppTheme;
        modifyTheme();

        // button to switch theme
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(activeThemeResId == R.style.DarkTheme) {
                    activeThemeResId = R.style.AppTheme;
                } else {
                    activeThemeResId = R.style.DarkTheme;
                }
                modifyTheme();
            }
        });

        // this is the entry of Example 2.
        findViewById(R.id.btn_example2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ThemeExample2.class);
                startActivity(intent);

            }
        });

    }

    // update style for all UIs to set the target theme
    private void modifyTheme(){
        setTheme(activeThemeResId);
        TypedValue typedValue = new TypedValue();

        // for background
        getTheme().resolveAttribute(R.attr.colorBackground, typedValue, true);
        findViewById(R.id.example1_bg).setBackgroundColor(typedValue.data);

        // for switch theme button
        getTheme().resolveAttribute(R.attr.colorButtonTextColor, typedValue, true);
        ((Button)findViewById(R.id.button)).setTextColor(typedValue.data);
        getTheme().resolveAttribute(R.attr.colorPrimary, typedValue, true);
        ((Button)findViewById(R.id.button)).setBackgroundTintList(ColorStateList.valueOf(typedValue.data));

        // for hello world text
        getTheme().resolveAttribute(R.attr.colorTextHighEmphasis, typedValue, true);
        ((TextView)findViewById(R.id.textView)).setTextColor(typedValue.data);

        // for text edit text
        getTheme().resolveAttribute(R.attr.colorTextHighEmphasis, typedValue, true);
        ((EditText)findViewById(R.id.editText)).setTextColor(typedValue.data);
        ((EditText)findViewById(R.id.editText)).setBackgroundTintList(getResources().getColorStateList(R.color.edit_text, getTheme()));

        // for example 2 button
        getTheme().resolveAttribute(R.attr.colorButtonTextColor, typedValue, true);
        ((Button)findViewById(R.id.btn_example2)).setTextColor(typedValue.data);
        getTheme().resolveAttribute(R.attr.colorPrimary, typedValue, true);
        ((Button)findViewById(R.id.btn_example2)).setBackgroundTintList(ColorStateList.valueOf(typedValue.data));

        setActionBarStyle();

        //int c = activeThemeResId == R.style.DarkTheme ? R.attr.color
        getTheme().resolveAttribute(R.attr.statusBarBackground, typedValue, true);
        getWindow().setStatusBarColor(typedValue.data);// set status background white
    }

    // for app action bar, if dark theme, color to be set accordingly.
    // if light theme, system default setting is good enough
    private void setActionBarStyle(){
        ActionBar actionBar;
        actionBar = getSupportActionBar();
        if (activeThemeResId == R.style.DarkTheme) {
            actionBar.setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(this, R.color.colorActionBarBackgroundDarkTheme)));
        } else {
            actionBar.setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(this, R.color.colorPrimary)));
        }
    }

}
