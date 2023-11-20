package com.nadla777;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import java.util.Arrays;
import java.util.List;

public class StatsView extends View {

    private Paint line;
    private List<Integer> values = Arrays.asList(20, 50, 70, 35, 15);
    public StatsView(Context context, AttributeSet attrs) {
        super(context, attrs);

        line = new Paint();
        line.setColor(Color.parseColor("#5ada86"));
        line.setStrokeWidth(5f);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int lineWidth = getWidth() / values.size();
        int maxValue = getMaxValue();

        for (int i = 0; i < values.size(); i++) {
            int value = values.get(i);
            int lineHeight = (int) ((float) value / maxValue * getHeight());

            int startX = i * lineWidth + lineWidth / 2;
            canvas.drawLine(startX, getHeight(), startX, getHeight() - lineHeight, line);
        }
    }

    private int getMaxValue() {
        int max = Integer.MIN_VALUE;
        for (Integer value : values) {
            if (value > max) {
                max = value;
            }
        }
        return max;
    }

    // You can add methods to set the values in StatsView
    public void setValues(List<Integer> values) {
        this.values = values;
        invalidate(); // Request a redraw
    }
}
