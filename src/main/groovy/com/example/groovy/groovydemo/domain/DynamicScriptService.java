package com.example.groovy.groovydemo.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DynamicScriptService {

    @Autowired
    private DynamicScriptRepository repository;

    public List<DynamicScript> get(String companyId, String eventType) {
        return repository.getAllByCompanyIdAndEventType(companyId, eventType);
    }
}
