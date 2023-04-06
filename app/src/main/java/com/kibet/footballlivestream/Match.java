package com.kibet.footballlivestream;
public class Match {
    private final String homeTeam;
    private final String awayTeam;
    private final String cupImage;
    private String score;

    private final String date;


    public Match(String homeTeam, String awayTeam, String cupImage, String score, String date) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.cupImage = cupImage;
        this.score = score;
        this.date = date;
    }

    public String getHomeTeam() {
        return homeTeam;
    }
    public String getAwayTeam() {
        return awayTeam;
    }

    public String getCupImage() {
        return cupImage;
    }
    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }
    public String getDate() {
        return date;
    }

}