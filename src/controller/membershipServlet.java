/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import business.Follow;
import business.Notification;
import business.Tweet;
import business.TweetUser;
import business.User;
import dataaccess.FollowDB;
import dataaccess.TweetDB;
import dataaccess.UserDB;

@WebServlet(name = "membershipServlet", urlPatterns = { "/membership" })
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
		maxFileSize = 1024 * 1024 * 10, // 10MB
		maxRequestSize = 1024 * 1024 * 50) // 50MB
public class membershipServlet extends HttpServlet {
	private static final String SAVE_DIR = "uploadfiles";

	public static String hashPassword(String password) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		md.reset();
		md.update(password.getBytes());
		byte[] mdArray = md.digest();
		StringBuilder sb = new StringBuilder(mdArray.length * 2);
		for (byte b : mdArray) {
			int v = b & 0xff;
			if (v < 16) {
				sb.append('0');
			}

			sb.append(Integer.toHexString(v));
		}
		return sb.toString();
	}

	public static String getSalt() {
		Random r = new SecureRandom();
		byte[] saltBytes = new byte[32];
		r.nextBytes(saltBytes);
		return Base64.getEncoder().encodeToString(saltBytes);
	}

	public static String hashAndSaltPassword(String password) throws NoSuchAlgorithmException {
		String salt = getSalt();
		return hashPassword(password + salt);
	}

	/**
	 * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
	 * methods.
	 *
	 * @param request
	 *            servlet request
	 * @param response
	 *            servlet response
	 * @throws ServletException
	 *             if a servlet-specific error occurs
	 * @throws IOException
	 *             if an I/O error occurs
	 */

	// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on
	// the + sign on the left to edit the code.">
	/**
	 * Handles the HTTP <code>GET</code> method.
	 *
	 * @param request
	 *            servlet request
	 * @param response
	 *            servlet response
	 * @throws ServletException
	 *             if a servlet-specific error occurs
	 * @throws IOException
	 *             if an I/O error occurs
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("doGet method");
		String action = request.getParameter("action");
		HttpSession session = request.getSession();
		if (action.equals("logout")) {
			// HttpSession logOutSession = request.getSession(false);
			User user_Log = (User) session.getAttribute("theUser");
			if (session.getAttribute("theUser1") != null) {
				session.invalidate();
				getServletContext().getRequestDispatcher("/logout.jsp").forward(request, response);
			}
			String timeStamp = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime());
			// java.util.Date utilDate = new java.util.Date();
			// java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
			// String emailId_Log = user_Log.getEmail();
			user_Log.setLastLoginTime(timeStamp);
			UserDB.updateLogOutTime(user_Log);
			session.invalidate();
			getServletContext().getRequestDispatcher("/logout.jsp").forward(request, response);
		}
	}

	/**
	 * Handles the HTTP <code>POST</code> method.
	 *
	 * @param request
	 *            servlet request
	 * @param response
	 *            servlet response
	 * @throws ServletException
	 *             if a servlet-specific error occurs
	 * @throws IOException
	 *             if an I/O error occurs
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String url = "/index.jsp";
		// get current action
		String action = request.getParameter("action");

		// creating a session object
		HttpSession session = request.getSession();

		ArrayList<Tweet> tweetList = new ArrayList<Tweet>();
		ArrayList trailList = new ArrayList();
		ArrayList<User> user_List = new ArrayList<User>();
		ArrayList<User> whoToFollow = new ArrayList<User>();
		HashMap<Tweet, User> viewtweets = new HashMap<Tweet, User>();
		ArrayList<TweetUser> tweetUserDet = new ArrayList<TweetUser>();
		String sb = "";
		String hashstr = "<font color='#2B7BB9'>";
		int tweetCount = 0;

		// perform action and set URL to appropriate page
		if (action.equals("logIn")) {
			// get parameters from the request
			System.out.println("Log In");
			try {
				String message = null;
				String email = request.getParameter("email");
				String password1 = request.getParameter("password");
				System.out.println("email is " + email);
				String saltUser = "";
				saltUser = UserDB.salt(email);
				System.out.println("salt is" + saltUser);
				String password = hashPassword(password1 + saltUser);
				// User theUser = UserDB.select(email,password);
				User theUser = null;
				theUser = UserDB.select(email, password);
				System.out.println("the user last login time " + theUser.getFullName());
				
				// System.out.println("User name is "+theUser.getFullName());
				// if(!(theUser.getFullName().equals(null)))
				// {
				// url = "/index.jsp"; // the "home" page
				// }

				if (theUser.getFullName() != null && !theUser.getUsertype().equals("admin")) {
					System.out.println("theuser value is not null");
					// set User object in request object and set URL
					request.setAttribute("user", theUser);

					// Binds the user object to the session
					session.setAttribute("theUser", theUser);
					user_List = UserDB.selectAll();
					// tweetList = TweetDB.search();
					int userId_Nu = theUser.getUserId();
					tweetList = TweetDB.search(email, userId_Nu);
					String temp1 = null;
					String temp2 = null;
					// trailList = TweetDB.search();
					for (int i = 0; i < tweetList.size(); i++) {
						// System.out.println("tweetList "+trailList.get(i));
						System.out.println("tweetList " + tweetList.get(i).getEmail());
						temp1 = tweetList.get(i).getEmail().trim();
						System.out.println("user list size " + user_List.size());
						System.out.println("i value " + i);
						TweetUser tuObj = new TweetUser();
						for (int j = 0; j < user_List.size(); j++) {
							System.out.println("for loop");
							System.out.println("j value " + j);
							System.out.println("user_List getEmail() " + user_List.get(j).getEmail());
							temp2 = user_List.get(j).getEmail().trim();
							System.out.println("temp1 " + temp1);
							System.out.println("temp2 " + temp2);
							// if(tweetList.get(i).getEmail().equals(user_List.get(j).getEmail()))
							if (temp1.equals(temp2)) {
								System.out.println("if condition");
								System.out.println(
										"user_List.get(j).getPicfilepath() " + user_List.get(j).getPicfilepath());
								tuObj.setPicfilepath(user_List.get(j).getPicfilepath());
								System.out.println("tuobj.setpicfilepath " + tuObj.getPicfilepath());
								tuObj.setFullName(user_List.get(j).getFullName());
								tuObj.setNickName(user_List.get(j).getNickName());
								tuObj.setDate(tweetList.get(i).getDate());
								tuObj.setTweetid(tweetList.get(i).getTweetid());
								tuObj.setEmail(tweetList.get(i).getEmail());
								System.out.println("testing tuObj " + tuObj.getTweetid());
								// Hash tag text
								String twitText = tweetList.get(i).getTweet();
								System.out.println("twitText " + twitText);
								String[] eachString = twitText.split(" ");
								for (int x = 0; x < eachString.length; x++) {
									String tempStr1 = eachString[x];
									char checkHash = tempStr1.charAt(0);
									// char checkspace = tempStr1.charAt(1);
									if (checkHash == '#' || checkHash == '@') {
										// eachString[x] =
										// eachString[x].replace(eachString[x],"<font
										// color='#2B7BB9'>eachString[x]</font>");
										// hashstr.concat("<font
										// color='#2B7BB9'>");

										// hashstr.concat(eachString[x]);
										// hashstr.concat("</font>");
										hashstr = hashstr + eachString[x] + "</font>";
										eachString[x] = hashstr;
										hashstr = "<font color='#2B7BB9'>";
										System.out.println("eachString[x] " + eachString[x]);
									}

								}
								// Appending the strings

								for (int y = 0; y < eachString.length; y++) {
									// sb.concat(eachString[y]);
									// sb.concat(" ");
									sb = sb + " " + eachString[y];
								}

								// tuObj.setText(tweetList.get(i).getTweet());
								System.out.println("sb value " + sb);
								tuObj.setText(sb);
								sb = "";
								tweetUserDet.add(tuObj);
								// viewtweets.put(tweetList.get(i),
								// user_List.get(j));

							} else {
								System.out.println("else");

							}
						}
					}

					// session.setAttribute("tweetList", tweetList);
					// session.setAttribute("viewtweets", viewtweets);
					session.setAttribute("tweetUserDet", tweetUserDet);

					// code for who to follow previous one
					// for(int k=0; k<user_List.size(); k++)
					// {
					// if(!(theUser.getEmail().trim().equals(user_List.get(k).getEmail().trim())))
					// {
					// whoToFollow.add(user_List.get(k));
					// }
					// }
					// session.setAttribute("whoToFollow", whoToFollow);

					// Code for who to follow FinalAssign begins
					ArrayList<Follow> list_followers = new ArrayList<Follow>();
					list_followers = FollowDB.getFollowersIds(theUser);
					ArrayList<User> otherUsers = new ArrayList<User>();
					ArrayList<User> followingUsers = new ArrayList<User>();
					otherUsers = UserDB.selectOtherUsers(theUser);
					for (int ii = 0; ii < list_followers.size(); ii++) {
						System.out.println("ii " + ii);
						System.out.println(
								"list_followers.get(ii).getUserId() " + list_followers.get(ii).getFollowedUserId());
						for (int jj = 0; jj < otherUsers.size(); jj++) {
							System.out.println("jj " + jj);
							System.out.println("otherUsers.get(jj).getUserId() " + otherUsers.get(jj).getUserId());
							if (list_followers.get(ii).getFollowedUserId() == otherUsers.get(jj).getUserId()) {
								System.out.println("if condition");
								System.out.println("otherUsers.get(jj).getUserId() " + otherUsers.get(jj).getUserId());
								otherUsers.remove(jj);
							}
						}
					}

					// Logic to get the user details from follower id
					for (int kk = 0; kk < list_followers.size(); kk++) {
						User tempUser = new User();
						tempUser = UserDB.selectUser_Id(list_followers.get(kk).getFollowedUserId());
						followingUsers.add(tempUser);
					}

					// sout statement
					for (int ll = 0; ll < followingUsers.size(); ll++) {
						System.out
								.println("followingUsers.get(ll).getFullName()" + followingUsers.get(ll).getFullName());
					}

					session.setAttribute("followers", followingUsers);
					session.setAttribute("nonFollowers", otherUsers);

					// Code for who to follow FinalAssign ends

					// code for no of tweets
					tweetCount = TweetDB.count(theUser);
					session.setAttribute("tweetCount", tweetCount);

					// Logic for notifications starts here
					ArrayList<Tweet> notifyTweets = new ArrayList<Tweet>();
					notifyTweets = TweetDB.searchNotifyTweets(theUser);
					// Logic to convert tweet class to tweetUser class begins
					// here
					ArrayList<TweetUser> tweetUserNotify = new ArrayList<TweetUser>();
					ArrayList<User> user_List2 = new ArrayList<User>();
					user_List2 = UserDB.selectAll();
					String sb2 = "";
					String hashstr2 = "<font color='#2B7BB9'>";
					String temp3 = null;
					String temp4 = null;
					// trailList = TweetDB.search();
					for (int i = 0; i < notifyTweets.size(); i++) {
						// System.out.println("notifyTweets "+trailList.get(i));
						System.out.println("notifyTweets " + notifyTweets.get(i).getEmail());
						temp3 = notifyTweets.get(i).getEmail().trim();
						System.out.println("user list size " + user_List2.size());
						System.out.println("i value " + i);
						TweetUser tuObj2 = new TweetUser();
						for (int j = 0; j < user_List2.size(); j++) {
							System.out.println("for loop");
							System.out.println("j value " + j);
							System.out.println("user_List2 getEmail() " + user_List2.get(j).getEmail());
							temp4 = user_List2.get(j).getEmail().trim();
							System.out.println("temp3 " + temp3);
							System.out.println("temp4 " + temp4);
							// if(notifyTweets.get(i).getEmail().equals(user_List2.get(j).getEmail()))
							if (temp3.equals(temp4)) {
								System.out.println("if condition");
								System.out.println(
										"user_List2.get(j).getPicfilepath() " + user_List2.get(j).getPicfilepath());
								tuObj2.setPicfilepath(user_List2.get(j).getPicfilepath());
								System.out.println("tuObj2.setpicfilepath " + tuObj2.getPicfilepath());
								tuObj2.setFullName(user_List2.get(j).getFullName());
								tuObj2.setNickName(user_List2.get(j).getNickName());
								tuObj2.setDate(notifyTweets.get(i).getDate());

								// Hash tag text
								String twitText2 = notifyTweets.get(i).getTweet();
								System.out.println("twitText2 " + twitText2);
								String[] eachString2 = twitText2.split(" ");
								for (int x = 0; x < eachString2.length; x++) {
									String tempStr2 = eachString2[x];
									char checkHash2 = tempStr2.charAt(0);
									// char checkspace = tempStr2.charAt(1);
									if (checkHash2 == '#' || checkHash2 == '@') {
										// eachString2[x] =
										// eachString2[x].replace(eachString2[x],"<font
										// color='#2B7BB9'>eachString2[x]</font>");
										// hashstr2.concat("<font
										// color='#2B7BB9'>");
										//
										// hashstr2.concat(eachString2[x]);
										// hashstr2.concat("</font>");
										hashstr2 = hashstr2 + eachString2[x] + "</font>";
										eachString2[x] = hashstr2;
										hashstr2 = "<font color='#2B7BB9'>";
										System.out.println("eachString2[x] " + eachString2[x]);
									}

								}
								// Appending the strings

								for (int y = 0; y < eachString2.length; y++) {
									// sb2.concat(eachString2[y]);
									// sb2.concat(" ");
									sb2 = sb2 + " " + eachString2[y];
								}

								// tuObj2.setText(notifyTweets.get(i).getTweet());
								System.out.println("sb2 value " + sb2);
								tuObj2.setText(sb2);
								sb2 = "";
								tweetUserNotify.add(tuObj2);
								// viewtweets.put(notifyTweets.get(i),
								// user_List2.get(j));

							} else {
								System.out.println("else");

							}
						}
					}
					ArrayList<Follow> notifyFollowers;
					notifyFollowers = FollowDB.searchNotifyFollowers(theUser);
					User eachFollower = new User();
					ArrayList<User> followerDetails = new ArrayList<>();
					// Logic to get all the user details of the followers starts
					// here
					for (int pp = 0; pp < notifyFollowers.size(); pp++) {
						System.out.println("notifyfollowers loop");
						System.out.println("follwedUserId " + notifyFollowers.get(pp).getFollowedUserId());
						eachFollower = UserDB.selectUser_Id(notifyFollowers.get(pp).getUserId());
						followerDetails.add(eachFollower);
					}
					// Logic to get all the user details of the followers ends
					// here
					int r = 0, s = 0, t = 0;
					ArrayList sortedNotification = new ArrayList<>();
					HashMap<String, ArrayList> notifyList = new HashMap<String, ArrayList>();
					int notifySize = notifyTweets.size() + notifyFollowers.size();
					// String[] tweetFollow = new String[notifySize];
					ArrayList<Notification> notifications = new ArrayList<Notification>();

					while (r < notifyTweets.size() && s < notifyFollowers.size()) {
						Notification eachNotify = new Notification();
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
						String tempDate1 = notifyTweets.get(r).getDate();
						String tempDate2 = notifyFollowers.get(s).getFollowDate();
						Date date1 = null;
						try {
							date1 = sdf.parse(tempDate1);
						} catch (ParseException ex) {
							Logger.getLogger(membershipServlet.class.getName()).log(Level.SEVERE, null, ex);
						}
						Date date2 = null;
						try {
							date2 = sdf.parse(tempDate2);
						} catch (ParseException ex) {
							Logger.getLogger(membershipServlet.class.getName()).log(Level.SEVERE, null, ex);
						}
						System.out.println("date1 " + date1);
						System.out.println("date2 " + date2);
						if (date1.after(date2)) {
							// tweetFollow[t] = "tweet";
							eachNotify.setNotifyType("tweet");
							eachNotify.setFullName(tweetUserNotify.get(r).getFullName());
							eachNotify.setNickName(tweetUserNotify.get(r).getNickName());
							eachNotify.setDate(tweetUserNotify.get(r).getDate());
							System.out.println("eachNotify.getDate() " + eachNotify.getDate());
							eachNotify.setText(tweetUserNotify.get(r).getText());
							eachNotify.setProfilePic(tweetUserNotify.get(r).getPicfilepath());
							notifications.add(eachNotify);
							// sortedNotification.add(tweetUserNotify.get(r));
							r++;
							// notifyList.put(tweetFollow[t],
							// sortedNotification);
						} else {
							// tweetFollow[t] = "follow";
							eachNotify.setNotifyType("follow");
							eachNotify.setFullName(followerDetails.get(s).getFullName());
							eachNotify.setNickName(followerDetails.get(s).getNickName());
							eachNotify.setDate(notifyFollowers.get(s).getFollowDate());
							System.out.println("eachNotify.getDate() " + eachNotify.getDate());
							System.out.println("eachNotify.getFullName " + eachNotify.getFullName());
							eachNotify.setProfilePic(followerDetails.get(s).getPicfilepath());
							notifications.add(eachNotify);
							s++;
							// notifyList.put(tweetFollow[t],
							// sortedNotification);
						}
					}
					while (r < notifyTweets.size()) {
						Notification eachNotify2 = new Notification();
						eachNotify2.setNotifyType("tweet");
						eachNotify2.setFullName(tweetUserNotify.get(r).getFullName());
						eachNotify2.setNickName(tweetUserNotify.get(r).getNickName());
						eachNotify2.setDate(tweetUserNotify.get(r).getDate());
						System.out.println("eachNotify2.getDate() " + eachNotify2.getDate());
						eachNotify2.setText(tweetUserNotify.get(r).getText());
						eachNotify2.setProfilePic(tweetUserNotify.get(r).getPicfilepath());
						notifications.add(eachNotify2);
						r++;
					}
					while (s < notifyFollowers.size()) {
						Notification eachNotify3 = new Notification();
						eachNotify3.setNotifyType("follow");
						eachNotify3.setFullName(followerDetails.get(s).getFullName());
						eachNotify3.setNickName(followerDetails.get(s).getNickName());
						eachNotify3.setDate(notifyFollowers.get(s).getFollowDate());
						System.out.println("eachNotify2.getDate() " + eachNotify3.getDate());
						System.out.println("eachNotify.getFullName " + eachNotify3.getFullName());
						eachNotify3.setProfilePic(followerDetails.get(s).getPicfilepath());
						notifications.add(eachNotify3);
						s++;
					}
					// notifyList.get(tweetFollow[t]);
					session.setAttribute("notifications", notifications);

					// Logic for notifications ends here

					// Logic for getting count of following starts here
					int followingCount = FollowDB.followingCount(theUser.getUserId());
					session.setAttribute("followingCount", followingCount);

					// Logic for getting count of following ends here

					// Logic for getting no of followers start here
					int no_followers = FollowDB.followersCount(theUser.getUserId());
					session.setAttribute("followersCount", no_followers);

					// Logic for getting no of followers end here

					url = "/home.jsp"; // the "home" page
				}

				else {
					System.out.println("Entered else part");
					User theUser1 = null;
					theUser1 = UserDB.selectAdmin(email, password);
					if (theUser1.getFullName() != null) {
						ArrayList<User> userListis = UserDB.selectAll();
						System.out.println("XXXXXXXXX");
						for (User u : userListis) {

							System.out.println(u.toString());
						}
						session.setAttribute("theUser1", theUser1);
						session.setAttribute("usrdelList", userListis);
						url = "/admin.jsp";
					}

					else {
						System.out.println("theuser value is null");
						message = "No user found or Username/ Password is incorrect";
						// request.setAttribute("message", message);
						// throw new IllegalArgumentException(message);
						url = "/index.jsp";
					}
				}
				request.setAttribute("message", message);

			} catch (NoSuchAlgorithmException ex) {
				System.out.println(ex);
			}

		}

		// profile updates logic
		if (action.equals("update")) {
			// get parameters from the request
			System.out.println("Update");
			try {
				String message = null;
				String fullName = request.getParameter("fullName");
				String email = request.getParameter("email");
				System.out.println("email value " + email);
				String dobDay = request.getParameter("dob_Day");
				String dobMonth = request.getParameter("dob_Month");
				String dobYear = request.getParameter("dob_Year");
				String nickName = request.getParameter("nickName");
				String password1 = request.getParameter("password");
				String saltUser = "";
				saltUser = UserDB.salt(email);
				System.out.println("salt is" + saltUser);
				String password = hashPassword(password1 + saltUser);

				if (dobMonth.equals("2") && (dobDay.equals("29") || dobDay.equals("30") || dobDay.equals("31"))) {

					message = "February has only 28 days";
					url = "/signup.jsp";
				} else if ((dobMonth.equals("4") || dobMonth.equals("6") || dobMonth.equals("9")
						|| dobMonth.equals("11")) && (dobDay.equals("31"))) {
					message = "This month has only 30days";
					url = "/signup.jsp";
				} else if (password.length() < 7) {
					message = "Password must be 7 characters or above";
					url = "/signup.jsp";

				}
				// store data in User object and save User object in database
				// User user = new User(fullName, dobDay, dobMonth, dobYear,
				// password );
				else {
					User user = new User(fullName, email, dobDay, dobMonth, dobYear, nickName, password, saltUser);
					UserDB.update(user);
					// Logic for uploading the profile pic starts here
					// String SAVE_DIR = "uploadfiles";
					// gets absolute path of the web application
					// String appPath =
					// request.getServletContext().getRealPath("");
					// System.out.println("appPath "+appPath);
					// System.out.println("else of membership servlet upload
					// pic");
					// // constructs path of the directory to save uploaded file
					// String savePath = appPath + File.separator + SAVE_DIR;
					// System.out.println("savePath "+savePath);
					//
					// // creates the save directory if it does not exists
					// File fileSaveDir = new File(savePath);
					// System.out.println("fileSaveDir.exist()
					// "+fileSaveDir.exists());
					// if (!fileSaveDir.exists()) {
					// fileSaveDir.mkdir();

					/*
					 * Testing begins for (Part part : request.getParts()) {
					 * String fileName = extractFileName(part);
					 * System.out.println("fileName "+fileName); // theUser =
					 * (User)request.getSession().getAttribute("theUser"); //
					 * System.out.println(
					 * "savePath + File.separator + fileName is "+savePath +
					 * File.separator + fileName); FilePermission permission =
					 * new FilePermission(savePath + File.separator + fileName,
					 * "write"); part.write(savePath + File.separator +
					 * fileName); UserDB.updatepic(user, fileName);
					 * user.setPicfilepath(fileName); } Testing ends
					 */
					// Part part = request.getPart ("file");
					// String fileName = extractFileName(part);
					// System.out.println("fileName "+fileName);
					//// theUser =
					// (User)request.getSession().getAttribute("theUser");
					//// System.out.println("savePath + File.separator +
					// fileName is "+savePath + File.separator + fileName);
					// FilePermission permission = new FilePermission(savePath +
					// File.separator + fileName, "write");
					// part.write(savePath + File.separator + fileName);
					// UserDB.updatepic(user, fileName);
					// user.setPicfilepath(fileName);

					// Logic for uploading the profile pic ends here

					// User theUser = UserDB.select(user.getEmail());

					// set User object in request object and set URL
					request.setAttribute("user", user);

					// Binds the user object to the session
					session.setAttribute("theUser", user);
					url = "/home.jsp"; // the "home" page
				}
				request.setAttribute("message", message);
			} catch (NoSuchAlgorithmException ex) {
				System.out.println(ex);
			}
		}

		// sign up logic
		if (action.equals("signUp")) {
			// get parameters from the request
			System.out.println("Sign Up");
			try {
				String message = null;
				String fullName = request.getParameter("fullName");
				String email = request.getParameter("email");
				String dobDay = request.getParameter("dob_Day");
				String dobMonth = request.getParameter("dob_Month");
				String dobYear = request.getParameter("dob_Year");
				String nickName = request.getParameter("nickName");
				String password1 = request.getParameter("password");

				String salt = getSalt();
				System.out.println(salt);
				String password = hashPassword(password1 + salt);

				// Date of birth validation
				if (dobMonth.equals("2") && (dobDay.equals("29") || dobDay.equals("30") || dobDay.equals("31"))) {

					message = "February has only 28 days";
					url = "/signup.jsp";
				} else if ((dobMonth.equals("4") || dobMonth.equals("6") || dobMonth.equals("9")
						|| dobMonth.equals("11")) && (dobDay.equals("31"))) {
					message = "This month has only 30days";
					url = "/signup.jsp";
				} else if (password1.length() < 7) {
					message = "Password must be 7 characters or above";
					url = "/signup.jsp";

				} else {
					// store data in User object and save User object in
					// database
					User user = new User(fullName, email, dobDay, dobMonth, dobYear, nickName, password, salt);
					UserDB.insert(user);

					// set User object in request object and set URL
					//////////////request.setAttribute("user", user);

					// Binds the user object to the session
					///////session.setAttribute("theUser", user);

					// code for getting other tweets
				/////////////	user_List = UserDB.selectAll();
					// tweetList = TweetDB.search();
					// code change for getting the tweets related to the user
					////int userId_Nu = user.getUserId();
					//////////tweetList = TweetDB.search(email, userId_Nu);
					// code change for getting the tweets related to the user
					String temp1 = null;
					String temp2 = null;
					// trailList = TweetDB.search();
/*                for (int i = 0; i < tweetList.size(); i++) {
						// System.out.println("tweetList "+trailList.get(i));
						System.out.println("tweetList " + tweetList.get(i).getEmail());
						temp1 = tweetList.get(i).getEmail().trim();
						System.out.println("user list size " + user_List.size());
						System.out.println("i value " + i);
						TweetUser tuObj = new TweetUser();
						for (int j = 0; j < user_List.size(); j++) {
							System.out.println("for loop");
							System.out.println("j value " + j);
							System.out.println("user_List getEmail() " + user_List.get(j).getEmail());
							temp2 = user_List.get(j).getEmail().trim();
							System.out.println("temp1 " + temp1);
							System.out.println("temp2 " + temp2);
							// if(tweetList.get(i).getEmail().equals(user_List.get(j).getEmail()))
							if (temp1.equals(temp2)) {
								System.out.println("if condition");
								tuObj.setPicfilepath(user_List.get(j).getPicfilepath());
								System.out.println("tuobj.setpicfilepath " + tuObj.getPicfilepath());
								tuObj.setFullName(user_List.get(j).getFullName());
								tuObj.setNickName(user_List.get(j).getNickName());
								tuObj.setDate(tweetList.get(i).getDate());
								tuObj.setTweetid(tweetList.get(i).getTweetid());
								tuObj.setEmail(tweetList.get(i).getEmail());
								System.out.println("testing tuObj " + tuObj.getTweetid());
								// tuObj.setText(tweetList.get(i).getTweet());
								// Hash tag code begins
								String twitText = tweetList.get(i).getTweet();
								System.out.println("twitText " + twitText);
								String[] eachString = twitText.split(" ");
								for (int x = 0; x < eachString.length; x++) {
									String tempStr1 = eachString[x];
									char checkHash = tempStr1.charAt(0);
									// char checkspace = tempStr1.charAt(1);
									if (checkHash == '#' || checkHash == '@') {
										// eachString[x] =
										// eachString[x].replace(eachString[x],"<font
										// color='#2B7BB9'>eachString[x]</font>");
										// hashstr.concat("<font
										// color='#2B7BB9'>");

										// hashstr.concat(eachString[x]);
										// hashstr.concat("</font>");
										hashstr = hashstr + eachString[x] + "</font>";
										eachString[x] = hashstr;
										hashstr = "<font color='#2B7BB9'>";
										System.out.println("eachString[x] " + eachString[x]);
									}

								}
								// Appending the strings

								for (int y = 0; y < eachString.length; y++) {
									// sb.concat(eachString[y]);
									// sb.concat(" ");
									sb = sb + " " + eachString[y];
								}

								// tuObj.setText(tweetList.get(i).getTweet());
								System.out.println("sb value " + sb);
								tuObj.setText(sb);
								sb = "";

								// Hashtag code ends here
								tweetUserDet.add(tuObj);
								// viewtweets.put(tweetList.get(i),
								// user_List.get(j));

							} else {
								System.out.println("else");

							}
						}
					}

					// session.setAttribute("tweetList", tweetList);
					// session.setAttribute("viewtweets", viewtweets);
					session.setAttribute("tweetUserDet", tweetUserDet);

					// code for who to follow
					for (int k = 0; k < user_List.size(); k++) {
						if (!(user.getEmail().trim().equals(user_List.get(k).getEmail().trim()))) {
							whoToFollow.add(user_List.get(k));
						}
					}
					session.setAttribute("whoToFollow", whoToFollow);
*/
					url = "/index.jsp";// "/home.jsp"; the "home" page
				}
				request.setAttribute("message", message);
			} catch (NoSuchAlgorithmException ex) {
				System.out.println(ex);
			}
		}

		if (action.equals("forgot")) {
			// get parameters from the request
			System.out.println("forgot");
			try {
				String email = request.getParameter("email");
				String ALPHA_CAPS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
				String ALPHA = "abcdefghijklmnopqrstuvwxyz";
				String NUM = "0123456789";
				String SPL_CHARS = "!@#$%^&*_=+-/";

				Random rnd = new Random();
				char[] pswd = new char[12];
				int index = -1;
				for (int i = 0; i < 3; i++) {
					pswd[++index] = ALPHA_CAPS.charAt(rnd.nextInt(ALPHA_CAPS.length()));
					pswd[++index] = ALPHA.charAt(rnd.nextInt(ALPHA.length()));
					pswd[++index] = NUM.charAt(rnd.nextInt(NUM.length()));
					pswd[++index] = SPL_CHARS.charAt(rnd.nextInt(SPL_CHARS.length()));
				}
				String newPassword1 = new String(pswd);
				String saltUser = "";
				saltUser = UserDB.salt(email);
				System.out.println("salt is" + saltUser);
				String newPassword = hashPassword(newPassword1 + saltUser);

				// update the password in the db
				UserDB.updatepass(email, newPassword);
				User theUser = null;
				theUser = UserDB.selectemail(email);
				System.out.println("the user is " + theUser);

				// send email to user
				String to = email;
				// String from = theUser.getEmail();
				String from = "UNCCTWEETS";
				String subject = "UNCCTWEETS-Forgot password e-mail";
				String body = "Dear " + theUser.getFullName() + ",\n\n" + "your new password is " + newPassword1;

				boolean isBodyHTML = false;

				try {
					MailUtilGmail.sendMail(to, from, subject, body, isBodyHTML);
				} catch (MessagingException e) {
					String errorMessage = "ERROR: Unable to send email. " + "Check Tomcat logs for details.<br>"
							+ "NOTE: You may need to configure your system " + "as described in chapter 14.<br>"
							+ "ERROR MESSAGE: " + e.getMessage();
					request.setAttribute("errorMessage", errorMessage);
					this.log("Unable to send email. \n" + "Here is the email you tried to send: \n"
							+ "=====================================\n" + "TO: " + email + "\n" + "FROM: " + from + "\n"
							+ "SUBJECT: " + subject + "\n" + "\n" + body + "\n\n");
				}
				// System.out.println("password"+passobj.getPassword());
				// logout logic
				// if (action.equals("logout")) {
				//
				// if(session!=null)
				// session.invalidate();
				//
				//
				// }
			} catch (NoSuchAlgorithmException ex) {
				System.out.println(ex);
			}
		}

		// Logic for inserting the followers in Follow table
		if (action.equals("insertFollow")) {
			String insertFollowTime = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss")
					.format(Calendar.getInstance().getTime());
			String nonFollowEmail = request.getParameter("nonFollowEmail");
			User nfUser = new User();
			nfUser = UserDB.selectemail(nonFollowEmail);
			User currUser = (User) session.getAttribute("theUser");
			// Follow nonFollowUser = new
			// Follow(currUser.getUserId(),nfUser.getUserId(),insertFollowTime);
			FollowDB.insertFollower(currUser.getUserId(), nfUser.getUserId(), insertFollowTime);
			// Logic for who to follow
			ArrayList<Follow> list_followers = new ArrayList<Follow>();
			list_followers = FollowDB.getFollowersIds(currUser);
			ArrayList<User> otherUsers = new ArrayList<User>();
			ArrayList<User> followingUsers = new ArrayList<User>();
			otherUsers = UserDB.selectOtherUsers(currUser);
			for (int ii = 0; ii < list_followers.size(); ii++) {
				System.out.println("ii " + ii);
				System.out.println("list_followers.get(ii).getUserId() " + list_followers.get(ii).getFollowedUserId());
				for (int jj = 0; jj < otherUsers.size(); jj++) {
					System.out.println("jj " + jj);
					System.out.println("otherUsers.get(jj).getUserId() " + otherUsers.get(jj).getUserId());
					if (list_followers.get(ii).getFollowedUserId() == otherUsers.get(jj).getUserId()) {
						System.out.println("if condition");
						System.out.println("otherUsers.get(jj).getUserId() " + otherUsers.get(jj).getUserId());
						otherUsers.remove(jj);
					}
				}
			}

			// Logic to get the user details from follower id
			for (int kk = 0; kk < list_followers.size(); kk++) {
				User tempUser = new User();
				tempUser = UserDB.selectUser_Id(list_followers.get(kk).getFollowedUserId());
				followingUsers.add(tempUser);
			}

			// sout statement
			for (int ll = 0; ll < followingUsers.size(); ll++) {
				System.out.println("followingUsers.get(ll).getFullName()" + followingUsers.get(ll).getFullName());
			}

			session.setAttribute("followers", followingUsers);
			session.setAttribute("nonFollowers", otherUsers);

			// Logic for getting count of following starts here
			int followingCount = FollowDB.followingCount(currUser.getUserId());
			session.setAttribute("followingCount", followingCount);

			// Logic for getting count of following ends here

			url = "/home.jsp";
		}

		// Logic for deleting the followers in Follow table
		if (action.equals("deleteFollow")) {
			String followEmail = request.getParameter("followEmail");
			User fUser = new User();
			fUser = UserDB.selectemail(followEmail);
			User currUser = (User) session.getAttribute("theUser");
			// Follow nonFollowUser = new
			// Follow(currUser.getUserId(),nfUser.getUserId(),insertFollowTime);
			FollowDB.deleteFollower(currUser.getUserId(), fUser.getUserId());
			// Logic for who to follow
			ArrayList<Follow> list_followers = new ArrayList<Follow>();
			list_followers = FollowDB.getFollowersIds(currUser);
			ArrayList<User> otherUsers = new ArrayList<User>();
			ArrayList<User> followingUsers = new ArrayList<User>();
			otherUsers = UserDB.selectOtherUsers(currUser);
			for (int ii = 0; ii < list_followers.size(); ii++) {
				System.out.println("ii " + ii);
				System.out.println("list_followers.get(ii).getUserId() " + list_followers.get(ii).getFollowedUserId());
				for (int jj = 0; jj < otherUsers.size(); jj++) {
					System.out.println("jj " + jj);
					System.out.println("otherUsers.get(jj).getUserId() " + otherUsers.get(jj).getUserId());
					if (list_followers.get(ii).getFollowedUserId() == otherUsers.get(jj).getUserId()) {
						System.out.println("if condition");
						System.out.println("otherUsers.get(jj).getUserId() " + otherUsers.get(jj).getUserId());
						otherUsers.remove(jj);
					}
				}
			}

			// Logic to get the user details from follower id
			for (int kk = 0; kk < list_followers.size(); kk++) {
				User tempUser = new User();
				tempUser = UserDB.selectUser_Id(list_followers.get(kk).getFollowedUserId());
				followingUsers.add(tempUser);
			}

			// sout statement
			for (int ll = 0; ll < followingUsers.size(); ll++) {
				System.out.println("followingUsers.get(ll).getFullName()" + followingUsers.get(ll).getFullName());
			}

			session.setAttribute("followers", followingUsers);
			session.setAttribute("nonFollowers", otherUsers);

			// Logic for getting count of following starts here
			int followingCount = FollowDB.followingCount(currUser.getUserId());
			session.setAttribute("followingCount", followingCount);

			// Logic for getting count of following ends here

			url = "/home.jsp";
		}

		if (action.equals("deleteUser")) {
			String emailID = request.getParameter("useremail");
			System.out.println(emailID);
			UserDB.deleteUser(emailID);
			ArrayList<User> userListis = UserDB.selectAll();
			session.setAttribute("usrdelList", userListis);
			url = "/admin.jsp";
		}

		// forward request and response objects to specified URL
		getServletContext().getRequestDispatcher(url).forward(request, response);
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

	/**
	 * Extracts file name from HTTP header content-disposition
	 */
	private String extractFileName(Part part) {
		System.out.println("extractFileName");
		System.out.println("Part " + part);
		String contentDisp = part.getHeader("content-disposition");
		System.out.println("contentDisp " + contentDisp);
		String[] items = contentDisp.split(";");
		for (String s : items) {
			System.out.println("s " + s);
			if (s.trim().startsWith("filename")) {
				return s.substring(s.indexOf("=") + 2, s.length() - 1);
			}
		}
		return "";
	}

}
