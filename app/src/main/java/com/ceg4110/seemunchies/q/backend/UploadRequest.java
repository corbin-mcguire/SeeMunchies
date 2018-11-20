package com.ceg4110.seemunchies.q.backend;

import android.os.StrictMode;

import java.net.MalformedURLException;
import java.net.URL;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class UploadRequest implements RequestToFlask
{
    private URL url;
    private String encoded;

    public UploadRequest(String encoded) throws MalformedURLException
    {
        this.encoded = encoded;
        url = new URL("http://18.224.114.186:5000/json_post");
    }

    public void enableStrictMode()
    {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    public void post() throws Exception
    {
        enableStrictMode();
        //System.out.println("Request executing process begins");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        //System.out.println("URLConnectionMade");
        con.setRequestMethod("POST");
        //System.out.println("Request method set");
        con.setRequestProperty("Content-Type", "application/json");
        //System.out.println("Request property set");
        this.encoded = this.encoded.replace("\n", "").replaceAll("\r", "");
        //System.out.println("Encoded string modified");
        //System.out.println(encoded);
        this.encoded = "Quinn";
        String postJsonData = "{\"value\":\"" + this.encoded + "\"}";
        //System.out.println("Request payload made.");

        con.setDoOutput(true);
        System.out.println("Request output set to true");
        try
        {
            DataOutputStream ds = new DataOutputStream(con.getOutputStream());
            System.out.println("Request outputstream made");
            ds.write(postJsonData.getBytes());
            System.out.println("Request write bytes");
            ds.flush();
            System.out.println("Flush");
            ds.close();
            System.out.println("Close");
        }
        catch(Exception e)
        {
            System.out.println(e);
        }


        BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String output = br.readLine();

        con.disconnect();

        Results.getInstance().getAIDecision().add(output);
        System.out.println("Request Done.");
        System.out.println(output);
    }
}
