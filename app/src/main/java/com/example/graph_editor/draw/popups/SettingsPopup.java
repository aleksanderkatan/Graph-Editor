package com.example.graph_editor.draw.popups;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Checkable;

import androidx.appcompat.widget.SwitchCompat;

import com.example.graph_editor.R;
import com.example.graph_editor.draw.Settings;

public class SettingsPopup {
    private final Context context;
    private final Runnable invalidateFunction;
    private AlertDialog dialog;

    public SettingsPopup(Context context, Runnable invalidateFunction) {
        this.context = context;
        this.invalidateFunction = invalidateFunction;
    }

    public void show() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = (LayoutInflater)context.getSystemService( Context.LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.settings_popup, null);

        SwitchCompat btnStylus = popupView.findViewById(R.id.toggle_stylus);
        btnStylus.setOnCheckedChangeListener((b, checked) ->
                Settings.setStylus(context, checked)
        );
        ((Checkable)btnStylus).setChecked(Settings.getStylus(context));

//        SwitchCompat btnDarkTheme = popupView.findViewById(R.id.toggle_buttons);
//        btnDarkTheme.setOnCheckedChangeListener((b, checked) -> {
//                    Settings.setDarkTheme(context, checked);
//                    Settings.updateCurrentTheme(context, checked);
//                }
//        );
//        ((Checkable)btnDarkTheme).setChecked(Settings.getDarkTheme(context));

        SwitchCompat btnFixedWidth = popupView.findViewById(R.id.toggle_fixed_width);
        btnFixedWidth.setOnCheckedChangeListener((buttonView, checked) -> {
            Settings.setFixedWidth(context, checked);
            invalidateFunction.run();
        });
        ((Checkable)btnFixedWidth).setChecked(Settings.getFixedWidth(context));


        popupView.findViewById(R.id.settings_popup_close).setOnClickListener(v ->
                dialog.dismiss()
        );

        builder.setView(popupView);
        dialog = builder.create();
        dialog.show();
    }
}
