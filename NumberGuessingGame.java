import java.util.Scanner;
import java.util.Random;
import java.io.File;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class NumberGuessingGame {
public static final String ANSI_RESET = "\u001B[0m";
public static final String ANSI_RED = "\u001B[31m";
public static final String ANSI_GREEN = "\u001B[32m";
public static final String ANSI_YELLOW = "\u001B[33m";

public static class Score implements Comparable<Score> {
  public String playerName;
  public int tries;

  public Score(String playerName, int tries) {
    this.playerName = playerName;
    this.tries = tries;
  }

  @Override
  public int compareTo(Score other) {
    return this.tries - other.tries;
  }
}

public static void main(String[] args) {
try (Scanner input = new Scanner(System.in)) {
  Random rand = new Random();
  int secretNumber = 0;
  int guess = 0;
  int tries = 0;
  int range = 100;
  System.out.println("-------------------------------");
  System.out.println("-------------------------------");
  System.out.println("Welcome to the number guessing game!");
  System.out.println("Please choose a game mode: ");
  System.out.println("1) Easy (1-100)");
  System.out.println("2) Medium (1-500)");
  System.out.println("3) Hard (1-1000)");
  
  int mode = input.nextInt();
  System.out.println("-------------------------------");
  
  if (mode == 1) {
    range = rand.nextInt(100 - 50 + 1) + 50;
  } else if (mode == 2) {
    range = rand.nextInt(500 - 250 + 1) + 250;
  } else if (mode == 3) {
    range = rand.nextInt(1000 - 500 + 1) + 500;
  } else {
    System.out.println("Invalid mode, defaulting to easy.");
    range = 100;
  }
  
  secretNumber = rand.nextInt(range) + 1;
  System.out.println("****************************************************************");
  System.out.println("I have generated a random number between 1 and " + range + ".");
  System.out.println("Can you guess what it is?");
  
  System.out.print("Enter your name: ");
  String playerName = input.next();
  
  while (guess != secretNumber) {
    System.out.print("Enter your guess: ");
    guess = input.nextInt();
    tries++;
  
    if (guess < secretNumber) {
      System.out.println(ANSI_RED + "Too low! 🙁 Try again." + ANSI_RESET);
    } else if (guess > secretNumber) {
      System.out.println(ANSI_RED + "Too high! 🙁 Try again." + ANSI_RESET);
    }
  }
  
  System.out.println(ANSI_GREEN + "You got it! 🎉 The secret number was " + secretNumber + " and it took you " + tries + " tries." + ANSI_RESET);
  
  try {
  ArrayList<Score> scores = new ArrayList<>();
  File file = new File("scores.csv");
  
  if (!file.exists()) {
  file.createNewFile();
  }
  
  BufferedReader reader = new BufferedReader(new FileReader("scores.csv"));
  String line;
  
  while ((line = reader.readLine()) != null) {
  String[] parts = line.split(",");
  scores.add(new Score(parts[0], Integer.parseInt(parts[1])));
  }
  
  reader.close();
  scores.add(new Score(playerName, tries));
  Collections.sort(scores);
  
  FileWriter writer = new FileWriter("scores.csv");
  for (Score score : scores) {
  writer.write(score.playerName + "," + score.tries + "\n");
  }
  
  writer.close();
  } catch (IOException e) {
  System.out.println("Error reading/writing scores file: " + e);
  }
} catch (NumberFormatException e1) {
  // TODO Auto-generated catch block
  e1.printStackTrace();
}
System.out.println("High scores: ");
System.out.println("Player Name, Tries");

try {
BufferedReader reader = new BufferedReader(new FileReader("scores.csv"));
String line;

while ((line = reader.readLine()) != null) {
System.out.println(line);
}

reader.close();
} catch (IOException e) {
System.out.println("Error reading scores file: " + e);
}
}
}

