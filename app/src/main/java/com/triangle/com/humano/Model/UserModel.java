package com.triangle.com.humano.Model;

import com.google.gson.annotations.SerializedName;

public class UserModel {
    @SerializedName("id")
    private String id;
    @SerializedName("name")
    private String name;
    @SerializedName("sname")
    private String sname;
    @SerializedName("emcode")
    private String emcode;
    @SerializedName("token")
    private String token;

    public UserModel(String id, String name, String sname, String emcode, String token) {
        this.id     = id;
        this.name   = name;
        this.sname  = sname;
        this.emcode = emcode;
        this.token  = token;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getSname() { return sname; }
    public  void setSname(String sname) { this.sname = sname; }

    public String getEmcode() { return emcode; }
    public void setEmcode(String emcode) { this.emcode = emcode; }

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }
}
