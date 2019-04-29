package com.example.groovy.groovydemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ItemController {

    @Autowired
    private ApplicationEventPublisher publisher;

    @RequestMapping("/hello")
    public String hello() {
        Item item = new Item();
        item.setId("id");
        item.setName("Item One");
        ItemReceivedEvent event = new ItemReceivedEvent(item, "c1011");

        publisher.publishEvent(event);

        return "Hello There!";
    }


}
