package com.theaudiochef.web.loginstub.domain;

import java.util.UUID;

public class ProfileError {
    
    public static enum ERROR_TYPE{
        INVALID_ACCESS_TOKEN("Invalid_Access_Token", "The access token received was invalid", "fake");
        
        private String error;
        private String errorDescription;
        
        private ERROR_TYPE(String error, String error_description, String error_uri){
            this.error = error;
            this.errorDescription = error_description;
        }

        public String getError() {
            return error;
        }

        public void setError(String error) {
            this.error = error;
        }

        public String getErrorDescription() {
            return errorDescription;
        }

        public void setErrorDescription(String errorDescription) {
            this.errorDescription = errorDescription;
        }
        
    }
    
    public static ProfileError getProfileError(ERROR_TYPE type){
        return new ProfileError(type);
    }
    
    private ProfileError(ERROR_TYPE type){
        this.error = type.getError();
        this.error_description = type.getErrorDescription();
        this.request_id = UUID.randomUUID().toString();
    }    
    
    private String error;
    private String error_description;
    private String request_id;

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
