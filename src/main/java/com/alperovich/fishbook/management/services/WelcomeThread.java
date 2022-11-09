package com.alperovich.fishbook.management.services;

public class WelcomeThread extends Thread {

    @Override
    public void run() {
        System.out.println(getName() + " is started");
    }
}
