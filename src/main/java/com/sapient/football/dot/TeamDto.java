package com.sapient.football.dto;

import com.sapient.football.model.Team;
import java.util.Objects;
import lombok.Data;

@Data
public class TeamDto {

  private String country;
  private String league;
  private String team;
  private int overallPosition;

  public static TeamDto from(Team teamStanding) {
    TeamDto dto = new TeamDto();
    if (Objects.nonNull(teamStanding)) {
      dto.setCountry("(" + teamStanding.getCountryId() + ") - " + teamStanding.getCountryName());
      dto.setLeague("(" + teamStanding.getLeagueId() + ") - " + teamStanding.getLeagueName());
      dto.setTeam("(" + teamStanding.getTeamId() + ") - " + teamStanding.getTeamName());
      dto.setOverallPosition(teamStanding.getLeaguePosition());
    }
    return dto;

  }
}
