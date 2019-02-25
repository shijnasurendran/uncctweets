/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package business;
import java.io.Serializable;
import java.util.Date;


public class Tweet implements Serializable {
     private int tweetid;
     private String tweet_text;
     private String email; 
     private String tweet_date; 
     private int mentionedUserID; 

    public String getText() {
        return tweet_text;
    }

    public void setText(String tweet_text) {
        this.tweet_text = tweet_text;
    }

    public int getMentionedUserID() {
        return mentionedUserID;
    }

    public void setMentionedUserID(int mentionedUserID) {
        this.mentionedUserID = mentionedUserID;
    }
     
     public Tweet()
     {
         
     }
     
     public Tweet(String email, String tweet_date, String tweet_text)
     {
         this.email = email; 
         this.tweet_date = tweet_date; 
         this.tweet_text = tweet_text;  
     }
     
     public Tweet(String email, String tweet_date, String tweet_text, int mentionedUserID)
     {
         this.email = email; 
         this.tweet_date = tweet_date; 
         this.tweet_text = tweet_text; 
         this.mentionedUserID = mentionedUserID; 
     }
     
     // Getters and setters 
     public String getTweet()
     {
         return tweet_text; 
     }
     
     public void setTweet(String tweet_text)
     {
         this.tweet_text = tweet_text; 
     }
     
      public String getEmail()
     {
         return email; 
     }
     
     public void setEmail(String email)
     {
         this.email = email; 
     }
     
       public String getDate()
     {
         return tweet_date; 
     }
     
     public void setDate(String tweet_date)
     {
         this.tweet_date = tweet_date; 
     }
     
      public int getTweetid() {
        return tweetid;
    }

    public void setTweetid(int tweetid) {
        this.tweetid = tweetid;
    }
}
