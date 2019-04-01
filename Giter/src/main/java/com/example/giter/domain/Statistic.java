package com.example.sweater.domain;

import javax.persistence.*;

@Entity
public class Statistic {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private int numberOfLanguagesUsed;
    private int numberOfRepositories;
    private String email;
    private String login;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User author;

    public Statistic(){
    }

    public Statistic(int numberOfLanguagesUsed, int numberOfRepositories, String email,String login, User user) {
        this.numberOfLanguagesUsed = numberOfLanguagesUsed;
        this.numberOfRepositories = numberOfRepositories;
        this.login = login;
        this.email = email;
        author = user;
    }

    public String getAuthorName() {
        return author != null ? author.getUsername() : "<none>";
    }

    public int getNumberOfRepositories() {
        return numberOfRepositories;
    }

    public void setNumberOfRepositories(int numberOfRepositories) {
        this.numberOfRepositories = numberOfRepositories;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getNumberOfLanguagesUsed() {
        return numberOfLanguagesUsed;
    }

    public void setNumberOfLanguagesUsed(int numberOfLanguagesUsed) {
        this.numberOfLanguagesUsed = numberOfLanguagesUsed;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}
