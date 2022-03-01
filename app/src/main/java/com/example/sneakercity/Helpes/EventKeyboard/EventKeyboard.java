package com.example.sneakercity.Helpes.EventKeyboard;

import android.view.KeyEvent;
import android.widget.TextView;

public class EventKeyboard implements TextView.OnEditorActionListener {

    private EventKeyboardInterface mInterface;

    public EventKeyboard(EventKeyboardInterface mInterface) {
        this.mInterface = mInterface;
    }

    @Override
    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        return false;
    }
}
