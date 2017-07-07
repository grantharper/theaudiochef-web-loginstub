package com.theaudiochef.web.loginstub.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties.Session;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.theaudiochef.web.loginstub.domain.AccessToken;
import com.theaudiochef.web.loginstub.domain.AmazonUser;
import com.theaudiochef.web.loginstub.domain.AuthRequest;
import com.theaudiochef.web.loginstub.domain.Profile;
import com.theaudiochef.web.loginstub.service.AppCredentialService;

@Controller
@SessionAttributes({ "authRequest" })
public class MainController {

    private Logger log = Logger.getLogger(MainController.class);

    @Autowired
    private AppCredentialService appCredentialService;

    // auth-request?client_id=1&scope=profile&response_type=code&redirect_uri=http://localhost:8080/&state=state1
    @RequestMapping(value = "/auth-request", method = RequestMethod.GET)
    public String processAuthRequest(Model model, Session session, @RequestParam("client_id") String clientId,
            @RequestParam("scope") String scope, @RequestParam("response_type") String responseType,
            @RequestParam("redirect_uri") String redirectUri, @RequestParam("state") String state) {

        AuthRequest authRequest = new AuthRequest(clientId, scope, responseType, redirectUri, state);
        if (appCredentialService.isValidAuthRequest(authRequest)) {
            model.addAttribute("authRequest", authRequest);
            return "redirect:/login";
        } else {
            return "redirect:" + redirectUri + "?error";
        }

    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String getLogin(Model model, @ModelAttribute("amazonUser") AmazonUser amazonUser) {
        return "login";
    }

    @RequestMapping(value = "/process", method = RequestMethod.POST)
    public String submitLogin(Model model, AmazonUser amazonUser,
            @ModelAttribute("authRequest") AuthRequest authRequest) {

        String authCode = appCredentialService.createAppCredential(amazonUser, authRequest);
        if (authCode != null) {
            return "redirect:" + authRequest.getRedirectUri() + "?code=" + authCode + "&state="
                    + authRequest.getState();
        }
        return "redirect:" + authRequest.getRedirectUri() + "?error";
    }

    //token?grant_type=authorization_code&code=1234&client_id=1&client_secret=secret
    @RequestMapping(value = "/token", params = {"grant_type=authorization_code"}, method = RequestMethod.POST)
    public @ResponseBody Object getFirstAccessToken(
            @RequestParam("code") String authorizationCode, @RequestParam("client_id") String clientId,
            @RequestParam("client_secret") String clientSecret) {
        
        log.info("auth code method");
        //check the auth code and the app credential. if successful, return the access token
        return appCredentialService.getAccessTokenFromAuthorizationCode(authorizationCode, clientId, clientSecret);
        
    }
    
    //token?grant_type=refresh_token&refresh_token=1234&client_id=1&client_secret=secret
    @RequestMapping(value  = "/token", params= {"grant_type=refresh_token"}, method = RequestMethod.POST)
    public @ResponseBody Object getRefreshAccessToken(
            @RequestParam("refresh_token") String refreshToken, @RequestParam("client_id") String clientId,
            @RequestParam("client_secret") String clientSecret){
        
        log.info("refresh token method");
        
        //TODO get new access token and refresh token if the token is valid
        
        AccessToken accessToken = new AccessToken();
        accessToken.setAccess_token("refresh");
        return accessToken;
        
    }

    @RequestMapping(value = "/user/profile", method = RequestMethod.GET)
    public @ResponseBody Object getUserProfile(@RequestParam("access_token") String accessToken){
        
        //TODO look up the appCredential with the access token and populate the Profile object
        
        Profile profile = new Profile();
        
        return profile;
    }

    @RequestMapping(value = "/rest", method = RequestMethod.GET) 
    public @ResponseBody AmazonUser serializeUser() {
        AmazonUser user = new AmazonUser();
        user.setName("Grant");
        user.setEmail("grant@grant.com");
        return user;
    }

}
