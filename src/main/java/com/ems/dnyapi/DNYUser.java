package com.ems.dnyapi;

public class DNYUser {
    public String UserID="";
    public String UID = "";
    public String RID="";
    public int relogin=0;
    public String mainid="";
    public String Token="";

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public String getRID() {
        return RID;
    }

    public void setRID(String RID) {
        this.RID = RID;
    }

    public int getRelogin() {
        return relogin;
    }

    public void setRelogin(int relogin) {
        this.relogin = relogin;
    }

    public String getMainid() {
        return mainid;
    }

    public void setMainid(String mainid) {
        this.mainid = mainid;
    }

    public String getToken() {
        return Token;
    }

    public void setToken(String token) {
        Token = token;
    }

}
