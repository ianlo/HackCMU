package ianlo.net.beepboop;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;
import android.widget.TextView;

import ianlo.net.visuals.CircleAngleAnimation;
import ianlo.net.visuals.HollowCircle;

public class MainActivity extends AppCompatActivity {
    private HollowCircle hollowCircle;
    private TextView goTV;
    private int interval = 0;
    private int greenInterval = 32;
    private TextView timeTV;
    private TextView untilNextTV;
    private TextView lightGreenTV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_main);


        hollowCircle = (HollowCircle) findViewById(R.id.circle);
        timeTV = (TextView) findViewById(R.id.timeTV);
        hollowCircle.setTextView(timeTV);
        goTV = (TextView) findViewById(R.id.goTV);
        untilNextTV = (TextView) findViewById(R.id.untilNextTV);
        lightGreenTV = (TextView) findViewById(R.id.lightGreenTV);
    }

    @Override
    protected void onResume() {
        super.onResume();
        CountdownRequest cr = new CountdownRequest(this);
        cr.execute();
    }

    public void startLightOff(int seconds) {
        hollowCircle.setGreen(false);
        hollowCircle.restart(seconds);
        hollowCircle.setAlpha(1);
        hollowCircle.setAngle((int) (seconds / (double) (interval - greenInterval) * 360));

        CircleAngleAnimation animation = new CircleAngleAnimation(hollowCircle, 0);
        animation.setDuration(seconds * 1000);
        animation.setInterpolator(new LinearInterpolator());


        Animation fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setStartOffset(seconds * 1000 - 1000);
        fadeIn.setDuration(1000);

        fadeIn.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                startLightOn(greenInterval);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        AnimationSet animationSet = new AnimationSet(true);
        animationSet.addAnimation(animation);
        hollowCircle.startAnimation(animationSet);
        AnimationSet as2 = new AnimationSet(true);
        as2.addAnimation(fadeIn);
        goTV.startAnimation(as2);
        lightGreenTV.startAnimation(as2);

        Animation fadeOut = new AlphaAnimation(1, 0);
        fadeOut.setStartOffset(seconds * 1000-1000);
        fadeOut.setDuration(1000);
        AnimationSet as3 = new AnimationSet(true);
        as3.addAnimation(fadeOut);
        timeTV.startAnimation(as3);
        untilNextTV.startAnimation(as3);
    }

    public void startLightOn(int seconds) {
        hollowCircle.setAlpha(1);
        timeTV.setText(interval - greenInterval + "");

        Animation fadeOut = new AlphaAnimation(1, 0);
        fadeOut.setStartOffset(seconds * 1000 - 3000);
        fadeOut.setDuration(3000);

        fadeOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                startLightOff(interval - greenInterval);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });

        AnimationSet as2 = new AnimationSet(true);
        as2.addAnimation(fadeOut);
        hollowCircle.startAnimation(as2);
        goTV.startAnimation(as2);
        lightGreenTV.startAnimation(as2);

        Animation fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setStartOffset(seconds * 1000 - 3000);
        fadeIn.setDuration(3000);
        AnimationSet as3 = new AnimationSet(true);
        as3.addAnimation(fadeIn);
        timeTV.startAnimation(as3);
        untilNextTV.startAnimation(as3);
    }
    public void handleError() {

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void setInterval(int interval) {
        this.interval = interval;
    }

    public int getGreenInterval() {
        return greenInterval;
    }

    public void setGreenInterval(int greenInterval) {
        this.greenInterval = greenInterval;
    }
}
