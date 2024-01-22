/*
 *  DedupeKeyRepository.java
 *  Copyright 2024 AutoZone, Inc.
 *  Content is confidential to and proprietary information of AutoZone, Inc.,
 *  its subsidiaries and affiliates.
 */
package az.supplychain.wms.commons.dedupe.persistence.repository;

import java.util.Optional;

import az.supplychain.wms.commons.dedupe.persistence.entity.DedupeKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * The JPA repository for Dedupe table
 */
@Repository
public interface DedupeKeyRepository extends JpaRepository<DedupeKey, String> {
    @Transactional
    Integer deleteBylogKey(String md5Hex);

    Optional<DedupeKey> findByLogKey(String logKey);
}
