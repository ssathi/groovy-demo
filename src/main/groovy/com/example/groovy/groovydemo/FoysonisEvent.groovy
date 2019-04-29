package com.example.groovy.groovydemo

abstract class FoysonisEvent<T> {
    T source
    String companyId

    FoysonisEvent(T source, String companyId) {
        this.source = source
        this.companyId = companyId
    }

    T getSource() {
        return source
    }

    String getCompanyId() {
        return companyId
    }

    String getEventType() {
        this.class.getSimpleName()
    }
}
