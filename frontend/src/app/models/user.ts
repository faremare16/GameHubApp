import { Game } from "./game";

export interface UserResponse {
    id: number;
    username: string;
    email: string;
    balance: number;
    games: Game[];
} 
