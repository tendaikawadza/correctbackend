package com.supportportaljsc.constant;

public class SecurityConstant {

    public static final long EXPIRATION_TIME= 500_000_000;
    public  static  final String TOKEN_HEADER="Bearer";
    public static final String TOKEN_PREFIX = "Bearer ";
    public  static   final String JWT_TOKEN_HEADER="Jwt-Token";
    public   static  final String  TOKEN_CANNOT_BE_VERIFIED="Token Caanot be verified";
    public    static final String  J_S_C ="Judicial Service Commision";
    public   static   final String J_S_C_ADMINISTARTION="USER MANAGEMENT PORTAL";
    public static final String AUTHORITIES ="authorities";
    public static final String FORBIDDEN_MESSAGE="You need to log to access this page ";
    public static final String  ACCESS_DENIED_MESSAGE="You do not have permision to access this page";
    public static final String   OPTIONS_HTTP_METHOD="OPTIONS";
   //public static final String [] PUBLIC_URLS= {"/user/login","/user/register","user/resetpassword/**","/user/image/**"};
   public static final String [] PUBLIC_URLS= {"/**"};




















}
