package com.sapient.football.controller;

import com.sapient.football.service.FootballService;
import com.sapient.football.dto.TeamDto;
import com.sapient.football.model.TeamRequest;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/football/team")
public class FootBallController {

  private final FootballService footballService;

  @Autowired
  public FootBallController(
      FootballService teamStandingService) {
    this.footballService = teamStandingService;
  }

  @GetMapping
  public ResponseEntity<TeamDto> getStandings(@Valid TeamRequest teamRequest) {
    return ResponseEntity.ok(footballService.getStandingTeam(teamRequest));
  }
}
