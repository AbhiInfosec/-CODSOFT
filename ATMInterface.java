import java.util.Scanner;

// Bank Account Class
class BankAccount {
    private double balance;
    private String accountHolder;
    private String accountNumber;
    
    public BankAccount(String holder, String accNum, double initialBalance) {
        this.accountHolder = holder;
        this.accountNumber = accNum;
        this.balance = initialBalance;
    }
    
    public double getBalance() { return balance; }
    public String getAccountHolder() { return accountHolder; }
    public String getAccountNumber() { return accountNumber; }
    
    public boolean deposit(double amount) {
        if (amount <= 0) return false;
        balance += amount;
        return true;
    }
    
    public boolean withdraw(double amount) {
        if (amount <= 0 || amount > balance) return false;
        balance -= amount;
        return true;
    }
}

// ATM Class
class ATM {
    private BankAccount account;
    private Scanner sc;
    
    public ATM(BankAccount account) {
        this.account = account;
        this.sc = new Scanner(System.in);
    }
    
    public void start() {
        System.out.println("╔══════════════════════════════════╗");
        System.out.println("║          WELCOME TO ATM 🏧       ║");
        System.out.println("╚══════════════════════════════════╝");
        
        if (!authenticate()) {
            System.out.println("❌ Authentication failed! Exiting.");
            return;
        }
        
        boolean running = true;
        while (running) {
            showMenu();
            System.out.print("Choose option (1-4): ");
            String choice = sc.nextLine().trim();
            
            switch (choice) {
                case "1": checkBalance(); break;
                case "2": deposit(); break;
                case "3": withdraw(); break;
                case "4":
                    System.out.println("\n✅ Thank you for using our ATM!");
                    System.out.println("Please collect your card. Goodbye! 👋");
                    running = false;
                    break;
                default:
                    System.out.println("❌ Invalid option! Please choose 1-4.");
            }
        }
        sc.close();
    }
    
    private boolean authenticate() {
        System.out.print("\nEnter your 4-digit PIN: ");
        String pin = sc.nextLine().trim();
        if (pin.equals("2007")) {
            System.out.println("✅ Authentication successful!");
            System.out.println("Welcome, " + account.getAccountHolder() + "!");
            return true;
        }
        return false;
    }
    
    private void showMenu() {
        System.out.println("\n──────────────────────────────────");
        System.out.println("           MAIN MENU");
        System.out.println("──────────────────────────────────");
        System.out.println("1. 💰 Check Balance");
        System.out.println("2. ➕ Deposit Money");
        System.out.println("3. ➖ Withdraw Money");
        System.out.println("4. 🚪 Exit");
        System.out.println("──────────────────────────────────");
    }
    
    private void checkBalance() {
        System.out.println("\n╔══════════════════════════════════╗");
        System.out.println("║         ACCOUNT DETAILS          ║");
        System.out.println("╚══════════════════════════════════╝");
        System.out.println("Account Holder : " + account.getAccountHolder());
        System.out.println("Account Number : " + account.getAccountNumber());
        System.out.printf("Current Balance: ₹%.2f%n", account.getBalance());
    }
    
    private void deposit() {
        System.out.print("\nEnter deposit amount: ₹");
        try {
            double amount = Double.parseDouble(sc.nextLine().trim());
            if (account.deposit(amount)) {
                System.out.println("✅ ₹" + amount + " deposited successfully!");
                System.out.printf("New Balance: ₹%.2f%n", account.getBalance());
            } else {
                System.out.println("❌ Invalid amount! Amount must be greater than 0.");
            }
        } catch (NumberFormatException e) {
            System.out.println("❌ Invalid input! Please enter a valid amount.");
        }
    }
    
    private void withdraw() {
        System.out.print("\nEnter withdrawal amount: ₹");
        try {
            double amount = Double.parseDouble(sc.nextLine().trim());
            if (amount > account.getBalance()) {
                System.out.println("❌ Insufficient balance!");
                System.out.printf("Available balance: ₹%.2f%n", account.getBalance());
            } else if (account.withdraw(amount)) {
                System.out.println("✅ ₹" + amount + " withdrawn successfully!");
                System.out.printf("Remaining Balance: ₹%.2f%n", account.getBalance());
            } else {
                System.out.println("❌ Invalid amount!");
            }
        } catch (NumberFormatException e) {
            System.out.println("❌ Invalid input! Please enter a valid amount.");
        }
    }
}

// Main Class
public class ATMInterface {
    public static void main(String[] args) {
        // Create bank account with initial balance
        BankAccount account = new BankAccount("Abhishek Mishra", "XXXX-XXXX-1234", 10000.00);
        
        // Start ATM
        ATM atm = new ATM(account);
        atm.start();
    }
}
