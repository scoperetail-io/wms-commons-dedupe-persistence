/*
 *  DedupeKeyRepositoryTest.java
 *  Copyright 2024 AutoZone, Inc.
 *  Content is confidential to and proprietary information of AutoZone, Inc.,
 *  its subsidiaries and affiliates.
 */
package az.supplychain.wms.commons.dedupe.persistence.repository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import az.supplychain.wms.commons.dedupe.persistence.config.DedupeConfig;
import az.supplychain.wms.commons.dedupe.persistence.entity.DedupeKey;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;

@DataJpaTest
@ContextConfiguration(classes = {DedupeKeyApplication.class, DedupeConfig.class})
@Sql(scripts = "/schema.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
@Sql(scripts = "/cleanup.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_CLASS)
public class DedupeKeyRepositoryTest { // NOSONAR

  @Autowired private TestEntityManager entityManager;
  @Autowired private DedupeKeyRepository repository;

  @Test
  public void contextLoads() { // NOSONAR
    Assertions.assertNotNull(entityManager);
  }

  @Test
  public void testFindByLogKey_existingKey() { // NOSONAR
    // Create a dummy DedupeKey object
    DedupeKey key = new DedupeKey();
    key.setLogKey("key1");

    // Simulate saving the object (using entityManager for testing)
    entityManager.persist(key);

    // Call the method findByLogKey
    Optional<DedupeKey> retrievedKey = repository.findByLogKey("key1");

    // Assert that the retrieved object is not empty and has the expected values
    Assertions.assertTrue(retrievedKey.isPresent());
    assertThat(retrievedKey).contains(key);
    Assertions.assertEquals("key1", retrievedKey.get().getId());
    Assertions.assertTrue(retrievedKey.get().isNew());
    Assertions.assertEquals(key.hashCode(), retrievedKey.get().hashCode());
  }

  @Test
  public void testFindByLogKey_nonExistingKey() { // NOSONAR
    // Call the method findByLogKey with a non-existing key
    Optional<DedupeKey> retrievedKey = repository.findByLogKey("non-existing-key");

    // Assert that the retrieved object is empty
    assertThat(retrievedKey).isEmpty();
  }

  @Test
  public void testDeleteByLogKey_existingKey() { // NOSONAR
    // Create a dummy DedupeKey object
    DedupeKey key = new DedupeKey();
    key.setLogKey("key2");

    // Simulate saving the object (using entityManager for testing)
    entityManager.persist(key);

    // Call the method deleteByLogKey
    int deletedCount = repository.deleteBylogKey("key2");

    // Assert that one object was deleted
    assertThat(deletedCount).isEqualTo(1);

    // Try to find the deleted object
    Optional<DedupeKey> retrievedKey = repository.findByLogKey("key2");

    // Assert that the retrieved object is empty
    assertThat(retrievedKey).isEmpty();
  }

  @Test
  public void testDeleteByLogKey_nonExistingKey() { // NOSONAR
    // Call the method deleteByLogKey with a non-existing key
    int deletedCount = repository.deleteBylogKey("non-existing-key");

    // Assert that no objects were deleted
    assertThat(deletedCount).isZero();
  }

  @Test
  public void testEqualsAndHashCode_Dedupekey() { // NOSONAR
    // Create a dummy DedupeKey object
    DedupeKey key1 = new DedupeKey();
    key1.setLogKey("key1");

    DedupeKey key2 = new DedupeKey();
    key2.setLogKey("key1");

    Assertions.assertEquals(key1, key2);
    Assertions.assertEquals(key1.hashCode(), key2.hashCode());
  }

  @Test
  public void testNotEqualsAndHashCode_Dedupekey() { // NOSONAR
    // Create a dummy DedupeKey object
    DedupeKey key1 = new DedupeKey();
    key1.setLogKey("key1");

    DedupeKey key2 = new DedupeKey();
    key2.setLogKey("key2");

    Assertions.assertNotEquals(key1, key2);
    Assertions.assertNotEquals(key1.hashCode(), key2.hashCode());
  }
}
