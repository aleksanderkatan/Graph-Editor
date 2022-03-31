package com.example.graph_editor.model;

import java.util.List;

public interface Graph {
    Vertex addVertex();
    void addEdge(Vertex source, Vertex target);
    void removeVertex(Vertex vertex);
    void removeEdge(Edge edge);
    GraphType getType();
    List<Vertex> getVertices();
    List<Edge> getEdges();
    Graph deepCopy();
}
