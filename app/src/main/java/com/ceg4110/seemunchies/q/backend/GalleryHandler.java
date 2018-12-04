package com.ceg4110.seemunchies.q.backend;

import android.graphics.Bitmap;

import java.io.File;
import java.util.ArrayList;

public class GalleryHandler
{
    private ArrayList<Bitmap> images;

    public GalleryHandler()
    {
        this.images = new ArrayList<Bitmap>();
    }

    public ArrayList<Bitmap> getImages()
    {
        return images;
    }

    public void setImages(ArrayList<Bitmap> images)
    {
        this.images = images;
    }

    public void makeGalleryRequest()
    {
        // TODO Make the Request
    }
}
