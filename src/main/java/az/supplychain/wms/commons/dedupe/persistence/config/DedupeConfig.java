/*
 *  DedupeConfig.java
 *  Copyright 2024 AutoZone, Inc.
 *  Content is confidential to and proprietary information of AutoZone, Inc.,
 *  its subsidiaries and affiliates.
 */
package az.supplychain.wms.commons.dedupe.persistence.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * The Configuration class for dedupe persistence component
 */
@Configuration
@EnableJpaRepositories(basePackages = "az.supplychain.wms.commons.dedupe.persistence")
@EntityScan(basePackages = "az.supplychain.wms.commons.dedupe.persistence")
public class DedupeConfig {
}
