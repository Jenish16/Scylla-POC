package com.codeistari.scyllapoc.service;

import com.codeistari.scyllapoc.entity.MonkeySpecies;
import com.codeistari.scyllapoc.repositories.MonkeySpeciesReactiveRepository;
import com.codeistari.scyllapoc.repositories.MonkeySpeciesRepository;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@AllArgsConstructor
public class ScyllaNativeReactiveService {

  private final MonkeySpeciesReactiveRepository monkeySpeciesRepository;

  public Mono<Boolean> createSpecies(MonkeySpecies monkeySpecies) {
    return monkeySpeciesRepository.createSpecies(monkeySpecies);
  }

  public Mono<List<MonkeySpecies>> getAllSpecies() {
    return monkeySpeciesRepository.getAll();
  }

  public Mono<Boolean> updateSpecies(MonkeySpecies monkeySpecies) {
    return monkeySpeciesRepository.updateSpecies(monkeySpecies);
  }

  public Mono<Boolean> deleteSpecies(String species) {
    return monkeySpeciesRepository.deleteSpecies(species);
  }

  public Mono<MonkeySpecies> getSpecies(String species) {
    return monkeySpeciesRepository.getSpecies(species);
  }

}
