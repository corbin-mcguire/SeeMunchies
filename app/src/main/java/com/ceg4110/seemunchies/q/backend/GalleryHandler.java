package com.ceg4110.seemunchies.q.backend;

import android.graphics.Bitmap;

import java.io.File;
import java.util.ArrayList;

public class GalleryHandler
{
    private ArrayList<Bitmap> images;
    private ArrayList<String> scores;

    public GalleryHandler()
    {
        this.images = new ArrayList<Bitmap>();
        this.scores = new ArrayList<String>();
    }

    public ArrayList<Bitmap> getImages()
    {
        return images;
    }
    public ArrayList<String> getScores()
    {
        return scores;
    }

    public void setImages(ArrayList<Bitmap> images)
    {
        this.images = images;
    }

    public void makeGalleryRequest() throws Exception
    {
        GalleryRequest gr = new GalleryRequest();
        this.images = gr.getBmArr();
        this.scores = gr.getStrArr();
    }
}
