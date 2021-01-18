package de.mrstrace.bermuda.utils;


public class Himmelsrichtungen {

	char[][] spielfeld;
	Schiff[] schiffe;
	Koordinate eingabe;

	public Himmelsrichtungen(char[][] Spielfeld, Schiff[] Schiffe, Koordinate Eingabe) {
		this.spielfeld = Spielfeld;
		this.schiffe = Schiffe;
		this.eingabe = Eingabe;
	}

	public int Ergebnis() {
		int i = N() + O() + S() + W() + NO() + NW() + SO() + SW();
		return i;
	}

	public int N() {

		for (int i = 0; i < schiffe.length; i++) {
			int y = eingabe.Y;
			int x = eingabe.X;
			while (y > 0) {
				y--;
				if (schiffe[i].koordinate.Y < y && schiffe[i].koordinate.X == x) {
					if(!schiffe[i].treffer)
					return 1;
				}
			}
		}

		return 0;
	}

	public int O() {
		for (int i = 0; i < schiffe.length; i++) {
			int y = eingabe.Y;
			int x = eingabe.X;
			while (x > 0) {
				x--;
				if (schiffe[i].koordinate.X < x && schiffe[i].koordinate.Y == y) {
					if(!schiffe[i].treffer)
					return 1;
				}
			}
		}

		return 0;
	}

	public int S() {

		for (int i = 0; i < schiffe.length; i++) {
			int y = eingabe.Y;
			int x = eingabe.X;
			while (y < spielfeld[0].length) {
				y++;
				if (schiffe[i].koordinate.X == x && schiffe[i].koordinate.Y > y) {
					if(!schiffe[i].treffer)
					return 1;
				}
			}
		}

		return 0;
	}

	public int W() {

		for (int i = 0; i < schiffe.length; i++) {
			int y = eingabe.Y;
			int x = eingabe.X;
			while (x < spielfeld.length) {
				x++;
				if (schiffe[i].koordinate.Y == y && schiffe[i].koordinate.X > x) {
					if(!schiffe[i].treffer)
					return 1;
				}
			}
		}

		return 0;
	}

	public int SO() {

		for (int j = 0; j < schiffe.length; j++) {

			int y = eingabe.Y;
			int x = eingabe.X;

			while (x < spielfeld.length && y < spielfeld[0].length) {

				if (y < spielfeld[0].length)
					y++;

				if (x < spielfeld.length)
					x++;

				if (schiffe[j].koordinate.Y == y && schiffe[j].koordinate.X == x) {
					if(!schiffe[j].treffer)
					return 1;
				}

			}
		}
		return 0;
	}

	public int SW() {

		for (int j = 0; j < schiffe.length; j++) {

			int y = eingabe.Y;
			int x = eingabe.X;

			while (x < spielfeld.length && y > 0) {

				if (y > 0)
					y--;

				if (x < spielfeld.length)
					x++;

				if (schiffe[j].koordinate.Y == y && schiffe[j].koordinate.X == x) {
					if(!schiffe[j].treffer)
					return 1;
				}

			}
		}
		return 0;
	}

	public int NW() {

		for (int j = 0; j < schiffe.length; j++) {

			int y = eingabe.Y;
			int x = eingabe.X;

			while (x > 0 && y > 0) {

				if (y > 0)
					y--;

				if (x > 0)
					x--;

				if (schiffe[j].koordinate.Y == y && schiffe[j].koordinate.X == x) {
					if(!schiffe[j].treffer)
					return 1;
				}

			}
		}
		return 0;
	}

	public int NO() {

		for (int j = 0; j < schiffe.length; j++) {

			int y = eingabe.Y;
			int x = eingabe.X;

			while (x > 0 && y < spielfeld[0].length) {

				if (y < spielfeld[0].length)
					y++;

				if (x > 0)
					x--;

				if (schiffe[j].koordinate.Y == y && schiffe[j].koordinate.X == x) {
					if(!schiffe[j].treffer)
					return 1;
				}

			}
		}
		return 0;
	}

}
