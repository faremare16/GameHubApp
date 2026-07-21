import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { UserService } from '../../services/user'; 

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './login.html',
  styleUrl: './login.css'
})
export class LoginComponent {

  credentials = {
    username: '',
    password: ''
  };

  errorMessage: string = '';

  constructor(
    private userService: UserService,
    private router: Router
  ) {}

  onLogin() {
    this.userService.login(this.credentials).subscribe({
      next: (user) => {
        console.log('Uspješna prijava!', user);
        this.router.navigate(['/store']);
      },
      error: (err) => {
        this.errorMessage = err.error?.Error || 'Incorrect username or password.';
      }
    });
  }
}