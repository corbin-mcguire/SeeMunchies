package com.ceg4110.seemunchies.q.backend;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.StrictMode;
import android.util.Base64;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class GalleryRequest implements RequestToFlask
{
    private URL url;
    private ArrayList<String> strArr;
    private ArrayList<Bitmap> bmArr;

    public GalleryRequest() throws MalformedURLException, Exception
    {
        this.url = new URL("http://18.224.114.186:5000/get_data");
        this.strArr = new ArrayList<String>();
        this.bmArr = new ArrayList<Bitmap>();
        this.get();
    }

    public ArrayList<String> getStrArr()
    {
        return this.strArr;
    }

    public ArrayList<Bitmap> getBmArr()
    {
        return this.bmArr;
    }

    public void enableStrictMode()
    {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }
    public void get() throws Exception
    {
        ArrayList<Bitmap> arr = new ArrayList<Bitmap>();

        enableStrictMode();

        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");

        con.setDoOutput(true);
        System.out.println("Request output set to true");

        BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String output = br.readLine();

        con.disconnect();

        output = output.replace("'", "");
        output = output.replace(" ", "");
        output = output.replace("[{uimage:u", "");
        output = output.replace("u_id:ObjectId", "");
        output = output.replace("ustat:u", "");
        output = output.replace("}]", "");

        String[] temp = output.split(",");

        for(int i = 0; i < temp.length; i++)
        {
            if(i%3 == 0)
            {
                byte[] imageBytes = Base64.decode(temp[i], Base64.DEFAULT);
                Bitmap decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                this.bmArr.add(decodedImage);
            }
            else if(i%3 == 2)
            {
                this.strArr.add(temp[i]);
            }
        }

    }
}
