package com.example.graph_editor.draw;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;

import com.example.graph_editor.model.graph_storage.GraphScanner;
import com.example.graph_editor.model.graph_storage.GraphWriter;
import com.example.graph_editor.model.graph_storage.InvalidGraphStringException;
import com.example.graph_editor.model.DrawManager;
import com.example.graph_editor.model.Graph;
import com.example.graph_editor.model.mathematics.Rectangle;
import com.example.graph_editor.model.state.State;
import com.example.graph_editor.model.state.StateStack;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

public class ImportExportLaunchers {
    public static void importCommand(ActivityResult result, Context context, StateStack stateStack) {
        if( result.getResultCode() != Activity.RESULT_OK || result.getData() == null)
            return;
        Uri uri = result.getData().getData();
        try {
            OutputStream outputStream = context.getContentResolver().openOutputStream(uri);
            outputStream.write(GraphWriter.toExact(stateStack.getCurrentState().getGraph()).getBytes());
            outputStream.close();
        } catch (Exception e) {
            Toast.makeText(context, "Invalid text", Toast.LENGTH_SHORT).show();
            return;
        }
        Toast.makeText(context, "Export complete", Toast.LENGTH_SHORT).show();
    }
    public static void exportCommand(ActivityResult result, Context context, StateStack stateStack) {
        if( result.getResultCode() != Activity.RESULT_OK || result.getData() == null)
            return;
        Uri uri = result.getData().getData();
        Graph g;
        try {
            String content;
            try {
                InputStream in = context.getContentResolver().openInputStream(uri);
                BufferedReader r = new BufferedReader(new InputStreamReader(in));
                StringBuilder total = new StringBuilder();
                for (String line; (line = r.readLine()) != null; ) {
                    total.append(line).append('\n');
                }
                content = total.toString();
                System.out.println(content);
            }catch (Exception e) {
                Toast.makeText(context, "Invalid text", Toast.LENGTH_SHORT).show();
                return;
            }
            g = GraphScanner.fromExact(content);
        } catch (InvalidGraphStringException e) {
            Toast.makeText(context, "Invalid graph", Toast.LENGTH_SHORT).show();
            return;
        }
        stateStack.backup();
        Rectangle oldRec = stateStack.getCurrentState().getRectangle();
        Rectangle optimalRec = DrawManager.getOptimalRectangle(g, 0.1, oldRec);
        State currentState = stateStack.getCurrentState();
        currentState.setGraph(g);
        currentState.setRectangle(optimalRec);
        stateStack.invalidateView();

        Toast.makeText(context, "Import complete", Toast.LENGTH_SHORT).show();
    }
}
