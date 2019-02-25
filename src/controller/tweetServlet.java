/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controller;

import business.Tweet;
import business.TweetUser;
import business.User;
import dataaccess.TweetDB;
import dataaccess.UserDB;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;



@WebServlet(name = "tweetServlet", urlPatterns = {"/tweet"})
public class tweetServlet extends HttpServlet {
    
     @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String url = "/home.jsp";
        // If session exists, then it returns the reference of that session object, if not, this methods will return null.
        HttpSession tweetSession = request.getSession(false); 
        // get current action
        String action = request.getParameter("action");
        ArrayList<Tweet> tweetList = new ArrayList<Tweet>(); 
        ArrayList<User> user_List = new ArrayList<User>(); 
        HashMap<Tweet,User> viewtweets = new HashMap<Tweet,User>(); 
        ArrayList<TweetUser> tweetUserDet = new ArrayList<TweetUser>(); 
        String sb = ""; 
        String hashstr = "<font color='#2B7BB9'>"; 
        int tweetCount = 0; 
         if (action.equals("tweet")) {                
            // get parameters from the request
           System.out.println("Tweet"); 
           User userDat = (User)tweetSession.getAttribute("theUser"); 
           
            String text = request.getParameter("tweetText");
            // Logic to get the nickname from text 
            String[] splittedString = text.split(" ");
            int validateUserId = 0; 
            for(int x=0; x<splittedString.length; x++)
                           {
                               String tempStr2 = splittedString[x]; 
                               char checkIsUser = tempStr2.charAt(0); 
//                               char checkspace = tempStr1.charAt(1); 
                               if(checkIsUser == '@')
                               {
                                   String isMentionedUser1 = tempStr2; 
                                   
                                   String isMentionedUser2 = isMentionedUser1.substring(1,isMentionedUser1.length()); 
                                   validateUserId = UserDB.select(isMentionedUser2); 
                                   System.out.println("validateUserId "+validateUserId);
                                      
                               }
                     
                           }
            String email = userDat.getEmail(); 
            Date date = new Date(); 
//            String timeStamp = new SimpleDateFormat("YYYY-MM-DD HH:MM:SS").format(Calendar.getInstance().getTime());
            String timeStamp = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime());
            
            // store data in Tweet object and save Tweet object in database
//            Tweet tweetObj = new Tweet(email,date,text);
            Tweet tweetObj = new Tweet();
            if(validateUserId != 0)
            {
                tweetObj.setEmail(email);
                tweetObj.setDate(timeStamp);
                tweetObj.setText(text);
                System.out.println("tweetObj.getDate(timeStamp) "+tweetObj.getDate());
                tweetObj.setMentionedUserID(validateUserId);
            }
            else
            {
                System.out.println("else tweetObj obj");
                tweetObj.setEmail(email);
                tweetObj.setDate(timeStamp);
                System.out.println("tweetObj.getDate(timeStamp) "+tweetObj.getDate());
                tweetObj.setText(text);
            }
           
            TweetDB.insert(tweetObj);  // Tweets are inserted into the tweetDB database
            String nickName2 = userDat.getNickName(); 
            
            user_List = UserDB.selectAll(); 
            int userId_Nu = UserDB.select(nickName2); 
            tweetList = TweetDB.search(email,userId_Nu);
            String temp1 = null; 
            String temp2 = null; 
            for(int i=0; i<tweetList.size(); i++)
            {
//                System.out.println("tweetList "+trailList.get(i)); 
                System.out.println("tweetList "+tweetList.get(i).getEmail());
                temp1 = tweetList.get(i).getEmail().trim(); 
                System.out.println("user list size "+user_List.size());
                System.out.println("i value "+i);
                TweetUser tuObj = new TweetUser(); 
                for(int j=0; j<user_List.size(); j++)
                {
                    System.out.println("for loop");
                    System.out.println("j value "+j);
                    System.out.println("user_List getEmail() "+user_List.get(j).getEmail());
                    temp2 = user_List.get(j).getEmail().trim();
                    System.out.println("temp1 "+temp1);
                    System.out.println("temp2 "+temp2);
//                    if(tweetList.get(i).getEmail().equals(user_List.get(j).getEmail()))
                    if(temp1.equals(temp2))
                    {
                           System.out.println("if condition");
                           
                           tuObj.setFullName(user_List.get(j).getFullName());
                           tuObj.setPicfilepath(user_List.get(j).getPicfilepath());
                           tuObj.setNickName(user_List.get(j).getNickName());
                           tuObj.setDate(tweetList.get(i).getDate());
                           tuObj.setTweetid(tweetList.get(i).getTweetid());
                           tuObj.setEmail(tweetList.get(i).getEmail());
                            System.out.println("testing tuObj " +tuObj.getTweetid());
//                           tuObj.setText(tweetList.get(i).getTweet());
                           // Hash tag code begins
                           String twitText = tweetList.get(i).getTweet();
                           System.out.println("twitText "+twitText);
                           String[] eachString = twitText.split(" ");
                           for(int x=0; x<eachString.length; x++)
                           {
                               String tempStr1 = eachString[x]; 
                               char checkHash = tempStr1.charAt(0); 
//                               char checkspace = tempStr1.charAt(1); 
                               if(checkHash == '#' || checkHash == '@')
                               {
//                                    eachString[x] = eachString[x].replace(eachString[x],"<font color='#2B7BB9'>eachString[x]</font>");
//                                    hashstr.concat("<font color='#2B7BB9'>"); 
                                  
//                                    hashstr.concat(eachString[x]); 
//                                    hashstr.concat("</font>"); 
                                      hashstr = hashstr +eachString[x] +"</font>"; 
                                      eachString[x] = hashstr; 
                                      hashstr = "<font color='#2B7BB9'>";  
                                    System.out.println("eachString[x] "+eachString[x]);
                               }
                     
                           }
                           // Appending the strings 
                          
                           for(int y=0; y<eachString.length; y++)
                           {
//                               sb.concat(eachString[y]); 
//                               sb.concat(" "); 
                               sb = sb + " " +eachString[y]; 
                           }
                           
//                           tuObj.setText(tweetList.get(i).getTweet());
                           System.out.println("sb value "+sb);
                           tuObj.setText(sb);
                           sb =""; 
						   
                           // Hashtag code ends here
                           tweetUserDet.add(tuObj); 
//                        viewtweets.put(tweetList.get(i), user_List.get(j)); 
                        }
                    else
                    {
                        System.out.println("else");
                            
                    }
                    
                } 
                  
                }
            
//            session.setAttribute("tweetList", tweetList);
//            session.setAttribute("viewtweets", viewtweets);
            tweetSession.setAttribute("tweetUserDet", tweetUserDet);
            
             // code for no of tweets 
            tweetCount = TweetDB.count(userDat); 
            tweetSession.setAttribute("tweetCount", tweetCount);
        }
         
         // Logic for deleting the tweet
            else if (action.equals("deleteTweet")) { 
            
              String tweetidStr = request.getParameter("tweetIdStr");
              System.out.println("tweetidStr "+tweetidStr);
              int tweetid = Integer.parseInt(tweetidStr); 
              System.out.println("tweetid "+tweetid);
                TweetDB.deletetweet(tweetid);  
                
                // Logic for deleting the tweet in Session or UI begins
                  HttpSession session=request.getSession();
                String sb_2 = ""; 
        String hashstr_2 = "<font color='#2B7BB9'>"; 
                User userDat_2 = (User)tweetSession.getAttribute("theUser"); 
                String email_2 = userDat_2.getEmail(); 
                int userId_Nu_2 = userDat_2.getUserId();
                 ArrayList<User> user_List_2 = new ArrayList<User>(); 
                 user_List_2 = UserDB.selectAll(); 
                ArrayList<Tweet> tweetList_2 = new ArrayList<Tweet>();
                tweetList_2 = TweetDB.search(email_2,userId_Nu_2);
            //        tweetList_2 = TweetDB.search(email,userId_Nu);
            String temp1_2 = null; 
            String temp2_2 = null; 
//            trailList = TweetDB.search();
            for(int i=0; i<tweetList_2.size(); i++)
            {
//                System.out.println("tweetList_2 "+trailList.get(i)); 
                System.out.println("tweetList_2 "+tweetList_2.get(i).getEmail());
                temp1_2 = tweetList_2.get(i).getEmail().trim(); 
                System.out.println("user list size "+user_List_2.size());
                System.out.println("i value "+i);
                TweetUser tuObj_2 = new TweetUser(); 
                for(int j=0; j<user_List_2.size(); j++)
                {
                    System.out.println("for loop");
                    System.out.println("j value "+j);
                    System.out.println("user_List_2 getEmail() "+user_List_2.get(j).getEmail());
                    temp2_2 = user_List_2.get(j).getEmail().trim();
                    System.out.println("temp1_2 "+temp1_2);
                    System.out.println("temp2_2 "+temp2_2);
//                    if(tweetList_2.get(i).getEmail().equals(user_List_2.get(j).getEmail()))
                    if(temp1_2.equals(temp2_2))
                    {
                           System.out.println("if condition");
                           System.out.println("user_List_2.get(j).getPicfilepath() " +user_List_2.get(j).getPicfilepath());
                           tuObj_2.setPicfilepath(user_List_2.get(j).getPicfilepath());
                           System.out.println("tuObj_2.setpicfilepath "+tuObj_2.getPicfilepath());
                           tuObj_2.setFullName(user_List_2.get(j).getFullName());
                           tuObj_2.setNickName(user_List_2.get(j).getNickName());
                           tuObj_2.setDate(tweetList_2.get(i).getDate());
                           tuObj_2.setTweetid(tweetList_2.get(i).getTweetid());
                           System.out.println("testing tuObj_2 " +tuObj_2.getTweetid());
                           tuObj_2.setEmail(tweetList_2.get(i).getEmail());
                           // Hash tag text
                           String twitText = tweetList_2.get(i).getTweet();
                           System.out.println("twitText "+twitText);
                           String[] eachString = twitText.split(" ");
                           for(int x=0; x<eachString.length; x++)
                           {
                               String tempStr1 = eachString[x]; 
                               char checkHash = tempStr1.charAt(0); 
//                               char checkspace = tempStr1.charAt(1); 
                               if(checkHash == '#' || checkHash == '@')
                               {
//                                    eachString[x] = eachString[x].replace(eachString[x],"<font color='#2B7BB9'>eachString[x]</font>");
//                                    hashstr_2.concat("<font color='#2B7BB9'>"); 
                                  
//                                    hashstr_2.concat(eachString[x]); 
//                                    hashstr_2.concat("</font>"); 
                                      hashstr_2 = hashstr_2 +eachString[x] +"</font>"; 
                                      eachString[x] = hashstr_2; 
                                      hashstr_2 = "<font color='#2B7BB9'>";  
                                    System.out.println("eachString[x] "+eachString[x]);
                               }
                     
                           }
                           // Appending the strings 
                          
                           for(int y=0; y<eachString.length; y++)
                           {
//                               sb_2.concat(eachString[y]); 
//                               sb_2.concat(" "); 
                               sb_2 = sb_2 + " " +eachString[y]; 
                           }
                           
//                           tuObj_2.setText(tweetList_2.get(i).getTweet());
                           System.out.println("sb_2 value "+sb_2);
                           tuObj_2.setText(sb_2);
                           sb_2 =""; 
                           tweetUserDet.add(tuObj_2); 
//                        viewtweets.put(tweetList_2.get(i), user_List_2.get(j)); 
                           
                    }
                    else
                    {
                        System.out.println("else");
                            
                    }
                }
            }
            
//            session.setAttribute("tweetList_2", tweetList_2);
//            session.setAttribute("viewtweets", viewtweets);
            session.setAttribute("tweetUserDet", tweetUserDet);
            // Logic for deleting the tweet in Session or UI ends
            
            // Logic for finding no of tweets after delete
            int tweetCount_2 = 0; 
            tweetCount_2 = TweetDB.count(userDat_2); 
            session.setAttribute("tweetCount", tweetCount_2);
                
               url = "/home.jsp";   // the "home" page  
                
           }
        
        // forward request and response objects to specified URL
        getServletContext()
            .getRequestDispatcher(url)
            .forward(request, response);
    
    }
    
     /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
    
}

