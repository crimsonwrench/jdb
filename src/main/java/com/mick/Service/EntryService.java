package com.mick.Service;

import com.mick.Entity.Entry;
import com.mick.Entity.Project;
import com.mick.Entity.User;
import com.mick.Repository.EntryRepository;
import com.mick.Repository.ProjectRepository;
import com.mick.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

@Service
public class BugTrackingService {

    private final EntryRepository entryRepository;
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;

    public BugTrackingService(EntryRepository entryRepository, UserRepository userRepository, ProjectRepository projectRepository) {
        this.entryRepository = entryRepository;
        this.userRepository = userRepository;
        this.projectRepository = projectRepository;
    }

    public String findByUserName(@Param("user_name") String userName){
        StringBuilder result = new StringBuilder();
        result.append("eid\tpid\tuid\tmessage\n");

        for(Entry entries: entryRepository.findByUserName(userName)){
            result.append(entries.toString()).append("\n");
        }
        return result.toString();
    }

    public String findByProjectTitle(@Param("project_title") String projectTitle){
        StringBuilder result = new StringBuilder();
        result.append("eid\tpid\tuid\tmessage\n");

        for(Entry entries: entryRepository.findByProjectTitle(projectTitle)){
            result.append(entries.toString()).append("\n");
        }
        return result.toString();
    }

    public String printEntries(){
        StringBuilder result = new StringBuilder();

        result.append("eid\tpid\tuid\tmessage\n");

        for(Entry entry : entryRepository.findAll()){
            result.append(entry.toString()).append("\n");
        }
        return result.toString();
    }

    public String printUsers(){
        StringBuilder result = new StringBuilder();

        result.append("uid\tname\n");

        for(User user : userRepository.findAll()){
            result.append(user.toString()).append("\n");
        }
        return result.toString();
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

