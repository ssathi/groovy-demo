package com.example.groovy.groovydemo;

import org.springframework.stereotype.Service;

@Service
public class ItemService {

    public String test() {
        return "Hi This is from Service layer injected by Spring";
    }
}
