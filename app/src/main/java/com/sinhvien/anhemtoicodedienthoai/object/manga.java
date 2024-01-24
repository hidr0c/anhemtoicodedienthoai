package com.sinhvien.anhemtoicodedienthoai.object;

import org.json.JSONException;
import org.json.JSONObject;

public class manga {
    private String title, relationships;
    public manga(){

    }
    public manga(JSONObject o)throws JSONException {
        title = o.getString("title");
        relationships=o.getString("relationships");

    }

    public manga(String title, String relationships) {
        this.title = title;
        this.relationships = relationships;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getRelationships() {
        return relationships;
    }

    public void setRelationships(String relationships) {
        this.relationships = relationships;
    }
}
