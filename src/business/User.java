/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;
import java.io.Serializable;


public class User implements Serializable {
    public String getPasswords() {
		return passwords;
	}



	public void setPasswords(String passwords) {
		this.passwords = passwords;
	}



	public String getLast_Login_Time() {
		return Last_Login_Time;
	}



	public void setLast_Login_Time(String last_Login_Time) {
		Last_Login_Time = last_Login_Time;
	}



	public void setUsertype(String usertype) {
		this.usertype = usertype;
	}

	//define attributes fullname, ...
     private int userId; 

   
     private String fullName;
     private String email;
     private String dobDay;
     private String dobMonth;
     private String dobYear;
     private String nickName;
     private String passwords;
     private String picfilepath; 
     private String Last_Login_Time; 
     private String salt;
     private String usertype;

    public String getUsertype() {
        return usertype;
    }

    

    public String getPicfilepath() {
        return picfilepath;
    }

    public void setPicfilepath(String picfilepath) {
        this.picfilepath = picfilepath;
    }
    
    public String getLastLoginTime() {
        return Last_Login_Time;
    }

    public void setLastLoginTime(String Last_Login_Time) {
        this.Last_Login_Time = Last_Login_Time;
    }
     
     public User(String fullName, String email, String dobDay, String dobMonth, String dobYear, String nickName,String passwords, String salt ) {
        this.fullName = fullName;
        this.email = email;
        this.dobDay = dobDay; 
        this.dobMonth = dobMonth; 
        this.dobYear = dobYear; 
        this.nickName = nickName; 
        this.passwords = passwords;   
        this.salt = salt;
        System.out.println("User arg constructor");
        
    }
     
     @Override
	public String toString() {
		return "User [userId=" + userId + ", fullName=" + fullName + ", email=" + email + ", dobDay=" + dobDay
				+ ", dobMonth=" + dobMonth + ", dobYear=" + dobYear + ", nickName=" + nickName + ", Last_Login_Time="
				+ Last_Login_Time + ", usertype=" + usertype + "]";
	}



	public User(String fullName, String email, String dobDay, String dobMonth, String dobYear, String nickName,String passwords, String picfilepath, String salt ) {
        this.fullName = fullName;
        this.email = email;
        this.dobDay = dobDay; 
        this.dobMonth = dobMonth; 
        this.dobYear = dobYear; 
        this.nickName = nickName; 
        this.passwords = passwords;  
        this.picfilepath = picfilepath; 
        System.out.println("User 8 arg constructor");
        this.salt = salt;
    }
     
     public User(String email, String Last_Login_Time)
     {
         this.email = email; 
         this.Last_Login_Time = Last_Login_Time; 
     }
     
//     public User(String fullName, String dobDay, String dobMonth, String dobYear, String password ) {
//        this.fullName = fullName;
//        this.dobDay = dobDay; 
//        this.dobMonth = dobMonth; 
//        this.dobYear = dobYear; 
//        this.password = password;   
//        System.out.println("User 5 arg constructor");
//        
//    }
     
     public User()
     {
         
     }
    
    //define set/get methods for all attributes.
      public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    
      public String getEmail() {
        return email;
    }
      
       public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    
      public String getDobDay() {
        return dobDay;
    }

    public void setDobDay(String dobDay) {
        this.dobDay = dobDay;
    }
    
      public String getDobMonth() {
        return dobMonth;
    }

    public void setDobMonth(String dobMonth) {
        this.dobMonth = dobMonth;
    }
    
      public String getDobYear() {
        return dobYear;
    }

    public void setDobYear(String dobYear) {
        this.dobYear = dobYear;
    }
    
      public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
    
      public String getPassword() {
        return passwords;
    }

    public void setPassword(String passwords) {
        this.passwords = passwords;
    }
    
     public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
    
}
