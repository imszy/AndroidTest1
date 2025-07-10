package com.example.demo.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

/**
 * Manages user operations like registration, login, and session management
 * This is a simple implementation for demo purposes only
 */
public class UserManager {
    private static final String TAG = "UserManager";
    private static final String PREF_NAME = "user_prefs";
    private static final String KEY_LOGGED_IN_USER = "logged_in_user";
    private static final String KEY_USER_PREFIX = "user_";
    
    private static UserManager instance;
    private Context context;
    private Map<String, User> userDatabase; // In-memory user database for demo
    private User currentUser;
    
    // 测试模式标志，在测试模式下不验证密码
    private boolean testMode = true;
    
    private UserManager(Context context) {
        this.context = context.getApplicationContext();
        this.userDatabase = new HashMap<>();
        loadUsers();
    }
    
    public static synchronized UserManager getInstance(Context context) {
        if (instance == null) {
            instance = new UserManager(context);
        }
        return instance;
    }
    
    /**
     * Set test mode status
     * @param enabled Whether test mode is enabled
     */
    public void setTestMode(boolean enabled) {
        this.testMode = enabled;
        Log.d(TAG, "Test mode " + (enabled ? "enabled" : "disabled"));
    }
    
    /**
     * Check if test mode is enabled
     * @return true if test mode is enabled
     */
    public boolean isTestMode() {
        return testMode;
    }
    
    /**
     * Register a new user
     * @param username Username
     * @param password Password
     * @param email Email
     * @return true if registration successful, false if username already exists
     */
    public boolean register(String username, String password, String email) {
        if (userDatabase.containsKey(username)) {
            return false; // Username already exists
        }
        
        User user = new User(username, password, email);
        userDatabase.put(username, user);
        saveUser(user);
        Log.d(TAG, "User registered: " + username);
        return true;
    }
    
    /**
     * Login a user
     * @param username Username
     * @param password Password
     * @return true if login successful, false otherwise
     */
    public boolean login(String username, String password) {
        User user = userDatabase.get(username);
        
        // 在测试模式下，只验证用户名存在，不验证密码
        if (testMode && user != null) {
            currentUser = user;
            saveCurrentUser(username);
            Log.d(TAG, "User logged in (test mode): " + username);
            return true;
        }
        
        // 正常模式下，验证用户名和密码
        if (user != null && user.getPassword().equals(password)) {
            currentUser = user;
            saveCurrentUser(username);
            Log.d(TAG, "User logged in: " + username);
            return true;
        }
        return false;
    }
    
    /**
     * Logout the current user
     */
    public void logout() {
        currentUser = null;
        saveCurrentUser(null);
        Log.d(TAG, "User logged out");
    }
    
    /**
     * Check if a user is currently logged in
     * @return true if a user is logged in, false otherwise
     */
    public boolean isLoggedIn() {
        return currentUser != null;
    }
    
    /**
     * Get the current logged in user
     * @return Current user or null if no user is logged in
     */
    public User getCurrentUser() {
        return currentUser;
    }
    
    /**
     * Update user profile
     * @param nickname New nickname
     * @param email New email
     * @return true if update successful, false otherwise
     */
    public boolean updateProfile(String nickname, String email) {
        if (currentUser == null) {
            return false;
        }
        
        currentUser.setNickname(nickname);
        currentUser.setEmail(email);
        saveUser(currentUser);
        Log.d(TAG, "User profile updated: " + currentUser.getUsername());
        return true;
    }
    
    /**
     * Change user password
     * @param oldPassword Old password
     * @param newPassword New password
     * @return true if password change successful, false otherwise
     */
    public boolean changePassword(String oldPassword, String newPassword) {
        if (currentUser == null || !currentUser.getPassword().equals(oldPassword)) {
            return false;
        }
        
        currentUser.setPassword(newPassword);
        saveUser(currentUser);
        Log.d(TAG, "User password changed: " + currentUser.getUsername());
        return true;
    }
    
    // Private helper methods
    
    private void saveUser(User user) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        
        // Simple serialization for demo purposes
        String key = KEY_USER_PREFIX + user.getUsername();
        editor.putString(key + "_password", user.getPassword());
        editor.putString(key + "_email", user.getEmail());
        editor.putString(key + "_nickname", user.getNickname());
        
        editor.apply();
    }
    
    private void loadUsers() {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        Map<String, ?> allEntries = prefs.getAll();
        
        // Find all usernames
        for (String key : allEntries.keySet()) {
            if (key.startsWith(KEY_USER_PREFIX) && key.endsWith("_password")) {
                String username = key.substring(KEY_USER_PREFIX.length(), key.length() - 9); // Remove prefix and "_password"
                loadUser(username, prefs);
            }
        }
        
        // Check if there's a logged in user
        String loggedInUsername = prefs.getString(KEY_LOGGED_IN_USER, null);
        if (loggedInUsername != null) {
            currentUser = userDatabase.get(loggedInUsername);
        }
    }
    
    private void loadUser(String username, SharedPreferences prefs) {
        String prefix = KEY_USER_PREFIX + username;
        String password = prefs.getString(prefix + "_password", "");
        String email = prefs.getString(prefix + "_email", "");
        String nickname = prefs.getString(prefix + "_nickname", username);
        
        User user = new User(username, password, email);
        user.setNickname(nickname);
        userDatabase.put(username, user);
    }
    
    private void saveCurrentUser(String username) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(KEY_LOGGED_IN_USER, username);
        editor.apply();
    }
} 