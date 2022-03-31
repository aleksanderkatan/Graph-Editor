package com.example.graph_editor.model;

import com.example.graph_editor.model.graph_storage.GraphScanner;
import com.example.graph_editor.model.graph_storage.GraphWriter;
import com.example.graph_editor.model.graph_storage.InvalidGraphStringException;

import java.util.ArrayList;
import java.util.List;

public class GraphImpl implements Graph {
    GraphType type;
    List<Vertex> vertices = new ArrayList<>();

    public GraphImpl(GraphType type) {
        this.type = type;
    }

    @Override
    public Vertex addVertex() {
        Vertex v = new VertexImpl();
        vertices.add(v);
        v.setIndex(vertices.size()-1);
        return v;
    }

    @Override
    public void addEdge(Vertex source, Vertex target) {
        if (source == target) return;
        for (Edge e : source.getEdges())
            if (e.getTarget() == target) return;

        source.addEdge(target);
        if (type == GraphType.UNDIRECTED) {
            target.addEdge(source);
        }
    }

    @Override
    public void removeVertex(Vertex vertex) {
        for(Vertex other : vertices) {
            other.removeEdge(vertex);
        }
        vertices.remove(vertex);
        for(int i=0; i<vertices.size(); i++)
            vertices.get(i).setIndex(i);
    }

    @Override
    public void removeEdge(Edge edge) {
        edge.getSource().removeEdge(edge);
        if(type == GraphType.UNDIRECTED)
            edge.getTarget().removeEdge(edge.getSource());
    }

    @Override
    public GraphType getType() {
        return type;
    }

    @Override
    public List<Vertex> getVertices() {
        return vertices;
    }

    @Override
    public List<Edge> getEdges() {
        List<Edge> result = new ArrayList<>();
        for(Vertex vertex : vertices) {
            if(type == GraphType.DIRECTED) {
                result.addAll(vertex.getEdges());
            } else {
                for(Edge edge : vertex.getEdges()) {
                    if(edge.getSource().getIndex() < edge.getTarget().getIndex())
                        result.add(edge);
                }
            }
        }
        return result;
    }

    @Override
    public Graph deepCopy() {
        Graph result = null;
        try {
            result = GraphScanner.fromExact(GraphWriter.toExact(this));
        } catch (InvalidGraphStringException e) {
            e.printStackTrace();
        }
        return result;
    }
}
