package hongik.enactus.myapplication;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Network;
import android.os.AsyncTask;
import android.util.Log;

import com.kakao.usermgmt.response.model.User;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class NetworkTask extends AsyncTask<String, Void, Void> {
    String result;
    private String url;
    private JSONObject parameters;
    private Context mContext;

    NetworkTask(String url, JSONObject params){
        this.url = url;
        this.parameters = params;
    }

    public void setContext(Context context) {
        this.mContext = context;
    }

    @Override
    protected Void doInBackground(String ...params) {
        try {
            boolean isLogin = false, isRegister = false;
            if(url.contains(URI.login)) isLogin = true;
            else if(url.contains(URI.register)) isRegister = true;

            Log.d("LOG", "[HTTP] url : " + url);
            Log.d("LOG", "[HTTP] parameters : " + parameters.toString());

            if(!url.contains("http") || !url.contains("https")){
                url = "https://"+url;
            }


            URL obj = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) obj.openConnection();


            if(conn != null){
                conn.setReadTimeout(10000); //10초 동안 기다린 후 응답 없을 시 종료
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);
                conn.setRequestProperty("Content-type", "application/json");
                conn.setRequestProperty("Accept", "application/json");

                // Request Body에 Data를 담기 위해 OutputStream 객체를 생성
                OutputStream os = null;
                os = conn.getOutputStream();
                os.write(parameters.toString().getBytes("utf-8"));
                os.flush(); os.close();
            }
            Log.d("LOG", "[HTTP] 응답코드 : " + conn.getResponseCode());
            Log.d("LOG", "[HTTP] 응답메세지 : " + conn.getResponseMessage());

            // 실제 서버로 Request 요청하는 부분 (응답코드를 받는다. 200: 성공)
            if(conn.getResponseCode() != HttpURLConnection.HTTP_OK){
                if(isLogin) UserInfo.setResult(0);

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

            if(isLogin){
                //HTTP Request Result : {"loginSucces":true,
                // "userId":"6091289d08a8620015f1091e",
                // "token":"eyJhbGciOiJIUzI1NiJ9.NjA5MTI4OWQwOGE4NjIwMDE1ZjEwOTFl.K0vUhGoju9UTgZPpzOMjZz3pWoz5IJBa-aOXUliCLXI","
                // name":"jiho_Google",
                // "avator":""}
                JSONObject jObject = new JSONObject(result);
                Boolean loginSuccess = jObject.getBoolean("loginSucces");
                String userId = jObject.getString("userId");
                String token = jObject.getString("token");
                String name = jObject.getString("name");
                if(loginSuccess){
                    UserInfo.setResult(1);
                    UserInfo.setName(name);
                    UserInfo.setToken(token);
                    UserInfo.setUserId(userId);
                }
            }

            // 접속 해지
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

        if (UserInfo.getResult() == UserInfo.LOGIN_SUCCESS) {
            Log.d("LOG", "[LOGIN] MAIN SUCCESS : " );
            Intent intent = new Intent(mContext, FragmentActivity.class);
            //REMOVE all previous activities
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(intent);
        }

    }
}
