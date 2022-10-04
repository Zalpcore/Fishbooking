package com.alperovich.fishbook.management.utils;

public class WelcomeThread extends Thread {

    @Override
    public void run() {
        System.out.println(getName() + " is started");
    }
}
