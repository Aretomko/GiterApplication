package com.example.sweater.domain;

import javax.persistence.*;

@Entity
public class Language {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private String name;
    private int numberOfOcurrences;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "statistic_id")
    private Statistic statistic;

    public Language(){

    }
    public Language(String name, int numberOfCurrences, Statistic statistic) {
        this.name = name;
        this.numberOfOcurrences = numberOfCurrences;
        this.statistic = statistic;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumberOfOcurrences() {
        return numberOfOcurrences;
    }

    public void setNumberOfOcurrences(int numberOfOcurrences) {
        this.numberOfOcurrences = numberOfOcurrences;
    }

    public Statistic getStatistic() {
        return statistic;
    }

    public void setStatistic(Statistic statistic) {
        this.statistic = statistic;
    }
}
