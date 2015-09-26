package ianlo.net.audiodetection;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    TextView textView;
    int average_buffer = 0;
    Button beepBoopButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.textview);
        beepBoopButton = (Button) findViewById(R.id.beepBoopButton);
        beepBoopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ResetRequest rr = new ResetRequest(MainActivity.this);
                rr.execute();
            }
        });
        final int minSize = AudioRecord.getMinBufferSize(8000, AudioFormat.CHANNEL_CONFIGURATION_MONO, AudioFormat.ENCODING_PCM_16BIT);
        final AudioRecord ar = new AudioRecord(MediaRecorder.AudioSource.MIC, 8000,AudioFormat.CHANNEL_CONFIGURATION_MONO, AudioFormat.ENCODING_PCM_16BIT,minSize);
        final short[] buffer = new short[minSize];
        ar.startRecording();
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                ar.read(buffer, 0, minSize);
                average_buffer = 0;
                for (int i = 0; i < minSize; i++) {
                    average_buffer += (int) Math.sqrt((float) buffer[i] * buffer[i]);
                }
                if(average_buffer > 1500000) {
                    ResetRequest rr = new ResetRequest(MainActivity.this);
                    rr.execute();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        textView.setText(average_buffer + "");
                    }
                });
            }

        }, 0, 1000);
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
