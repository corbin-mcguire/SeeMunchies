package com.ceg4110.seemunchies.q.backend;

import java.util.ArrayList;

public class Results
{

    private ArrayList<String> aiDecision;
    private static Results results;

    private Results()
    {
        this.aiDecision = new ArrayList<String>();
    }

    public static Results getInstance()
    {
        if(results == null)
        {
            results = new Results();
        }

        return results;
    }

    public ArrayList<String> getAIDecision()
    {
        return this.aiDecision;
    }

    public void setAIDecision(ArrayList<String> decision)
    {
        this.aiDecision = decision;
    }

}
