package com.example.graph_editor.browse;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.graph_editor.R;
import com.example.graph_editor.database.SavesDatabase;
import com.example.graph_editor.draw.DrawActivity;

public class BrowseActivity extends AppCompatActivity {
    RecyclerView saved;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse);

        saved = findViewById(R.id.saved);
    }

    @Override
    protected void onResume() {
        super.onResume();
        SavesDatabase database = SavesDatabase.getDbInstance(getApplicationContext());
        SavedAdapter adapter = new SavedAdapter(this,
                database.saveDao().getAllSaves(), this);
        saved.setAdapter(adapter);
        saved.setLayoutManager(new LinearLayoutManager(this));

        TextView noSavedGraphs = findViewById(R.id.txt_no_saved_graphs);
        if (adapter.data.size() > 0) noSavedGraphs.setAlpha(0f);
    }

    public void changeActivity(String graphString, long id) {
        SharedPreferences sharedPref = this.getSharedPreferences("GLOBAL", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("currentGraph", graphString);
        editor.putLong("currentGraphId", id);
        editor.apply();

        Intent intent = new Intent(this, DrawActivity.class);
        startActivity(intent);
    }
}