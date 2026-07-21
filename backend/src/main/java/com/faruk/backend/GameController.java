package com.faruk.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/games")
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE}) // Dozvoljavamo našem Angularu pristup
public class GameController {

    @Autowired
    private GameRepository gameRepository;

    // 1. GET - Vrati sve igrice
    @GetMapping
    public List<Game> getAllGames() {
        return gameRepository.findAll();
    }

    // 2. POST - Dodaj novu igricu
    @PostMapping
    public Game createGame(@RequestBody Game game) {
        return gameRepository.save(game);
    }

    // 3. DELETE - Obriši igricu preko ID-ja
    @DeleteMapping("/{id}")
    public void deleteGame(@PathVariable Long id) {
        gameRepository.deleteById(id);
    }
}
