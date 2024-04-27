package com.ClearSolutions.testassig;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {


    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getUsers(){
        return userRepository.findAll();
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }

    public User getUserById(Long id) {
        return userRepository.getUserById(id);
    }

    @Transactional
    public void deleteUserById(Long id){
        userRepository.deleteUserById(id);
    }

    public List<User> findByBirthDateBetween(String date_from, String date_to){
        return userRepository.findByBirthDateBetween(date_from, date_to);
    }

}
