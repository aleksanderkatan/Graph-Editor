package com.example.graph_editor.model;

public class EdgeImpl implements Edge {
    Vertex source;
    Vertex target;

    public EdgeImpl(Vertex source, Vertex target) {
        this.source = source;
        this.target = target;
    }

    @Override
    public Vertex getSource() {
        return source;
    }

    @Override
    public Vertex getTarget() {
        return target;
    }
}
