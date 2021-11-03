package com.example.assessment2.Utilities;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.view.View;
import android.widget.Button;

import androidx.cardview.widget.CardView;

public class AnimationHandler
{

    public static void moveCard(float dyCompare, float dx, CardView cardview, Button btnEdit)
    {
        //dyCompare = 150;
        //dx = 50
        float transX = cardview.getTranslationX() + dx;
        if(dyCompare == dyCompare + dx)
        {
            transX = transX - dx;
            PropertyValuesHolder pvnY = PropertyValuesHolder.ofFloat("translationX", transX);
            ObjectAnimator.ofPropertyValuesHolder(cardview, pvnY).start();

            return;
        }
        btnEdit.setVisibility(View.VISIBLE);
        transX = transX + dx;
        PropertyValuesHolder pvnY = PropertyValuesHolder.ofFloat("translationX", transX);
        ObjectAnimator.ofPropertyValuesHolder(cardview, pvnY).start();
    }

    public static void moveCardAlt(CardView cardview, Button btnEdit)
    {

    }
}
