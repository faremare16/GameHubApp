import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Game } from '../models/game';


@Injectable({
    providedIn: 'root' //this says that this service is available for all aplication
})
export class GameServices {
    private apiUrl='http://localhost:8080/api/games';

    constructor(private http: HttpClient){}

    getAllGames(): Observable<Game[]>{
        return this.http.get<Game[]>(this.apiUrl);
    }

    createGame(game: Game): Observable<Game>{
        return this.http.post<Game>(this.apiUrl, game);
    }
}
