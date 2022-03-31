package com.example.graph_editor.model;

import com.example.graph_editor.model.mathematics.Point;

import java.util.ArrayList;
import java.util.List;

public class VertexImpl implements Vertex {
    int index = -1;
    List<Edge> edges = new ArrayList<>();
    Point absolutePoint;
    EdgeFactory edgeFactory;

    public VertexImpl() {
        edgeFactory = new EdgeFactory(this);
        this.absolutePoint = new Point(0, 0);
    }

    @Override
    public int getIndex() {
        return index;
    }

    @Override
    public void setIndex(int index) {
        this.index = index;
    }

    @Override
    public void addEdge(Vertex target) {
        Edge e = edgeFactory.produce(target);
        edges.add(e);
    }

    @Override
    public void removeEdge(Edge edge) {
        edges.remove(edge);
    }

    @Override
    public void removeEdge(Vertex target) {
        List<Edge> toRemove = new ArrayList<>();
        for (Edge e : edges)
            if (e.getTarget() == target)
                toRemove.add(e);
        edges.removeAll(toRemove);
    }

    @Override
    public List<Edge> getEdges() {
        return edges;
    }

    @Override
    public void setPoint(Point point) {
        this.absolutePoint = point;
    }

    @Override
    public Point getPoint() {
        return absolutePoint;
    }
}
