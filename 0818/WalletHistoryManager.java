class Transaction {
    private int sequence;
    private String type; // "DEPOSIT", "PAYMENT", "TRANSFER_IN", "TRANSFER_OUT"
    private double amount;

    public Transaction(int sequence, String type, double amount) {
        this.sequence = sequence;
        this.type = type;
        this.amount = amount;
    }

    public int getSequence() { return sequence; }
    public String getType() { return type; }
    public double getAmount() { return amount; }

    @Override
    public String toString() {
        return String.format("#%d [%s] $%.0f", sequence, type, amount);
    }
}

class Wallet {
    private String owner;
    private double balance;
    private Transaction[] history;
    private int txCount;

    public Wallet(String owner, double balance, int maxHistory) {
        this.owner = owner;
        this.balance = balance;
        this.history = new Transaction[maxHistory];
        this.txCount = 0;
    }

    public boolean isHistoryFull() {
        return txCount >= history.length;
    }

    private void addTransaction(String type, double amount) {
        history[txCount] = new Transaction(txCount + 1, type, amount);
        txCount++;
    }

    public boolean transferTo(Wallet target, double amount) {
        if (target == null || target == this || amount <= 0 || this.balance < amount) {
            return false;
        }
        // 交易陣列已滿時不得修改餘額
        if (this.isHistoryFull() || target.isHistoryFull()) {
            System.out.println("[交易失敗] 交易紀錄空間已滿");
            return false;
        }

        this.balance -= amount;
        target.balance += amount;

        this.addTransaction("TRANSFER_OUT", amount);
        target.addTransaction("TRANSFER_IN", amount);
        return true;
    }

    public Transaction findTransaction(int sequence) {
        for (int i = 0; i < txCount; i++) {
            if (history[i].getSequence() == sequence) {
                return history[i];
            }
        }
        return null;
    }

    public double totalByType(String type) {
        double total = 0;
        for (int i = 0; i < txCount; i++) {
            if (history[i].getType().equalsIgnoreCase(type)) {
                total += history[i].getAmount();
            }
        }
        return total;
    }

    public void printStatement() {
        System.out.println("=== " + owner + " 的完整對帳單 (Statement) ===");
        System.out.println("當前餘額: $" + balance);
        System.out.println("交易明細:");
        for (int i = 0; i < txCount; i++) {
            System.out.println("  " + history[i]);
        }
        System.out.println("==========================================");
    }
}

public class WalletHistoryManager {
    public static void main(String[] args) {
        Wallet walletA = new Wallet("Alice", 1000, 5);
        Wallet walletB = new Wallet("Bob", 500, 5);

        walletA.transferTo(walletB, 200);
        walletA.transferTo(walletB, 300);

        walletA.printStatement();
        walletB.printStatement();

        System.out.println("尋找 Alice 的第 2 筆交易: " + walletA.findTransaction(2));
        System.out.println("Alice 轉出總額 (TRANSFER_OUT): $" + walletA.totalByType("TRANSFER_OUT"));
        System.out.println("Bob 轉入總額 (TRANSFER_IN): $" + walletB.totalByType("TRANSFER_IN"));
    }
}