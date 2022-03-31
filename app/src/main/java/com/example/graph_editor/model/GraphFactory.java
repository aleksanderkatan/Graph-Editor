package com.example.graph_editor.model;

public class GraphFactory {
    GraphType type;

    public GraphFactory(GraphType type) {
        this.type = type;
    }

    public Graph produce() {
        return new GraphImpl(type);
    }
}
