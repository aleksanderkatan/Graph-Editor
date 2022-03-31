package com.example.graph_editor.model.graph_generators;

import com.example.graph_editor.model.Graph;
import com.example.graph_editor.model.GraphImpl;
import com.example.graph_editor.model.GraphType;
import com.example.graph_editor.model.Vertex;
import com.example.graph_editor.model.mathematics.Geometry;

import java.util.ArrayList;
import java.util.List;

public class GraphGeneratorBipartiteClique implements GraphGenerator {
    @Override
    public List<Parameter> getParameters() {
        List<Parameter> parameters = new ArrayList<>();
        parameters.add(new Parameter("Left vertices", 1, 32));
        parameters.add(new Parameter("Right vertices", 1, 32));
        return parameters;
    }

    @Override
    public Graph generate(List<Integer> parameters) {
        int left = parameters.get(0);
        int right = parameters.get(1);
        Graph result = new GraphImpl(GraphType.UNDIRECTED);
        for (int i = 0; i< left+right; i++) result.addVertex();

        List<Vertex> vertices = result.getVertices();

        for (int i = 0; i< left; i++) {
            for (int j = left; j<left+right; j++) {
                result.addEdge(vertices.get(i), vertices.get(j));
            }
        }

        for (int i = 0; i< left+right; i++) {
            vertices.get(i).setPoint(Geometry.getPointBipartite(i, left, right));
        }

        return result;
    }
}
