package ianlo.net.visuals;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import java.util.Calendar;

/**
 * Created by ianlo on 2015-09-25.
 */
public class HollowCircle extends View {

    private Paint paint;
    private RectF rect;
    private final int strokeWidth = 10;
    private float angle;
    private int viewWidth = 0;
    private TextView textView = null;
    private long startTime = 0;
    private int length = 0;
    private boolean green = false;

    public HollowCircle(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(strokeWidth);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setColor(Color.parseColor("#ffffffff"));
        ViewTreeObserver viewTreeObserver = getViewTreeObserver();
        final ViewTreeObserver observer = getViewTreeObserver();
        observer.addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        viewWidth = getWidth() - 20;
                        //size 200x200 example
                        rect = new RectF(strokeWidth, strokeWidth, viewWidth + strokeWidth, viewWidth + strokeWidth);
                    }
                }
        );

        restart(60);

        //Initial Angle (optional, it can be zero)
        angle = 0;
    }

    public void setAngle(int angle) {
        this.angle = angle;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int timeLeft = length * 1000 - (int) (Calendar.getInstance().getTimeInMillis() - startTime);

        if(textView != null && timeLeft > 0) {
            textView.setText(((int) timeLeft / 100)/10.0 + "");
        }
        else if (timeLeft <= 0) {
            textView.setText("");
        }

        canvas.drawArc(rect, 270, 360 - angle, false, paint);

    }

    public float getAngle() {
        return angle;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }

    public void restart(int length) {
        clearAnimation();
        startTime = Calendar.getInstance().getTimeInMillis();
        this.length = length;
    }
    public void setTextView(TextView textView) {
        this.textView = textView;
    }

    public boolean isGreen() {
        return green;
    }

    public void setGreen(boolean green) {
        this.green = green;
    }
}