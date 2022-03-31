package com.example.graph_editor.menu;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.example.graph_editor.R;
import com.example.graph_editor.browse.BrowseActivity;
import com.example.graph_editor.draw.DrawActivity;
import com.example.graph_editor.model.GraphType;

public class MenuActivity extends AppCompatActivity {
    private SharedPreferences sharedPref;

    static {
        System.loadLibrary("sample_lib");
    }
    public static native int doubleUp(int x);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        //System.out.println("2*2="+doubleUp(2));

        sharedPref = this.getSharedPreferences("GLOBAL", Context.MODE_PRIVATE);

        Button btnBrowse = findViewById(R.id.btnBrowse);
        btnBrowse.setOnClickListener(v -> {
            Intent intent = new Intent(this, BrowseActivity.class);
            startActivity(intent);
        });

        Button btnCreate = findViewById(R.id.btnCreate);
        btnCreate.setOnClickListener(v -> displayGraphTypePopup());

        Button btnCreators = findViewById(R.id.btnCreators);
        btnCreators.setOnClickListener(v -> {
            Intent intent = new Intent(this, CreatorsActivity.class);
            startActivity(intent);
        });
    }


    private void displayGraphTypePopup() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        final View enterNamePopup = getLayoutInflater().inflate(R.layout.graph_type_popup, null);

        dialogBuilder.setView(enterNamePopup);
        AlertDialog dialog = dialogBuilder.create();

        Button btnUndirected = enterNamePopup.findViewById(R.id.btnUndirected);
        Button btnDirected = enterNamePopup.findViewById(R.id.btnDirected);

        btnUndirected.setOnClickListener(v -> {
            dialog.dismiss();
            changeToDrawActivity(GraphType.UNDIRECTED);
        });

        btnDirected.setOnClickListener(v -> {
            dialog.dismiss();
            changeToDrawActivity(GraphType.DIRECTED);
        });

        dialog.show();
    }

    private void changeToDrawActivity(GraphType type) {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("GraphType", type.ordinal());
        editor.putLong("currentGraphId", -1);
        editor.apply();

        Intent intent = new Intent(this, DrawActivity.class);
        startActivity(intent);
    }
}