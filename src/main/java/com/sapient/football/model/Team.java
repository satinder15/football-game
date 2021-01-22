package com.sapient.football.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Team {

  private int countryId;

  @JsonProperty("country_name")
  private String countryName;

  @JsonProperty("league_id")
  private int leagueId;

  @JsonProperty("league_name")
  private String leagueName;

  @JsonProperty("team_id")
  private int teamId;

  @JsonProperty("team_name")
  private String teamName;

  @JsonProperty("league_position")
  private int leaguePosition;
}
