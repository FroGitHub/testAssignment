package com.ClearSolutions.testassig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Map;

@RestController
@RequestMapping
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/users")
    public List<User> home(){
        return userService.getUsers();
    }


    @PostMapping("/create")
    public ResponseEntity<String> create(@RequestBody User user){

        if (!user.checkFields().equals("fine")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(user.checkFields());
        }
        if(!user.validEmail().equals("fine")){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(user.validEmail());
        }
        if (user.getAge() < 18) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("error: less than 18");
        }

        userService.saveUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body("user is created");
    }


    @PutMapping("/update")
    public ResponseEntity<String> update(@RequestBody User updatedUser) {
        User existingUser = userService.getUserById(updatedUser.getId()); // getting user by id from Json request
        if (existingUser == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        if(updatedUser.getBirthDate() != null && updatedUser.getAge() <= 18){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("error: updated age less than 18");
        }
        if(updatedUser.getEmail() != null && !(updatedUser.validEmail().equals("fine"))){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(updatedUser.validEmail());
        }
        if (updatedUser.getEmail() != null) {
            existingUser.setEmail(updatedUser.getEmail());
        }
        if (updatedUser.getFirstName() != null) {
            existingUser.setFirstName(updatedUser.getFirstName());
        }
        if (updatedUser.getLastName() != null) {
            existingUser.setLastName(updatedUser.getLastName());
        }
        if (updatedUser.getBirthDate() != null) {
            existingUser.setBirthDate(updatedUser.getBirthDate());
        }
        if (updatedUser.getAddress() != null) {
            existingUser.setAddress(updatedUser.getAddress());
        }
        if (updatedUser.getPhoneNumber() != null) {
            existingUser.setPhoneNumber(updatedUser.getPhoneNumber());
        }

        userService.saveUser(existingUser);

        return ResponseEntity.status(HttpStatus.OK).body("User updated successfully");
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteUser(@RequestBody Map<String, Long> requestBody){
        Long id = requestBody.get("id");
        User existingUser = userService.getUserById(id);
        if (existingUser == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found for deleting");
        }

        userService.deleteUserById(id);
        return ResponseEntity.status(HttpStatus.OK).body("user is deleted");
    }

    @GetMapping("/search")
    public List<User> getUsersByBirthDate(@RequestBody DateRange dateRange){

        return userService.findByBirthDateBetween(dateRange.getDate_from(), dateRange.getDate_to());
    }

}
