package com.example.hw3;

import java.util.Comparator;

public class SortContacts implements Comparator<Contact> {

    @Override
    public int compare(Contact anyContact, Contact otherContact) {
        return anyContact.getLine1().compareTo(otherContact.getLine1());
    }
}
