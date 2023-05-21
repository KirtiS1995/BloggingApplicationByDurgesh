package com.codewithdurgesh.blog.payloads;

public class AppConstants {
	
	public static final String USER_DELETE= "User Deleted Successfully ..!!";

	public static final String POST_DELETE= "Post Deleted Successfully ..!!";
	
	public static final String CATEGORY_DELETE= "User Deleted Successfully ..!!";

	public static final String COMMENT_DELETE= "Comment Deleted Successfully ..!!";
	
	public static final String PAGE_NUMBER="0";
	
	public static final String PAGE_SIZE="0";
	
	public static final String SORT_DIR="asc";
	
	public static final String SORT_BY="postId";

	public static final Integer NORMAL_USER=502;

	public static final Integer ADMIN_USER=501;
	
	public static final String[] PUBLIC_URLS= {"/api/v1/auth/**","/v3/api-docs",
			"/v2/api-docs","/swagger-resources/**","/swagger-ui/**","/webjars/**"};
	
	public static final String AUTHORIZATION_HEADER="Authorization";

	public static final String ACCESS_DENY="Access Denied..!!";
	
	public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;

	public static final String INVALID_DETAILS = "Invalid Username Or Password";
}
