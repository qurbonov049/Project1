package com.example.jobalifproject;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class JSONResponse {
    @SerializedName("data")
    @Expose
    private List<Model> datas;

    public ArrayList<Model> getResults() {
        return (ArrayList<Model>) datas;
    }

}
