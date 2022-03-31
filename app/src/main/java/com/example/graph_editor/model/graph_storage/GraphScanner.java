package com.example.graph_editor.model.graph_storage;

import com.example.graph_editor.model.Graph;
import com.example.graph_editor.model.GraphFactory;
import com.example.graph_editor.model.GraphType;
import com.example.graph_editor.model.Vertex;
import com.example.graph_editor.model.mathematics.Point;

import java.util.ArrayList;
import java.util.List;

public class GraphScanner {
    public static Graph fromExact(String s) throws InvalidGraphStringException {
        Graph g;
        String[] t = s.split("\n");

        if (t[0].equals("DIRECTED")) {
            g = new GraphFactory(GraphType.DIRECTED).produce();
        } else if (t[0].equals("UNDIRECTED")) {
            g = new GraphFactory(GraphType.UNDIRECTED).produce();
        } else {
            throw new InvalidGraphStringException();
        }

        try {
            int v = Integer.parseInt(t[1].split(" ")[0]);
            int e = Integer.parseInt(t[1].split(" ")[1]);

            for (int i = 0; i < v; i++) {
                g.addVertex();
            }
            List<Vertex> vertices = g.getVertices();


            for (int i = 2; i < 2 + v; i++) {
                String[] split = t[i].split(" ");
                int index = Integer.parseInt(split[0]);
                double x = Double.parseDouble(split[1]);
                double y = Double.parseDouble(split[2]);
                Point point = new Point(x, y);
                g.getVertices().get(index).setPoint(point);
            }

            for (int i = 2 + v; i < 2 + v + e; i++) {
                String[] split = t[i].split(" ");
                int a = Integer.parseInt(split[0]);
                int b = Integer.parseInt(split[1]);

                g.addEdge(vertices.get(a), vertices.get(b));
            }
        } catch (NumberFormatException e) {
            throw new InvalidGraphStringException();
        }

        return g;
    }

    public static List<Graph> fromExactList(String s) throws InvalidGraphStringException {
        List<Graph> result = new ArrayList<>();
        String[] t = s.split("#");
        for (String g : t) {
            result.add(fromExact(g));
        }
        return result;
    }
}
