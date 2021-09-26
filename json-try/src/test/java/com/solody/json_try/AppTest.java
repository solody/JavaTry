package com.solody.json_try;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.json.JSONObject;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
        JSONObject jo = new JSONObject("""
                {
                    "a": "123",
                    "b": "456",
                    "c": {"a": "1", "b": "2"},
                    "d": [
                        {"a": "1", "b": "2"},
                        {"a": "1", "b": "2"},
                        {"a": "1", "b": "2"}
                    ],
                    "e": {},
                    "f": [],
                    "g": "01ww",
                    "h": true
                }
                """);
        assertEquals("123", jo.get("a"));
        assertEquals("org.json.JSONObject", jo.get("c").getClass().getName());
        assertEquals("org.json.JSONArray", jo.get("d").getClass().getName());
        assertEquals("org.json.JSONObject", jo.get("e").getClass().getName());
        assertEquals("org.json.JSONArray", jo.get("f").getClass().getName());
        assertEquals("java.lang.String", jo.get("g").getClass().getName());
        assertEquals("java.lang.Boolean", jo.get("h").getClass().getName());
    }
}
