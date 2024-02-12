/*
 *  DedupeKeyRepositoryCustomImpl.java
 *  Copyright 2024 AutoZone, Inc.
 *  Content is confidential to and proprietary information of AutoZone, Inc.,
 *  its subsidiaries and affiliates.
 */
package az.supplychain.wms.commons.dedupe.persistence.repository.custom.impl;

import az.supplychain.wms.commons.dedupe.persistence.entity.DedupeKey;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import az.supplychain.wms.commons.dedupe.persistence.repository.custom.DedupeKeyRepositoryCustom;

/**
 * Custom Repository Implementation of check duplicate method
 */
@Repository
@Transactional
public class DedupeKeyRepositoryCustomImpl implements DedupeKeyRepositoryCustom {

    @PersistenceContext
    EntityManager entityManager;

    @Value("${CHECK_DUPLICATE:#{null}}")
    private String duplicateCheck;

    @Override
    public Integer checkDuplicate(String logKey) {
        Query query = entityManager.createNativeQuery(duplicateCheck, DedupeKey.class);
        query.setParameter(1, logKey);
        query.setParameter(2, logKey);
        return query.executeUpdate();
    }
}
