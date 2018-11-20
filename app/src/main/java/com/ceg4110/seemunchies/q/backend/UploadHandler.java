package com.ceg4110.seemunchies.q.backend;

import com.sun.org.apache.xml.internal.security.utils.Base64;

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

    public void makeUploadRequest() throws Exception
    {
        UploadRequest ur = new UploadRequest(encodeFile());
    }

    public String encodeFile() throws FileNotFoundException, IOException
    {
        File f = images.get(0);
        FileInputStream fis = new FileInputStream(f);
        byte[] bytes = new byte[(int)f.length()];
        fis.read(bytes);
        return Base64.encode(bytes).toString();
    }
}
