import java.util.Scanner;

import model.entities.Game;

/**
 * Creates an instance of the game with 2 to 4 players.
 */
public class Main {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);

		System.out.print("$$\\      $$\\  $$$$$$\\  $$\\   $$\\  $$$$$$\\  $$$$$$$\\   $$$$$$\\  $$\\   $$\\     $$\\ \n");
		System.out.print("$$$\\    $$$ |$$  _$$\\  $$$\\  $$ |$$  _$$\\  $$  _$$\\  $$   _$$\\ $$ |  \\$$\\   $$  |\n");
		System.out.print("$$$$\\  $$$$ |$$ /  $$ |$$$$\\ $$ |$$ /  $$ |$$ |  $$ |$$ /  $$ |$$ |   \\$$\\ $$  / \n");
		System.out.print("$$\\$$\\$$ $$ |$$ |  $$ |$$ $$\\$$ |$$ |  $$ |$$$$$$$  |$$ |  $$ |$$ |    \\$$$$  /  \n");
		System.out.print("$$ \\$$$  $$ |$$ |  $$ |$$ \\$$$$ |$$ |  $$ |$$  __/   $$ |  $$ |$$ |     \\$$  /   \n");
		System.out.print("$$ |\\$  /$$ |$$ |  $$ |$$ |\\$$$ |$$ |  $$ |$$ |      $$ |  $$ |$$ |      $$ |    \n");
		System.out.print("$$ | \\_/ $$ | $$$$$$  |$$ | \\$$ | $$$$$$  |$$ |       $$$$$$  |$$$$$$$$\\ $$ |    \n\n");

		Game game;

		while (true) {
			int playersCount = readNaturalNumber(scanner, "Number of players: ");

			if (playersCount >= 2 && playersCount <= 8) {
				String[] players = new String[playersCount];

				for (int i = 0; i < playersCount; i++) {
					String playerName;
					boolean nameAlreadyExists;

					do {
						System.out.printf("Player %d: ", i + 1);
						playerName = scanner.nextLine();
						nameAlreadyExists = checkIfNameExists(players, playerName);

						if (nameAlreadyExists) {
							System.out.println("Player with this name already exists. Please choose a different name..:)");
						}

					} while (nameAlreadyExists);

					players[i] = playerName;
				}

				game = new Game(players, scanner);
				break;
			} else {
				System.out.print("The game must have between 2 and 8 players!\n");
			}
		}

		while (true) {
			System.out.println();
			game.start();
			System.out.println();

			System.out.print("Play again?\n");
			System.out.print("[1] Yes\n");
			System.out.print("[2] No\n");

			int option;
			while (true) {
				option = readNaturalNumber(scanner, "Option: ");
				if (option == 1 || option == 2)
					break;
				else
					System.out.print("Wrong option, please enter again!\n");
			}

			if (option == 2)
				break;
		}

		scanner.close();
	}

	private static int readNaturalNumber(Scanner scanner, String prompt) {
		while (true) {
			try {
				System.out.print(prompt);
				int number = Integer.parseInt(scanner.nextLine());
				if (number >= 0)
					return number;
				else
					System.out.print("Negative numbers are not allowed!\n");
			} catch (NumberFormatException e) {
				System.out.print("Please insert an integer!\n");
			}
		}
	}

	private static boolean checkIfNameExists(String[] names, String playerName) {
		for (String name : names) {
			if (name != null && name.equals(playerName)) {
				return true;
			}
		}
		return false;
	}
}