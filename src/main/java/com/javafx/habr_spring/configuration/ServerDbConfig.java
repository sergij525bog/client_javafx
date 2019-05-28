package com.javafx.habr_spring.configuration;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "serverEntityManagerFactory",
        transactionManagerRef = "serverTransactionManager",
    basePackages = {"com.javafx.habr_spring.server.repository"})
public class ServerDbConfig {

  @Primary
  @Bean(name = "serverDataSource")
  @ConfigurationProperties(prefix = "myserver.datasource")
  public DataSource dataSource() {
    return DataSourceBuilder.create().build();
  }

  @Primary
  @Bean(name = "serverEntityManagerFactory")
  public LocalContainerEntityManagerFactoryBean serverEntityManagerFactory(
          EntityManagerFactoryBuilder builder, @Qualifier("serverDataSource") DataSource dataSource) {
    return builder.dataSource(dataSource).packages("com.javafx.habr_spring.client.domain").persistenceUnit("server_files")
        .build();
  }

  @Primary
  @Bean(name = "serverTransactionManager")
  public PlatformTransactionManager serverTransactionManager(
      @Qualifier("serverEntityManagerFactory") EntityManagerFactory serverEntityManagerFactory) {
    return new JpaTransactionManager(serverEntityManagerFactory);
  }
}
