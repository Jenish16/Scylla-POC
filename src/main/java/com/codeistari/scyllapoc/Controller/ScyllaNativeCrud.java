package com.codeistari.scyllapoc.Controller;

import com.codeistari.scyllapoc.entity.MonkeySpecies;
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

@RestController
@RequestMapping("/scylla/native")
@AllArgsConstructor
public class ScyllaNativeCrud {

  private final ScyllaNativeService scyllaNativeService;

  @PostMapping()
  public ResponseEntity<Boolean> create(@RequestBody MonkeySpecies monkeySpecies) {
    boolean result = scyllaNativeService.createSpecies(monkeySpecies);
    return ResponseEntity.ok(result);
  }

  @GetMapping()
  public List<MonkeySpecies> getAll() {
    return scyllaNativeService.getAllSpecies();
  }

  @GetMapping("/{species}")
  public MonkeySpecies getSpecies(@PathVariable String species) {
    return scyllaNativeService.getSpecies(species);
  }

  @PutMapping()
  public ResponseEntity<String> update(@RequestBody MonkeySpecies monkeySpecies) {
    return ResponseEntity.ok(scyllaNativeService.updateSpecies(monkeySpecies) ? "Updated" : "Not Updated");
  }

  @DeleteMapping("/{species}")
  public ResponseEntity<Boolean> delete(@PathVariable String species) {
    boolean result = scyllaNativeService.deleteSpecies(species);
    return ResponseEntity.ok(result);
  }

}
