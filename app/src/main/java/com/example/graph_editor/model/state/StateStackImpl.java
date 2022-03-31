package com.example.graph_editor.model.state;

import com.example.graph_editor.model.Graph;

import java.util.ArrayList;
import java.util.List;


//pointer always points towards what should be drawn right now
public class StateStackImpl implements StateStack {
    private final List<Graph> stack;
    private final State currentState;
    private final Runnable invalidateFunction;

    private int pointer;

    public StateStackImpl(Runnable invalidateFunction, State initialState, List<Graph> stack, int pointer) {
        this.invalidateFunction = invalidateFunction;
        if (stack == null) {
            stack = new ArrayList<>();
            stack.add(initialState.getGraph());
        } else {
            stack.set(pointer, initialState.getGraph());
        }
        currentState = initialState;
        this.stack = stack;
        this.pointer = pointer;
    }

    public StateStackImpl(Runnable invalidateFunction, State initialState) {
        this(invalidateFunction, initialState, null, 0);
    }

    @Override
    public State undo() {
        stack.set(pointer, currentState.getGraph());
        pointer = Math.max(pointer-1, 0);
        currentState.setGraph(stack.get(pointer));
        invalidateView();
        return currentState;
    }

    @Override
    public State redo() {
        pointer = Math.min(pointer+1, stack.size()-1);
        currentState.setGraph(stack.get(pointer));
        invalidateView();
        return currentState;
    }

//    private void put(Graph g) {
//        stack.subList(pointer + 1, stack.size()).clear();
//        stack.add(g);
//        pointer += 1;
//    }

    @Override
    public void backup() {
        Graph g = currentState.getGraph();
        if (pointer >= 1) {
            stack.subList(pointer, stack.size()).clear();
            stack.add(g.deepCopy());
            stack.add(g);
            pointer += 1;
        } else {
            stack.clear();
            stack.add(currentState.getGraph().deepCopy());
            stack.add(currentState.getGraph());
            pointer = 1;
        }
        invalidateView();
    }

    @Override
    public boolean isUndoPossible() {
        return pointer != 0;
    }

    @Override
    public boolean isRedoPossible() {
        return pointer != stack.size()-1;
    }

    @Override
    public State getCurrentState() {
        return currentState;
    }

    @Override
    public List<Graph> getGraphStack() {
        stack.set(pointer, currentState.getGraph());
        return stack;
    }

    @Override
    public int getPointer() {
        return pointer;
    }

    @Override
    public void invalidateView() {
        invalidateFunction.run();
    }
}
