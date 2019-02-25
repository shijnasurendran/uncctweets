/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package business;

import java.io.Serializable;
import java.util.Date;


public class Follow implements Serializable {
    
    private int UserId; 
    private int followedUserId; 
    private String followDate; 
    
    public Follow()
    {
        
    }
    
    public Follow(int UserId, int followedUserId, String followDate)
    {
        this.UserId = UserId; 
        this.followedUserId = followedUserId; 
        this.followDate = followDate; 
    }

    public String getFollowDate() {
        return followDate;
    }

    public void setFollowDate(String followDate) {
        this.followDate = followDate;
    }

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int UserId) {
        this.UserId = UserId;
    }

    public int getFollowedUserId() {
        return followedUserId;
    }

    public void setFollowedUserId(int followedUserId) {
        this.followedUserId = followedUserId;
    }

   
   
    
    
    
}
