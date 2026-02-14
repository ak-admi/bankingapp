package com.alok.bankingapp.dto;

public class HelloResponse {
    private String message;
    private String developer;
    private String status;

    public HelloResponse(String message, String developer, String status){
        this.message=message;
        this.developer=developer;
        this.status=status;
    }
    public String getMessage(){
        return message;
    }
    public String getDeveloper(){
        return developer;
    }
    public String getStatus(){
        return status;
    }
}
