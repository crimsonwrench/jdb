package com.mick.Utility;

import com.mick.Service.IssueService;
import com.mick.Service.ProjectService;
import com.mick.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Scanner;

import static java.lang.System.exit;

@Component
public class CmdHandler {

    private final IssueService issueService;
    private final UserService userService;
    private final ProjectService projectService;

    @Autowired
    public CmdHandler(IssueService issueService, UserService userService, ProjectService projectService) {
        this.issueService = issueService;
        this.userService = userService;
        this.projectService = projectService;
    }


    private Scanner scan = new Scanner(System.in);

    public void parse(){
        System.out.print(">");
        String command = scan.nextLine();
        try {
            String[] tokens = command.split(" ");
            switch (tokens[0].toLowerCase()) {
                case "create":
                    if(tokens.length < 2){
                        System.out.println("Usage: create <table>. Type \'help create\' for help.");
                    } else {
                        System.out.println(createEntry(tokens[1]));
                    }
                    break;
                case "update":
                    if(tokens.length < 4){
                        System.out.println("Usage: update <table> <id> <parameter>. Type \'help update\' for help.");
                    } else {
                        System.out.println(updateEntry(tokens[1], Integer.valueOf(tokens[2]), tokens[3]));
                    }
                    break;
                case "delete":
                    if(tokens.length < 3){
                        System.out.println("Usage: delete <type> <id>. Type \'help remove \' for help.");
                    } else {
                        System.out.println(deleteEntry(tokens[1], Integer.valueOf(tokens[2])));
                    }
                    break;
                case "find":
                    //TODO: Find
                    break;
                case "list":
                    if(tokens.length < 2) {
                        System.out.println("Usage: list <table>. Tables available: users, projects, issues.");
                    } else {
                        System.out.println(listTable(tokens[1]));
                    }
                    break;
                case "help":
                    if(tokens.length < 2){
                        System.out.println("Usage: help <command>. Displays information about <command>");
                        System.out.println("Commands available: create, update, delete, find, list");
                    } else {
                        displayHelp(tokens[1]);
                    }
                    break;
                case "exit":
                    exit(0);
                    break;
                default:
                    System.out.println("Unknown command!");
                    break;
            }
        } catch(Exception ex){
            System.out.println("Error! " + ex.getMessage());
        }
    }

    private void displayHelp(String command) {
        switch (command.toLowerCase()) {
            case "create":
                System.out.println("\'create <type>\' - where <type> is an entry type: issue, user, entry.");
                break;
            case "update":
                System.out.println("\'update <type> <id> <parameter>\' - where <type> is an entry type to be updated: user, issue, project.");
                System.out.println("<parameter> - parameter to be updated");
                break;
            case "delete":
                System.out.println("\'delete <type> <id>\' - where <type> is an entry type: issue, user, entry.");
                break;
            case "find":
                System.out.println("\'find <ENTITY>\'   - where <ENTITY> is an entity to be searched: User, Issue, Project.");
                break;
            case "list":
                System.out.println("\'list <table>\'  - where <table> is a table to be displayed: Users, Issues, Projects.");
                break;
            default:
                System.out.println("Unknown command!");
                break;
        }
    }
    private String updateEntry(String table, int id, String parameter){
        switch(table.toLowerCase()){
            case "user":
                userService.updateUser(id, parameter);
                return listTable("users");
            case "project":
                projectService.updateProject(id, parameter);
                return listTable("projects");
            case "issue":
                issueService.updateIssue(id, parameter);
                return listTable("issues");
            default:
                return "Wrong parameter!";
        }
    }
    private String listTable(String table){
        switch(table.toLowerCase()){
            case "users":
                return userService.printUsers();
            case "projects":
                return projectService.printProjects();
            case "issues":
                return issueService.printIssues();
            default:
                return "Wrong table!";
        }
    }
    private String createEntry(String type){
        switch(type.toLowerCase()){
            case "user":
                userService.createUser();
                return userService.printUsers();
            case "project":
                projectService.createProject();
                return projectService.printProjects();
            case "issue":
                issueService.createIssue();
                return issueService.printIssues();
            default:
                return "Wrong type!";
        }
    }
    private String deleteEntry(String type, int id){
        switch(type.toLowerCase()){
            case "user":
                userService.deleteById(id);
                return userService.printUsers();
            case "project":
                projectService.deleteById(id);
                return projectService.printProjects();
            case "issue":
                issueService.deleteById(id);
                return issueService.printIssues();
            default:
                return "Wrong type!";
        }
    }
}
