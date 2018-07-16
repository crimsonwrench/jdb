package com.mick.Service;

import com.mick.Entity.User;
import com.mick.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String printUsers(){
        StringBuilder result = new StringBuilder();

        result.append("uid\tname\n");

        for(User user : userRepository.findAll()){
            result.append(user.toString()).append("\n");
        }
        return result.toString();
    }

    public void insertUser(String userName){
        userRepository.save(new User(userName));
    }
    public void deleteById(int id){
        userRepository.deleteById(id);
    }
}
