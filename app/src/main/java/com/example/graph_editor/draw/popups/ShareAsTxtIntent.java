package com.example.graph_editor.draw.popups;

import android.content.Context;
import android.content.Intent;

import com.example.graph_editor.model.graph_storage.GraphWriter;
import com.example.graph_editor.model.state.StateStack;

public class ShareAsTxtIntent {
    private final Context context;
    private final StateStack stateStack;

    public ShareAsTxtIntent(Context context, StateStack stateStack) {
        this.context = context;
        this.stateStack = stateStack;
    }

    public void show() {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);  // for some reason this action won't display custom titles
        sendIntent.putExtra(Intent.EXTRA_TEXT, GraphWriter.toExact(stateStack.getCurrentState().getGraph()));
        sendIntent.setType("text/plain");

        Intent shareIntent = Intent.createChooser(sendIntent, "Export your graph");
        context.startActivity(shareIntent);
    }
}
