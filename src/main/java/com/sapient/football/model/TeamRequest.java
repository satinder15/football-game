package com.sapient.football.model;

import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TeamRequest {

  @NotBlank
  private String teamName;
  @NotBlank
  private String countryName;
  @NotBlank
  private String leagueName;
}
