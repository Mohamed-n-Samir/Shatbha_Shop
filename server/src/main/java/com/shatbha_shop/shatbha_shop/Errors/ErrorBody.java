package com.shatbha_shop.shatbha_shop.Errors;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ErrorBody {
    
    private String message;
    private String uri;

    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "dd-mm-yyyy hh:mm:ss" )
    private Date timestamp;
    
    public ErrorBody(){
        this.timestamp = new Date();
    }

    public ErrorBody(String message, String uri){
        this();
        this.message = message;
        this.uri = uri;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
