package com.example.graph_editor.model.graph_generators;

import com.example.graph_editor.model.Graph;
import com.example.graph_editor.model.GraphImpl;
import com.example.graph_editor.model.GraphType;
import com.example.graph_editor.model.Vertex;
import com.example.graph_editor.model.mathematics.Geometry;

import java.util.ArrayList;
import java.util.List;

public class GraphGeneratorFullBinaryTree implements GraphGenerator {
    @Override
    public List<Parameter> getParameters() {
        List<Parameter> parameters = new ArrayList<>();
        parameters.add(new Parameter("Layers", 2, 8));
        return parameters;
    }

    @Override
    public Graph generate(List<Integer> parameters) {
        int layers = parameters.get(0);
        int v = (1 << layers) - 1;
        Graph result = new GraphImpl(GraphType.UNDIRECTED);
        for (int i = 0; i< v; i++) result.addVertex();

        List<Vertex> vertices = result.getVertices();

        for (int i = 0; i< v/2; i++) {
            result.addEdge(vertices.get(i), vertices.get((i+1)*2-1));
            result.addEdge(vertices.get(i), vertices.get((i+1)*2));
        }

        for (int i = 0; i< v; i++) {
            vertices.get(i).setPoint(Geometry.getPointBinaryTree(i, layers));
        }

        return result;
    }
}
