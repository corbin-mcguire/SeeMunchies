package com.ceg4110.seemunchies.q.backend;

import java.io.File;
import java.util.ArrayList;

public class GalleryHandler
{
    private ArrayList<File> images;

    public GalleryHandler()
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

    public void makeGalleryRequest()
    {
        // TODO Make the Request
    }
}
