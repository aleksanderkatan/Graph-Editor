package com.example.graph_editor.browse;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.graph_editor.R;
import com.example.graph_editor.database.Save;
import com.example.graph_editor.database.SaveDao;
import com.example.graph_editor.database.SavesDatabase;
import com.example.graph_editor.draw.action_mode_type.ActionModeType;
import com.example.graph_editor.draw.graph_view.GraphView;
import com.example.graph_editor.draw.popups.ShareAsTxtIntent;
import com.example.graph_editor.model.graph_storage.GraphScanner;
import com.example.graph_editor.model.Graph;
import com.example.graph_editor.model.mathematics.Point;
import com.example.graph_editor.model.mathematics.Rectangle;
import com.example.graph_editor.model.state.State;
import com.example.graph_editor.model.state.StateStack;
import com.example.graph_editor.model.state.StateStackImpl;

import java.util.List;

public class SavedAdapter extends RecyclerView.Adapter<SavedAdapter.Holder> {
    Context context;
    List<Save> data;
    BrowseActivity browseActivity;
    SavedAdapter(Context context, List<Save> data, BrowseActivity browseActivity) {
        this.context = context;
        this.data = data;
        this.browseActivity = browseActivity;
    }
    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.saved_row, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.txtName.setText(data.get(position).name);
        String graphString = data.get(position).graph;
        try {
            Graph graph = GraphScanner.fromExact(graphString);
            StateStack stack = new StateStackImpl(
                () -> {},
                new State(graph, new Rectangle(new Point(0, 0), new Point(1, 1)), ActionModeType.MOVE_CANVAS)
            );
            holder.dataGraph.initialize(stack, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        holder.editButton.setOnClickListener(v -> {
            Save s = data.get(position);
            SaveDao dao = SavesDatabase.getDbInstance(context).saveDao();
            dao.updateGraph(s.uid, s.graph, System.currentTimeMillis());
            browseActivity.changeActivity(graphString, data.get(position).uid);
        });
        holder.deleteButton.setOnClickListener(v ->
                new ConfirmPopup(context, holder.dataGraph.getStateStack().getCurrentState().getGraph(), () -> {
                    Save s = data.get(position);
                    data.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, data.size());
                    SavesDatabase.getDbInstance(context).saveDao().delete(s);
                }).show()
        );
        holder.shareButton.setOnClickListener(v -> new ShareAsTxtIntent(context, holder.dataGraph.getStateStack()).show());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void update(List<Save> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    static class Holder extends RecyclerView.ViewHolder {
        TextView txtName;
        GraphView dataGraph;
        ImageButton editButton;
        ImageButton deleteButton;
        ImageButton shareButton;

        public Holder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtName);
            dataGraph = itemView.findViewById(R.id.dataGraph);
            editButton = itemView.findViewById(R.id.btnEdit);
            deleteButton = itemView.findViewById(R.id.btnDelete);
            shareButton = itemView.findViewById(R.id.btnShare);
        }
    }
}
