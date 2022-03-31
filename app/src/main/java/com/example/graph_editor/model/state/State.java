package com.example.graph_editor.model.state;

import com.example.graph_editor.draw.action_mode_type.ActionModeType;
import com.example.graph_editor.draw.action_mode_type.ActionModeTypeObserver;
import com.example.graph_editor.model.Graph;
import com.example.graph_editor.model.mathematics.Rectangle;

import java.util.ArrayList;
import java.util.List;

public class State {
    private Graph graph;
    private Rectangle rectangle;
    private ActionModeType actionModeType;
    private boolean currentlyModified; //more accurately - isCurrentlyTouched, if true ignore button presses
    private final List<ActionModeTypeObserver> observers = new ArrayList<>();

    public State(Graph graph, Rectangle rectangle, ActionModeType actionModeType) {
        this.graph = graph;
        this.rectangle = rectangle;
        this.actionModeType = actionModeType;
    }

    public Graph getGraph() { return graph; }
    public Rectangle getRectangle() { return rectangle; }
    public ActionModeType getActionModeType() { return actionModeType; }
    public boolean isCurrentlyModified() { return currentlyModified; }

    public void setGraph(Graph graph) { this.graph = graph; }
    public void setRectangle(Rectangle rectangle) { this.rectangle = rectangle; }
    public void setCurrentModeType(ActionModeType modeType) {
        actionModeType = modeType;
        tellObservers();
    }
    public void setCurrentlyModified(boolean value) { this.currentlyModified = value; }

    public synchronized void addObserver(ActionModeTypeObserver observer) {
        observers.add(observer);
    }

    public synchronized void removeObserver(ActionModeTypeObserver observer) {
        observers.remove(observer);
    }

    private synchronized void tellObservers() {
        for (ActionModeTypeObserver o : observers) {
            o.update(actionModeType);
        }
    }
}
