package com.example.chat_app.requests;

public class InterviewRequest {
    private int categoryId;
    private int authorId;
    private int peerId;
    private String authorFeedback;
    private String peerFeedback;

    private String date;

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public int getPeerId() {
        return peerId;
    }

    public void setPeerId(int peerId) {
        this.peerId = peerId;
    }

    public String getAuthorFeedback() {
        return authorFeedback;
    }

    public void setAuthorFeedback(String authorFeedback) {
        this.authorFeedback = authorFeedback;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPeerFeedback() {
        return peerFeedback;
    }

    public void setPeerFeedback(String peerFeedback) {
        this.peerFeedback = peerFeedback;
    }
}
