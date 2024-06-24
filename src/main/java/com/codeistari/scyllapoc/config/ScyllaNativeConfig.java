package com.codeistari.scyllapoc.config;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.config.DriverConfigLoader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ScyllaNativeConfig {

  @Bean(name="scyllaSession")
  public CqlSession createScyllaSession() {
    DriverConfigLoader loader = DriverConfigLoader.fromClasspath("scylla.conf");
    CqlSession session = CqlSession.builder()
        .withConfigLoader(loader)
        .withKeyspace("mykeyspace")
        .build();

    return session;
  }

}
