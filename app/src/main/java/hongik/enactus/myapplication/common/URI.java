package hongik.enactus.myapplication.common;

public class URI {
    public static final String hostName ="hongikeatme.herokuapp.com";

    public static final String registerUsers ="/api/users/register";  //Local 유저 가입
    public static final String confirmEmail ="/api/users/confirmEmail"; //이메일 인증
    public static final String login ="/api/users/login";   //로컬 유저 로그인.
    public static final String kakaoLogout ="/api/users/oauth/kakao/logout";
    public static final String kakaoLogin ="/api/users/oauth/kakao/login";
    public static final String info ="/api/users/info"; //유저 정보.
    public static final String tags ="/api/users/tags"; //유저 태그 설정.

    // 공공데이터 활용
    public static final String getDrugInfoService ="http://apis.data.go.kr/1471000/DrbEasyDrugInfoService/getDrbEasyDrugList";
    public static final String getDrugInfoService_key = "/aTAozSuBe7v6MPbUPh113p53kNS3TG7nUQQFwRsDRIInT8Vdsjvq4I0oAgD/ZgoJDXeXclHGnOBrWumVvS4Kg==";
}
