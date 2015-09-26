package ianlo.net.beepboop;

import android.app.ProgressDialog;
import android.os.AsyncTask;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by ianlo on 2015-09-26.
 */
public class CountdownRequest extends AsyncTask {
    MainActivity context;
    ProgressDialog pd;
    int interval = 0;
    int countdown = 0;
    boolean error = false;
    public CountdownRequest(MainActivity context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pd = new ProgressDialog(context);
        pd.setMessage("Loading...");
        pd.show();
    }

    @Override
    protected Object doInBackground(Object[] params) {
        HttpClient httpclient = new DefaultHttpClient();
        HttpResponse response;
        String responseString = null;
        try {
            response = httpclient.execute(new HttpGet("http://www.californiaclarks.com/beepboop/api.php?do=countdown"));
            StatusLine statusLine = response.getStatusLine();
            if(statusLine.getStatusCode() == HttpStatus.SC_OK){
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                response.getEntity().writeTo(out);
                responseString = out.toString();
                out.close();

                JSONObject object = new JSONObject(responseString);
                if(object.getString("status").equals("success")) {
                    JSONObject light = object.getJSONObject("light");
                    interval = light.getInt("interval");
                    countdown = light.getInt("countdown");
                }
                else {
                    error = true;
                }

            } else{
                //Closes the connection.
                response.getEntity().getContent().close();
                throw new IOException(statusLine.getReasonPhrase());
            }
        } catch (ClientProtocolException e) {
            //TODO Handle problems..
        } catch (IOException e) {
            //TODO Handle problems..
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        if(interval != 0 && countdown != 0) {
            context.setInterval(interval);
            if (countdown > context.getGreenInterval()) {
                context.startLightOff(countdown - context.getGreenInterval());
            }
            else {
                context.startLightOn(countdown);
            }

        }
        pd.dismiss();
    }
}
