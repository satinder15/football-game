package com.sapient.football.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.sapient.football.model.Country;
import com.sapient.football.model.League;
import com.sapient.football.model.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.Map;

@Service
public class FootballClient {

  private static final String APIKEY = "APIkey";
  private final RestTemplate restTemplate;

  @Value("${football.base.url}")
  private String baseUrl;

  @Value("${football.action.standings}")
  private String standingsAction;

  @Value("${football.action.countries}")
  private String countriesAction;

  @Value("${football.action.leagues}")
  private String leaguesAction;

  @Value("${football.api}")
  private String api;

  @Autowired
  public FootballClient(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  @HystrixCommand(fallbackMethod = "getCountries_Fallback",  commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "5000") })
  public Country[] getCountryList() {
    Map<String, String> queryParams = new HashMap<>();
    queryParams.put("action", countriesAction);
    UriComponentsBuilder builder = getUriComponentsBuilder(baseUrl, queryParams);
    return this.restTemplate
        .exchange(builder.toUriString(), HttpMethod.GET, new HttpEntity<>(getHeaders()),
            Country[].class).getBody();
  }

  private Country[] getCountries_Fallback() {
    return new Country[]{new Country()};
  }

  @HystrixCommand(fallbackMethod = "getLeagues_Fallback",  commandProperties = {
      @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "5000") })
  public League[] getLeagueList(int countryId) {
    Map<String, String> queryParams = new HashMap<>();
    queryParams.put("action", leaguesAction);
    queryParams.put("country_id", String.valueOf(countryId));
    UriComponentsBuilder builder = getUriComponentsBuilder(baseUrl, queryParams);
    return this.restTemplate
        .exchange(builder.toUriString(), HttpMethod.GET, new HttpEntity<>(getHeaders()),
            League[].class).getBody();
  }

  private League[] getLeagues_Fallback(int countryId) {
    League leagues = new League();
    leagues.setCountryId(countryId);
    return new League[]{leagues};
  }

  @HystrixCommand(fallbackMethod = "getTeamStanding_Fallback",  commandProperties = {
      @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "5000") })
  public Team[] getTeamStandingList(int leagueId) {
    Map<String, String> queryParams = new HashMap<>();
    queryParams.put("action", standingsAction);
    queryParams.put("league_id", String.valueOf(leagueId));
    UriComponentsBuilder builder = getUriComponentsBuilder(baseUrl, queryParams);
    return this.restTemplate
        .exchange(builder.toUriString(), HttpMethod.GET, new HttpEntity<>(getHeaders()),
            Team[].class).getBody();
  }

  private Team[] getTeamStanding_Fallback(int leagueId) {
    Team teamStanding = new Team();
    teamStanding.setLeagueId(leagueId);
    return new Team[]{teamStanding};
  }

  private UriComponentsBuilder getUriComponentsBuilder(String url,
      Map<String, String> queryParams) {
    UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
        .queryParam(APIKEY, api);
    queryParams.forEach(builder::queryParam);
    return builder;
  }

  private HttpHeaders getHeaders() {
    HttpHeaders headers = new HttpHeaders();
    headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
    return headers;
  }
}
