/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dataaccess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class HashTagDB {
    
     
    
     public static void insertHashTag(String hashText, int hashCount)
    {
        //search in the database and find the User, if does not exist return null; if exist make a User object and return it.
    System.out.println("control is within insertHashTag method");
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
      
      String sql = "INSERT INTO uncctweets.HashTagDB (hashtagText, hashtagCount) VALUES (" +hashText +",'" +hashCount +"')";         
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
    
}
