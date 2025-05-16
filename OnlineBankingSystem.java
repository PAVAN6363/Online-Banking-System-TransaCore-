import java.util.ArrayList;
import java.util.Scanner;

// Class for Transaction Details
class Transaction {
    private String type;
    private double amount;
    private double balanceAfterTransaction;

    public Transaction(String type, double amount, double balanceAfterTransaction) {
        this.type = type;
        this.amount = amount;
        this.balanceAfterTransaction = balanceAfterTransaction;
    }

    public void showTransaction() {
        System.out.println(type + ": $" + amount + " | Balance after transaction: $" + balanceAfterTransaction);
    }
}

// Class for Bank Account
class BankAccount {
    private String accountNumber;
    private String name;
    private String email;
    private double balance;
    private ArrayList<Transaction> transactionHistory = new ArrayList<>();

    public BankAccount(String accountNumber, String name, String email, double initialDeposit) {
        this.accountNumber = accountNumber;
        this.name = name;
        this.email = email;
        this.balance = initialDeposit;
        transactionHistory.add(new Transaction("Account Created", initialDeposit, initialDeposit));
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getName() {
        return name;
    }

    public void updateName(String newName) {
        this.name = newName;
    }

    public String getEmail() {
        return email;
    }

    public void updateEmail(String newEmail) {
        this.email = newEmail;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            transactionHistory.add(new Transaction("Deposit", amount, balance));
            System.out.println("Deposit successful! New balance: $" + balance);
        } else {
            System.out.println("Invalid deposit amount.");
        }
    }

    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            transactionHistory.add(new Transaction("Withdrawal", amount, balance));
            System.out.println("Withdrawal successful! New balance: $" + balance);
        } else {
            System.out.println("Insufficient balance or invalid amount.");
        }
    }

    public void transfer(BankAccount receiver, double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            receiver.balance += amount;
            transactionHistory.add(new Transaction("Transfer to " + receiver.getAccountNumber(), amount, balance));
            receiver.transactionHistory.add(new Transaction("Transfer from " + this.accountNumber, amount, receiver.balance));
            System.out.println("Transfer successful! New balance: $" + balance);
        } else {
            System.out.println("Insufficient balance or invalid amount.");
        }
    }

    public void showTransactionHistory() {
        if (transactionHistory.isEmpty()) {
            System.out.println("No transaction history available.");
        } else {
            System.out.println("Transaction History for Account: " + accountNumber);
            for (Transaction t : transactionHistory) {
                t.showTransaction();
            }
        }
    }

    public void showAccountDetails() {
        System.out.println("Account Number: " + accountNumber);
        System.out.println("Account Holder: " + name);
        System.out.println("Email: " + email);
        System.out.println("Balance: $" + balance);
    }
}

// Main Banking System Class
public class OnlineBankingSystem {
    private static ArrayList<BankAccount> accounts = new ArrayList<>();
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        int choice;

        do {
            System.out.println("\n==== Online Banking System ====");
            System.out.println("1. Create Account");
            System.out.println("2. View Account Details");
            System.out.println("3. Deposit Funds");
            System.out.println("4. Withdraw Funds");
            System.out.println("5. Transfer Funds");
            System.out.println("6. View Transaction History");
            System.out.println("7. Update Personal Information");
            System.out.println("8. Exit");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    createAccount();
                    break;
                case 2:
                    viewAccountDetails();
                    break;
                case 3:
                    depositFunds();
                    break;
                case 4:
                    withdrawFunds();
                    break;
                case 5:
                    transferFunds();
                    break;
                case 6:
                    viewTransactionHistory();
                    break;
                case 7:
                    updatePersonalInformation();
                    break;
                case 8:
                    System.out.println("Thank you for using the Online Banking System!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 8);
    }

    private static void createAccount() {
        System.out.print("Enter Account Number: ");
        String accountNumber = sc.next();
        System.out.print("Enter Account Holder Name: ");
        String name = sc.next();
        System.out.print("Enter Email: ");
        String email = sc.next();
        System.out.print("Enter Initial Deposit: ");
        double initialDeposit = sc.nextDouble();
        accounts.add(new BankAccount(accountNumber, name, email, initialDeposit));
        System.out.println("Account created successfully!");
    }

    private static void viewAccountDetails() {
        BankAccount account = findAccount();
        if (account != null) {
            account.showAccountDetails();
        }
    }

    private static void depositFunds() {
        BankAccount account = findAccount();
        if (account != null) {
            System.out.print("Enter amount to deposit: ");
            double amount = sc.nextDouble();
            account.deposit(amount);
        }
    }

    private static void withdrawFunds() {
        BankAccount account = findAccount();
        if (account != null) {
            System.out.print("Enter amount to withdraw: ");
            double amount = sc.nextDouble();
            account.withdraw(amount);
        }
    }

    private static void transferFunds() {
        System.out.print("Enter your Account Number: ");
        String senderAccountNumber = sc.next();
        BankAccount sender = findAccountByNumber(senderAccountNumber);

        if (sender != null) {
            System.out.print("Enter Receiver's Account Number: ");
            String receiverAccountNumber = sc.next();
            BankAccount receiver = findAccountByNumber(receiverAccountNumber);

            if (receiver != null) {
                System.out.print("Enter amount to transfer: ");
                double amount = sc.nextDouble();
                sender.transfer(receiver, amount);
            } else {
                System.out.println("Receiver's account not found.");
            }
        }
    }

    private static void viewTransactionHistory() {
        BankAccount account = findAccount();
        if (account != null) {
            account.showTransactionHistory();
        }
    }

    private static void updatePersonalInformation() {
        BankAccount account = findAccount();
        if (account != null) {
            System.out.println("1. Update Name");
            System.out.println("2. Update Email");
            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter new name: ");
                    String newName = sc.next();
                    account.updateName(newName);
                    System.out.println("Name updated successfully!");
                    break;
                case 2:
                    System.out.print("Enter new email: ");
                    String newEmail = sc.next();
                    account.updateEmail(newEmail);
                    System.out.println("Email updated successfully!");
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private static BankAccount findAccount() {
        System.out.print("Enter Account Number: ");
        String accountNumber = sc.next();
        return findAccountByNumber(accountNumber);
    }

    private static BankAccount findAccountByNumber(String accountNumber) {
        for (BankAccount account : accounts) {
            if (account.getAccountNumber().equals(accountNumber)) {
                return account;
            }
        }
        System.out.println("Account not found.");
        return null;
    }
}
