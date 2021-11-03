package com.example.assessment2.Utilities;

import android.content.Intent;
import android.view.View;

public class IntentHolder
{
    private View currentView;
    private View nextView;

    public IntentHolder(View currentView, View nextView) {
        this.currentView = currentView;
        this.nextView = nextView;
    }

    public View getCurrentView() {
        return currentView;
    }

    public void setCurrentView(View currentView) {
        this.currentView = currentView;
    }

    public View getNextView() {
        return nextView;
    }

    public void setNextView(View nextView) {
        this.nextView = nextView;
    }
}
