package com.example.graph_editor.model.graph_storage;

import com.example.graph_editor.model.Edge;
import com.example.graph_editor.model.Graph;
import com.example.graph_editor.model.Vertex;

import java.util.List;

public class GraphWriter {
    public static String toVE(Graph g) {
        StringBuilder s = new StringBuilder();

        List<Vertex> vertices = g.getVertices();
        List<Edge> edges = g.getEdges();

        s.append(vertices.size());
        s.append(" ");
        s.append(edges.size());
        s.append("\n");

        for (Edge e : edges) {
            int index1 = e.getSource().getIndex();
            int index2 = e.getTarget().getIndex();
//            s.append(Math.min(index1, index2)).append(" ").append(Math.max(index1, index2)).append("\n");
            s.append(index1).append(" ").append(index2).append("\n");
        }

        return new String(s);
    }

    public static String toExact (Graph g) {
        String ve = toVE(g);
        String[] veSplit = ve.split("\n");

        StringBuilder s = new StringBuilder();
        s.append(g.getType().toString()).append("\n");
        s.append(veSplit[0]).append("\n");

        List<Vertex> vertices = g.getVertices();

        for (Vertex v : vertices) {
            s.append(v.getIndex());
            s.append(" ");
            s.append((float)v.getPoint().getX());
            s.append(" ");
            s.append((float)v.getPoint().getY());
            s.append("\n");
        }

        for (int i = 1; i< veSplit.length; i++) {
            s.append(veSplit[i]).append("\n");
        }

        return new String(s);
    }

    public static String toExactList(List<Graph> list){
        StringBuilder result = new StringBuilder();
        for (Graph g : list) {
            result.append(toExact(g));
            result.append("#");
        }
        return result.substring(0, result.length()-1);
    }
}
