package com.codeistari.scyllapoc.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MonkeySpecies {

  private String species;
  private String commonName;
  private Long population;
  private Integer averageSize;

}
