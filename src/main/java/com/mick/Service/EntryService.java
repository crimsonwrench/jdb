package com.mick.Service;

import com.mick.Entity.Entry;
import com.mick.Entity.Project;
import com.mick.Entity.User;
import com.mick.Repository.EntryRepository;
import com.mick.Repository.ProjectRepository;
import com.mick.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

@Service
public class EntryService {

    private final EntryRepository entryRepository;
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;

    @Autowired
    public EntryService(EntryRepository entryRepository, UserRepository userRepository, ProjectRepository projectRepository) {
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
    public void insertEntry(int uid, int pid, String message){
        User user = userRepository.findById(uid).get();
        Project project = projectRepository.findById(pid).get();
        entryRepository.save(new Entry(user, project, message));
    }
    public User findById(int id){
        return userRepository.findById(id).get();
    }
}

