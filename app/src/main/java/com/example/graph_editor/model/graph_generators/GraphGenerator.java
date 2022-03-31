package com.example.graph_editor.model.graph_generators;

import com.example.graph_editor.model.Graph;

import java.util.List;

public interface GraphGenerator {
    List<Parameter> getParameters();
    Graph generate(List<Integer> parameters); //also assigns Points between (0, 0) and (1, 1)
}
