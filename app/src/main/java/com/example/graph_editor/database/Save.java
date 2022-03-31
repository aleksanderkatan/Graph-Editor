package com.example.graph_editor.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Save {
    @PrimaryKey(autoGenerate = true)
    public long uid;
    @ColumnInfo(name = "Name")
    public String name;
    @ColumnInfo(name = "Graph")
    public String graph;
    @ColumnInfo(name = "Date")
    public long date;

    public Save(String name, String graph, long date) {
        this.name = name;
        this.graph = graph;
        this.date = date;
    }

}
