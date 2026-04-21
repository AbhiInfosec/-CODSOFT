import java.util.*;

public class NumberGame {
    
    // Player stats
    static int totalScore = 0;
    static int roundsPlayed = 0;
    static int roundsWon = 0;
    static int bestScore = 0;
    static List<Integer> attemptHistory = new ArrayList<>();
    static Scanner sc = new Scanner(System.in);
    static Random rand = new Random();
    
    public static void main(String[] args) {
        showBanner();
        
        System.out.print("Enter your name: ");
        String playerName = sc.nextLine().trim();
        if (playerName.isEmpty()) playerName = "Player";
        
        System.out.println("\n👋 Welcome, " + playerName + "! Let's play!\n");
        showRules();
        
        boolean playing = true;
        while (playing) {
            playRound(playerName);
            System.out.print("\n🔄 Play another round? (yes/no): ");
            String resp = sc.nextLine().trim().toLowerCase();
            playing = resp.equals("yes") || resp.equals("y");
        }
        
        showFinalStats(playerName);
        sc.close();
    }
    
    static void playRound(String playerName) {
        roundsPlayed++;
        
        // Difficulty selection
        System.out.println("\n🎯 Select Difficulty:");
        System.out.println("  1. Easy   (1-50,  10 attempts)");
        System.out.println("  2. Medium (1-100,  7 attempts)");
        System.out.println("  3. Hard   (1-500,  5 attempts)");
        System.out.print("Choice (1/2/3): ");
        
        int maxNum, maxAttempts, diffMultiplier;
        String diffName;
        
        switch (sc.nextLine().trim()) {
            case "1": maxNum = 50;  maxAttempts = 10; diffMultiplier = 1; diffName = "Easy";   break;
            case "3": maxNum = 500; maxAttempts = 5;  diffMultiplier = 3; diffName = "Hard";   break;
            default:  maxNum = 100; maxAttempts = 7;  diffMultiplier = 2; diffName = "Medium"; break;
        }
        
        int secret = rand.nextInt(maxNum) + 1;
        int attempts = 0;
        boolean won = false;
        List<Integer> guesses = new ArrayList<>();
        
        System.out.println("\n╔══════════════════════════════════════╗");
        System.out.printf ("║  Round %-2d | Difficulty: %-8s      ║%n", roundsPlayed, diffName);
        System.out.printf ("║  Range: 1 to %-4d | Attempts: %-3d    ║%n", maxNum, maxAttempts);
        System.out.println("╚══════════════════════════════════════╝");
        System.out.println("💭 I'm thinking of a number... GO!\n");
        
        while (attempts < maxAttempts && !won) {
            int remaining = maxAttempts - attempts;
            System.out.printf("Attempt %d/%d (Remaining: %d) → ", attempts + 1, maxAttempts, remaining);
            
            int guess;
            try {
                guess = Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("  ⚠️  Please enter a valid number!");
                continue;
            }
            
            if (guess < 1 || guess > maxNum) {
                System.out.println("  ⚠️  Number must be between 1 and " + maxNum + "!");
                continue;
            }
            
            guesses.add(guess);
            attempts++;
            
            if (guess == secret) {
                won = true;
                int roundScore = calculateScore(attempts, maxAttempts, diffMultiplier);
                totalScore += roundScore;
                roundsWon++;
                attemptHistory.add(attempts);
                if (roundScore > bestScore) bestScore = roundScore;
                
                System.out.println("\n  🎉 CORRECT! You got it in " + attempts + " attempt(s)!");
                System.out.println("  ⭐ Round Score: +" + roundScore + " points");
                System.out.println("  📊 Total Score: " + totalScore);
                
                // Rating
                if (attempts == 1) System.out.println("  🏆 INCREDIBLE! First guess!");
                else if (attempts <= maxAttempts / 3) System.out.println("  🌟 Amazing! Very few attempts!");
                else if (attempts <= maxAttempts / 2) System.out.println("  👍 Good job!");
                else System.out.println("  😅 Made it just in time!");
                
            } else {
                // Hint system
                int diff = Math.abs(guess - secret);
                String hint;
                if (diff <= 5) hint = "🔥 Extremely HOT! So close!";
                else if (diff <= 15) hint = "♨️  Very WARM! Getting there!";
                else if (diff <= 30) hint = "🌡️  Warm. Keep trying!";
                else hint = "❄️  Cold. Far away!";
                
                if (guess < secret) System.out.println("  📈 Too LOW!  " + hint);
                else System.out.println("  📉 Too HIGH! " + hint);
                
                if (remaining - 1 == 1) {
                    System.out.println("  ⚠️  LAST ATTEMPT! Think carefully!");
                }
            }
        }
        
        if (!won) {
            System.out.println("\n  💔 Out of attempts! The number was: " + secret);
            System.out.println("  Your guesses: " + guesses);
        }
    }
    
    static int calculateScore(int attempts, int maxAttempts, int multiplier) {
        int base = (maxAttempts - attempts + 1) * 100;
        if (attempts == 1) base += 500; // Bonus for first guess
        return base * multiplier;
    }
    
    static void showBanner() {
        System.out.println("╔══════════════════════════════════════════╗");
        System.out.println("║        NUMBER GUESSING GAME  🎮          ║");
        System.out.println("║      CodSoft Java Internship Task 1      ║");
        System.out.println("║    Developed by: Abhishek Mishra         ║");
        System.out.println("╚══════════════════════════════════════════╝");
    }
    
    static void showRules() {
        System.out.println("📋 RULES:");
        System.out.println("  ✔ Guess the secret number within allowed attempts");
        System.out.println("  ✔ Hot/Cold hints will guide you");
        System.out.println("  ✔ Fewer attempts = more score");
        System.out.println("  ✔ Hard difficulty = 3x score multiplier!\n");
    }
    
    static void showFinalStats(String playerName) {
        double avgAttempts = attemptHistory.isEmpty() ? 0 :
                attemptHistory.stream().mapToInt(i -> i).average().orElse(0);
        
        System.out.println("\n╔══════════════════════════════════════════╗");
        System.out.println("║            FINAL STATISTICS 📊           ║");
        System.out.println("╠══════════════════════════════════════════╣");
        System.out.printf ("║  Player       : %-25s║%n", playerName);
        System.out.printf ("║  Total Score  : %-25d║%n", totalScore);
        System.out.printf ("║  Best Score   : %-25d║%n", bestScore);
        System.out.printf ("║  Rounds Played: %-25d║%n", roundsPlayed);
        System.out.printf ("║  Rounds Won   : %-25d║%n", roundsWon);
        System.out.printf ("║  Win Rate     : %-24s ║%n", 
                roundsPlayed == 0 ? "N/A" : String.format("%.1f%%", (roundsWon * 100.0 / roundsPlayed)));
        System.out.printf ("║  Avg Attempts : %-24s ║%n", 
                avgAttempts == 0 ? "N/A" : String.format("%.1f", avgAttempts));
        System.out.println("╠══════════════════════════════════════════╣");
        
        // Rank
        String rank;
        if (totalScore >= 5000) rank = "🏆 GRANDMASTER";
        else if (totalScore >= 3000) rank = "💎 DIAMOND";
        else if (totalScore >= 1500) rank = "🥇 GOLD";
        else if (totalScore >= 500) rank = "🥈 SILVER";
        else rank = "🥉 BRONZE";
        
        System.out.printf("║  Final Rank   : %-25s║%n", rank);
        System.out.println("╠══════════════════════════════════════════╣");
        System.out.println("║     Thanks for playing! Come back soon!  ║");
        System.out.println("╚══════════════════════════════════════════╝");
    }
}
