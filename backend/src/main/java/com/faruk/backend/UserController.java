package com.faruk.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register") //register
    public ResponseEntity<UserResponse> createUser(@RequestBody User user) {
        return ResponseEntity.ok(userService.registerUser(user));
    }

    @PostMapping("/login") //login
    public ResponseEntity<UserResponse> login(@RequestBody Map<String, String> credentials){
        return ResponseEntity.ok(userService.loginUser(credentials));
    }

    @PostMapping("/{userId}/buy/{gameId}")
    public ResponseEntity<UserResponse> buyGame(@PathVariable Long userId, @PathVariable Long gameId){
        return  ResponseEntity.ok(userService.buyGame(userId, gameId));
    }

    @GetMapping("/{userId}/games")
    public ResponseEntity<List<Game>> getUserGames(@PathVariable Long userId){
        return ResponseEntity.ok(userService.getUserGames(userId));
    }
}
