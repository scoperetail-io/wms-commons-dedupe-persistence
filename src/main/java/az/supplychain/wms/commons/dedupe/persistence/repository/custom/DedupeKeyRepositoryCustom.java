/*
 *  DedupeKeyRepositoryCustom.java
 *  Copyright 2024 AutoZone, Inc.
 *  Content is confidential to and proprietary information of AutoZone, Inc.,
 *  its subsidiaries and affiliates.
 */
package az.supplychain.wms.commons.dedupe.persistence.repository.custom;

import org.springframework.stereotype.Repository;

/** For customised implementation of the check duplicate method */
@Repository
public interface DedupeKeyRepositoryCustom {

  Integer checkDuplicate(String logKey);
}
