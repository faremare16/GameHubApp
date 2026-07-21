import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, Observable, tap } from 'rxjs';
import { UserResponse } from '../models/user';

@Injectable({
  providedIn: 'root'
})
export class UserService {
    private apiUrl = 'http://localhost:8080/api/users';
    private currentUserSubject= new BehaviorSubject<UserResponse | null>(
        JSON.parse(localStorage.getItem('currentUser') || 'null')
    );
    public currentUser$ = this.currentUserSubject.asObservable();

    constructor(private http: HttpClient) { 
        const savedUser = localStorage.getItem('currentUser');
        if (savedUser) {
            this.currentUserSubject.next(JSON.parse(savedUser));
        }
    }

    register(user: any): Observable<UserResponse> {
        return this.http.post<UserResponse>(`${this.apiUrl}/register`, user);
    }

    login(credentials: any): Observable<UserResponse> {
        return this.http.post<UserResponse>(`${this.apiUrl}/login`, credentials).pipe(
            tap(user => {
                localStorage.setItem('currentUser', JSON.stringify(user));
                this.currentUserSubject.next(user);
            })
        );
    }

    buyGame(userId: number, gameId: number): Observable<UserResponse> {
        return this.http.post<UserResponse>(`${this.apiUrl}/${userId}/buy/${gameId}`, {}).pipe(
            tap(updatedUser => {
                localStorage.setItem('currentUser', JSON.stringify(updatedUser));
                this.currentUserSubject.next(updatedUser);
            })
        );
    }

    logout(): void {
        localStorage.removeItem('currentUser');
        this.currentUserSubject.next(null);
    }
}
