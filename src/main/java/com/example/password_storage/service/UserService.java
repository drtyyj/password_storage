package com.example.password_storage.service;

import com.example.password_storage.model.User;
import com.example.password_storage.repository.UserRepository;
import com.example.password_storage.util.Encryption;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
                Encryption.hashWithPepper(user);
                break;
            case 4:
                Encryption.hashWithSaltPepper(user);
                break;
            //TODO: what else is needed?
        }
        return this.userRepository.save(user);
    }

    public void deleteAllUsers() {
        this.userRepository.deleteAll();
    }

    public User verifyUser(User user, Integer safetyLevel) {
        Optional<User> verifyUserOpt = userRepository.findById(user.getUsername());
        if(verifyUserOpt.isEmpty())
            return null;
        User verifyUser = verifyUserOpt.get();
        String encryptedPassword = user.getPassword();

        switch(safetyLevel) {
            case 1:
                encryptedPassword = Encryption.verifyHash(user.getPassword());
                break;
            case 2:
                encryptedPassword = Encryption.verifyWithSalt(user.getPassword(), verifyUser.getSalt());
                break;
            case 3:
                encryptedPassword = Encryption.verifyWithPepper(user.getPassword());
                break;
            case 4:
                encryptedPassword = Encryption.verifyWithSaltPepper(user.getPassword(), verifyUser.getSalt());
                break;
        }
        if(verifyUser.getPassword().equals(encryptedPassword))
            return verifyUser;
        else
            return null;
    }
}
