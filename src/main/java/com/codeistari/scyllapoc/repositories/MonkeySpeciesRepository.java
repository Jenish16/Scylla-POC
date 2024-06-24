package com.codeistari.scyllapoc.repositories;

import static com.datastax.oss.driver.api.querybuilder.QueryBuilder.deleteFrom;
import static com.datastax.oss.driver.api.querybuilder.QueryBuilder.insertInto;
import static com.datastax.oss.driver.api.querybuilder.QueryBuilder.literal;
import static com.datastax.oss.driver.api.querybuilder.QueryBuilder.selectFrom;
import static com.datastax.oss.driver.api.querybuilder.QueryBuilder.update;
import static com.datastax.oss.driver.api.querybuilder.SchemaBuilder.createKeyspace;
import static com.datastax.oss.driver.api.querybuilder.SchemaBuilder.createTable;

import com.codeistari.scyllapoc.entity.MonkeySpecies;
import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.ResultSet;
import com.datastax.oss.driver.api.core.cql.Row;
import com.datastax.oss.driver.api.core.cql.SimpleStatement;
import com.datastax.oss.driver.api.core.type.DataTypes;
import com.datastax.oss.driver.api.querybuilder.delete.Delete;
import com.datastax.oss.driver.api.querybuilder.insert.InsertInto;
import com.datastax.oss.driver.api.querybuilder.schema.CreateKeyspaceStart;
import com.datastax.oss.driver.api.querybuilder.schema.CreateTableStart;
import com.datastax.oss.driver.api.querybuilder.select.Select;
import com.datastax.oss.driver.api.querybuilder.update.Update;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
@Slf4j
public class MonkeySpeciesRepository {

  private final CqlSession cqlSession;

  private final String keyspace;

  private final String table;

  public MonkeySpeciesRepository(@Qualifier("scyllaSession") CqlSession cqlSession) {
    this.cqlSession = cqlSession;
    this.keyspace = "mykeyspace";
    this.table = "monkeySpecies";

    CreateKeyspaceStart createKeyspace = createKeyspace(keyspace);
    SimpleStatement keypaceStatement = createKeyspace.ifNotExists()
        .withSimpleStrategy(3)
        .build();

    CreateTableStart createTable = createTable(keyspace, table);
    SimpleStatement tableStatement = createTable.ifNotExists()
        .withPartitionKey("species", DataTypes.TEXT)
        .withColumn("common_name", DataTypes.TEXT)
        .withColumn("population", DataTypes.BIGINT)
        .withColumn("average_size", DataTypes.INT)
        .build();

      ResultSet rs = cqlSession.execute(keypaceStatement);
      if (null == rs.getExecutionInfo().getErrors() || rs.getExecutionInfo().getErrors().isEmpty()) {
        rs = cqlSession.execute(tableStatement);
      }

      log.info("Table created: {}", rs.wasApplied());
  }

  public boolean createSpecies(MonkeySpecies monkeySpecies) {
    boolean response = false;
    InsertInto insert = insertInto(keyspace, table);
    SimpleStatement statement = insert
        .value("species", literal(monkeySpecies.getSpecies()))
        .value("common_name", literal(monkeySpecies.getCommonName()))
        .value("population", literal(monkeySpecies.getPopulation()))
        .value("average_size", literal(monkeySpecies.getAverageSize()))
        .build();
    ResultSet rs = cqlSession.execute(statement);
    response = null == rs.getExecutionInfo().getErrors() || rs.getExecutionInfo().getErrors().isEmpty();
    return response;
  }

  public List<MonkeySpecies> getAll() {
    List<MonkeySpecies> species = new ArrayList<>();

    String query = String.format("select * from %s.%s", keyspace, table);
    ResultSet rs = cqlSession.execute(query);
    for (Row r : rs.all())
      species.add(MonkeySpecies.builder()
          .species(r.getString("species"))
          .commonName(r.getString("common_name"))
          .population(r.getLong("population"))
          .averageSize(r.getInt("average_size"))
          .build());
    return species;
  }

  public MonkeySpecies getSpecies(String species) {

    Select query = selectFrom(keyspace, table).all()
        .whereColumn("species")
        .isEqualTo(literal(species))
        .allowFiltering();
    SimpleStatement statement = query.build();
    ResultSet rs = cqlSession.execute(statement);
    Row r = rs.one();
    Objects.requireNonNull(r);
    return MonkeySpecies.builder()
        .species(r.getString("species"))
        .commonName(r.getString("common_name"))
        .population(r.getLong("population"))
        .averageSize(r.getInt("average_size"))
        .build();
  }

  public boolean updateSpecies(MonkeySpecies monkeySpecies) {
    boolean response = false;
    Update query = update(keyspace, table)
        .setColumn("common_name", literal(monkeySpecies.getCommonName()))
        .setColumn("population", literal(monkeySpecies.getPopulation()))
        .setColumn("average_size", literal(monkeySpecies.getAverageSize()))
        .whereColumn("species")
        .isEqualTo(literal(monkeySpecies.getSpecies()));
    SimpleStatement statement = query.build();
    ResultSet rs = cqlSession.execute(statement);
    response = null == rs.getExecutionInfo().getErrors() || rs.getExecutionInfo().getErrors().isEmpty();
    return response;
  }

  public boolean deleteSpecies(String species) {
    boolean response = false;
    Delete query = deleteFrom(keyspace, table)
        .whereColumn("species")
        .isEqualTo(literal(species));
    SimpleStatement statement = query.build();
    ResultSet rs = cqlSession.execute(statement);
    response = null == rs.getExecutionInfo().getErrors() || rs.getExecutionInfo().getErrors().isEmpty();
    return response;
  }
}