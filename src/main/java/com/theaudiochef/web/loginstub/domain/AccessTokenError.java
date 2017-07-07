package com.theaudiochef.web.loginstub.domain;

public class AccessTokenError {

    public static enum ERROR_TYPE{
        INVALID_CLIENT("Invalid_Client", "The client id was invalid", "fake"),
        INVALID_REQUEST("Invalid_Request", "The request was invalid", "fake"),
        INVALID_GRANT("Invalid_Grant", "The grant could not be performed", "fake");
        
        private String error;
        private String errorDescription;
        private String errorUri;
        
        private ERROR_TYPE(String error, String error_description, String error_uri){
            this.error = error;
            this.errorDescription = error_description;
            this.errorUri = error_uri;
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

        public String getErrorUri() {
            return errorUri;
        }

        public void setErrorUri(String errorUri) {
            this.errorUri = errorUri;
        }
        
        
    }
    
    public static AccessTokenError getError(AccessTokenError.ERROR_TYPE errorType){
        
        AccessTokenError accessTokenError = new AccessTokenError();
        accessTokenError.setError(errorType.getError());
        accessTokenError.setError_description(errorType.getErrorDescription());
        accessTokenError.setError_uri(errorType.getErrorUri());
        
        return accessTokenError;
        
    }
    
    private String error;
    private String error_description;
    private String error_uri;
    
    
    
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
    public String getError_uri() {
        return error_uri;
    }
    public void setError_uri(String error_uri) {
        this.error_uri = error_uri;
    }
    
    
    
}
