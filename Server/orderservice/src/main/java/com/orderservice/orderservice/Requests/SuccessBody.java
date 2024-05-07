package com.orderservice.orderservice.Requests;

import java.util.Date;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonFormat;

public class SuccessBody {
    private String message;
    private Map<String,Object> data;
    private String uri;

    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "dd-mm-yyyy hh:mm:ss" )
    private Date timestamp;
    
    public SuccessBody(){
        this.timestamp = new Date();
    }

    public SuccessBody(String message, String uri){
        this();
        this.message = message;
        this.uri = uri;
    }

    public SuccessBody(String message, String uri, Map<String,Object> data){
        this();
        this.message = message;
        this.uri = uri;
        this.data = data;
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

    public Map<String,Object> getData() {
        return data;
    }

    public void setData(Map<String,Object> data) {
        this.data = data;
    }
}
