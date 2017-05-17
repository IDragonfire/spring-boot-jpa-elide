package com.matthewcasperson.elidetest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yahoo.elide.Elide;
import com.yahoo.elide.ElideSettingsBuilder;
import com.yahoo.elide.audit.Slf4jLogger;
import com.yahoo.elide.core.EntityDictionary;
import com.yahoo.elide.core.filter.dialect.RSQLFilterDialect;
import com.yahoo.elide.datastores.hibernate5.HibernateStore;
import com.yahoo.elide.datastores.hibernate5.HibernateStore.Builder;
import com.yahoo.elide.jsonapi.JsonApiMapper;
import com.yahoo.elide.security.checks.Check;
import org.hibernate.SessionFactory;
import org.hibernate.jpa.HibernateEntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.Transactional;

import java.util.concurrent.ConcurrentHashMap;

@Transactional
@Configuration
public class ElideConfig {

  @Autowired
  private EntityManager entityManager;

  @Bean
  public Elide elide(EntityManagerFactory entityManagerFactory, ObjectMapper objectMapper) {
    ConcurrentHashMap<String, Class<? extends Check>> checks = new ConcurrentHashMap<>();

    EntityDictionary entityDictionary = new EntityDictionary(checks);
    RSQLFilterDialect rsqlFilterDialect = new RSQLFilterDialect(entityDictionary);

    HibernateStore hibernateStore = new Builder((HibernateEntityManager) entityManager).build();

    return new Elide(new ElideSettingsBuilder(hibernateStore)
            .withJsonApiMapper(new JsonApiMapper(entityDictionary, objectMapper))
            .withAuditLogger(new Slf4jLogger())
            .withEntityDictionary(entityDictionary)
            .withJoinFilterDialect(rsqlFilterDialect)
            .withSubqueryFilterDialect(rsqlFilterDialect)
            .build());
  }
}
