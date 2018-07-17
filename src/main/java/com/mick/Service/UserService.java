package com.mick.Service;

import com.mick.Entity.User;
import com.mick.Repository.UserRepository;
import com.mick.Utility.CmdHandler;
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

        result.append(CmdHandler.getSpaces("uid")).append("name\n");
        for(User user : userRepository.findAll()){
            result.append(user.toString());
        }
        return result.toString();
    }

    public String createUser(String userName){
        try {
            userRepository.save(new User(userName));
            System.out.println("User was created successfully.");
            return printUsers();
        } catch (Exception ex){
            return "Failed to create user! Error text: " + ex.getMessage();
        }
    }

    public String updateUser(int id, String parameter, String value){
        try{
            User currentUser = userRepository.findById(id).get();
            switch(parameter.toLowerCase()){
                case "name":
                    currentUser.setName(value);
                    userRepository.save(currentUser);
                    break;
                default:
                    System.out.println("Unknown parameter!");
            }
            System.out.println("User was updated successfully.");
            userRepository.save(currentUser);
            return printUsers();
        } catch (Exception ex){
            return "Failed to update user! Error text: " + ex.getMessage();
        }
    }

    public String deleteById(int id){
        try {
            userRepository.deleteById(id);
            System.out.println("User was deleted successfully.");
            return printUsers();
        } catch(Exception ex){
            return "Failed to delete user! Error text: " + ex.getMessage();
        }
    }
    public String findById(int uid){
        return CmdHandler.getSpaces("uid") + "name\n" +
                userRepository.findById(uid).get().toString();
    }
}
