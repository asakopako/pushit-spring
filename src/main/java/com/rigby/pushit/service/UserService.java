package com.rigby.pushit.service;

import com.rigby.pushit.config.exception.BadRequestException;
import com.rigby.pushit.config.exception.NotFoundException;
import com.rigby.pushit.model.User;
import com.rigby.pushit.repository.UserRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired private UserRepository userRepository;


    public void register(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new BadRequestException("Email already exists");
        }
        user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
        userRepository.save(user);
    }

    public User login(String email, String password) {
        if(!userRepository.existsByEmail(email))
            throw new BadRequestException("Invalid credentials");

        User user = userRepository.findByEmail(email);

        if (!BCrypt.checkpw(password, user.getPassword()))
            throw new BadRequestException("Invalid credentials");

        return user;
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public List<User> findContactsById(Long id) {
        if(!userRepository.existsById(id))
            throw new NotFoundException("User id doesn't exist");

        return userRepository.getOne(id).getContacts();
    }

    public void createContact(Long userId, String email) {
        if(!userRepository.existsById(userId) || !userRepository.existsByEmail(email)) {
            throw new NotFoundException("User id or email doesn't exist");
        }
        User user = userRepository.getOne(userId);
        user.addContact(userRepository.findByEmail(email));
        userRepository.save(user);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

}
