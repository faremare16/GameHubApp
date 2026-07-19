package com.faruk.backend;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "games")
@Data // lombok automaticly makes getters and setters
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String genre;
    private double price;
    private String description;
}