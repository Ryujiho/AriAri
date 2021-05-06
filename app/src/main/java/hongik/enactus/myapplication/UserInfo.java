package hongik.enactus.myapplication;

public class UserInfo {
    public static final int LOGIN_SUCCESS = 1, LOGIN_FAIL = 0;

    private static int loginResult;
    private static String userName;
    private static String userId;
    //private static String userEmail;
    //private String userPassword;
    private static String userToken;

    public static int getResult() {
        return loginResult;
    }

    public static void setResult(int result) {
        loginResult = result;
    }

    public static String getName() {
        return userName;
    }

    public static void setName(String name) {
        userName = name;
    }

    public static String getUserId() {
        return userId;
    }

    public static void setUserId(String userId) {
        UserInfo.userId = userId;
    }

    public static String getToken() {
        return userToken;
    }

    public static void setToken(String token) {
        userToken = token;
    }




}
