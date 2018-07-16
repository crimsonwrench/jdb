package com.mick.Service;

import com.mick.Entity.Issue;
import com.mick.Entity.Project;
import com.mick.Entity.User;
import com.mick.Repository.IssueRepository;
import com.mick.Repository.ProjectRepository;
import com.mick.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.Scanner;

@Service
public class IssueService {

    private final IssueRepository issueRepository;
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;

    private Scanner scan = new Scanner(System.in);

    @Autowired
    public IssueService(IssueRepository issueRepository, UserRepository userRepository, ProjectRepository projectRepository) {
        this.issueRepository = issueRepository;
        this.userRepository = userRepository;
        this.projectRepository = projectRepository;
    }

    public String findByUserName(@Param("user_name") String userName){
        StringBuilder result = new StringBuilder();
        result.append("id\tpid\tuid\tmessage\n");

        for(Issue issues : issueRepository.findByUserName(userName)){
            result.append(issues.toString()).append("\n");
        }
        return result.toString();
    }

    public String findByUserId(@Param("user_id") int userId){
        StringBuilder result = new StringBuilder();
        result.append("id\tpid\tuid\tmessage\n");

        for(Issue issues : issueRepository.findByUserId(userId)){
            result.append(issues.toString()).append("\n");
        }
        return result.toString();
    }

    public String findByProjectTitle(@Param("project_title") String projectTitle){
        StringBuilder result = new StringBuilder();
        result.append("id\tpid\tuid\tmessage\n");

        for(Issue issues: issueRepository.findByProjectTitle(projectTitle)){
            result.append(issues.toString()).append("\n");
        }
        return result.toString();
    }

    public String printIssues(){
        StringBuilder result = new StringBuilder();

        result.append("id\tpid\tuid\tmessage\n");

        for(Issue issue : issueRepository.findAll()){
            result.append(issue.toString()).append("\n");
        }
        return result.toString();
    }
    public void createIssue(){
        try {
            System.out.println("Enter issue message: ");
            String message = scan.nextLine();
            System.out.println("Enter user id: ");
            int uid = scan.nextInt();
            System.out.println("Enter project id: ");
            int pid = scan.nextInt();
            User user = userRepository.findById(uid).get();
            Project project = projectRepository.findById(pid).get();
            issueRepository.save(new Issue(user, project, message));
        } catch (Exception ex){
            System.out.println("Failed to create new issue! Error text: " + ex.getMessage());
        }
    }
    public void updateIssue(int eid, String updateWhat) {
        try {
            Issue currentIssue = issueRepository.findById(eid).get();
            switch (updateWhat.toLowerCase()) {
                case "uid":
                    System.out.println("Enter new UID: ");
                    int uid = scan.nextInt();
                    currentIssue.setUser(userRepository.findById(uid).get());
                    issueRepository.save(currentIssue);
                    break;
                case "pid":
                    System.out.println("Enter new PID: ");
                    int pid = scan.nextInt();
                    currentIssue.setUser(userRepository.findById(pid).get());
                    issueRepository.save(currentIssue);
                    break;
                case "message":
                    System.out.println("Enter new message:");
                    String message = scan.nextLine();
                    currentIssue.setReportText(message);
                    issueRepository.save(currentIssue);
                    break;
                default:
                    System.out.println("Unknown parameter. Use \'user\'. \'project\' or \'message\'");
                    break;
            }
        } catch(Exception ex){
            System.out.println("Failed to update an existing issue! Error text: " + ex.getMessage());
        }
    }
    public User findById(int id){
        return userRepository.findById(id).get();
    }
    public void deleteById(int id){
        try {
            issueRepository.deleteById(id);
        } catch(Exception ex){
            System.out.println("Failed to delete an issue! Error text: " + ex.getMessage());
        }
    }
}

