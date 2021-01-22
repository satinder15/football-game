package com.sapient.football.util;

import com.sapient.football.exception.BadRequestException;
import com.sapient.football.model.Country;
import com.sapient.football.model.League;
import com.sapient.football.model.Team;
import com.sapient.football.model.TeamRequest;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Objects;

@Slf4j
public class FootballUtility {

    public static boolean isValidLeagueResponse(TeamRequest teamStandingRequest,
                                          Team teamStandingDefault, League leagues) {
        if (Objects.isNull(leagues)) {
            throw new BadRequestException(
                    "leagues Not Found by name " + teamStandingRequest.getLeagueName());
        }
        log.info("league found {}", leagues.toString());
        if (leagues.getLeagueId() == 0) {
            return false;
        }
        return true;
    }

    public static boolean isValidateCountryResponse(TeamRequest teamStandingRequest,
                                              Team teamStandingDefault, Country country) {
        if (Objects.isNull(country)) {
            throw new BadRequestException(
                    "Country Not Found by name " + teamStandingRequest.getCountryName());
        }
        log.info("Country found {}", country.toString());

        if (country.getId() == 0) {
            teamStandingDefault.setCountryId(0);
            return false;
        }
        return true;
    }

    public static Team getTeamStanding(TeamRequest teamStandingRequest) {
        Team teamStanding = new Team();
        teamStanding.setTeamName(teamStandingRequest.getTeamName());
        teamStanding.setCountryName(teamStandingRequest.getCountryName());
        teamStanding.setLeagueName(teamStandingRequest.getLeagueName());
        return teamStanding;
    }

    public static League getLeaguesByName(TeamRequest teamStandingRequest,
                                    List<League> leaguesList) {
        return leaguesList.stream()
                .filter(l -> teamStandingRequest.getLeagueName().equalsIgnoreCase(l.getLeagueName()))
                .findFirst().orElse(null);
    }

    public static Team getFilteredTeamStanding(TeamRequest teamStandingRequest,
                                         List<Team> teamStandings) {
        return teamStandings.stream()
                .filter(t -> teamStandingRequest.getTeamName().equalsIgnoreCase(t.getTeamName()))
                .findFirst().orElse(null);
    }

    public static Country getCountryByName(TeamRequest teamStandingRequest,
                                     List<Country> countries) {
        return countries.stream()
                .filter(c -> teamStandingRequest.getCountryName().equalsIgnoreCase(c.getName())).findFirst()
                .orElse(null);
    }
}
