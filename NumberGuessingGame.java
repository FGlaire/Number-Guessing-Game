import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.Scanner;
import java.util.Random;

public class NumberGuessingGame {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        int secretNumber = random.nextInt(100) + 1;
        int maxAttempts = 10;
        int attempts = 0;

        boolean guessedCorrectly = false;

        System.out.println("This is a Number Guessing Game! Guess incorectly and i'll delete a random file from your pc!");
        System.out.println("Guess a number between 1 and 100");

        while (attempts < maxAttempts && !guessedCorrectly){

            System.out.print("\nEnter your guess: ");
            int guess = scanner.nextInt();
            scanner.nextLine();

            if (guess < 1 || guess > 100) {
                System.out.println("Please enter a number between 1 and 100 you imbecile.");
                continue;
            }

            attempts++;

            if (guess == secretNumber) {
                System.out.println("Congrats! You guessed the number " + secretNumber + " correctly in " + attempts + " attempts. Your file is safe for now :)");
                guessedCorrectly = true;
            }
            else if (guess < secretNumber) {
                System.out.println("\nTry a higher number.");
                if (attempts == (maxAttempts - 1) ) {
                    System.out.print("Your one random file is " + (maxAttempts - attempts) + " attempt away from deletion :D");
                }
                else {
                    System.out.print("Your one random file is " + (maxAttempts - attempts) + " attempts away from deletion :D");
                }
            }
            else {
                System.out.println("\nTry a lower number");
                if (attempts == (maxAttempts - 1)) {
                    System.out.print("Your one random file is " + (maxAttempts - attempts) + " attempt away from deletion :D");
                }
                else {
                    System.out.print("Your one random file is " + (maxAttempts - attempts) + " attempts away from deletion :D");
                }
            }
        }
        
        if (!guessedCorrectly){
            System.out.println("\nSorry little one, you did not guess the number right. The correct number was: " + secretNumber + ". Say your goodbyes.");
            deleteRandomFile("c:/Users/surip/Desktop/515");
        }

        scanner.close();

    }

    private static void deleteRandomFile(String directoryPath) {

        File directory = new File(directoryPath);
        File[] files = directory.listFiles();

        if (files != null && files.length > 0){
            Random random = new Random();
            File fileToDelete = files[random.nextInt(files.length)];
            try {
                Files.delete(fileToDelete.toPath());
                System.out.println("File " + fileToDelete.getName() + " was deleted.");
            }
            catch (IOException e) {
                System.out.println("Failed to delete file: " + e.getMessage());
            }
        }
        else {
            System.out.println("No files to delete in the specified directory.");
        }
    }
}
