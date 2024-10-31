package com.api.MedSync.shared.configurations;

import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableJpaRepositories(basePackages = "com.api.medsync")
@EnableTransactionManagement
@EnableJpaAuditing
public class DataBaseConfiguration {

}
