package hongik.enactus.myapplication.common;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import hongik.enactus.myapplication.R;
import hongik.enactus.myapplication.activity.HomeActivity;

public class NetworkTask extends AsyncTask<String, Void, Void> {
    private boolean isLogin = false, isRegister = false, isGetDrugInfo = false;

    String result;
    private String url, mType;
    private JSONObject parameters;
    private Context mContext;

    public NetworkTask(String url, JSONObject params){
        super();
        this.url = url;
        this.parameters = params;
    }

    public void setContext(Context context) {
        this.mContext = context;
    }

    public void setRequestType(String type) {
        this.mType = type;
    }

    @Override
    protected Void doInBackground(String ...params) {

        try {
            if(url.contains(URI.login) | url.contains(URI.kakaoLogin)) isLogin = true;
            else if(url.contains(URI.registerUsers)) isRegister = true;
            else if(url.contains(URI.getDrugInfoService)) isGetDrugInfo = true;

            if(!url.contains("http") && !url.contains("https")){
                url = "https://"+url;
            }

            URL obj = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) obj.openConnection();

            Log.d("LOG", "[HTTP] url : " + url);
            Log.d("LOG", "[HTTP] parameters : " + parameters.toString());


            if(conn != null){
                conn.setReadTimeout(10000); // 10초 동안 기다린 후 응답 없을 시 종료
                conn.setConnectTimeout(15000);
                conn.setRequestMethod(mType);
                if(mType.equals("POST")){
                    conn.setDoOutput(true);
                    conn.setDoInput(true);
                }
                conn.setRequestProperty("Content-type", "application/json");
                conn.setRequestProperty("Accept", "application/json");

                if(parameters.length() != 0){
                    // Request Body에 Data를 담기 위해 OutputStream 객체를 생성
                    OutputStream os = conn.getOutputStream();
                    os.write(parameters.toString().getBytes("utf-8"));
                    os.flush(); os.close();
                }
            }

            // 실제 서버로 Request 요청하는 부분 (응답코드를 받는다. 200: 성공)
            if(conn.getResponseCode() != HttpURLConnection.HTTP_OK){
                Log.d(Tag.NETWORK, "getResponseFail");
                if(isLogin) UserInfo.setResult(0);
                if(isRegister) UserInfo.setRegisterResult(0);
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
                Boolean loginSuccess = jObject.getBoolean(Parameter.LOGIN_SUCCESS); //loginSucces
                if(loginSuccess){
                    String userId = jObject.getString(Parameter.USER_ID);
                    String token = jObject.getString(Parameter.TOKEN);
                    String name = jObject.getString(Parameter.NAME);
                    UserInfo.setResult(1);
                    UserInfo.setName(name);
                    UserInfo.setToken(token);
                    UserInfo.setUserId(userId);
                } else {
                    String message = jObject.getString(Parameter.MESSAGE);
                    Log.e(Tag.TEST, message);
                    UserInfo.setResult(0);
                }

            } else if(isRegister){
                JSONObject jObject = new JSONObject(result);
                Boolean result = jObject.getBoolean(Parameter.SUCCESS);
                if(result){
                    UserInfo.setRegisterResult(1);
                } else {
                    UserInfo.setRegisterResult(0);
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
        Log.d(Tag.NETWORK, "HTTP 응답 결과 : "+result);

        int loginResult = UserInfo.getResult();
        int registerResult = UserInfo.getRegisterResult();

        if (isLogin && loginResult == UserInfo.LOGIN_SUCCESS) {
            Log.d(Tag.NETWORK, " LOGIN SUCCESS " );
            Intent intent = new Intent(mContext, HomeActivity.class); //로그인용
            //REMOVE all previous activities
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(intent);

        } else if(isLogin && loginResult == UserInfo.LOGIN_FAIL){
            AlertDialog.Builder builder = new AlertDialog.Builder(mContext, R.style.ErrorDialogTheme);
            builder.setMessage(R.string.alert_login_error)
                    .setTitle(R.string.alert_login_error_title)
                    .setPositiveButton("확인", null)
                    .setCancelable(false);
            AlertDialog alert = builder.show();

        }

        if(isRegister && registerResult == UserInfo.REGISTER_FAIL){
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext, R.style.ErrorDialogTheme);
                builder.setMessage(R.string.alert_register_error_email)
                        .setTitle(R.string.alert_register_error_title)
                        .setPositiveButton("확인", null)
                        .setCancelable(false);
                AlertDialog alert = builder.show();
        }
        if(isGetDrugInfo){
            List<String> drug_list = new ArrayList<>();

            try {
                JSONObject jObject = new JSONObject(result);
                JSONObject body = jObject.getJSONObject(Parameter.BODY);
                JSONArray items = body.getJSONArray(Parameter.ITEMS);
                for(int i=0;i<items.length();i++){
                    JSONObject item = (JSONObject) items.get(i);
                    String itemName = item.getString(Parameter.ITEM_NAME);
                    drug_list.add(itemName);
                }
            } catch (Exception e){
                e.printStackTrace();
            }
            DrugInfo.setList(drug_list);

            Log.e(Tag.TEST, drug_list.toString());
        }


    }
}
