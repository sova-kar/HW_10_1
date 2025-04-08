package org.skypro.skyshop.service;

import org.springframework.stereotype.Service;

@Service
public class CounterService {
    private int count = 0;


    public CounterService() {
    }

    public String getCount() {
        return String.valueOf(count);
    }

    public void increment() {
        count++;
    }
}