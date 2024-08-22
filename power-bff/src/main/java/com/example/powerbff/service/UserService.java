package com.example.powerbff.service;

import com.example.powerbff.entity.User;
import com.example.powerbff.exception.user.*;
import com.example.powerbff.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User createUser(User user) throws UsernameAlreadyExistsException, EmailAlreadyExistsException,
                                            BlankUsernameException {
        if (user.getUsername() == null || user.getUsername().isEmpty()) {
            throw new BlankUsernameException();
        } else if (userRepository.existsByUsername(user.getUsername())) {
            throw new UsernameAlreadyExistsException(user.getUsername());
        } else if (user.getEmail() != null && userRepository.existsByEmail(user.getEmail())) {
            throw new EmailAlreadyExistsException(user.getEmail());
        }
        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Transactional
    public User updateUser(Long id, User userDetails) throws UserNotFoundException, UsernameAlreadyExistsException {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));

        if (!Objects.equals(userDetails.getUsername(), "") && !Objects.equals(userDetails.getUsername(), null)) {
            if (this.getUserByUsername(userDetails.getUsername()).isPresent()) {
                throw new UsernameAlreadyExistsException(user.getUsername());
            }
            user.setUsername(userDetails.getUsername());
        }
        if (!Objects.equals(userDetails.getPassword(), "") && !Objects.equals(userDetails.getPassword(), null))
            user.setPassword(userDetails.getPassword());
        if (!Objects.equals(userDetails.getEmail(), "") && !Objects.equals(userDetails.getEmail(), null))
            user.setEmail(userDetails.getEmail());
        if (!Objects.equals(userDetails.getName(), "") && !Objects.equals(userDetails.getName(), null))
            user.setName(userDetails.getName());
        if (!Objects.equals(userDetails.getImgUrl(), "") && !Objects.equals(userDetails.getImgUrl(), null))
            user.setImgUrl(userDetails.getImgUrl());

        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

}
