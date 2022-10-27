package com.supportportaljsc.constant;

public class SecurityConstant {

public static final long EXPIRATION_TIME =500_111_000;
public static final String TOKEN_PREFIX="Bearer";

public static final String JWT_TOKEN_HEADER="Jwt-Token";

public static final String TOKEN_CANNOT_BE_VERIFIED="token cannot be verofied";


public static final String   J_S_C ="PUBLIC SERVICE COMMISION";

public static final String J_S_C_ADMINISTRATION="User management portal";
public static final String AUTHORITIES ="Authorities";

public static final String FORBIDDEN_MESSAGE="You need to log  to access the page";

public static final String ACCESS_DENIED_MESSAGE="You do  not have access to this page ";
public static final String OPTIONS_HTTP_METHOD="OPTIONS";
//public static final String []PUBLIC_URLS={"/user/login/","/user/register/","/user/resetpassword/**","/user/image/**"};
public static final String[] PUBLIC_URLS = { "**" };

}
