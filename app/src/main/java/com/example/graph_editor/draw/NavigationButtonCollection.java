package com.example.graph_editor.draw;

import android.content.Context;
import android.widget.ImageButton;

import androidx.core.content.ContextCompat;

import com.example.graph_editor.R;
import com.example.graph_editor.draw.action_mode_type.ActionModeType;
import com.example.graph_editor.model.state.State;
import com.example.graph_editor.model.state.StateStack;

import java.util.HashMap;
import java.util.Map;

public class NavigationButtonCollection {
    private final StateStack stateStack;

    private final Map<ActionModeType, ImageButton> buttons;
    private ImageButton current = null;
    private final int ACTIVE;
    private final int NOT_ACTIVE;

    public NavigationButtonCollection(Context context, StateStack stateStack) {
        this.stateStack = stateStack;
        this.buttons = new HashMap<>();
        ACTIVE = ContextCompat.getColor(context, R.color.grey);
        NOT_ACTIVE = ContextCompat.getColor(context, R.color.lt_grey);
    }
    public void add(ImageButton button, ActionModeType modeType) {
        button.setOnClickListener(v -> {
            State state = stateStack.getCurrentState();
            if (state.isCurrentlyModified()) return;

            stateStack.getCurrentState().setCurrentModeType(modeType);
            setCurrent(button);
        });
        button.setBackgroundColor(NOT_ACTIVE);
        buttons.put(modeType, button);
    }
    public void setCurrent(ImageButton button) {
        if(current != null) {
            current.setClickable(true);
            current.setBackgroundColor(NOT_ACTIVE);
        }
        current = button;
        button.setClickable(false);
        current.setBackgroundColor(ACTIVE);
    }

    public void setCurrent(ActionModeType type) {
        ImageButton button = buttons.get(type);
        if (button != null)
            setCurrent(button);
    }
}
