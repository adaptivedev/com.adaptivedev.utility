package com.adaptivedev.utility;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by rover on 4/1/14.
 * A Wrapper to the JSON class to encapsulate exception handling.
 */
public class Jw {
    private static final String mfn = GS.mn + ": Jw:";
    private JSONObject jo;
    public JSONObject jo() { return jo; }
    public void jo(JSONObject jo)
    {
        if(jo==null)
        {
            Log.e(mfn, "jo==null");
        } else if (jo.length()==0) {
            Log.e(mfn, "jo.length()==0");
        } else {
            this.jo = jo;
        }
    }
    Jw()
    {
        jo = new JSONObject();
    }
    public int getInt(String field)
    {
        int rv = -2;
        try {
            rv = jo.getInt(field);
        } catch (JSONException e) {
            Log.e(mfn, field);
            e.printStackTrace();
        }
        return rv;
    }
    public double getDouble(String field)
    {
        double rv = -2;
        try {
            rv = jo.getDouble(field);
        } catch (JSONException e) {
            Log.e(mfn, field);
            e.printStackTrace();
        }
        return rv;
    }
    public String getString(String field)
    {
        String rv = null;
        try {
            rv = jo.getString(field);
        } catch (JSONException e) {
            Log.e(mfn, field);
            e.printStackTrace();
        }
        return rv;
    }

    static public String getString(JSONObject jo, String s)
    {
        String rv = null;
        try {
            rv = jo.getString(s);
        } catch (JSONException e) {
            String sts = GS.stackTrace();
            Log.e(mfn, sts);
        }
        return rv;
    }


    static public JSONObject getJSONObject(String string) {
        JSONObject jo=null;
        try {
            jo = new JSONObject(string);
        } catch (JSONException e)
        {
            String sts = GS.stackTrace();
            Log.e(mfn, sts);
            e.printStackTrace();
        }
        return jo;
    }

    static public JSONArray getJSONArray(String string) {
        JSONArray ja=null;
        try {
            ja = new JSONArray(string);
        } catch (JSONException e)
        {
            String sts = GS.stackTrace();
            Log.e(mfn, sts);
            e.printStackTrace();
        }
        return ja;
    }

    static public JSONArray getJSONArray(JSONObject jo, String str)
    {
        JSONArray rv = null;
        try {
            rv = jo.getJSONArray(str);
        } catch (JSONException e) {
            //if(GS.dl>0) e.printStackTrace();
        }
        return rv;
    }

    static public JSONObject getJSONObject(JSONArray ja, int i)
    {
        JSONObject jo = null;
        try {
            jo = ja.getJSONObject(i);
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e(mfn, GS.stackTrace());
        }
        return jo;
    }

    static public JSONArray getJSONArray(HttpEntity theHttpEntity)
    {
        JSONArray theJSONArray = null;
        try {
            String theEntityUtilsString = EntityUtils.toString(theHttpEntity);
            theJSONArray = new JSONArray(theEntityUtilsString);
            //if(GS.dl>3) Log.d("GRA: MainActivity", "getJSONArray=" + theJSONArray.toString());
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e(mfn, GS.stackTrace());
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(mfn, GS.stackTrace());
        }
        return theJSONArray;
    }

    static public JSONObject getJSONObject(HttpEntity theHttpEntity)
    {
        JSONObject theJSONObject = null;
        try {
            String theEntityUtilsString = EntityUtils.toString(theHttpEntity);
            theJSONObject = new JSONObject(theEntityUtilsString);
            //if(GS.dl>3) Log.d("GRA: MainActivity", "getJSONArray=" + theJSONObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e(mfn, GS.stackTrace());
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(mfn, GS.stackTrace());
        }
        return theJSONObject;
    }
}
