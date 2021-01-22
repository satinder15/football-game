package com.sapient.football.service;

import com.sapient.football.dto.TeamDto;
import com.sapient.football.model.Country;
import com.sapient.football.model.League;
import com.sapient.football.model.Team;
import com.sapient.football.model.TeamRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.sapient.football.util.FootballUtility;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class FootballService {

  private final FootballClient footballClient;

  @Autowired
  public FootballService(FootballClient footballClient) {
    this.footballClient = footballClient;
  }

  public TeamDto getStandingTeam(TeamRequest teamRequest) {
    Team teamStanding = FootballUtility.getTeamStanding(teamRequest);
    List<Country> countries = getCountryList();
    Country country = FootballUtility.getCountryByName(teamRequest, countries);
    if (!FootballUtility.isValidateCountryResponse(teamRequest, teamStanding, country)) {
      return TeamDto.from(teamStanding);
    }

    teamStanding.setCountryId(country.getId());
    List<League> leaguesList = getLeagueList(country.getId());
    log.info("league list {}", leaguesList.toString());
    League leagues = FootballUtility.getLeaguesByName(teamRequest, leaguesList);
    if (!FootballUtility.isValidLeagueResponse(teamRequest, teamStanding, leagues)) {
      return(TeamDto.from(teamStanding));
    }
    teamStanding.setLeagueId(leagues.getLeagueId());
    List<Team> teamStandings = getTeamStandingList(leagues.getLeagueId());
    log.info("standing team {}", teamStandings.toString());

    Team teamStandingsFiltered = FootballUtility.getFilteredTeamStanding(teamRequest,
        teamStandings);
    teamStandingsFiltered.setCountryId(country.getId());
    log.info("team filtered {}", teamStandingsFiltered.toString());
    if (teamStandingsFiltered.getTeamId()==0){
      return TeamDto.from(teamStanding);
    }
    return TeamDto.from(teamStandingsFiltered);
  }
  private List<Country> getCountryList() {
    return new ArrayList<>(Arrays.asList(footballClient.getCountryList()));
  }

  private List<League> getLeagueList(int countryId) {
    return new ArrayList<>(Arrays.asList(footballClient.getLeagueList(countryId)));
  }

  private List<Team> getTeamStandingList(int leagueId) {
    return new ArrayList<>(Arrays.asList(footballClient.getTeamStandingList(leagueId)));
  }

}
