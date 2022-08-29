package com.example.scouting;

public class CardView {

    private String setDate;
    private String [] sxDatas;
    private String [] dxDatas;


    public CardView(String setDate, String[] sxDatas, String[] dxDatas) {
        this.setDate = setDate;
        this.sxDatas = sxDatas;
        this.dxDatas = dxDatas;
    }

    public String getSetDate() {
        return setDate;
    }

    public String[] getSxDatas() {
        return sxDatas;
    }

    public String[] getDxDatas() {
        return dxDatas;
    }
}
