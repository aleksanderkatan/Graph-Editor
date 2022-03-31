package com.example.graph_editor.model.graph_generators;

import com.example.graph_editor.model.Graph;
import com.example.graph_editor.model.GraphImpl;
import com.example.graph_editor.model.GraphType;
import com.example.graph_editor.model.Vertex;
import com.example.graph_editor.model.mathematics.Geometry;

import java.util.ArrayList;
import java.util.List;

public class GraphGeneratorGrid implements GraphGenerator {
    @Override
    public List<Parameter> getParameters() {
        List<Parameter> parameters = new ArrayList<>();
        parameters.add(new Parameter("Horizontal", 1, 32));
        parameters.add(new Parameter("Vertical", 1, 32));
        return parameters;
    }

    @Override
    public Graph generate(List<Integer> parameters) {
        int hor = parameters.get(0);
        int ver = parameters.get(1);
        Graph result = new GraphImpl(GraphType.UNDIRECTED);
        for (int i = 0; i< hor*ver; i++) result.addVertex();

        List<Vertex> vertices = result.getVertices();

        // horizontal edges
        for (int i = 0; i< ver; i++) {
            for (int j = 0; j< hor-1; j++) {
                result.addEdge(vertices.get(i*hor+j), vertices.get(i*hor+(j+1)));
            }
        }

        // vertical edges
        for (int i = 0; i< ver-1; i++) {
            for (int j = 0; j< hor; j++) {
                result.addEdge(vertices.get(i*hor+j), vertices.get((i+1)*hor+j));
            }
        }

        // placement
        for (int i = 0; i< ver; i++) {
            for (int j = 0; j< hor; j++) {
                vertices.get(i*hor+j).setPoint(Geometry.getGridPoint(j, i, hor, ver));
            }
        }

        return result;
    }
}
