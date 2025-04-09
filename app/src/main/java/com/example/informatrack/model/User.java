package com.example.informatrack.model;

public class User {
    private String id_user;
    private String username;
    private String nim;
    private String email;
    private String password;

    // Constructor tanpa parameter
    public User() {}

    // Constructor dengan parameter
    public User(String id_user, String username, String nim, String email, String password) {
        this.id_user = id_user;
        this.username = username;
        this.nim = nim;
        this.email = email;
        this.password = password;
    }

    // Getter dan Setter
    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    public String getNim() {
        return nim;
    }

    public void setNim(String nim) {
        this.nim = nim;
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // toString untuk debugging
    @Override
    public String toString() {
        return "User{" +
                "id_user='" + id_user + '\'' +
                ", username='" + username + '\'' +
                ", nim='" + nim + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
