package com.testtask.controller;

import com.testtask.model.User;
import com.testtask.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
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
        //example
        repository.save(new User("first", "first"));
        repository.save(new User("second", "second"));
        repository.save(new User("third", "third"));
    }

    @GetMapping()
    public List<User> getAllUsers() {
        return repository.findAll();
    }

    @GetMapping("/{name}")
    public Optional<User> getUser(@PathVariable String name) {
        return repository.findById(name);
    }

    @PostMapping()
    public String addUser(@RequestParam User user) {
        repository.save(user);
        return user + " added.";
    }

    @DeleteMapping("/{name}")
    public String deleteUser(@PathVariable String name) {
        repository.deleteById(name);
        return "user with name : " + name + " deleted.";
    }

//    @PatchMapping("/{name}")
//    public Optional<User> updateUser(@PathVariable String name, @RequestParam User user) {
//
//        User originalUser = repository.findById(name).get();
//        originalUser.setEmail(user.getEmail());
//        originalUser.setName(user.getName());
//        repository.save()
//
//        return repository.findById(name);
//    }
}
