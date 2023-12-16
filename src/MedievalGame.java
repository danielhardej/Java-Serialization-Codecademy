import java.util.Scanner;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;

public class MedievalGame {

    private Player player;

    public static void main(String[] args) {
        Scanner console = new Scanner(System.in);
        MedievalGame game = new MedievalGame();
        game.player = game.start(console);
        try {
            game.addDelay(500);
            System.out.println("\nLet's take a quick look at you to make sure you're ready to head out the door.");
            System.out.println(game.player);
            game.addDelay(1000);
            System.out.println("\nWell, you're off to a good start, let's get your game saved so we don't lose it.");
            game.save();
            game.addDelay(2000);
            System.out.println("We just saved your game...");
            System.out.println("Now we are going to try to load your character to make sure the save worked...");
            game.load(game.player.getName(), console);
            game.addDelay(1000);
            System.out.println("Deleting character...");
            String charName = game.player.getName();
            game.player = null;
            game.addDelay(1500);
            game.player = game.load(charName, console);
            System.out.println("Loading character...");
            game.addDelay(2000);
            System.out.println("Now let's print out your character again to make sure everything loaded:");
            game.addDelay(500);
            System.out.println(game.player);
            System.out.println("Looks like everything worked! Have fun on your adventure!");
        } catch (Exception e) {
            System.out.println("There was an error loading your game: " + e);
        }
    }

    private Player start(Scanner console) {
        Player player;
        Art.homeScreen();
        System.out.println("Welcome to your latest adventure!");
        System.out.println("Tell me traveler, have you been here before?");
        System.out.print("   Enter 'y' to load a game, 'n' to create a new game: ");
        String answer = console.next().toLowerCase();
        while (true) {
            if (answer.equals("y")) {
                System.out.println("Enter the name of the player you want to load: ");
                String playerName = console.next();
                player = load(playerName, console);
                break;
            } else if (answer.equals("n")) {
                System.out.print("\nWell then, don't be shy, go ahead and tell me your name: ");
                String newPLayerName = console.next();
                player = new Player(newPLayerName);
                break;
            } else {
                System.out.print("   Invalid input! Please enter 'y' to load a game, 'n' to create a new game: ");
                answer = console.next().toLowerCase();
            }
        }
        return player;
    }

    private void save() {
        String fileName = player.getName() + ".svr";
        try {
            FileOutputStream fos = new FileOutputStream(fileName);
            ObjectOutputStream playerSaver = new ObjectOutputStream(fos);
            playerSaver.writeObject(this.player);
            playerSaver.close();
        } catch (IOException e) {
            System.out.println("There was an error saving your game, your file might not be available the next time you go to load a game.");
        }
    }

    private Player load(String playerName, Scanner console) {
        Player loadedPlayer = null;
        try {
            System.out.print("Loading character with from file: ");
            String fileName = playerName + ".svr";
            System.out.print(fileName);
            FileInputStream fis = new FileInputStream(fileName);
            ObjectInputStream playerLoader = new ObjectInputStream(fis);
            try {
                loadedPlayer = (Player) playerLoader.readObject();
            } catch (ClassNotFoundException | IOException e) { 
                e.printStackTrace();
            }
            playerLoader.close();
            System.out.println("\nWelcome back " + loadedPlayer.getName() + "!");
        } catch (IOException e) {
            System.out.println("\nThere was a problem loading your character due to an IO issue. Please check if the file exists and is accessible:" + e);
        } catch (NullPointerException e) {
            System.out.println("\nThere was a problem loading your character due to a null pointer exception. Please check if the file exists and is accessible:" + e);
        }
        if (loadedPlayer == null) {
            addDelay(1500);
            System.out.println("\nWe've created a new player with the name you entered. If you're sure the spelling is correct, your character file may no longer exist, please reload the game if you'd like to try again.");
            System.out.println("In the mean time, we'll create you a new character with the name: " + playerName);
            addDelay(2000);
            loadedPlayer = new Player(playerName);
        }
        return loadedPlayer;
    }

    private void addDelay(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}