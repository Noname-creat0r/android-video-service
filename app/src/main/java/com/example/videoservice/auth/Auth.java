package com.example.videoservice.auth;

import com.google.firebase.auth.FirebaseAuth;

public class Auth {
    public FirebaseAuth mAauth;

    private static final Auth auth = new Auth();
    public static Auth getInstance() {
        return auth;
    }

    private Auth(){
        mAauth = FirebaseAuth.getInstance();
    }

    public void setAuth(FirebaseAuth auth){
        mAauth = auth;
    }

    public FirebaseAuth getAuth(){
        return mAauth;
    }
}
