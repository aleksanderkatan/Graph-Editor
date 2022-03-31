package com.example.graph_editor.model.graph_storage;

import static org.junit.Assert.assertEquals;

import com.example.graph_editor.model.Graph;
import com.example.graph_editor.model.GraphFactory;
import com.example.graph_editor.model.GraphType;
import com.example.graph_editor.model.Vertex;
import com.example.graph_editor.model.mathematics.Point;

import org.junit.Test;

import java.util.List;

public class GraphWriterTest {
    @Test
    public void testToVEDirected() {
        Graph graph = new GraphFactory(GraphType.DIRECTED).produce();
        graph.addVertex();
        graph.addVertex();
        graph.addVertex();
        List<Vertex> l = graph.getVertices();
        graph.addEdge(l.get(0), l.get(1));
        graph.addEdge(l.get(2), l.get(1));

        String graphString = GraphWriter.toVE(graph);

        assertEquals("3 2\n0 1\n2 1\n", graphString);
    }

    @Test
    public void testToVEUndirected() {
        Graph graph = new GraphFactory(GraphType.UNDIRECTED).produce();
        graph.addVertex();
        graph.addVertex();
        graph.addVertex();
        List<Vertex> l = graph.getVertices();
        graph.addEdge(l.get(0), l.get(1));
        graph.addEdge(l.get(2), l.get(1));

        String graphString = GraphWriter.toVE(graph);

        assertEquals("3 2\n0 1\n1 2\n", graphString);
    }

    @Test
    public void testToExact() {
        Graph graph = new GraphFactory(GraphType.UNDIRECTED).produce();
        graph.addVertex();
        graph.addVertex();
        graph.addVertex();
        List<Vertex> l = graph.getVertices();
        l.get(0).setPoint(new Point(0f, 0f));
        l.get(1).setPoint(new Point(0f, 1f));
        l.get(2).setPoint(new Point(1f, 1f));
        graph.addEdge(l.get(0), l.get(1));
        graph.addEdge(l.get(2), l.get(1));

        String graphString = GraphWriter.toExact(graph);

        assertEquals("UNDIRECTED\n3 2\n0 0.0 0.0\n1 0.0 1.0\n2 1.0 1.0\n0 1\n1 2\n", graphString);
    }

}
