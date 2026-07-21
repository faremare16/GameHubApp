import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { UserService } from '../../services/user';
import { UserResponse } from '../../models/user';

@Component({
  selector: 'app-store',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './store.html',
  styleUrl: './store.css',
})
export class StoreComponent implements OnInit {
  games: any[] = [];
  currentUser: UserResponse | null = null;
  message: string = '';
  errorMessage: string = '';

  constructor(private http: HttpClient, private userService: UserService) {}

  ngOnInit() {
    this.userService.currentUser$.subscribe((user) => {
      this.currentUser = user;
    });
    this.loadGames();
  }

  loadGames(){
    this.http.get<any[]>('http://localhost:8080/api/games').subscribe({
      next: (data) => this.games = data,
      error: (err) => {
        console.error('Error loading games:', err);
        this.errorMessage = 'Failed to load games.';
      }
    });
  }

  buyGame(gameId: number) {
    if (!this.currentUser) {
      this.errorMessage = 'You must be logged in to buy a game.';
      return;
    }

    this.userService.buyGame(this.currentUser.id, gameId).subscribe({
      next: (updatedUser) => {
        this.currentUser = updatedUser;
        this.message = 'Game purchased successfully!';
        this.errorMessage = '';
      },
      error: (err) => {
        console.error('Error buying game:', err);
        this.errorMessage = err.error?.Error || 'Failed to buy game.';
        this.message = '';
      }
    });
  }

  hasGame(gameId: number): boolean {
    if (!this.currentUser || !this.currentUser.games) return false;
    return this.currentUser.games.some((g: any) => g.id === gameId);
  }
} 

