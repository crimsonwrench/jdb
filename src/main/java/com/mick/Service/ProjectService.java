package com.mick.Service;

import com.mick.Entity.Project;
import com.mick.Repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Scanner;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;

    private Scanner scan = new Scanner(System.in);

    @Autowired
    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public String printProjects(){
        StringBuilder result = new StringBuilder();

        result.append("pid\ttitle\n");

        for(Project project : projectRepository.findAll()){
            result.append(project.toString()).append("\n");
        }
        return result.toString();
    }

    public void createProject(){
        try {
            System.out.println("Enter project name: ");
            String title = scan.nextLine();
            projectRepository.save(new Project(title));
        } catch(Exception ex){
            System.out.println("Failed to create new project! Error text: " + ex.getMessage());
        }
    }

    public void updateProject(int id, String newTitle){
        try{
            Project currentProject = projectRepository.findById(id).get();
            currentProject.setTitle(newTitle);
        } catch (Exception ex){
            System.out.println("Failed to update user! Error text: " + ex.getMessage());
        }
    }

    public void deleteById(int id){
        try {
            projectRepository.deleteById(id);
        } catch(Exception ex){
            System.out.println("Failed to delete a project! Error text: " + ex.getMessage());
        }
    }
}
