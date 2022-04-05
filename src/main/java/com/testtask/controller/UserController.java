package com.testtask.controller;

import com.testtask.model.User;
import com.testtask.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserRepository repository;

    @Autowired
    public UserController(UserRepository repository) {
        this.repository = repository;
        //test case
        repository.save(new User("first", "first@mail.com"));
        repository.save(new User("second", "second@mail.com"));
        repository.save(new User("third", "third@mail.com"));
    }

    @GetMapping()
    public ResponseEntity<?> getAllUsers() {
        List<User> users = repository.findAll();
        return new ResponseEntity<>(users, users.size() > 0 ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{name}")
    public ResponseEntity<?> getUser(@PathVariable String name) {
        Optional<User> user = repository.findById(name);
        if (user.isPresent())
            return new ResponseEntity<>(user.get(), HttpStatus.OK);
        else
            return new ResponseEntity<>("User doesn't exist", HttpStatus.NOT_FOUND);
    }

    @PostMapping()
    public ResponseEntity<?> addUser(@RequestBody User user) {
        if (repository.findById(user.getName()).isPresent()) {
            return new ResponseEntity<>("User already exist", HttpStatus.CONFLICT);
        }
        repository.save(user);
        return new ResponseEntity<>("User " + user.getName() + " added", HttpStatus.OK);
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<?> deleteUser(@PathVariable String name) {
        if (repository.findById(name).isEmpty()) {
            return new ResponseEntity<>("User doesn't exist", HttpStatus.NOT_FOUND);
        }
        repository.deleteById(name);
        return new ResponseEntity<>("User " + name +  " successfully deleted", HttpStatus.OK);
    }

    @PutMapping("/{name}")
    public ResponseEntity<?> updateUser(@PathVariable String name, @RequestBody User user) {
        Optional<User> userOpt = repository.findById(name);
        if (userOpt.isPresent()) {
            User updatedUser = userOpt.get();
            updatedUser.setEmail(user.getEmail());
            repository.save(updatedUser);
            return new ResponseEntity<>("User " + name + " updated", HttpStatus.OK);
        } else
            return new ResponseEntity<>("User doesn't exist", HttpStatus.NOT_FOUND);
    }
}
