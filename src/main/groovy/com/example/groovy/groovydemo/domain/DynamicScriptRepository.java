package com.example.groovy.groovydemo.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DynamicScriptRepository extends JpaRepository<DynamicScript, Long> {

    List<DynamicScript> getAllByCompanyIdAndEventType(String companyId, String eventType);

}
