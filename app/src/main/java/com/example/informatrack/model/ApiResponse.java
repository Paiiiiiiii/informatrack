package com.example.informatrack.model;

public class ApiResponse {
    private String message;
    private String status;
    private User user;

    // Constructor
    public ApiResponse(String message, String status, User user) {
        this.message = message;
        this.status = status;
        this.user = user;
    }

    // Getter and Setter
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
