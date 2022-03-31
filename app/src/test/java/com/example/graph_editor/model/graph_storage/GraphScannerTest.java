package com.example.graph_editor.model.graph_storage;

import static org.junit.Assert.assertEquals;

import com.example.graph_editor.model.Edge;
import com.example.graph_editor.model.Graph;
import com.example.graph_editor.model.Vertex;

import org.junit.Test;

import java.util.List;

public class GraphScannerTest {
    @Test
    public void testFromExact() throws InvalidGraphStringException {
        Graph graph = GraphScanner.fromExact("UNDIRECTED\n3 2\n0 0.0 0.0\n1 0.0 1.0\n2 1.0 1.0\n0 1\n1 2\n");

        List<Vertex> v = graph.getVertices();
        List<Edge> e = graph.getEdges();

        assertEquals(3, v.size());
        assertEquals(0f, v.get(0).getPoint().getX(), 0.0001);
        assertEquals(0f, v.get(0).getPoint().getY(), 0.0001);
        assertEquals(0f, v.get(1).getPoint().getX(), 0.0001);
        assertEquals(1f, v.get(1).getPoint().getY(), 0.0001);
        assertEquals(1f, v.get(2).getPoint().getX(), 0.0001);
        assertEquals(1f, v.get(2).getPoint().getY(), 0.0001);
        assertEquals(2, e.size());
        assertEquals(0, e.get(0).getSource().getIndex());
        assertEquals(1, e.get(0).getTarget().getIndex());
        assertEquals(1, e.get(1).getSource().getIndex());
        assertEquals(2, e.get(1).getTarget().getIndex());
    }
}
