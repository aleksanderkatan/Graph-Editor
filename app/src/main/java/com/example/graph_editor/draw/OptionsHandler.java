package com.example.graph_editor.draw;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.MenuItem;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;

import com.example.graph_editor.R;
import com.example.graph_editor.draw.graph_view.GraphView;
import com.example.graph_editor.draw.popups.GeneratePopup;
import com.example.graph_editor.draw.popups.ImportFromTxtPopup;
import com.example.graph_editor.draw.popups.SavePopup;
import com.example.graph_editor.draw.popups.SettingsPopup;
import com.example.graph_editor.draw.popups.ShareAsTxtIntent;
import com.example.graph_editor.model.DrawManager;
import com.example.graph_editor.model.graph_generators.GraphGeneratorBipartiteClique;
import com.example.graph_editor.model.graph_generators.GraphGeneratorClique;
import com.example.graph_editor.model.graph_generators.GraphGeneratorCycle;
import com.example.graph_editor.model.graph_generators.GraphGeneratorFullBinaryTree;
import com.example.graph_editor.model.graph_generators.GraphGeneratorGrid;
import com.example.graph_editor.model.graph_generators.GraphGeneratorKingGrid;
import com.example.graph_editor.model.mathematics.Rectangle;
import com.example.graph_editor.model.state.State;
import com.example.graph_editor.model.state.StateStack;

public class OptionsHandler {
    @SuppressLint("NonConstantResourceId")
    public static boolean handle(@NonNull MenuItem item, DrawActivity context, StateStack stateStack,
                                 GraphView graphView, Runnable makeSave,
                                 ActivityResultLauncher<Intent> importActivityResultLauncher,
                                 ActivityResultLauncher<Intent> exportActivityResultLauncher) {
        switch (item.getItemId()) {
            case R.id.options_btn_save:
                makeSave.run();
                return true;
            case R.id.options_btn_redo:
                stateStack.redo();
                graphView.postInvalidate();
                return true;
            case R.id.options_btn_undo:
                stateStack.undo();
                graphView.postInvalidate();
                return true;
            //more actions
            case R.id.options_btn_clear:
                stateStack.backup();
                stateStack.getCurrentState().getGraph().getVertices().clear();
                graphView.postInvalidate();
                return true;
//            case R.id.options_btn_normalize:
//                stateStack.backup();
//                State state = stateStack.getCurrentState();
//                DrawManager.normalizeGraph(state.getGraph());
//                Rectangle newRectangle = DrawManager.getOptimalRectangle(state.getGraph(), 0.1, state.getRectangle());
//                state.setRectangle(new Rectangle(newRectangle, 1.2));
//                graphView.postInvalidate();
//                return true;
            case R.id.options_btn_recenter:
                State state1 = stateStack.getCurrentState();
                Rectangle newRectangle1 = DrawManager.getOptimalRectangle(state1.getGraph(), 0.1, state1.getRectangle());
                state1.setRectangle(newRectangle1);
                graphView.postInvalidate();
                return true;
            case R.id.options_btn_settings:
                new SettingsPopup(context, graphView::postInvalidate).show();
                return true;
            case R.id.options_btn_save_as:
                new SavePopup().show(stateStack.getCurrentState().getGraph(), context, ()->{});
                return true;
            case R.id.options_btn_export_txt:
                new ShareAsTxtIntent(context, stateStack).show();
                return true;
            case R.id.options_btn_import_txt:
                new ImportFromTxtPopup(context, stateStack).show();
                return true;
            case R.id.options_btn_export_file:
                Intent exportAsFileIntent = new Intent(Intent.ACTION_CREATE_DOCUMENT);
                exportAsFileIntent.setType("text/plain");
                exportAsFileIntent.addCategory(Intent.CATEGORY_OPENABLE);
                exportAsFileIntent = Intent.createChooser(exportAsFileIntent, "Choose where to save the graph");
                exportActivityResultLauncher.launch(exportAsFileIntent);
                return true;
            case R.id.options_btn_import_file:
                Intent importFromFileIntent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                importFromFileIntent.setType("text/plain");

                importFromFileIntent = Intent.createChooser(importFromFileIntent, "Choose file containing a graph");
                importActivityResultLauncher.launch(importFromFileIntent);
                return true;
            //generate graph
            case R.id.generate_btn_cycle:
                new GeneratePopup(context, stateStack, new GraphGeneratorCycle()).show();
                return true;
            case R.id.generate_btn_clique:
                new GeneratePopup(context, stateStack, new GraphGeneratorClique()).show();
                return true;
            case R.id.generate_btn_bipartite_clique:
                new GeneratePopup(context, stateStack, new GraphGeneratorBipartiteClique()).show();
                return true;
            case R.id.generate_btn_full_binary_tree:
                new GeneratePopup(context, stateStack, new GraphGeneratorFullBinaryTree()).show();
                return true;
            case R.id.generate_btn_grid:
                new GeneratePopup(context, stateStack, new GraphGeneratorGrid()).show();
                return true;
            case R.id.generate_btn_king_grid:
                new GeneratePopup(context, stateStack, new GraphGeneratorKingGrid()).show();
                return true;
            default:
                return false;
        }
    }
}
