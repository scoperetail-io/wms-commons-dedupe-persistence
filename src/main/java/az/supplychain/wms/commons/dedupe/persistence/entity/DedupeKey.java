/*
 *  DedupeKey.java
 *  Copyright 2024 AutoZone, Inc.
 *  Content is confidential to and proprietary information of AutoZone, Inc.,
 *  its subsidiaries and affiliates.
 */
package az.supplychain.wms.commons.dedupe.persistence.entity;

import az.supplychain.wms.commons.shared.kernel.domain.BaseCreateTsEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.domain.Persistable;

/** The Entity class for the Dedupe table */
@Entity
@Table(name = "dedupe_key")
@Data
@EqualsAndHashCode(
    of = {"logKey"},
    callSuper = false)
public class DedupeKey extends BaseCreateTsEntity implements Persistable<String> {

  @Id
  @Column(name = "log_key")
  private String logKey;

  @Override
  public String getId() {
    return logKey;
  }

  @Override
  public boolean isNew() {
    return true;
  }
}
