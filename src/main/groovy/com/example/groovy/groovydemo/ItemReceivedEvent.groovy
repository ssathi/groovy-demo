package com.example.groovy.groovydemo

class ItemReceivedEvent extends FoysonisEvent<Item> {

    ItemReceivedEvent(Item item, String companyId) {
        super(item, companyId)
    }

}
