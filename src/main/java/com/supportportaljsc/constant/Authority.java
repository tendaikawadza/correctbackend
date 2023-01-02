package com.supportportaljsc.constant;

public class Authority {

    public static final String[]USER_AUTHORITIES={"user:read","stock:read"};
    public static final String[]ADMIN_AUTHORITIES={"user:read","user:update","stock:read","stock:update"};
    public static final String[] HR_AUTHORITIES = { "user:read", "user:update" };
    public static final String[] MANAGER_AUTHORITIES = { "user:read", "user:update" };
    public static final String[] SUPER_ADMIN_AUTHORITIES = { "user:read", "user:create", "user:update", "user:delete" };





}