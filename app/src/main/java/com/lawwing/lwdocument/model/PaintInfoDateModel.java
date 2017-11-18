package com.lawwing.lwdocument.model;

import java.util.ArrayList;

/**
 * Created by lawwing on 2017/11/18.
 */

public class PaintInfoDateModel
{
    private String dateString;
    
    private ArrayList<PaintInfoModel> datas;
    
    public String getDateString()
    {
        return dateString;
    }
    
    public void setDateString(String dateString)
    {
        this.dateString = dateString;
    }
    
    public ArrayList<PaintInfoModel> getDatas()
    {
        return datas;
    }
    
    public void setDatas(ArrayList<PaintInfoModel> datas)
    {
        this.datas = datas;
    }
}
