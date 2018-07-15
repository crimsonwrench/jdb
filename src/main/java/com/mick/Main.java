package com.mick;

import com.mick.Service.BugTrackingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

import static java.lang.System.exit;

@SpringBootApplication
public class Main implements CommandLineRunner {

    private final BugTrackingService bugTrackingService;

    @Autowired
    public Main(BugTrackingService bugTrackingService) {
        this.bugTrackingService = bugTrackingService;
    }

    public static void main(String[] args)
    {

        SpringApplication.run(Main.class, args);
    }

    @Override
    public void run(String... args) {
        String command;
        String[] tokens;
        Scanner scan = new Scanner(System.in);
        System.out.println("------------------------------------------------------");
        System.out.println("Welcome to the bugtracking system. ");
        System.out.println("Type \'help\' to print available commands.");
        while(true){
            System.out.print(">");
            command = scan.nextLine().toLowerCase();
            tokens = command.split(" ");

            switch(tokens[0]){
                case "create":
                    //TODO: Create
                    break;
                case "update":
                    //TODO: Update
                    break;
                case "delete":
                    //TODO: Remove
                    break;
                case "find":
                    //TODO: Find
                    break;
                case "print":
                    //TODO: Print
                    break;
                case "help":
                    //TODO: Help
                    break;
                case "exit":
                    exit(0);
                    break;
                default:
                    System.out.println("Unknown command!");

            }
        }

    }
}
