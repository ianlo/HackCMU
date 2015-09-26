package ianlo.net.beepboop;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import ianlo.net.visuals.CircleAngleAnimation;
import ianlo.net.visuals.HollowCircle;

public class MainActivity extends AppCompatActivity {
    private HollowCircle hollowCircle;
    private ImageView greenCircle;
    private TextView goTV;
    private long startTime = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_main);


        hollowCircle = (HollowCircle) findViewById(R.id.circle);
        hollowCircle.setTextView((TextView) findViewById(R.id.timeTV));
        greenCircle = (ImageView) findViewById(R.id.greenCircle);
        goTV = (TextView) findViewById(R.id.goTV);

        startLightOff(60);

        Button b = (Button) findViewById(R.id.animateButton);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startLightOff(3);
            }
        });

    }

    public void startLightOff(int seconds) {
        hollowCircle.setGreen(false);
        hollowCircle.restart(seconds);
        hollowCircle.setAlpha(1);
        hollowCircle.setAngle(seconds * 6);

        CircleAngleAnimation animation = new CircleAngleAnimation(hollowCircle, 0);
        animation.setDuration(seconds * 1000);
        animation.setInterpolator(new LinearInterpolator());
        Animation fadeOut = new AlphaAnimation(1, 0);
        fadeOut.setStartOffset(seconds * 1000);
        fadeOut.setDuration(500);

        Animation fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setStartOffset(seconds * 1000 - 1000);
        fadeIn.setDuration(1000);

        fadeIn.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                startLightOn(8);
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
        greenCircle.setAlpha(0f);
        goTV.startAnimation(as2);
    }

    public void startLightOn(int seconds) {
        hollowCircle.setAlpha(1);
        Animation fadeOut = new AlphaAnimation(1, 0);
        fadeOut.setStartOffset(seconds * 1000 - 3000);
        fadeOut.setDuration(3000);

        fadeOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                startLightOff(60);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        AnimationSet as2 = new AnimationSet(true);
        as2.addAnimation(fadeOut);
        hollowCircle.startAnimation(as2);
        goTV.startAnimation(as2);
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
}
