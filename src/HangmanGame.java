import java.io.*;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * You write code here to play a game of Hangman.
 * Some sample code provided at the start. You'll probably remove almost 
 * all of it (readString might stick around).
 * 
 * @author Scotty Shaw
 */

public class HangmanGame {

	// Used for reading data from the console.
	Scanner myInput;

	public HangmanGame() {
		// Set up our read-from-console.
		myInput = new Scanner(
				new BufferedReader(new InputStreamReader(System.in)));
	}


	/**
	 * Get a line from the user and return the line as a String.
	 * 
	 * @param prompt is printed as an instruction to the user
	 * @return entire line entered by the user
	 */
	public String readString(String prompt) {
		System.out.printf("%s ", prompt);
		String entered = myInput.nextLine();
		return entered;
	}

	/**
	 * Play one game of Hangman. This should prompt
	 * user for parameters and then play a complete game.
	 * You'll likely want to call other functions from this one. 
	 * The existing code may provide some helpful examples.
	 */
	public void play() {

		HangmanFileLoader data = new HangmanFileLoader();

		//initialize all variables to default settings
		boolean addedLetter = false; 
		boolean guessedRight = false;
		boolean nba = false;
		boolean win = false;
		boolean firstGuess = true;
		char guessedLetterInputChar = 'a';
		int letterCount = 0;
		int wrongGuessesLimit = 0;
		int correctWords = 0;
		String wordInput = "";
		String secretWord = "";
		String guessInput = "";
		String guessedLetterInput = "";
		String guessNBA = "";
		String secretWordLower = "";
		String guessedLetterInputLower = "";
		char[] secretWordChar = {};
		char[] secretWordCharDisplay = {};
		char[] secretNBADisplay = {};
		Set<Character> guessedLetterSet = new HashSet<Character>();

		//introduction
		System.out.println("How many dudes can you save? Guess a secret word to save one.");
		System.out.println("If you fail to guess a word, that dude dies... and you with him.");
		guessNBA = readString("Or you can guess something else... Enter 'NBA' if you want to do that instead.");

		//sets up NBA game or standard game
		if (guessNBA.equals("NBA")) {
			nba = true;
		}
		else {
			nba = false;
		}

		//sets up text file to read for NBA player names or secret words to guess
		if (nba == true) {
			data.readFile("NBA.txt");
			//uses low-tech method to set the range of potential NBA player name lengths
			letterCount = (int) ((Math.random() * 30) % 18 + 7);
			
			//FOR REFERENCE
			//int letterCount = (int) (Math.random() * 17) + 7;
			
			//FOR REFERENCE
			
			//avoids 20-letter names, which do not currently exist on any 2013-2014 NBA rosters
			if (letterCount == 20) {
				letterCount = 21;
			}
			secretWord = data.getRandomWord(letterCount);
		}
		else {
			data.readFile("lowerwords.txt");
			wordInput = readString("How many letters do you want in the secret word?");
			letterCount = Integer.parseInt(wordInput);
			secretWord = data.getRandomWord(letterCount);
		}

		//sets up dash display
		if (nba == true) {
			secretWordLower = secretWord.toLowerCase();
			secretWordChar = secretWordLower.toCharArray();
			secretWordCharDisplay = secretWord.toCharArray();
			secretNBADisplay = secretWord.toCharArray();
		}
		else {
			secretWordLower = secretWord.toLowerCase();
			secretWordChar = secretWordLower.toCharArray();
			secretWordCharDisplay = secretWord.toCharArray();
		}

		//creates dash displays for secret words
		for (int i = 0; i < letterCount; i++) {
			//accounts for periods, hyphens, spaces, and apostrophes in NBA player names
			if (nba == true) {
				if (secretWordCharDisplay[i] == '.' || secretWordCharDisplay[i] == '-' || secretWordCharDisplay[i] == ' ' || secretWordCharDisplay[i] == '\'') {
					secretWordCharDisplay[i] = secretWordCharDisplay[i];
					secretNBADisplay[i] = secretNBADisplay[i];
				}
				else {
					secretWordCharDisplay[i] = '_';
					secretNBADisplay[i] = '_';
				}
			}
			else {
				secretWordCharDisplay[i] = '_';
			}
		}

		//prompts player for number of wrong guesses
		if (nba == true) {
			guessInput = readString("How many wrong guesses do you want before we hang this NBA player?");
		}
		else {
			guessInput = readString("How many wrong guesses do you want before we hang the dude?");
		}

		wrongGuessesLimit = Integer.parseInt(guessInput);

		//creates for loop to allow consecutive game play as long the player continues to guess NBA player names or secret words correctly
		//keeps track of overall game stats to determine end-game dialogue and display player performance stats
		//records individual game stats for each NBA player name or secret word
		for (int wrongGuesses = 0; wrongGuesses < wrongGuessesLimit; wrongGuesses += 0) {
			System.out.println();
			//displays dash display
			if (nba == true) {
				System.out.println(secretNBADisplay);
			}
			else {
				System.out.println(secretWordCharDisplay);
			}
			
			//displays current game stats
			System.out.println("You have " + (wrongGuessesLimit - wrongGuesses) + " wrong guesses before we hang him.");
			System.out.println("Letters used: " + guessedLetterSet);

			//prompts player for letter guess
			if (firstGuess == true) {
				guessedLetterInput = readString("Pick a letter. I'm only using the first one you type, so make it good. What's your guess?");
				firstGuess = false;
			}
			else {
				guessedLetterInput = readString("Guess a letter.");
			}
			
			//sets up letters guessed set
			guessedLetterInputLower = guessedLetterInput.toLowerCase();
			guessedLetterInputChar = guessedLetterInputLower.charAt(0);
			addedLetter = guessedLetterSet.add(guessedLetterInputChar);
			guessedLetterSet.add(guessedLetterInputChar);

			//resets win boolean to default
			win = true;

			//notifies player of duplicate guess and asks for a different letter
			if (addedLetter == false) {
				System.out.println("You already used that letter. Pick another. What's your guess?");
			}
			else {
				//checks to see if the guessed letter is correct in any position
				for (int i = 0; i < letterCount; i++) {
					//changes guessedRight to true to avoid incrementing wrongGuesses
					if (nba == true) {
						//sets up character reveal with capital letters for NBA player names
						if (guessedLetterInputChar == secretWordChar[i]) {
							secretWordCharDisplay[i] = guessedLetterInputChar;
							secretNBADisplay[i] = (secretWord.toCharArray())[i];
							guessedRight = true;
						}
					}
					else {
						if (guessedLetterInputChar == secretWordChar[i]) {
							secretWordCharDisplay[i] = guessedLetterInputChar;
							guessedRight = true;
						}
					}
				}

				//increments wrongGuesses if no letters matched
				if (guessedRight == false) {
					wrongGuesses++;
				}

				//resets guessedRight for incrementing wrongGuesses
				guessedRight = false;

				//checks if all letters in secret word or NBA player name have been guessed and sets win boolean
				for (int j = 0; j < secretWordCharDisplay.length; j++) {
					if (secretWordCharDisplay[j] != secretWordChar[j]) {
						win = false;
					}
				}

				//sets up game-winning scenario
				if (win == true) {
					correctWords++;
					System.out.println();
					//displays overall stats
					if (nba == true) {
						System.out.println("You just saved " + secretWord + " from the gallows.");
						//uses proper grammar
						if (correctWords == 1) {
							System.out.println("That's " + correctWords + " NBA player you saved so far.");
						}
						else {
							System.out.println("That's " + correctWords + " NBA players you saved so far.");
						}
					}
					else {
						System.out.println("The word was " + secretWord + ", so you saved the dude.");
						//uses proper grammar
						if (correctWords == 1) {
							System.out.println("That's " + correctWords + " dude you saved so far.");
						}
						else {
							System.out.println("That's " + correctWords + " dudes you saved so far.");
						}
					}

					//uses low-tech method of randomly selecting another NBA player name based on new word length
					if (nba == true) {
						data.readFile("NBA.txt");
						letterCount = (int) ((Math.random() * 30) % 18 + 7);
						if (letterCount == 20) {
							letterCount = 21;
						}
						secretWord = data.getRandomWord(letterCount);
					}
					//selects new secret word based on new word length
					else {
						wordInput = readString("How many letters do you want in the new secret word?");
						letterCount = Integer.parseInt(wordInput);
						secretWord = data.getRandomWord(letterCount);
					}

					//resets secret words and displays and guessed letter sets
					secretWordLower = secretWord.toLowerCase();
					secretWordChar = secretWordLower.toCharArray();
					secretWordCharDisplay = secretWord.toCharArray();
					secretNBADisplay = secretWord.toCharArray();
					guessedLetterSet = new HashSet<Character>();

					//uses low-tech method of repeating the beginning set ups
					//this would definitely be a method, but i am not yet familiar enough to create methods and meet the submission deadline
					for (int i = 0; i < letterCount; i++) {
						if (nba == true) {
							if (secretWordCharDisplay[i] == '.' || secretWordCharDisplay[i] == '-' || secretWordCharDisplay[i] == ' ' || secretWordCharDisplay[i] == '\'') {
								secretWordCharDisplay[i] = secretWordCharDisplay[i];
								secretNBADisplay[i] = secretNBADisplay[i];
							}
							else {
								secretWordCharDisplay[i] = '_';
								secretNBADisplay[i] = '_';
							}
						}
						else {
							secretWordCharDisplay[i] = '_';
						}
					}

					//allows player the option of changing the number of allowed wrong guesses
					if (nba == true) {
						guessInput = readString("How many wrong guesses do you want before we hang this NBA player?");
					}
					else {
						guessInput = readString("How many wrong guesses do you want before we hang the dude?");
					}

					//resets variables to default settings
					wrongGuessesLimit = Integer.parseInt(guessInput);
					wrongGuesses = 0;
					guessedRight = false;
				}
			}
		}
		
		//sets up different end-game dialogues, depending on player performance
		if (nba == true) {
			//designates different dialogues for NBA game
			if (correctWords == 0) {
				System.out.println("The NBA player was " + secretWord + ". How did you not save any NBA players?");
				System.out.println("Get him out of here. I'm hanging you instead.");
			}
			else if (correctWords == 1) {
				System.out.println("The NBA player was " + secretWord + ". You saved... You only saved " + correctWords + " NBA player? Okay, well, " + secretWord + " hangs, and so do you.");
			}
			else if (correctWords < 10) {
				System.out.println("The NBA player was " + secretWord + ". You saved " + correctWords + " other NBA players, but " + secretWord + " hangs... with you.");
			}
			else {
				System.out.println("The NBA player was " + secretWord + ". You saved... Wow, you saved " + correctWords + " already?");
				System.out.println("You know what? I'm not even mad. That's amazing. Hey yo, he saved " + correctWords + " NBA players!");
				System.out.println("I'll let y'all live... for now. Get ready for Russian Roulette tomorrow... where only the ugliest survives.");
			}
		}
		else {
			//designates different dialogues for standard game
			if (correctWords == 0) {
				System.out.println("The secret word was " + secretWord + ". How did you not save anyone? Get this dude out of here. I'm hanging you instead.");
			}
			else if (correctWords == 1) {
				System.out.println("The secret word was " + secretWord + ". You saved... You only saved " + correctWords + " dude? Okay, well, this dude hangs, and so do you.");
			}
			else if (correctWords < 20) {
				System.out.println("The secret word was " + secretWord + ". You saved " + correctWords + " other dudes, but this dude hangs... with you.");
			}
			else {
				System.out.println("The secret word was " + secretWord + ". You saved... Wow, you saved " + correctWords + " dudes?");
				System.out.println("You know what? I'm not even mad. That's amazing. Hey y'all, he saved " + correctWords + " dudes!");
				System.out.println("I'll let you live... for now. Get ready for Russian Roulette tomorrow... where only the drunkest survives.");
			}
		}
	}
}