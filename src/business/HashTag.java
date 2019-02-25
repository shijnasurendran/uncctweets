/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package business;

import java.io.Serializable;


public class HashTag implements Serializable {
    private int hashtagID; 
    private String hashtagText; 
    private int hashtagCount; 
    
    public int getHashtagID() {
        return hashtagID;
    }

    public void setHashtagID(int hashtagID) {
        this.hashtagID = hashtagID;
    }

    public String getHashtagText() {
        return hashtagText;
    }

    public void setHashtagText(String hashtagText) {
        this.hashtagText = hashtagText;
    }

    public int getHashtagCount() {
        return hashtagCount;
    }

    public void setHashtagCount(int hashtagCount) {
        this.hashtagCount = hashtagCount;
    }
    
    
    
}
