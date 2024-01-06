package com.nadla777;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.nadla777.managers.UserManager;

import java.util.Arrays;
import java.util.List;

public class StatsView extends View {

    private Paint line;
    //own margins for xml
    private int marginTop = 0, marginBottom = 0;
    private List<Integer> values; //= Arrays.asList(20, 50, 70, 35, 15, 25, 55, 75);

    private UserManager u_manager;

    public StatsView(Context context, AttributeSet attrs) {
        super(context, attrs);

        u_manager = new UserManager(getContext());

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.StatsView);
        marginTop = a.getDimensionPixelSize(R.styleable.StatsView_marginTop, 0);
        marginBottom = a.getDimensionPixelSize(R.styleable.StatsView_marginBottom, 0);
        a.recycle();

        line = new Paint();
        line.setColor(Color.BLACK);
        line.setStrokeWidth(10f);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        values = u_manager.get_list();

        if(values == null || values.isEmpty())
            return;

        int lineWidth = (getWidth() - 2 * marginBottom) / values.size();
        float maxValue = getMaxValue();
        int drawingHeight = getHeight() - marginTop;

        Paint textPaint = new Paint();
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(70);

        for (int i = 0; i < values.size(); i++) {
            int valueInMillis = values.get(i);
            int valueInSeconds = valueInMillis / 1000;
            int valueInMinutes = valueInSeconds / 60;

            float lineHeight = (valueInMillis / maxValue) * drawingHeight;

            int startX = i * lineWidth + marginBottom + lineWidth / 2;
            canvas.drawLine(startX, drawingHeight + marginTop, startX, drawingHeight + marginTop - lineHeight, line);

            String text;
            if (valueInMinutes > 0) {
                text = valueInMinutes + "m";
            } else {
                text = valueInSeconds + "s";
            }

            float textWidth = textPaint.measureText(text);
            canvas.drawText(text, startX - textWidth / 2, drawingHeight + marginTop - lineHeight - 20, textPaint);
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

    //functions for xml
    public void setValues(List<Integer> values) {
        this.values = values;
        invalidate();
    }

    public int getMarginTop() {
        return marginTop;
    }

    public void setMarginTop(int marginTop) {
        this.marginTop = marginTop;
        requestLayout();
    }

    public int getMarginBottom() {
        return marginBottom;
    }

    public void setMarginBottom(int marginBottom) {
        this.marginBottom = marginBottom;
        requestLayout();
    }
}
