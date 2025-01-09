package com.example.password_storage.service;

import com.example.password_storage.model.User;
import com.example.password_storage.repository.UserRepository;
import com.example.password_storage.util.Encryption;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Iterable<User> findAllUsers() {
        return this.userRepository.findAll();
    }

    public User addOneUser(User user, Integer safetyLevel) {
        switch(safetyLevel) {
            case 1:
                Encryption.hash(user);
                break;
            case 2:
                Encryption.hashWithSalt(user);
                break;
            case 3:
                Encryption.hashWithSaltPepper(user);
                break;
            //TODO: what else is needed?
        }
        return this.userRepository.save(user);
    }

    public void deleteAllUsers() {
        this.userRepository.deleteAll();
    }
}
