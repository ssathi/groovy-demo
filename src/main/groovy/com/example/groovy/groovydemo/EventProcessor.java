package com.example.groovy.groovydemo;

public interface EventProcessor<T> {

    void process(T t) throws Exception;
}
