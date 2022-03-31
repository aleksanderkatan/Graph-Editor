package com.example.graph_editor.browse;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.example.graph_editor.R;
import com.example.graph_editor.draw.action_mode_type.ActionModeType;
import com.example.graph_editor.draw.graph_view.GraphView;
import com.example.graph_editor.model.Graph;
import com.example.graph_editor.model.mathematics.Point;
import com.example.graph_editor.model.mathematics.Rectangle;
import com.example.graph_editor.model.state.State;
import com.example.graph_editor.model.state.StateStack;
import com.example.graph_editor.model.state.StateStackImpl;

public class ConfirmPopup {
    Context context;
    Graph graph;
    Runnable deleteFunction;

    AlertDialog dialog;

    ConfirmPopup(Context context, Graph graph, Runnable deleteFunction) {
        this.context = context;
        this.graph = graph;
        this.deleteFunction = deleteFunction;
    }

    public void show() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = (LayoutInflater)context.getSystemService( Context.LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.confirm_popup, null);

        GraphView graphView = popupView.findViewById(R.id.graphView);
        Button btnYes = popupView.findViewById(R.id.btn_yes);
        Button btnNo = popupView.findViewById(R.id.btn_no);

        StateStack stack = new StateStackImpl(
                () -> {},
                new State(graph, new Rectangle(new Point(0, 0), new Point(1, 1)), ActionModeType.MOVE_CANVAS)
        );
        graphView.initialize(stack, false);

        btnYes.setOnClickListener(v -> {
            deleteFunction.run();
            dialog.dismiss();
        });
        btnNo.setOnClickListener(v -> dialog.dismiss());

        builder.setView(popupView);
        dialog = builder.create();
        dialog.show();
    }
}
