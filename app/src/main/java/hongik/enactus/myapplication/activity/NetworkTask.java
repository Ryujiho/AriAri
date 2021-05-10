package hongik.enactus.myapplication.activity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import hongik.enactus.myapplication.common.Parameter;
import hongik.enactus.myapplication.common.Tag;
import hongik.enactus.myapplication.common.URI;
import hongik.enactus.myapplication.common.UserInfo;

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

            if(url.contains(URI.login) | url.contains(URI.kakaoLogin)) isLogin = true;
            else if(url.contains(URI.register)) isRegister = true;

            if(!url.contains("http") || !url.contains("https")){
                url = "https://"+url;
            }

            URL obj = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) obj.openConnection();

            Log.d("LOG", "[HTTP] url : " + url);
            Log.d("LOG", "[HTTP] parameters : " + parameters.toString());


            if(conn != null){
                conn.setReadTimeout(10000); // 10초 동안 기다린 후 응답 없을 시 종료
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

            // 실제 서버로 Request 요청하는 부분 (응답코드를 받는다. 200: 성공)
            if(conn.getResponseCode() != HttpURLConnection.HTTP_OK){
                Log.d(Tag.NETWORK, "getResponseFail");
                if(isLogin) UserInfo.setResult(0);
                return null;
            }

            Log.d("LOG", "[HTTP] 응답코드 : " + conn.getResponseCode());
            Log.d("LOG", "[HTTP] 응답메세지 : " + conn.getResponseMessage());

            // Response Message 읽는 부분
            InputStream is = conn.getInputStream();
            StringBuilder builder = new StringBuilder();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
            // Set the Response result
            result = builder.toString();

            // Set UserInfo data
            if(isLogin){
                JSONObject jObject = new JSONObject(result);
                Boolean loginSuccess = jObject.getBoolean(Parameter.TOKEN); //loginSucces
                String userId = jObject.getString(Parameter.USER_ID);
                String token = jObject.getString(Parameter.TOKEN);
                String name = jObject.getString(Parameter.NAME);
                if(loginSuccess){
                    UserInfo.setResult(1);
                    UserInfo.setName(name);
                    UserInfo.setToken(token);
                    UserInfo.setUserId(userId);
                }
            }
            conn.disconnect();

        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        Log.d(Tag.NETWORK, "HTTP Request Result : "+result);

        if (UserInfo.getResult() == UserInfo.LOGIN_SUCCESS) {
            Log.d(Tag.NETWORK, " LOGIN SUCCESS " );
            Intent intent = new Intent(mContext, onBoardingActivity.class); //로그인용
            //REMOVE all previous activities
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(intent);
        }

    }
}
