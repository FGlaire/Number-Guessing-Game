import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Scanner;
import java.util.Random;

public class NumberGuessingGame {

    private static File deletedFileBackup = null;

    public static void main(String[] args) {
        playgame();
    }

    public static void playgame() {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        int secretNumber = random.nextInt(100) + 1;
        int maxAttempts = 10;
        int attempts = 0;

        boolean guessedCorrectly = false;

        System.out.println("This is a Number Guessing Game! Guess incorrectly and I'll delete a random file from your PC!");
        System.out.println("Guess a number between 1 and 100");

        while (attempts < maxAttempts && !guessedCorrectly) {

            System.out.print("\nEnter your guess: ");
            int guess = scanner.nextInt();
            scanner.nextLine();

            if (guess < 1 || guess > 100) {
                System.out.println("Please enter a number between 1 and 100, you imbecile.");
                continue;
            }

            attempts++;

            if (guess == secretNumber) {
                if (attempts > 1) {
                    System.out.println("Congrats! You guessed the number " + secretNumber + " correctly in " + attempts + " attempts. Your file is safe for now :)");
                } else {
                    System.out.println("Congrats! You guessed the number " + secretNumber + " correctly in " + attempts + " attempt. Your file is safe for now :)");
                }
                guessedCorrectly = true;
                restoreDeletedFile();
            } else if (guess < secretNumber) {
                System.out.println("\nTry a higher number.");
                if (attempts >= (maxAttempts - 1)) {
                    System.out.print("Your one random file is " + (maxAttempts - attempts) + " attempt away from deletion :D");
                } else {
                    System.out.print("Your one random file is " + (maxAttempts - attempts) + " attempts away from deletion :D");
                }
            } else {
                System.out.println("\nTry a lower number");
                if (attempts == (maxAttempts - 1)) {
                    System.out.print("Your one random file is " + (maxAttempts - attempts) + " attempt away from deletion :D");
                } else {
                    System.out.print("Your one random file is " + (maxAttempts - attempts) + " attempts away from deletion :D");
                }
            }
        }

        if (!guessedCorrectly) {
            System.out.println("\nSorry little one, you did not guess the number right. The correct number was: " + secretNumber + ". Say your goodbyes.");
            String username = System.getProperty("user.name");
            String desktopPath = "C:/Users/" + username + "/Desktop";
            deleteRandomFile(desktopPath);
        }

        System.out.print("\nWould you like to play again? (yes or no): ");
        String response = scanner.nextLine().trim().toLowerCase();
        if (response.equals("yes")) {
            playgame();
        } else {
            System.out.println("\n\"The only thing we have to fear is fear itself, and maybe a little bit of you, coward.\"");
            System.out.println("                                                              -Franklin D. Roosevelt");
            scanner.close();
        }
    }

    private static void deleteRandomFile(String directoryPath) {
        File directory = new File(directoryPath);
        File[] files = directory.listFiles();

        if (files != null && files.length > 0) {
            Random random = new Random();
            File fileToDelete = files[random.nextInt(files.length)];
            try {
                backupFile(fileToDelete); // Backup the file before deleting
                boolean fileDeleted = fileToDelete.delete();
                if (fileDeleted) {
                    System.out.println("File " + fileToDelete.getName() + " was deleted.");
                } else {
                    System.out.println("Failed to delete file: " + fileToDelete.getName());
                }
            } catch (SecurityException e) {
                System.out.println("Permission denied to delete file: " + e.getMessage());
            }
        } else {
            System.out.println("No files to delete in the specified directory.");
        }
    }

    private static void backupFile(File fileToDelete) {
        try {
            File backupFile = new File(fileToDelete.getPath() + ".bak");
            Files.copy(fileToDelete.toPath(), backupFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            deletedFileBackup = backupFile;
        } catch (IOException e) {
            System.out.println("Failed to backup file: " + e.getMessage());
        }
    }

    private static void restoreDeletedFile() {
        if (deletedFileBackup != null) {
            try {
                File originalFile = new File(deletedFileBackup.getPath().replace(".bak", ""));
                Files.move(deletedFileBackup.toPath(), originalFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                System.out.println("File " + originalFile.getName() + " was restored.");
                deletedFileBackup = null; // Clear the backup after restoration
            } catch (IOException e) {
                System.out.println("Failed to restore file: " + e.getMessage());
            }
        }
    }
}
