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

import java.io.*;
import java.util.NoSuchElementException;

@Service
public class IssueService {

    private final IssueRepository issueRepository;
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;

    private File issuesFolder = new File("./db/Entities/Issues");

    @Autowired
    public IssueService(IssueRepository issueRepository, UserRepository userRepository, ProjectRepository projectRepository) {
        this.issueRepository = issueRepository;
        this.userRepository = userRepository;
        this.projectRepository = projectRepository;
    }

    public String loadIssues()
    {
        File[] listOfIssues = issuesFolder.listFiles((issuesFolder, name) -> name.toLowerCase().contains("issue"));

        if(listOfIssues != null) {
            for (File issue : listOfIssues) {
                try (BufferedReader br = new BufferedReader(new FileReader(issue))) {
                    StringBuilder result = new StringBuilder();
                    String currentLine;

                    while ((currentLine = br.readLine()) != null) {
                        result.append(currentLine);
                    }

                    String[] tokens = result.toString().split(";");
                    System.out.println(issue.getName() + ": pid="+tokens[0]+" uid="+tokens[1]+" text="+tokens[2]);


                    Project project = projectRepository.findById(Integer.valueOf(tokens[0])).get();
                    User user = userRepository.findById(Integer.valueOf(tokens[1])).get();

                    issueRepository.save(new Issue(project, user, tokens[2]));
                } catch (IOException ex) {
                    System.out.println("Failed to load an issue " + issue.getName() + "! Error text: " + ex.getMessage());
                } catch (NoSuchElementException ex) {
                    System.out.println("Could not create an issue: missing user, project or both with such IDs!");
                }
            }
        }
        return "\n" + printIssues();
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
            issueRepository.save(new Issue(project, user, text));
            System.out.println("An issue was created successfully.");
            return printIssues();
        } catch (Exception ex){
            return "Failed to create an issue! Error text: " + ex.getMessage();
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
            return "Failed to update an issue! Error text: " + ex.getMessage();
        }
    }
    public String deleteById(int id){
        try {
            issueRepository.deleteById(id);
            System.out.println("Issue was deleted successfully.");
            return printIssues();
        } catch(Exception ex){
            return "Failed to delete an issue! Error text: " + ex.getMessage();
        }
    }
}

