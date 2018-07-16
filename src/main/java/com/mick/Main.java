package com.mick;

import com.mick.Utility.CmdHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main implements CommandLineRunner {

    private final CmdHandler handler;

    @Autowired
    public Main(CmdHandler handler) {
        this.handler = handler;
    }

    public static void main(String[] args) {

        SpringApplication.run(Main.class, args);
    }

    @Override
    public void run(String... args) {

        System.out.println("------------------------------------------------------");
        System.out.println("Welcome to the issue tracking system. ");
        System.out.println("Type \'help\' to print available commands.");

        while (true) {
            handler.parse();
        }
    }
}
