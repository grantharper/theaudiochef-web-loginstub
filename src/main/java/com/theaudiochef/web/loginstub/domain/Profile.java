package com.theaudiochef.web.loginstub.domain;

public class Profile {

    private String email;
    private String name;
    private String user_id;
    
    public Profile(){}
    
    public Profile(AppCredential appCredential){
        this.email = appCredential.getUser().getEmail();
        this.name = appCredential.getUser().getName();
        this.user_id = appCredential.getAppUserId();
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getUser_id() {
        return user_id;
    }
    
    
    
}
