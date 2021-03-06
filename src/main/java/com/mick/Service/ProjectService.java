package com.mick.Service;

import com.mick.Entity.Project;
import com.mick.Repository.ProjectRepository;
import com.mick.Utility.CmdHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;
    private File projectsFolder = new File("./db/Entities/Projects");

    @Autowired
    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public String printProjects(){
        StringBuilder result = new StringBuilder();

        result.append(CmdHandler.getSpaces("pid")).append("title\n");
        for(Project issue : projectRepository.findAll()){
            result.append(issue.toString());
        }
        return result.toString();
    }

    public String loadProjects(){

        File[] listOfProjects = projectsFolder.listFiles((projectsFolder, name) -> name.toLowerCase().endsWith(".pr"));

        if (listOfProjects != null) {
            for (File project : listOfProjects) {
                    projectRepository.save(new Project(project.getName().substring(0, project.getName().length() - 3)));
                    System.out.println("Loading project " + project.getAbsolutePath());
            }
        }
        return "\n" + printProjects();
    }

    public String createProject(String title){
        try {
            projectRepository.save(new Project(title));
            System.out.println("Project was created successfully.");
            return printProjects();
        } catch(Exception ex){
            return "Failed to create project! Error text: " + ex.getMessage();
        }
    }

    public String updateProject(int id, String parameter, String value){
        try{
            Project currentProject = projectRepository.findById(id).get();
            switch(parameter.toLowerCase()){
                case "title":
                    currentProject.setTitle(value);
                break;
                default:
                    System.out.println("Unknown parameter!");
            }
            projectRepository.save(currentProject);
            System.out.println("Project was updated successfully.");
            return printProjects();
        } catch (Exception ex){
            return "Failed to update project! Error text: " + ex.getMessage();
        }
    }
    public String deleteById(int id){
        try {
            projectRepository.deleteById(id);
            System.out.println("Project was deleted successfully.");
            return printProjects();
        } catch(Exception ex){
            return "Failed to delete project! Error text: " + ex.getMessage();
        }
    }
    public String findById(int uid){

        return CmdHandler.getSpaces("pid") + "name\n" +
                projectRepository.findById(uid).get().toString();
    }
}
