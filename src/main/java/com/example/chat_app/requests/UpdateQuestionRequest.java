package com.example.chat_app.requests;

public class UpdateQuestionRequest {
    private String title;
    private String content;

    // Constructors, getters, and setters

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

