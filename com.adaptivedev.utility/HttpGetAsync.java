package com.adaptivedev.utility;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;

/**
 * Created by rover on 3/16/14.
 */
public class HttpGetAsync extends AsyncTask<String, Integer, String>
{
    static final String mfn = GS.mn + ": HttpGetAsync";
    private HttpEntity m_HttpEntity;
    private String m_HttpEntityString=null;

    private OnPostExecuteHandler m_OnPostExecuteHandler;
    public HttpGetAsync(OnPostExecuteHandler listener)
    {
        m_OnPostExecuteHandler = listener;
    }

    protected String doInBackground(String ... args)
    {
        final String url = args[0];
        //if(GS.dl>5) Log.d(mfn, "doInBackground: Thread.currentThread().getId()=" + Thread.currentThread().getId());
        m_HttpEntity = visit(url);
        m_HttpEntityString = GS.getHttpEntityString(m_HttpEntity);
        Log.d(mfn, "getHttpEntity: m_HttpEntityString="+m_HttpEntityString);
        return m_HttpEntityString;
    }

    protected void onProgressUpdate(Integer... progress) {
    }

    protected void onPostExecute(String theString) {
        //if(GS.dl>5) Log.d(mfn, "onPostExecute: Thread.currentThread().getId()=" + Thread.currentThread().getId());
        m_OnPostExecuteHandler.handlePostExecute(m_HttpEntityString);
    }

    public HttpEntity visit(String url)
    {
        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpGet theHttpGet = new HttpGet(url);
        HttpEntity theHttpEntity = null;
        try {
            theHttpEntity = getHttpEntity(httpClient, theHttpGet);
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(mfn, "visit: e="+e);
        }
        return theHttpEntity;
    }

    public HttpEntity getHttpEntity(HttpClient httpClient, HttpGet theHttpGet) throws IOException
    {
        //if(GS.dl>5) Log.d(mfn, "getHttpEntity: theHttpGet.getURI()=" + theHttpGet.getURI());
        HttpResponse theHttpResponse = null;
        theHttpResponse = httpClient.execute(theHttpGet);
        m_HttpEntity = theHttpResponse.getEntity();
        return m_HttpEntity;
    }
}
