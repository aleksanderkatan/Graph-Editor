package com.example.graph_editor.draw.popups;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.example.graph_editor.R;

public class DiscardPopup {
    Context context;
    Runnable deleteFunction;
    Runnable saveFunction;

    AlertDialog dialog;

    public DiscardPopup(Context context, Runnable deleteFunction, Runnable saveFunction) {
        this.context = context;
        this.deleteFunction = deleteFunction;
        this.saveFunction = saveFunction;
    }

    public void show() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = (LayoutInflater)context.getSystemService( Context.LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.discard_popup, null);

        Button btnCancel = popupView.findViewById(R.id.btn_cancel);
        Button btnNo = popupView.findViewById(R.id.btn_no);
        Button btnYes = popupView.findViewById(R.id.btn_yes);


        btnCancel.setOnClickListener(v -> dialog.dismiss());
        btnNo.setOnClickListener(v -> {
            dialog.dismiss();
            deleteFunction.run();
        });
        btnYes.setOnClickListener(v -> {
            dialog.dismiss();
            saveFunction.run();
        });

        builder.setView(popupView);
        dialog = builder.create();
        dialog.show();
    }
}
