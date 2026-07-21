import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { UserService } from '../../services/user';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './register.html',
  styleUrl: './register.css',
})
export class Register {
  userData = {
    username: '',
    password: '',
    email: '',
  };

  errorMessage: string = '';

  constructor(private userService: UserService, private router: Router) {}

  onRegister() {
    this.userService.register(this.userData).subscribe({
      next: (user) => {
        console.log('Success:', user);
        this.router.navigate(['/login']);
      },
      error: (err) => {
        this.errorMessage = err.error?.Error || 'Registration failed.';
      },
    });
  }
}
