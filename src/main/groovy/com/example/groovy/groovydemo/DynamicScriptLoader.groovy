package com.example.groovy.groovydemo

import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component

@Component
@Scope("singleton")
class DynamicScriptLoader {

    private static final GroovyClassLoader classLoader = new GroovyClassLoader()

    public GroovyClassLoader getClassLoader() {
        return classLoader
    }
}
