package com.ceg4110.seemunchies.q.backend;

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

    public void post() throws Exception
    {
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json");
        encoded = encoded.replace("\n", "").replaceAll("\r", "");
        String postJsonData = "{\"value\": " + this.encoded + "}";

        con.setDoOutput(true);
        DataOutputStream ds = new DataOutputStream(con.getOutputStream());
        ds.write(postJsonData.getBytes());
        ds.flush();
        ds.close();
        BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String output = br.readLine();

        Results.getInstance().getAIDecision().add(output);
        System.out.println("Request Done.");
        System.out.println(output);
    }
}
