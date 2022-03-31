package com.example.graph_editor.model.graph_generators;

import com.example.graph_editor.model.Graph;
import com.example.graph_editor.model.GraphImpl;
import com.example.graph_editor.model.GraphType;
import com.example.graph_editor.model.Vertex;
import com.example.graph_editor.model.mathematics.Geometry;

import java.util.ArrayList;
import java.util.List;

public class GraphGeneratorCycle implements GraphGenerator {
    @Override
    public List<Parameter> getParameters() {
        List<Parameter> parameters = new ArrayList<>();
        parameters.add(new Parameter("Vertices", 3, 256));
        return parameters;
    }

    @Override
    public Graph generate(List<Integer> parameters) {
        int v = parameters.get(0);
        Graph result = new GraphImpl(GraphType.UNDIRECTED);
        for (int i = 0; i< v; i++) result.addVertex();

        List<Vertex> vertices = result.getVertices();

        for (int i = 0; i< v; i++) {
            result.addEdge(vertices.get(i), vertices.get((i+1)%v));
        }

        for (int i = 0; i< v; i++) {
            vertices.get(i).setPoint(Geometry.getPointOnCircle(i, v));
        }

        return result;
    }
}
