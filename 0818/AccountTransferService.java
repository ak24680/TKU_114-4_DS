class Account {
    private String id;
    private int balance;

    public Account(String id, int balance) {
        this.id = id;
        this.balance = balance;
    }

    public String getId() { return id; }
    public int getBalance() { return balance; }
    public void setBalance(int balance) { this.balance = balance; }
}

class TransferService {
    public static boolean transfer(Account source, Account target, int amount) {
        // 1. 來源與目標不是 null
        if (source == null || target == null) {
            System.out.println("[轉帳失敗] 帳戶不得為 null");
            return false;
        }
        // 2. 來源與目標不是同一個物件
        if (source == target) {
            System.out.println("[轉帳失敗] 不能轉帳給同一帳戶");
            return false;
        }
        // 3. 金額大於 0 且來源餘額足夠
        if (amount <= 0 || source.getBalance() < amount) {
            System.out.println("[轉帳失敗] 金額不合法或餘額不足");
            return false;
        }

        // 驗證通過後才更新（任一驗證失敗時兩帳戶均不改變）
        source.setBalance(source.getBalance() - amount);
        target.setBalance(target.getBalance() + amount);
        System.out.println("[轉帳成功] 轉出 $" + amount);
        return true;
    }
}

public class AccountTransferService {
    public static void main(String[] args) {
        Account accA = new Account("A001", 1000);
        Account accB = new Account("B002", 500);

        System.out.println("--- 測試 1: 成功轉帳 ---");
        TransferService.transfer(accA, accB, 300);
        System.out.println("A 餘額: " + accA.getBalance() + " | B 餘額: " + accB.getBalance());

        System.out.println("\n--- 測試 2: 餘額不足 ---");
        TransferService.transfer(accA, accB, 2000);

        System.out.println("\n--- 測試 3: 同帳戶轉帳 ---");
        TransferService.transfer(accA, accA, 100);

        System.out.println("\n--- 測試 4: null 目標 ---");
        TransferService.transfer(accA, null, 100);

        System.out.println("\n最終帳戶狀態 -> A: " + accA.getBalance() + " | B: " + accB.getBalance());
    }
}