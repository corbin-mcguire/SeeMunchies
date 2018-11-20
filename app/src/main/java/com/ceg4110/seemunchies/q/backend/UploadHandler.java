package com.ceg4110.seemunchies.q.backend;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class UploadHandler
{
    private ArrayList<File> images;

    public UploadHandler()
    {
        this.images = new ArrayList<File>();
    }

    public ArrayList<File> getImages()
    {
        return images;
    }

    public void setImages(ArrayList<File> images)
    {
        this.images = images;
    }

    public void makeUploadRequest(String encoded) throws Exception
    {
        UploadRequest ur = new UploadRequest(encoded);
        System.out.println("Request Made.");
        ur.post();
    }

    public String encodeFile() throws FileNotFoundException, IOException
    {
        File f = images.get(0);
        Bitmap bm = BitmapFactory.decodeFile(f.getAbsolutePath());
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();
        String value = Base64.encodeToString(b, Base64.DEFAULT);

        //FileInputStream fis = new FileInputStream(f);
        //byte[] bytes = new byte[(int)f.length()];
        //fis.read(bytes);
        //String temp = Base64.encodeToString(bytes, Base64.DEFAULT);
        value = value.replace("\n", "").replaceAll("\r", "");
        return value;
    }
}
