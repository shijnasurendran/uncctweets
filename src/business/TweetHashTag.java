/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package business;

import java.io.Serializable;


public class TweetHashTag implements Serializable {
    private int tweetID; 
    private int hashtagID; 

    public int getTweetID() {
        return tweetID;
    }

    public void setTweetID(int tweetID) {
        this.tweetID = tweetID;
    }

    public int getHashtagID() {
        return hashtagID;
    }

    public void setHashtagID(int hashtagID) {
        this.hashtagID = hashtagID;
    }
}
