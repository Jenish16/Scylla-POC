package com.codeistari.scyllapoc.Controller;

import com.codeistari.scyllapoc.entity.MonkeySpecies;
import com.codeistari.scyllapoc.service.ScyllaNativeReactiveService;
import com.codeistari.scyllapoc.service.ScyllaNativeService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/scylla/native")
@AllArgsConstructor
public class ScyllaNativeReactiveCrud {

  private final ScyllaNativeReactiveService scyllaNativeService;

  @PostMapping()
  public ResponseEntity<Mono<Boolean>> create(@RequestBody MonkeySpecies monkeySpecies) {
    Mono<Boolean> result = scyllaNativeService.createSpecies(monkeySpecies);
    return ResponseEntity.ok(result);
  }

  @GetMapping()
  public Mono<List<MonkeySpecies>> getAll() {
    return scyllaNativeService.getAllSpecies();
  }

  @GetMapping("/{species}")
  public Mono<MonkeySpecies> getSpecies(@PathVariable String species) {
    return scyllaNativeService.getSpecies(species);
  }

  @PutMapping()
  public ResponseEntity<Mono<String>> update(@RequestBody MonkeySpecies monkeySpecies) {
    Mono<String> isUpdated = scyllaNativeService.updateSpecies(monkeySpecies)
        .map(r -> r ? "Updated" : "Not Updated");
    return ResponseEntity.ok(isUpdated);
  }

  @DeleteMapping("/{species}")
  public ResponseEntity<Mono<Boolean>> delete(@PathVariable String species) {
    Mono<Boolean> result = scyllaNativeService.deleteSpecies(species);
    return ResponseEntity.ok(result);
  }

}
