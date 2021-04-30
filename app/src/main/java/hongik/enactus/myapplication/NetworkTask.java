package hongik.enactus.myapplication;

import android.net.Network;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class NetworkTask extends AsyncTask<String, Void, Void> {
    String result;
    private String url, params;

    NetworkTask(String url, String params){
        this.url = url;
        this.params = params;
    }

    @Override
    protected Void doInBackground(String ...params) {
        try {
            String url="http://www.naver.com";
            URL obj = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) obj.openConnection();

            if(conn != null){
                conn.setReadTimeout(10000); //10초 동안 기다린 후 응답 없을 시 종료
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);
                conn.setRequestProperty("Content-type", "application/json");
            }
            Log.d("LOG", "[LOGIN] 응답코드 : " + conn.getResponseCode());
            Log.d("LOG", "[LOGIN] 응답메세지 : " + conn.getResponseMessage());

            if(conn.getResponseCode() != HttpURLConnection.HTTP_OK){
                Log.d("http_test", "getResponseFail");
                return null;
            }
            InputStream is = conn.getInputStream();
            // Get the stream
            StringBuilder builder = new StringBuilder();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
            // Set the result
            result = builder.toString();

            conn.disconnect();

        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        Log.d("LOG", "[LOGIN] HTTP Request Result : "+result);
    }
}
