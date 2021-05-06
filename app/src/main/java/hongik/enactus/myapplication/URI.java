package hongik.enactus.myapplication;

public class URI {
    public static final String hostName ="hongikeatme.herokuapp.com";
    public static final String register ="/api/users/register";  //Local 유저 가입
    public static final String confirmEmail ="/api/users/confirmEmail"; //이메일 인증
    public static final String login ="/api/users/login";   //로컬 유저 로그인.
    public static final String kakaoLogout ="/api/users/oauth/kakao/logout";
    public static final String kakaoLogin ="/api/users/oauth/kakao/login";
    public static final String info ="/api/users/info"; //유저 정보.
    public static final String tags ="/api/users/tags"; //유저 태그 설정.
}
