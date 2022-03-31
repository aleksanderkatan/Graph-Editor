package com.example.graph_editor.draw.graph_view;

import static java.lang.Math.PI;
import static java.lang.Math.atan2;
import static java.lang.Math.cos;
import static java.lang.Math.sin;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.graph_editor.draw.Settings;
import com.example.graph_editor.draw.action_mode_type.ActionModeType;
import com.example.graph_editor.draw.action_mode_type.ActionModeTypeObserver;
import com.example.graph_editor.model.DrawManager;
import com.example.graph_editor.model.Edge;
import com.example.graph_editor.model.Graph;
import com.example.graph_editor.model.GraphType;
import com.example.graph_editor.model.Vertex;
import com.example.graph_editor.model.mathematics.Point;
import com.example.graph_editor.model.mathematics.Rectangle;
import com.example.graph_editor.model.state.State;
import com.example.graph_editor.model.state.StateStack;

public class GraphView extends View implements ActionModeTypeObserver {
    private final int baseVertexRadius = 7;
    private final int baseEdgeWidth = 5;

    private Paint vertexPaint;
    private double vertexRadius = baseVertexRadius;
    private Paint edgePaint;
    private boolean fixedWidth;

    private StateStack stateStack;

    private boolean interactive = false;
    private boolean isLazyInitialised = false;

    public GraphView(Context context) {
        super(context);
        init(null);
    }

    public GraphView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public GraphView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    public GraphView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    private void init(@Nullable AttributeSet set) {
        vertexPaint = new Paint();
        vertexPaint.setColor(Color.BLUE);
        vertexPaint.setStyle(Paint.Style.FILL);
        vertexPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        edgePaint = new Paint();
        edgePaint.setColor(Color.GRAY);
        edgePaint.setStyle(Paint.Style.FILL);
        edgePaint.setStrokeWidth(baseEdgeWidth);
        edgePaint.setFlags(Paint.ANTI_ALIAS_FLAG);
    }

    // !! this alone is not enough, all due to height being lazily calculated
    public void initialize(StateStack stack, boolean interactive) {
        this.interactive = interactive;
        this.stateStack = stack;
        isLazyInitialised = false;

        postInvalidate();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void lazyInitialize() {
        State currentState = stateStack.getCurrentState();
        Rectangle rec = new Rectangle(new Point(0, 0), new Point(1.0, 1.0 * getHeight() / getWidth()));
        Rectangle optimalRec = DrawManager.getOptimalRectangle(currentState.getGraph(),0.1, rec);
        currentState.setRectangle(optimalRec);
        if (interactive) {
            GraphOnTouchListener onTouchListener = new GraphOnTouchListener(getContext(), this, stateStack);
            this.setOnTouchListener(onTouchListener);
        }
        isLazyInitialised = true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (!isLazyInitialised) {   //has to be done here instead of in init or initializeGraph since height is lazily calculated
            lazyInitialize();
        }

        fixedWidth = Settings.getFixedWidth(getContext());
        State state = stateStack.getCurrentState();
        Rectangle rectangle = state.getRectangle();
        Graph graph = state.getGraph();

        vertexRadius = getDrawWidth(rectangle.getScale(), baseVertexRadius);
        edgePaint.setStrokeWidth((float)getDrawWidth(rectangle.getScale(), baseEdgeWidth));

        for (Edge e : graph.getEdges())
            drawEdge(canvas, e, DrawManager.getRelative(rectangle, e.getSource().getPoint()),
                    DrawManager.getRelative(rectangle, e.getTarget().getPoint()), graph.getType());

        for (Vertex v : graph.getVertices())
            drawVertex(canvas, v, DrawManager.getRelative(rectangle, v.getPoint()));
    }

    private void drawVertex(Canvas canvas, Vertex vertex, Point point) {
        float x = (float)point.getX()*getWidth();
        float y = (float)point.getY()*getHeight();

        canvas.drawCircle(x, y, (float)vertexRadius, vertexPaint);
    }

    private void drawEdge(Canvas canvas, Edge edge, Point start, Point end, GraphType type) {
        float x1 = (float) start.getX() * getWidth();
        float y1 = (float) start.getY() * getHeight();
        float x2 = (float) end.getX() * getWidth();
        float y2 = (float) end.getY() * getHeight();

        float dx = x2-x1;
        float dy = y2-y1;
        if (dx*dx + dy*dy < 0.1*0.1) return;

        canvas.drawLine(x1, y1, x2, y2, edgePaint);
        if (type == GraphType.DIRECTED) {
            drawArrow(edgePaint, canvas, x1, y1, x2, y2);
        }
    }

    private void drawArrow(Paint paint, Canvas canvas, float x1, float y1, float x2, float y2) {
        float radius = (float)getDrawWidth(stateStack.getCurrentState().getRectangle().getScale(), 50);
        float angle = 30;

        float angleRad= (float) (PI*angle/180.0f);
        float lineAngleRad= (float) (atan2(y2-y1,x2-x1));

        Path path = new Path();
        path.setFillType(Path.FillType.EVEN_ODD);
        path.moveTo(x2, y2);
        path.lineTo((float)(x2-radius*cos(lineAngleRad - (angleRad / 2.0))),
                (float)(y2-radius*sin(lineAngleRad - (angleRad / 2.0))));
        path.lineTo((float)(x2-radius*cos(lineAngleRad + (angleRad / 2.0))),
                (float)(y2-radius*sin(lineAngleRad + (angleRad / 2.0))));
        path.close();

        canvas.drawPath(path, paint);
    }

    private double getDrawWidth(double scale, double value) {
        double fun = fixedWidth ? 1 : scale;
        return value / fun * (getWidth()/1000.0);
    }

    @Override
    public void update(ActionModeType newType) {
        postInvalidate();
    }

    public Point getRelative(Point point) {
        return new Point(point.getX()/getWidth(), point.getY()/getHeight());
    }

    public StateStack getStateStack() { return stateStack; }

}
