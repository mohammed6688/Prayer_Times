package com.mocomp.prayertimes.setting;

public class settings {
    private String mprimarytext;
    private String msecondarytext;
    private int mimage;

    public settings(String primarytext , String secondarytext , int image){
        mimage = image;
        mprimarytext = primarytext ;
        msecondarytext = secondarytext;
    }

    public String getprimarytext(){
        return mprimarytext;
    }
    public String getsecondarytext(){
        return msecondarytext;
    }
    public int getMimage(){
        return mimage;
    }
}
