public abstract class Translator {
	
	final static int DO = 60;
	final static int RE = 62;
	final static int MI = 64;
	final static int FA = 65;
	final static int SOL = 67;
	final static int LA = 69;
	final static int SI = 70;

	final static int OCTAVE = 12;
	final static int OCTAVEx2 = 24;
	final static int MAXNOTE = 127;
	final static int MINNOTE = 0;
	final static int MAX_VOL = 16383;
	final static int NOTSIGNED = -1;
	//final static String ACORD_MAJ = "maj"; 

	final static String SILENCE = "R ";

	public static String tranlateText(String text) {
		
		char currentChar;
		int currentNote = NOTSIGNED;
		int pastNote = NOTSIGNED;
		int volume = MAX_VOL / 2;
		String stringMusic = "";
		boolean writeNote = false;

		text = text.toUpperCase();
		int stringLength = text.length();
		for (int i = 0; i < stringLength; i++) {
			currentChar = text.charAt(i);

			if (currentChar == 'A') {
				currentNote = LA;
				writeNote = true;
			} else if (currentChar == 'B') {
				currentNote = SI;
				writeNote = true;
			} else if (currentChar == 'C') {
				currentNote = DO;
				writeNote = true;
			} else if (currentChar == 'D') {
				currentNote = RE;
				writeNote = true;
			} else if (currentChar == 'E') {
				currentNote = MI;
				writeNote = true;
			} else if (currentChar == 'F') {
				currentNote = FA;
				writeNote = true;
			} else if (currentChar == 'G') {
				currentNote = SOL;
				writeNote = true;
			} else if (currentChar == ' ') {
				stringMusic = stringMusic.concat(SILENCE);
				writeNote = false;
			} else if (isSimpleVowel(currentChar)) {
				if (pastNote != NOTSIGNED) {
					currentNote = pastNote;
					writeNote = true;
				} else
					writeNote = false;
			}

			// Sustenido
			else if (currentChar == ';') {
				if (pastNote != NOTSIGNED && currentNote < MAXNOTE) {
					currentNote++;
					writeNote = true;
				}
			}

			// Bemol
			else if (currentChar == ',') {
				if (pastNote != NOTSIGNED && currentNote > MINNOTE) {
					currentNote--;
					writeNote = true;
				}
			}

			else if (isPairDigit(currentChar)) {
				if (pastNote != NOTSIGNED) {
					currentNote = upOneOctave(pastNote);
					writeNote = true;
				}
			}

			else if (idOddDigit(currentChar)) {
				if (pastNote != NOTSIGNED) {
					currentNote = downOneOctave(pastNote);
					writeNote = true;
				}
			}

			else if (currentChar == '!' || currentChar == '?'
					|| currentChar == '.') {
				if (pastNote != NOTSIGNED && volume <= (MAX_VOL - 200)) {
					volume = volume + 200;
					String upVolume = "X[Volume]=" + volume + " ";
					stringMusic = stringMusic.concat(upVolume);
				}
				writeNote = false;
			}
			
			else if (isSimpleConsonant(currentChar)) {
				if (pastNote != NOTSIGNED) {
					currentNote = upTwoOctave(pastNote);
					writeNote = true;
				}
			}
			
			pastNote = currentNote;

			if (writeNote) {
				stringMusic = stringMusic.concat(formateNote(currentNote) + " ");
				writeNote = false;
				pastNote = currentNote;
			}

		}

		System.out.println(stringMusic);// Impressão de Teste
		return stringMusic;
	}

	// _________
	private static String formateNote(int note) {
		String stringNote = "[";
		stringNote = stringNote.concat(note + "]");
		return stringNote;
	}

	private static boolean isSimpleVowel(char currentChar) {
		return currentChar == 'I' || currentChar == 'O' || currentChar == 'U';
	}

	private static boolean isSimpleConsonant(char currentChar) {
		String consonants = "HJKLMNPQRSTVWXYZ";

		for (int consonantIndex = 0; consonantIndex < consonants.length(); consonantIndex++) {
			if (currentChar == consonants.charAt(consonantIndex)) {
				return true;
			}
		}
		return false;
	}

	private static boolean isPairDigit(char digit) {
		return digit == '0' || digit == '2' || digit == '4' || digit == '6'
				|| digit == '8';
	}

	private static boolean idOddDigit(char digit) {
		return digit == '1' || digit == '3' || digit == '5' || digit == '7'
				|| digit == '9';
	}

	private static int upOneOctave(int note) {
		if ((note + OCTAVE) <= MAXNOTE)
			return note + OCTAVE;
		else
			return note;
	}
	
	private static int upTwoOctave(int note) {
		if ((note + OCTAVEx2) <= MAXNOTE)
			return note + OCTAVEx2;
		else
			return note;
	}

	private static int downOneOctave(int note) {
		if ((note - OCTAVE) >= MINNOTE)
			return note - OCTAVE;
		else
			return note;
	}
}
