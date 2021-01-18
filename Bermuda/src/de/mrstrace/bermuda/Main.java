package de.mrstrace.bermuda;

import de.mrstrace.bermuda.utils.Game;

public class Main {

	public static Game game;
	
	public static void main(String[] args) {
		game = new Game(12/*X(Breite)*/, 9/*Y(Höhe)*/, 8/*Schiffe*/);
	}

	
}