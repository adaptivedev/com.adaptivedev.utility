package com.adaptivedev.utility;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;
import android.widget.ImageView;

import org.apache.http.HttpEntity;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by rover on 3/17/14.
 */
public class GS {

    static Activity currActivity;

    public static final String mn = "GRA";
    public static final String mfn = mn + ": GS:";

    private static String m_url = null;
    private static String m_server_path = null;

    //TODO: comment-out urls for launch
    public static final int dl=11; //debug level
    public static final int dev=2; // 0 = live, 1 = staging, 2 = dev
    public static final String liveBaseUrl = "http://gamerush.com:3000";
    public static final String stageBaseUrl = "http://stage.gamerush.com:3000";
    public static final String devBaseUrl = "http://dev.gamerush.com:3000";
    public static final String dev1BaseUrl = "http://10.109.171.108:3000";
    public static final String dev2BaseUrl = "http://192.168.1.11:3000";
    public static final String dev3BaseUrl = "http://172.16.113.148:3000";
    public static final String osxServerPath = "/Users/rover/Documents/Dev/proj/GameRush/server/";
    public static final String linuxServerPath = "/usr/share/nginx/html/sites/gamerushapp.com/";

    static {
        m_url = dev1BaseUrl;
        m_server_path =osxServerPath;
        //setEnvConfigs();
    }

    public GS() {
    }

    public static String url()          { return m_url; }
    public static String serverPath()   { return m_server_path; }

    public static enum EEnv {
        live,
        stage,
        dev,
        dev1,
        dev2,
        dev3
    };
    public static EEnv theEEnv = EEnv.dev1;

    public static void setEnvConfigs()
    {
        // default setup, eg, for all local dev env
        m_server_path = osxServerPath;

        // specify further for others (eg live, stage, server dev)
        switch (theEEnv) {
            case dev:
                m_url = devBaseUrl;
                m_server_path = linuxServerPath+"/dev";
            case dev1:
                m_url = dev1BaseUrl;
            case dev2:
                m_url = dev2BaseUrl;
            case dev3:
                m_url = dev3BaseUrl;
            case stage:
                m_url = stageBaseUrl;
                m_server_path = linuxServerPath+"/stage";
            case live:
                m_url = liveBaseUrl;
                m_server_path = linuxServerPath+"/live";
        }
    }
    //public static String baseUrl(){ return "http://dev.gamerushapp.com:3000"; }

    static public String getHttpEntityString(HttpEntity theHttpEntity)
    {
        String rv = null;
        try {
            rv = EntityUtils.toString(theHttpEntity);
        } catch (IOException e)
        {
            String sts = GS.stackTrace();
            Log.e(mfn, sts);
        }
        return rv;
    }

    /**
     * Updates the singleton UserInfo.getInstance()
     */
    public static void server_getUserBydId(final OnPostExecuteHandler handler)
    {
        final String url = GS.url() + "/users/dId/"+UserInfo.getInstance().m_device_id;
        HttpGetAsync theHttpGetAsync = new HttpGetAsync(new OnPostExecuteHandler() {
            @Override
            public void handlePostExecute(Object oString) {
                String theString = (String) oString;
                //JSONArray ja = Jw.getJSONArray(theString);
                //JSONObject jo = Jw.getJSONObject(ja, 0);
                JSONObject jo = Jw.getJSONObject(theString);
                if(jo!=null) {
                    UserInfo.getInstance().jo(jo);
                }
                else {
                    Log.e(mfn, "server_getUserBydId: jo==null");
                }
                handler.handlePostExecute(jo);
            }
        });
        theHttpGetAsync.execute(url);
    }


    static void displayAlertMessage(String title, String message)
    {
        GS.displayAlert(title, mfn + "err: prob with: " + message + ": stackTraceMl="+GS.stackTraceMl(), GS.currActivity);
    }

    static void displayAlertError(String title, String message)
    {
        GS.displayAlert(title, mfn + "err: prob with: " + message + ": stackTraceMl="+GS.stackTraceMl(), GS.currActivity);
    }

    static ImageView getFlag(String country)
    {
        ImageView iv = new ImageView(GS.currActivity);
        return iv;
    }

    static String stackTraceMl()
    {
        String str = stackTrace();
        str = getMl(str);
        return str;
    }

    static String getMl(String str)
    {
        char[] cs = str.toCharArray();
        int step = 50;
        //int count = (int) (cs.length / step);
        //int limit = count * step + 1;

        int j=0;
        for (int i = step - 1; i < cs.length; i++) {
            j++;
            if(j>=step) {
                cs[i] = '\n';
                j=0;
            }
        }
        String rv = new String(cs);
        return rv;
    }

    static String stackTrace()
    {
        String stackTrackToString = Thread.currentThread().getStackTrace().toString();
        return stackTrackToString;
    }

    static void displayAlert(String title, String message, Activity theActivity)
    {
        if(dl>0)
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(theActivity);
            builder.setTitle(title);
            builder.setMessage(message);
            builder.setPositiveButton("Ok",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                        }
                    });
            builder.show();
        }
    }
}
