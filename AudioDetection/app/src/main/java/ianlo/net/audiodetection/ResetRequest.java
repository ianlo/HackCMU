package ianlo.net.audiodetection;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by ianlo on 2015-09-26.
 */
public class ResetRequest extends AsyncTask {
    ProgressDialog pd;
    Context context;
    String responseString;
    public ResetRequest(Context context) {
        this.context = context;
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        ((Activity) context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                long time = Calendar.getInstance().getTimeInMillis()/1000;
                Toast.makeText(context, "Sending request... " + time, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected Object doInBackground(Object[] params) {
        // Create a new HttpClient and Post Header
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost("http://www.cmubeepboop.com/api.php?do=reset");

        try {
            long time = Calendar.getInstance().getTimeInMillis()/1000;
            // Add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
            nameValuePairs.add(new BasicNameValuePair("time", time+""));
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);

        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
        } catch (IOException e) {
            // TODO Auto-generated catch block
        }
        return null;
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
    }
}
