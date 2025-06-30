package com.example.demo.model;

/**
 * User model class to store user information
 */
public class User {
    private String username;
    private String password;
    private String email;
    private String nickname;
    
    public User() {
        // Empty constructor needed for some cases
    }
    
    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.nickname = username; // Default nickname is username
    }
    
    // Getters and setters
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getNickname() {
        return nickname;
    }
    
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
} 