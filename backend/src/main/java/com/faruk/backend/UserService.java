package com.faruk.backend;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GameRepository gameRepository;

    public UserResponse registerUser(User user) { //register method
        if(userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new RuntimeException("Username is already in use");
        }

        String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
        user.setPassword(hashedPassword);

        if(user.getBalance()==0.0){
            user.setBalance(100.0);
        }

        User savedUser = userRepository.save(user);
        return new UserResponse(savedUser);
    }

    public UserResponse loginUser(Map <String, String> credentials) {
        String username = credentials.get("username");
        String password = credentials.get("password");

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Username or password is invalid"));

        if(BCrypt.checkpw(password, user.getPassword())) {
            return new UserResponse(user);
        } else {
            throw new RuntimeException("Username or password is invalid");
        }
    }

    public UserResponse buyGame(Long userId, Long gameId){
        User user =  userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new RuntimeException("Game not found"));

        if(user.getGames().contains(game)) {
            throw new RuntimeException("You already own this game");
        }

        if(user.getBalance()<game.getPrice()){
            throw new  RuntimeException("You dont have enough balance");
        }

        user.setBalance(user.getBalance()-game.getPrice());
        user.getGames().add(game);
        userRepository.save(user);

        return new UserResponse(user);
    }

    public List<Game> getUserGames(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return user.getGames();
    }
}
