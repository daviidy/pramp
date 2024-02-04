package com.example.chat_app.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "interviews")
public class Interview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private User author;

    @ManyToOne
    @JoinColumn(name = "peer_id")
    private User peer;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @Column (nullable = true)
    private String author_feedback;

    @Column (nullable = true)
    private String peer_feedback;

    // add date field
    @Column (nullable = false)
    private String date;

    @ManyToMany
    @JoinTable(
            name = "interview_question",
            joinColumns = @JoinColumn(name = "interview_id"),
            inverseJoinColumns = @JoinColumn(name = "question_id")
    )
    private List<Question> questions;

    // Other fields, getters, and setters

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public User getPeer() {
        return peer;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setPeer(User peer) {
        this.peer = peer;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getAuthor_feedback() {
        return author_feedback;
    }

    public void setAuthor_feedback(String author_feedback) {
        this.author_feedback = author_feedback;
    }

    public String getPeer_feedback() {
        return peer_feedback;
    }

    public void setPeer_feedback(String peer_feedback) {
        this.peer_feedback = peer_feedback;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }
}
