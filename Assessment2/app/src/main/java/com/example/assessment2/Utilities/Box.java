package com.example.assessment2.Utilities;

import android.view.View;

public class Box {

    public static boolean withinViewBounds(View view, int rx, int ry)
    {
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        int x = location[0];
        int y = location[1];
        int width = view.getWidth();
        int height = view.getHeight();

        if(rx < x || rx > x+width|| ry < y || ry > y + height)
        {
            return false;
        }
        return true;
    }
}
