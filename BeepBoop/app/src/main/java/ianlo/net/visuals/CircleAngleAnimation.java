package ianlo.net.visuals;

import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.Transformation;

public class CircleAngleAnimation extends Animation {

    private HollowCircle hollowCircle;

    private float oldAngle;
    private float newAngle;

    public CircleAngleAnimation(HollowCircle hollowCircle, int newAngle) {
        this.oldAngle = hollowCircle.getAngle();
        this.newAngle = newAngle;
        this.hollowCircle = hollowCircle;
        setInterpolator(new LinearInterpolator());
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation transformation) {
        float angle = oldAngle + ((newAngle - oldAngle) * interpolatedTime);

        hollowCircle.setAngle(angle);
        hollowCircle.requestLayout();
    }
}