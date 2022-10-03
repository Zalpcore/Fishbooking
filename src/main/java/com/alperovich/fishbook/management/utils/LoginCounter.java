package com.alperovich.fishbook.management.utils;

public class LoginCounter extends Thread{

    @Override
    public void run() {
        System.out.println(getName() + "is started");

    }
}
