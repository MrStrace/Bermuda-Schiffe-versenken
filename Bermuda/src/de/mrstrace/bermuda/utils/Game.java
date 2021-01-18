package de.mrstrace.bermuda.utils;

import java.util.Random;
import java.util.Scanner;


public class Game {

	int X = 12; // Breite des Spielfeldes
	int Y = 9; // Höhe des Spielfeldes
	int geraten; // Rate counter
	char[][] feld; // Spielfeld
	Schiff[] schiffe;
	boolean cheats = false;

	public Game(int X, int Y, int Schiffe) {
		this.X = X;
		this.Y = Y;
		this.geraten = 0;
		this.schiffe = new Schiff[Schiffe];
		this.feld = new char[X][Y];

		welcome();
	}

	public void cheat() {

		for (int i = 0; i < schiffe.length; i++) {
			System.out.println("Schiff " + i + ": " + (char) (schiffe[i].koordinate.X + 'A') + " - "
					+ (schiffe[i].koordinate.Y + 1));
		}
		System.out.println();

	}

	public void start() {

		// Initialisierung des Spiefeldes
		initSpielfeld();

		// Initialisierung der Schiffe
		initSchiffe();

		/*
		 * In der do-while() Schleife wird jedes mal: 1. Das game.getFeld() angezeigt 2.
		 * Die Eingabe abgefragt mit der Rate Funktion 3. das Symbol auf dem
		 * game.getFeld() verändert 4. eingetragen ob ein Schiff versenkt wurde oder
		 * nicht 5. der Rate counter erhört.
		 * 
		 * während: noch mindestens ein Schiff glücklich auf dem Meer schwimmt.
		 */

		do {
			anzeigen();
			Koordinate eingabe = rate();
			char symbol = pruefe(eingabe);
			eintragen(eingabe, symbol);

			geraten++; // Zählt wie oft geraten wurde.

		} while (nichtgefunden(getSchiffe()));

		// Letzte anzeige des Spielfeldes
		anzeigen();

		// Nachricht wenn man gewonnen hat, mit den gebrauchten Versuchen
		System.out.println("Sie haben Gewonnen!!");
		System.out.println("Versuche gebraucht: " + geraten);
	}

	public void welcome() {
		System.out.flush();
		System.out.println("");
		System.out.println("____                                _       ");
		System.out.println("|  _ \\                              | |");
		System.out.println("| |_) | ___ _ __ _ __ ___  _   _  __| | __ _ ");
		System.out.println("|  _ < / _ \\ '__| '_ ` _ \\| | | |/ _` |/ _` |");
		System.out.println("| |_) |  __/ |  | | | | | | |_| | (_| | (_| |");
		System.out.println("|____/ \\___|_|  |_| |_| |_|\\__,_|\\__,_|\\__,_|");
		System.out.println("");
		System.out.println("Willkommen bei Bermuda Schiffe versenken!");
		System.out.println("");
		System.out.println("Gebe eine Zahl von 1-3 ein.");
		System.out.println("- 1. Start");
		System.out.println("- 2. Einführung");
		System.out.println("- 3. Exit");

		@SuppressWarnings("resource")
		Scanner scan = new Scanner(System.in);
		switch (scan.nextInt()) {
		case 1:
			start();
			break;
		case 2:

			System.out.println("Bei Bermuda Schiffe versenken, geht es darum");
			System.out.println("alle Random platzierten Schiffe, durch gezielte");
			System.out.println("Schüsse taktisch, in so wenig Zügen wie möglich");
			System.out.println("zu versenken. Sobald alle Schiffe getroffen wurden,");
			System.out.println("ist das Spiel gewonnen!");

			System.out.println();
			System.out.println("Bei einem Fehlschuss wird eine Zahl angezeigt");
			System.out.println("diese sagt aus wie viele Schiffe in allen 8 Himmelsrichtungen gesehen wurden.");
			System.out.println();

			System.out.println(
					"Falls du hilfe brauchst benutze den Command 'Cheat' um alle verbleibenden Schiffe zu sehen.");
			System.out.println("Aber sobald Cheats benutzt wurden, wird dies am Ende angezeigt!");
			System.out.println();
			System.out.println("Gebe eine Zahl von 1-2 ein.");
			System.out.println("- 1. Start");
			System.out.println("- 2. Exit");
			Scanner scan2 = new Scanner(System.in);
			if (scan2.nextInt() == 1) {
				System.out.flush();
				start();
			} else {
				System.out.flush();
				System.out.println("bye bye!");
				System.exit(0);
			}
			break;
		default:
			System.out.flush();
			System.out.println("bye bye!");
			System.exit(0);
			break;
		}

	}

	/**
	 * Für alle schiffe wird abgefragt ob sie in der boolean[] getroffen wurden,
	 * falls alle Schiffe auf true sind, also alle gefunden worden wird false
	 * returned. Falls mindestens einer noch nicht gefunden wurde wird weiterhin
	 * true returned und das Spiel geht weiter.
	 * 
	 * @param schifftreffer
	 * @param schiffx
	 * @return gefunden
	 */
	private boolean nichtgefunden(Schiff[] schiffe) {
		for (int i = 0; i < schiffe.length; i++) {
			if (schiffe[i].treffer == false) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Trägt das jeweilig passende Symbol ein in das game.getFeld() ein.
	 */
	public void eintragen(Koordinate eingabe, char symbol) {
		getFeld()[eingabe.X][eingabe.Y] = symbol;
	}

	/**
	 * Diese Funktion überprüft ob sich ein Schiff auf der Position der eingabe
	 * befindet, wenn dies der fall ist wird ein Treffer hinzugefügt und das symbol
	 * 'X' für getroffen wird returned. Falls kein Schiff getroffen wird, wird '.'
	 * als "Kein Treffer" returned.
	 * 
	 * @param schiffx
	 * @param schiffy
	 * @param schifftreffer
	 * @param eingabe
	 * @return (char) symbol
	 */
	public char pruefe(Koordinate eingabe) {
		char symbol = '.';
		for (int i = 0; i < schiffe.length; i++) {
			if (schiffe[i].koordinate.X == eingabe.X && schiffe[i].koordinate.Y == eingabe.Y) {
				schiffe[i].treffer = true;
				symbol = 'X';
			}
		}

		if (symbol != 'X') {
			Himmelsrichtungen h = new Himmelsrichtungen(getFeld(), schiffe, eingabe);
			symbol = (char) ((char) h.Ergebnis() + 48);
		}

		return symbol;
	}

	/**
	 * Diese Funktion lässt die Schiffe random ins game.getFeld() mit einlaufen.
	 * Falls Schiffe aufeinander liegen sollten wird ein Schiff versetzt.
	 * 
	 * @param schiffx
	 * @param schiffy
	 * @param schifftreffer
	 */
	public void initSchiffe() {
		Random zufall = new Random();
		for (int i = 0; i < schiffe.length; i++) {
			schiffe[i] = new Schiff();
			schiffe[i].koordinate = new Koordinate();
			schiffe[i].koordinate.X = zufall.nextInt(9);
			schiffe[i].koordinate.Y = zufall.nextInt(7);
			schiffe[i].treffer = false;
			for (int j = 0; j < i; j++) {
				if (schiffe[i].koordinate.X == schiffe[j].koordinate.X
						&& schiffe[i].koordinate.Y == schiffe[j].koordinate.Y) {
					i--;
				}
			}

		}

	}

	/**
	 * Diese Funktion kreiert das game.getFeld().
	 * 
	 * 2 Forschleifen wird eine char matrix mit '+' vollgepackt um das
	 * game.getFeld() zu repräsentieren.
	 * 
	 * @param game.getFeld()
	 */
	public void initSpielfeld() {
		for (int i = 0; i < getFeld().length; i++) {
			for (int j = 0; j < getFeld()[0].length; j++) {
				getFeld()[i][j] = '+';
			}
		}
	}

	/**
	 * Diese Funktion ist zur Eingabe des Schiffes welches man versenken möchte
	 * nötig. Der Scanner scannt die Eingabe der Konsole, welche wir dann als String
	 * auslesen.
	 * 
	 * Der String wird unterteilt in 2 chars und mit dem ASCII code in Zahlen
	 * umgewandelt, welche das Koordinaten System wiederspiegeln.
	 * 
	 * Die 2 ints (x & y) werden in eine Int Array gepackt und dann als Koordinate
	 * returned.
	 * 
	 * @param game.getFeld()
	 * @return Koordinate
	 */

	public Koordinate rate() {
		int Xmin = 65; // A
		int Xmax = Xmin + getFeld().length - 1;
		int Ymin = 1;
		int Ymax = getFeld()[0].length;
		System.out.println("Wähle ein Feld (" + (char) Xmin + "" + Ymin + "-" + (char) Xmax + "" + Ymax + ")");

		@SuppressWarnings("resource")
		Scanner scan = new Scanner(System.in);
		String str = scan.nextLine();

		if (str.equalsIgnoreCase("cheat")) {
			this.cheats = true;
			cheat();
			rate();
			return new Koordinate();
		} else if (str.contains("exit") || str.contains("close")) {
			System.exit(0);
		} else if (str.length() != 2) {
			System.out.println("Ups, vertippt?");
			rate();
			return new Koordinate();
		}

		int x = str.charAt(0) - 'A';
		int y = str.charAt(1) - '1';

		Koordinate ko = new Koordinate();

		ko.X = x;
		ko.Y = y;

		return ko;

	}

	/**
	 * Diese Funktion zeigt das game.getFeld() in der Konsole an.
	 * 
	 * Die Buchstaben lassen sich ganzeinfach Dynamisch bearbeiten durch dem
	 * ASCII-Code in welchen 65 für A, 66 für B usw.
	 * 
	 * @param game.getFeld()
	 */
	public void anzeigen() {
		System.out.print("   ");
		for (int i = 0; i < getFeld().length; i++) {
			System.out.print((char) (i + 65) + " ");
		}
		System.out.println();
		for (int j = 0; j < getFeld()[0].length; j++) {
			System.out.print((j + 1) + " ");
			for (int i = 0; i < getFeld().length; i++) {

				System.out.print(" " + getFeld()[i][j]);
			}
			System.out.println();
		}
	}

	public Schiff[] getSchiffe() {
		return schiffe;
	}

	public void setSchiffe(Schiff[] schiffe) {
		this.schiffe = schiffe;
	}

	public int getGeraten() {
		return geraten;
	}

	public void setGeraten(int geraten) {
		this.geraten = geraten;
	}

	public char[][] getFeld() {
		return feld;
	}

	public void setFeld(char[][] feld) {
		this.feld = feld;
	}

	public int getX() {
		return X;
	}

	public int getY() {
		return Y;
	}

}
