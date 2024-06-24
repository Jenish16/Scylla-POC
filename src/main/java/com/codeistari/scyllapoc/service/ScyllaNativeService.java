package com.codeistari.scyllapoc.service;

import com.codeistari.scyllapoc.entity.MonkeySpecies;
import com.codeistari.scyllapoc.repositories.MonkeySpeciesRepository;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class ScyllaNativeService {

  private final MonkeySpeciesRepository monkeySpeciesRepository;

  public boolean createSpecies(MonkeySpecies monkeySpecies) {
    return monkeySpeciesRepository.createSpecies(monkeySpecies);
  }

  public List<MonkeySpecies> getAllSpecies() {
    return monkeySpeciesRepository.getAll();
  }

  public boolean updateSpecies(MonkeySpecies monkeySpecies) {
    return monkeySpeciesRepository.updateSpecies(monkeySpecies);
  }

  public boolean deleteSpecies(String species) {
    return monkeySpeciesRepository.deleteSpecies(species);
  }

  public MonkeySpecies getSpecies(String species) {
    return monkeySpeciesRepository.getSpecies(species);
  }

}
