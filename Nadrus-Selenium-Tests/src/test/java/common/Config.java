package common;

public class Config {
	public static final String username = System.getenv("SAUCE_USER_NAME") != null ? System.getenv("SAUCE_USER_NAME") :"zaher_mb";
	public static final String accesskey = System.getenv("SAUCE_API_KEY") != null ? System.getenv("SAUCE_API_KEY") :  "a9ec05bc-8c52-4227-a2d6-c79978cc072c";	    
	public final static int TIMEOUT=60;
	public final static String startURL="http://dev.nadrus.com";
	public static final Object[] browsers={
		   
		   new String[]{"OS X 10.10", "40.0", "firefox", null, null,"1440x900"},
		  // new String[]{"OS X 10.10", "45.0", "chrome", null, null,"1440x900"}
   };
    

}
