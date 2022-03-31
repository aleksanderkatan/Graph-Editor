package com.example.graph_editor.model;

import com.example.graph_editor.model.mathematics.Point;

import java.util.List;

public interface Vertex {
    int getIndex();
    void setIndex(int index);
    void addEdge(Vertex target);
    void removeEdge(Edge edge);
    void removeEdge(Vertex target);
    List<Edge> getEdges();
    void setPoint(Point point);
    Point getPoint();
}
