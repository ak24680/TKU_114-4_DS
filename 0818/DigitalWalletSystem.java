class DigitalWallet {
    private String walletId;
    private String owner;
    private double balance;
    private int transactionCount;

    public DigitalWallet(String walletId, String owner, double initialBalance) {
        this.walletId = walletId;
        this.owner = owner;
        this.balance = Math.max(0, initialBalance);
        this.transactionCount = 0;
    }

    public boolean deposit(double amount) {
        if (amount <= 0) return false;
        this.balance += amount;
        this.transactionCount++;
        return true;
    }

    public boolean pay(double amount) {
        if (amount <= 0 || amount > this.balance) return false;
        this.balance -= amount;
        this.transactionCount++;
        return true;
    }

    public boolean refund(double amount) {
        if (amount <= 0) return false;
        this.balance += amount;
        this.transactionCount++;
        return true;
    }

    public String getWalletId() { return walletId; }
    public String getOwner() { return owner; }
    public double getBalance() { return balance; }
    public int getTransactionCount() { return transactionCount; }
}

public class DigitalWalletSystem {
    public static void main(String[] args) {
        DigitalWallet wallet = new DigitalWallet("W101", "Alice", 1000);

        System.out.println("正常儲存 (500): " + wallet.deposit(500));        // true
        System.out.println("正常付款 (300): " + wallet.pay(300));            // true
        System.out.println("餘額不足付款 (2000): " + wallet.pay(2000));      // false
        System.out.println("負數金額退款 (-100): " + wallet.refund(-100));    // false
        System.out.println("正常退款 (100): " + wallet.refund(100));          // true

        System.out.println("\n最終餘額: " + wallet.getBalance() + " (預期: 1300.0)");
        System.out.println("交易成功次數: " + wallet.getTransactionCount() + " (預期: 3)");
    }
}