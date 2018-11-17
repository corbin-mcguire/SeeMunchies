package com.ceg4110.seemunchies.q.backend;

import java.util.ArrayList;

public class ResultHandler
{
    private ArrayList<String> results;

    public ResultHandler()
    {
        this.results = new ArrayList<String>();
    }

    public void updateResults()
    {
        this.results = Results.getInstance().getAIDecision();
    }

    public ArrayList<String> getResults()
    {
        return this.getResults();
    }

}