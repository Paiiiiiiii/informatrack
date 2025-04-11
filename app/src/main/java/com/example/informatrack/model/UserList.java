package com.example.informatrack.model;

import java.util.List;

public class UserList {
    private String message;
    private String status;
    private List<User> user;

    public UserList(String message, String status, List<User> user) {
        this.message = message;
        this.status = status;
        this.user = user;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<User> getUser() {
        return user;
    }

    public void setUser(List<User> user) {
        this.user = user;
    }
}
