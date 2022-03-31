package com.example.graph_editor.model;

public class EdgeFactory {
    Vertex source;
    EdgeFactory(Vertex source) {
        this.source = source;
    }
    Edge produce(Vertex target) {
        return new EdgeImpl(source, target);
    }
}
