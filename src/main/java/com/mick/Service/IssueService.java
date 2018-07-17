package com.mick.Service;

import com.mick.Entity.Issue;
import com.mick.Entity.Project;
import com.mick.Entity.User;
import com.mick.Repository.IssueRepository;
import com.mick.Repository.ProjectRepository;
import com.mick.Repository.UserRepository;
import com.mick.Utility.CmdHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Arrays;
import java.util.Scanner;

@Service
public class IssueService {

    private final IssueRepository issueRepository;
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;

    private Scanner scan = new Scanner(System.in);
    private File usersFolder = new File("./db/Entities/Users");
    private File projectsFolder = new File("./db/Entities/Projects");
    private File issuesFolder = new File("./db/Entities/Issues");

    @Autowired
    public IssueService(IssueRepository issueRepository, UserRepository userRepository, ProjectRepository projectRepository) {
        this.issueRepository = issueRepository;
        this.userRepository = userRepository;
        this.projectRepository = projectRepository;
    }

    public void FillRepositories()
    {

        File[] listOfUsers = usersFolder.listFiles();
        File[] listOfProjects = projectsFolder.listFiles();
        File[] listOfIssues = issuesFolder.listFiles();


        if (listOfUsers != null) {
            for (File user : listOfUsers) {
                userRepository.save(new User(user.getName()));
                System.out.println("Saving user " + user.getName());
            }
        }
        if (listOfProjects != null) {
            for (File project : listOfProjects) {
                projectRepository.save(new Project(project.getName()));
                System.out.println("Saving project " + project.getName());
            }
        }
        if(listOfIssues != null) {
            for (File issue : listOfIssues) {
                System.out.println("Reading issue: " + issue.getName());
                try (BufferedReader br = new BufferedReader(new FileReader(issue))) {
                    StringBuilder result = new StringBuilder();
                    String currentLine;

                    while((currentLine = br.readLine()) != null) {
                        result.append(currentLine).append("~");
                    }

                    String[] tokens = result.toString().split("~");

                    createIssue(Integer.valueOf(tokens[0]), Integer.valueOf(tokens[1]), tokens[2]);
                }
                catch (Exception ex) {
                    System.out.println("Failed to load issue: " +ex.getMessage());
                }
            }
        }

    }

    public String findByUserId(@Param("user_id") int userId){
        StringBuilder result = new StringBuilder();

        result.append(CmdHandler.getSpaces("id")).append(CmdHandler.getSpaces("pid")).append(CmdHandler.getSpaces("uid")).append("text\n");

        for(Issue issues : issueRepository.findByUserId(userId)){
            result.append(issues.toString());
        }
        return result.toString();
    }

    public String findByProjectId(@Param("project_id") int userId){
        StringBuilder result = new StringBuilder();

        result.append(CmdHandler.getSpaces("id")).append(CmdHandler.getSpaces("pid")).append(CmdHandler.getSpaces("uid")).append("text\n");

        for(Issue issues : issueRepository.findByProjectId(userId)){
            result.append(issues.toString());
        }
        return result.toString();
    }

    public String findByProjectIdAndUserId(@Param("project_id") int projectId, @Param("user_id") int userId){
        StringBuilder result = new StringBuilder();

        result.append(CmdHandler.getSpaces("id")).append(CmdHandler.getSpaces("pid")).append(CmdHandler.getSpaces("uid")).append("text\n");

        for(Issue issues : issueRepository.findByProjectIdAndUserId(projectId, userId)){
            result.append(issues.toString());
        }
        return result.toString();
    }

    public String printIssues(){
        StringBuilder result = new StringBuilder();

        result.append(CmdHandler.getSpaces("id")).append(CmdHandler.getSpaces("pid")).append(CmdHandler.getSpaces("uid")).append("text\n");
        for(Issue issue : issueRepository.findAll()){
            result.append(issue.toString());
        }
        return result.toString();
    }
    public String createIssue(int pid, int uid, String text){
        try {
            User user = userRepository.findById(uid).get();
            Project project = projectRepository.findById(pid).get();
            issueRepository.save(new Issue(user, project, text));
            System.out.println("Issue was created successfully.");
            return printIssues();
        } catch (Exception ex){
            return "Failed to create issue! Error text: " + ex.getMessage();
        }
    }
    public String updateIssue(int eid, String parameter, String value) {
        try {
            Issue currentIssue = issueRepository.findById(eid).get();
            switch (parameter.toLowerCase()) {
                case "uid":
                    currentIssue.setUser(userRepository.findById(Integer.valueOf(value)).get());
                    break;
                case "pid":
                    currentIssue.setUser(userRepository.findById(Integer.valueOf(value)).get());
                    break;
                case "text":
                    currentIssue.setReportText(value);
                    break;
                default:
                    System.out.println("Unknown parameter. Use \'uid\'. \'pid\' or \'text\'");
                    break;
            }
            issueRepository.save(currentIssue);
            System.out.println("Issue was updated successfully.");
            return printIssues();
        } catch(Exception ex){
            return "Failed to update issue! Error text: " + ex.getMessage();
        }
    }
    public String deleteById(int id){
        try {
            issueRepository.deleteById(id);
            System.out.println("Issue was deleted successfully.");
            return printIssues();
        } catch(Exception ex){
            return "Failed to delete issue! Error text: " + ex.getMessage();
        }
    }
}

