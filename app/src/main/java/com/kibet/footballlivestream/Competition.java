package com.kibet.footballlivestream;
public class Competition {
    private final String competitionImage;
    private final String competitionName;
    private final String competitionArena;
    private final String competitionId;

    public Competition(String arena, String name, String id, String image) {
        this.competitionImage = image;
        this.competitionName = name;
        this.competitionArena = arena;
        this.competitionId = id;
    }

    public String getCompetitionImage() {
        return competitionImage;
    }
    public String getCompetitionName() {
        return competitionName;
    }
    public String getCompetitionArena() {
        return competitionArena;
    }
}
