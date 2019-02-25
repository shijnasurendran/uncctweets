/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package business;

import java.io.Serializable;
import java.util.Date;


public class TweetUser implements Serializable {
    private int tweetid; 
    private String picfilepath; 
    private String fullName;
    private String nickName;
    private String date; 
    private String text;
    private String email; 
    
    
    public TweetUser()
    {
        
    }
    
    public TweetUser(String fullName, String nickName, String date, String text )
    {
        this.fullName = fullName; 
        this.nickName = nickName; 
        this.date = date; 
        this.text = text; 
    }
    
    public String getFullName()
    {
        return fullName; 
    }
    
    public void setFullName(String fullName)
    {
        this.fullName = fullName; 
    }
    
    public String getNickName()
    {
        return nickName; 
    }
    
    public void setNickName(String nickName)
    {
        this.nickName = nickName; 
    }
    public String getDate()
    {
        return date; 
    }
    
    public void setDate(String date)
    {
        this.date = date; 
    }
    public String getText()
    {
        return text; 
    }
    
    public void setText(String text)
    {
        this.text = text; 
    }
    
     public String getPicfilepath()
    {
        return picfilepath; 
    }
    
    public void setPicfilepath(String picfilepath)
    {
        this.picfilepath = picfilepath; 
    }
    
    
    public int getTweetid() {
        return tweetid;
    }

    public void setTweetid(int tweetid) {
        this.tweetid = tweetid;
    }
    
     public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
