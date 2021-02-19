package com.michelle_condon.is4401_finalyearproject;

import android.app.Application;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

public class Module extends Application {

    public ArrayList<String> itemList = new ArrayList<>();
    public ArrayAdapter<String> itemAdp;
    public String gvalue_id;
    public String gvalue_Name;

    public String getGvalue_id() {
        return gvalue_id;
    }

    public void setGvalue_id(String gvalue_id) {
        this.gvalue_id = gvalue_id;
    }

    public String getGvalue_Name() {
        return gvalue_Name;
    }

    public void setGvalue_Name(String gvalue_Name) {
        this.gvalue_Name = gvalue_Name;
    }
}
