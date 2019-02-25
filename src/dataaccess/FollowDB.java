/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dataaccess;

import business.Follow;
import business.User;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;


public class FollowDB {
   
   
   public static ArrayList searchNotifyFollowers(User theUserDet)
    {
        //search in the database and find the User, if does not exist return null; if exist make a User object and return it.
    System.out.println("control is within searchNotifyFollowers method");
       //implement insert into database
     Connection conn = null;
   Statement stmt = null;
   ResultSet rs = null; 
   ArrayList<Follow> list_followers = new ArrayList<Follow>(); 
    
//    userData = null; 
   try{
      //STEP 2: Register JDBC driver
     

      //STEP 3: Open a connection
      System.out.println("Connecting to a selected database...");
     
      conn = ConnectionPool.getConnection();
      System.out.println("Connected database successfully...");
      
      
      //STEP 4: Execute a query
      System.out.println("Creating statement...");
      stmt = conn.createStatement();
       System.out.println("theUserDet.getLastLoginTime() "+theUserDet.getLastLoginTime());
      String sql = "SELECT * FROM uncctweets.FOLLOW " + "WHERE followDate >='" +theUserDet.getLastLoginTime() +"' AND followedUserId =" +theUserDet.getUserId();         
      System.out.println("sql is "+sql);
      rs = stmt.executeQuery(sql);
      SimpleDateFormat formatter = new SimpleDateFormat("E MMM dd HH:mm:ss z yyyy"); 
      while(rs.next())
      {
//           java.util.Date uDate = formatter.parse(rs.getString("followDate").trim());
//           followData.setFollowDate(uDate);
          Follow followData = new Follow();
           followData.setFollowDate(rs.getString("followDate"));
           followData.setFollowedUserId(rs.getInt("followedUserId"));
           followData.setUserId(rs.getInt("UserId"));
           System.out.println("followDate "+followData.getFollowDate());
           System.out.println("followedUserId "+followData.getFollowedUserId());
           System.out.println("UserId "+followData.getUserId());
           list_followers.add(followData); 
      }
      for(int i=0; i<list_followers.size(); i++)
      {
          System.out.println("list_follwers followedUserId "+list_followers.get(i).getFollowedUserId());
      }
   }catch(SQLException se){
      //Handle errors for JDBC
      se.printStackTrace();
   }catch(Exception e){
      //Handle errors for Class.forName
      e.printStackTrace();
   }finally{
      //finally block used to close resources
      try{
         if(stmt!=null)
            conn.close();
      }catch(SQLException se){
      }// do nothing
      try{
         if(conn!=null)
            conn.close();
      }catch(SQLException se){
         se.printStackTrace();
      }//end finally try
   }//end try
   
 //   System.out.println("userData fullname is "+userData.getFullName()); 
//   if(userData.getEmail() != null)
//       return userData; 
//   else
//       return null; 
    return list_followers;   
    }
   
   // Logic to get the list of followers from user id 
   public static ArrayList getFollowersIds(User theUserDet)
    {
        //search in the database and find the User, if does not exist return null; if exist make a User object and return it.
    System.out.println("control is within getFollowersIds method");
       //implement insert into database
     Connection conn = null;
   Statement stmt = null;
   ResultSet rs = null; 
   ArrayList<Follow> list_followers = new ArrayList<Follow>(); 
    
//    userData = null; 
   try{
      //STEP 2: Register JDBC driver
      

      //STEP 3: Open a connection
      System.out.println("Connecting to a selected database...");
     
      conn = ConnectionPool.getConnection();
      System.out.println("Connected database successfully...");
      
      
      //STEP 4: Execute a query
      System.out.println("Creating statement...");
      stmt = conn.createStatement();
       System.out.println("theUserDet.getLastLoginTime() "+theUserDet.getLastLoginTime());
      String sql = "SELECT * FROM uncctweets.FOLLOW WHERE UserId =" +theUserDet.getUserId();         
      System.out.println("sql is "+sql);
      rs = stmt.executeQuery(sql);
      SimpleDateFormat formatter = new SimpleDateFormat("E MMM dd HH:mm:ss z yyyy"); 
      while(rs.next())
      {
//           java.util.Date uDate = formatter.parse(rs.getString("followDate").trim());
//           followData.setFollowDate(uDate);
          Follow followData = new Follow();
           followData.setFollowDate(rs.getString("followDate"));
           followData.setFollowedUserId(rs.getInt("followedUserId"));
           followData.setUserId(rs.getInt("UserId"));
           System.out.println("followDate "+followData.getFollowDate());
           System.out.println("followedUserId "+followData.getFollowedUserId());
           System.out.println("UserId "+followData.getUserId());
           list_followers.add(followData); 
      }
   
   }catch(SQLException se){
      //Handle errors for JDBC
      se.printStackTrace();
   }catch(Exception e){
      //Handle errors for Class.forName
      e.printStackTrace();
   }finally{
      //finally block used to close resources
      try{
         if(stmt!=null)
            conn.close();
      }catch(SQLException se){
      }// do nothing
      try{
         if(conn!=null)
            conn.close();
      }catch(SQLException se){
         se.printStackTrace();
      }//end finally try
   }//end try
   
 //   System.out.println("userData fullname is "+userData.getFullName()); 
//   if(userData.getEmail() != null)
//       return userData; 
//   else
//       return null; 
    return list_followers;   
    }
   
   // Logic for inserting the follower details into the table 
   public static void insertFollower(int userId, int followerId, String followDate)
    {
        //search in the database and find the User, if does not exist return null; if exist make a User object and return it.
    System.out.println("control is within insertFollower method");
       //implement insert into database
     Connection conn = null;
   Statement stmt = null;
   ResultSet rs = null; 
//   ArrayList<Follow> list_followers = new ArrayList<Follow>(); 
    
//    userData = null; 
   try{
      //STEP 2: Register JDBC driver
      

      //STEP 3: Open a connection
      System.out.println("Connecting to a selected database...");
     
      conn = ConnectionPool.getConnection();
      System.out.println("Connected database successfully...");
      
      
      //STEP 4: Execute a query
      System.out.println("Creating statement...");
      stmt = conn.createStatement();
 //      System.out.println("theUserDet.getLastLoginTime() "+theUserDet.getLastLoginTime());
      
      String sql = "INSERT INTO uncctweets.Follow (UserId, followedUserId, followDate) VALUES (" +userId +"," +followerId +",'" +followDate +"')";         
      System.out.println("sql is "+sql);
    //  rs = stmt.executeQuery(sql);
       stmt.executeUpdate(sql);
   
   }catch(SQLException se){
      //Handle errors for JDBC
      se.printStackTrace();
   }catch(Exception e){
      //Handle errors for Class.forName
      e.printStackTrace();
   }finally{
      //finally block used to close resources
      try{
         if(stmt!=null)
            conn.close();
      }catch(SQLException se){
      }// do nothing
      try{
         if(conn!=null)
            conn.close();
      }catch(SQLException se){
         se.printStackTrace();
      }//end finally try
   }//end try
   
 //   System.out.println("userData fullname is "+userData.getFullName()); 
//   if(userData.getEmail() != null)
//       return userData; 
//   else
//       return null;   
    }
   
    // Logic to  delete the follower from user id 
   public static void deleteFollower(int userId, int followerId)
    {
        //search in the database and find the User, if does not exist return null; if exist make a User object and return it.
    System.out.println("control is within deleteFollower method");
       //implement insert into database
     Connection conn = null;
   Statement stmt = null;
   ResultSet rs = null; 
//   ArrayList<Follow> list_followers = new ArrayList<Follow>(); 
    
//    userData = null; 
   try{
      //STEP 2: Register JDBC driver
      

      //STEP 3: Open a connection
      System.out.println("Connecting to a selected database...");
      
      conn = ConnectionPool.getConnection();
      System.out.println("Connected database successfully...");
      
      
      //STEP 4: Execute a query
      System.out.println("Creating statement...");
      stmt = conn.createStatement();
 //      System.out.println("theUserDet.getLastLoginTime() "+theUserDet.getLastLoginTime());
      
      String sql = "DELETE FROM uncctweets.Follow WHERE UserId =" +userId +" AND followedUserId=" +followerId;         
      System.out.println("sql is "+sql);
    //  rs = stmt.executeQuery(sql);
       stmt.executeUpdate(sql);
   
   }catch(SQLException se){
      //Handle errors for JDBC
      se.printStackTrace();
   }catch(Exception e){
      //Handle errors for Class.forName
      e.printStackTrace();
   }finally{
      //finally block used to close resources
      try{
         if(stmt!=null)
            conn.close();
      }catch(SQLException se){
      }// do nothing
      try{
         if(conn!=null)
            conn.close();
      }catch(SQLException se){
         se.printStackTrace();
      }//end finally try
   }//end try
   
 //   System.out.println("userData fullname is "+userData.getFullName()); 
//   if(userData.getEmail() != null)
//       return userData; 
//   else
//       return null;   
    }
   
   // Logic to get the count of following 
   public static int followingCount(int curUserId) {
        System.out.println("control is within followingCount");
       //implement insert into database
     Connection conn = null;
   Statement stmt = null;
   ResultSet rs = null; 
   int count = 0; 
   try{
      //STEP 2: Register JDBC driver
     

      //STEP 3: Open a connection
      System.out.println("Connecting to a selected database...");
     
      conn = ConnectionPool.getConnection();
      System.out.println("Connected database successfully...");
      
      //STEP 4: Execute a query
      System.out.println("Creating statement...");
      stmt = conn.createStatement();
      String sql = "SELECT COUNT(*) AS TOTAL FROM uncctweets.FOLLOW WHERE UserId =" +curUserId;
          
      rs = stmt.executeQuery(sql);
      while(rs.next())
      {
      if(rs.getInt("TOTAL") == 0)
      {
          count = 0; 
      }
      else
      {
          count = rs.getInt("TOTAL"); 
      }
      }
      
   
   }catch(SQLException se){
      //Handle errors for JDBC
      se.printStackTrace();
   }catch(Exception e){
      //Handle errors for Class.forName
      e.printStackTrace();
   }finally{
      //finally block used to close resources
      try{
         if(stmt!=null)
            conn.close();
      }catch(SQLException se){
      }// do nothing
      try{
         if(conn!=null)
            conn.close();
      }catch(SQLException se){
         se.printStackTrace();
      }//end finally try
   }//end try
   System.out.println("Goodbye!");
        // return list_tweets;
        return count;
    }
   
   // Logic to get no of followers 
   public static int followersCount(int curUserId) {
        System.out.println("control is within followersCount");
       //implement insert into database
     Connection conn = null;
   Statement stmt = null;
   ResultSet rs = null; 
   int count = 0; 
   try{
      //STEP 2: Register JDBC driver
     

      //STEP 3: Open a connection
      System.out.println("Connecting to a selected database...");
      
      conn = ConnectionPool.getConnection();
      System.out.println("Connected database successfully...");
      
      //STEP 4: Execute a query
      System.out.println("Creating statement...");
      stmt = conn.createStatement();
      String sql = "SELECT COUNT(*) AS TOTAL FROM uncctweets.FOLLOW WHERE followedUserId =" +curUserId;
          
      rs = stmt.executeQuery(sql);
      while(rs.next())
      {
      if(rs.getInt("TOTAL") == 0)
      {
          count = 0; 
      }
      else
      {
          count = rs.getInt("TOTAL"); 
      }
      }
      
   
   }catch(SQLException se){
      //Handle errors for JDBC
      se.printStackTrace();
   }catch(Exception e){
      //Handle errors for Class.forName
      e.printStackTrace();
   }finally{
      //finally block used to close resources
      try{
         if(stmt!=null)
            conn.close();
      }catch(SQLException se){
      }// do nothing
      try{
         if(conn!=null)
            conn.close();
      }catch(SQLException se){
         se.printStackTrace();
      }//end finally try
   }//end try
   System.out.println("Goodbye!");
        // return list_tweets;
        return count;
    }
}
