package com.restassured.model.request;

public class Credential {
    public String username;
    public String password;

    public Credential(){}
    public Credential(String username, String password){
        this.username = username;
        this.password = password;
    }

}
