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

    public static String getSpaces(String word){
        StringBuilder result = new StringBuilder();
        result.append(word);
        for (int i = word.length(); i < 8; i++) {
            if (i == 6) {
                result.append("|");
            } else {
                result.append(" ");
            }
        }
        return result.toString();
    }

    private Scanner scan = new Scanner(System.in);

    public void Init(){
        System.out.print(">");
        String command = scan.nextLine();
        try {
            String[] tokens = command.split(" ");
            switch (tokens[0].toLowerCase()) {
                case "create":
                    if(tokens.length < 3){
                        System.out.println("Usage: create <type> [parameters]. Type \'help create\' for help.");
                    } else {
                        if(tokens[1].equals("issue")){
                            for(int i = 5; i < tokens.length; i++)
                                tokens[4] += " " + tokens[i];
                            System.out.println(createEntry(tokens[1], tokens[2], tokens[3], tokens[4]));
                        }
                        else if (tokens[1].equals("project") || tokens[1].equals("user")){
                            for(int i = 3; i < tokens.length; i++){
                                tokens[2] += " " + tokens[i];
                            }
                            System.out.println(createEntry(tokens[1], tokens[2], tokens[2], tokens[2]));
                        }
                    }
                    break;
                case "update":
                    if(tokens.length < 5){
                        System.out.println("Usage: update <table> <id> <parameter> <value> . Type \'help update\' for help.");
                    } else {
                        if((tokens[1].equals("issue") && tokens[3].equals("text")) || (tokens[1].equals("project") && tokens[3].equals("title")) || (tokens[1].equals("user") && tokens[3].equals("name"))) {

                            for(int i = 5; i < tokens.length; i++){
                                tokens[4] += " " + tokens[i];
                            }
                        }
                        System.out.println(updateEntry(tokens[1], Integer.valueOf(tokens[2]), tokens[3], tokens[4]));
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
                    if(tokens.length < 3){
                        System.out.println("Usage: find <type> [parameters]. Type \'help find\' for help.");
                    } else {
                        if (tokens[1].equals("issue") && tokens.length > 3) {
                            if (tokens[2].equals("all")) {
                                System.out.println(findEntry(tokens[1], tokens[2], tokens[3], tokens[4]));
                            } else {
                                System.out.println(findEntry(tokens[1], tokens[2], tokens[3], tokens[3]));
                            }
                        } else if (tokens[1].equals("user") || tokens[1].equals("project")) {
                            System.out.println(findEntry(tokens[1], tokens[2], tokens[2], tokens[2]));
                        }
                    }
                    break;
                case "list":
                    if(tokens.length < 2) {
                        System.out.println("Usage: list <table>. Tables available: users, projects, issues, all.");
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
                case "load":
                    System.out.println("Loading filesystem repository...");
                    issueService.FillRepositories();
                    System.out.println(listTable("all"));
                    break;
                case "exit":
                    exit(0);
                    break;
                default:
                    System.out.println("Unknown command!");
                    break;
            }
        } catch(Exception ex){
            System.out.println("Error! " + ex.toString());
        }
    }

    private void displayHelp(String command) {
        switch (command.toLowerCase()) {
            case "create":
                System.out.println("\'create <type> [parameters]\' - where <type> is an entry type: issue, user, project.");
                System.out.println("[parameters]       - vary for entry type:");
                System.out.println("   for \'issue\'   - <pid> <uid> <text>");
                System.out.println("   for \'user\'    - <name>");
                System.out.println("   for \'project\' - <name>");
                break;
            case "update":
                System.out.println("\'update <type> <id> <parameter> <value>\' - where <type> is an entry type to be updated: issue, user, project.");
                System.out.println("<parameter> - parameter to be updated");
                break;
            case "delete":
                System.out.println("\'delete <type> <id>\' - where <type> is an entry type to be deleted: issue, user, project.");
                break;
            case "find":
                System.out.println("\'find <type> [parameters]\'   - where <entry> is entry type to be looked for: issue, user, project.");
                System.out.println("[parameters]        - vary for entry type:");
                System.out.println("    for \'user\'    - <uid>    - finds user by his id");
                System.out.println("    for \'project\' - <pid>    - finds project by its id");
                System.out.println("    for \'issue\'   - [criteria]      - finds all issues based on criteria:");
                System.out.println("        \'uid    - <uid>\'            - finds all issues made by user with id <uid> ");
                System.out.println("        \'pid    - <pid>\'            - finds all issues made in project with id <pid>");
                System.out.println("        \'all    - <pid> <uid>\'      - finds all issues made by user with id <uid> in project with id <pid>");

                break;
            case "list":
                System.out.println("\'list <table>\'  - where <table> is a table to be displayed: issues, users, projects, all.");
                break;
            default:
                System.out.println("Unknown command!");
                break;
        }
    }
    private String updateEntry(String table, int id, String parameter, String value){
        switch(table.toLowerCase()){
            case "user":
                return userService.updateUser(id, parameter, value);
            case "project":
                return projectService.updateProject(id, parameter, value);
            case "issue":
                return issueService.updateIssue(id, parameter, value);
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
            case "all":
                return issueService.printIssues() + "\n" + userService.printUsers() + "\n" + projectService.printProjects();
            default:
                return "Wrong table!";
        }
    }
    private String createEntry(String type, String parameterOne, String parameterTwo, String parameterThree){
        switch(type.toLowerCase()){
            case "user":
                return userService.createUser(parameterOne);
            case "project":
               return  projectService.createProject(parameterOne);
            case "issue":
                return issueService.createIssue(Integer.valueOf(parameterOne), Integer.valueOf(parameterTwo), parameterThree);
            default:
                return "Wrong type!";
        }
    }
    private String deleteEntry(String type, int id){
        switch(type.toLowerCase()){
            case "user":
                return userService.deleteById(id);
            case "project":
                return projectService.deleteById(id);
            case "issue":
                return issueService.deleteById(id);
            default:
                return "Wrong type!";
        }
    }
    private String findEntry(String type, String parameterOne, String parameterTwo, String parameterThree){
        switch(type.toLowerCase()){
            case "user":
                return userService.findById(Integer.valueOf(parameterOne));
            case "project":
                return projectService.findById(Integer.valueOf(parameterOne));
            case "issue":
                switch(parameterOne.toLowerCase()){
                    case "uid":
                        return issueService.findByUserId(Integer.valueOf(parameterTwo));
                    case "pid":
                        return issueService.findByProjectId(Integer.valueOf(parameterTwo));
                    case "all":
                        return issueService.findByProjectIdAndUserId(Integer.valueOf(parameterTwo), Integer.valueOf(parameterThree));
                        default:
                        return "Wrong criteria!";
                }
            default:
                return "Wrong type!";
        }
    }
}
