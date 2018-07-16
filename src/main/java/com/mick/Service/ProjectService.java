package com.mick.Service;

import com.mick.Entity.Project;
import com.mick.Repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;

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
}
