package com.theaudiochef.web.loginstub.domain;

public class ProfileError {
    
    private String error;
    private String error_description;
    private String request_id;
    
    public ProfileError(){}

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getError_description() {
        return error_description;
    }

    public void setError_description(String error_description) {
        this.error_description = error_description;
    }

    public String getRequest_id() {
        return request_id;
    }

    public void setRequest_id(String request_id) {
        this.request_id = request_id;
    }
    
    

}
