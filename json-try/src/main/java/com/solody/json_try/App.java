package com.solody.json_try;

import org.json.JSONObject;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        JSONObject jo = new JSONObject("{ \"abc\" : { \"def\": \"def\" } }");
        System.out.println(jo);
    }
}
