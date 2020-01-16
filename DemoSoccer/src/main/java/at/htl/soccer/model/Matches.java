package at.htl.soccer.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Matches implements Serializable {
    @Id
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    private Team team1;

    @ManyToOne(cascade = CascadeType.ALL)
    private Team team2;
    private Integer goalsTeam1;
    private Integer goalsTeam2;
    private Integer matchday;

    public Matches() {
    }

    public Matches(Long id, Team team1, Team team2, Integer goalsTeam1, Integer goalsTeam2, Integer matchday) {
        this.id = id;
        this.team1 = team1;
        this.team2 = team2;
        this.goalsTeam1 = goalsTeam1;
        this.goalsTeam2 = goalsTeam2;
        this.matchday = matchday;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Team getTeam1() {
        return team1;
    }

    public void setTeam1(Team team1) {
        this.team1 = team1;
    }

    public Team getTeam2() {
        return team2;
    }

    public void setTeam2(Team team2) {
        this.team2 = team2;
    }

    public Integer getGoalsTeam1() {
        return goalsTeam1;
    }

    public void setGoalsTeam1(Integer goalsTeam1) {
        this.goalsTeam1 = goalsTeam1;
    }

    public Integer getGoalsTeam2() {
        return goalsTeam2;
    }

    public void setGoalsTeam2(Integer goalsTeam2) {
        this.goalsTeam2 = goalsTeam2;
    }

    public Integer getMatchday() {
        return matchday;
    }

    public void setMatchday(Integer matchday) {
        this.matchday = matchday;
    }
}
