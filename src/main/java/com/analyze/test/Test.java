package com.analyze.test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URL;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

public class Test {
	public static void main(String args[]) throws Exception 
    { 
        // Returns the instance of InetAddress containing 
        // local host name and address 
        InetAddress localhost = InetAddress.getLocalHost(); 
        System.out.println("System IP Address : " + 
                      (localhost.getHostAddress()).trim()); 
  
        // Find public IP address 
        String systemipaddress = ""; 
        try
        { 
            URL url_name = new URL("http://bot.whatismyipaddress.com"); 
  
            BufferedReader sc = 
            new BufferedReader(new InputStreamReader(url_name.openStream())); 
  
            // reads system IPAddress 
            systemipaddress = sc.readLine().trim(); 
        } 
        catch (Exception e) 
        { 
            systemipaddress = "Cannot Execute Properly"; 
        } 
        System.out.println("Public IP Address: " + systemipaddress +"\n"); 
    }

	public static String getClientIpAddress(HttpServletRequest request) {
		String xForwardedForHeader = request.getHeader("X-Forwarded-For");
		if (xForwardedForHeader == null) {
			return request.getRemoteAddr();
		} else {
			// As of https://en.wikipedia.org/wiki/X-Forwarded-For
			// The general format of the field is: X-Forwarded-For: client, proxy1, proxy2
			// ...
			// we only want the client
			return new StringTokenizer(xForwardedForHeader, ",").nextToken().trim();
		}
	}
}
