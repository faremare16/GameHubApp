package com.faruk.backend;

import java.util.List;
import lombok.Data;

@Data
public class UserResponse {
    private Long id;
    private String username;
    private String email;
    private Double balance;
    private List<Game> games;

    public UserResponse(User user){
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.balance = user.getBalance();
        this.games = user.getGames();
    }
}
