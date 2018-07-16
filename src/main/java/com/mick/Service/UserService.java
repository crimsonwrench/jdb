package com.mick.Service;

import com.mick.Entity.User;
import com.mick.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Scanner;

@Service
public class UserService {

    private final UserRepository userRepository;

    private Scanner scan = new Scanner(System.in);

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

    public void createUser(){
        try {
            System.out.println("Enter user name:");
            String userName = scan.nextLine();
            userRepository.save(new User(userName));
        } catch (Exception ex){
            System.out.println("Failed to create new user! Error text: " + ex.getMessage());
        }
    }

    public void updateUser(int id, String newName){
        try{
            User currentUser = userRepository.findById(id).get();
            currentUser.setName(newName);
        } catch (Exception ex){
            System.out.println("Failed to update user! Error text: " + ex.getMessage());
        }
    }

    public void deleteById(int id){
        try {
            userRepository.deleteById(id);
        } catch(Exception ex){
            System.out.println("Failed to delete user! Error text: " + ex.getMessage());
        }
    }

    public void deleteByName(String userName){

    }
}
