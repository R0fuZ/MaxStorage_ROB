package com.example.maxstorage.Login;


public class User {
    private int    userId;
    private String username;
    private String passwordHash;
    private String ownerName;
    private String email;
    private String createdAt;   // fecha ISO “YYYY-MM-DD HH:MM:SS”

    public User() { }

    /** Constructor completo (incluye userId y createdAt) */
    public User(int userId,
                String username,
                String passwordHash,
                String ownerName,
                String email,
                String createdAt) {
        this.userId       = userId;
        this.username     = username;
        this.passwordHash = passwordHash;
        this.ownerName    = ownerName;
        this.email        = email;
        this.createdAt    = createdAt;
    }

    /** Constructor para inserción (no lleva userId ni createdAt) */
    public User(String username,
                String passwordHash,
                String ownerName,
                String email) {
        this.username     = username;
        this.passwordHash = passwordHash;
        this.ownerName    = ownerName;
        this.email        = email;
    }

    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }
    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getOwnerName() {
        return ownerName;
    }
    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
