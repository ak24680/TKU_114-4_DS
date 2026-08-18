class Customer {
    private String customerId;
    private String name;

    public Customer(String customerId, String name) {
        this.customerId = customerId;
        this.name = name;
    }

    public String getName() { return name; }
    public String getCustomerId() { return customerId; }
}

class OrderItem {
    private String productName;
    private double price;
    private int quantity;

    public OrderItem(String productName, double price, int quantity) {
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
    }

    public double getSubtotal() { return price * quantity; }
    public int getQuantity() { return quantity; }
    public String getProductName() { return productName; }
    public double getPrice() { return price; }
}

class CustomerOrder {
    private String orderId;
    private Customer customer;
    private OrderItem[] items;
    private int itemCount;

    public CustomerOrder(String orderId, Customer customer, int maxItems) {
        this.orderId = orderId;
        this.customer = customer;
        this.items = new OrderItem[maxItems];
        this.itemCount = 0;
    }

    public boolean addItem(OrderItem item) {
        if (itemCount < items.length) {
            items[itemCount++] = item;
            return true;
        }
        return false;
    }

    public double calculateTotal() {
        double total = 0;
        for (int i = 0; i < itemCount; i++) {
            total += items[i].getSubtotal();
        }
        return total;
    }

    public int getTotalQuantity() {
        int totalQty = 0;
        for (int i = 0; i < itemCount; i++) {
            totalQty += items[i].getQuantity();
        }
        return totalQty;
    }

    public void printSummary() {
        System.out.println("======== 訂單摘要 ========");
        System.out.println("訂單編號: " + orderId);
        System.out.println("顧客姓名: " + customer.getName() + " (" + customer.getCustomerId() + ")");
        System.out.println("------------------------");
        for (int i = 0; i < itemCount; i++) {
            OrderItem item = items[i];
            System.out.printf("- %s: $%.0f x %d = $%.0f\n", 
                item.getProductName(), item.getPrice(), item.getQuantity(), item.getSubtotal());
        }
        System.out.println("------------------------");
        System.out.println("品項總數量: " + getTotalQuantity());
        System.out.printf("訂單總金額: $%.0f\n", calculateTotal());
        System.out.println("========================");
    }
}

public class CustomerOrderSystem {
    public static void main(String[] args) {
        Customer customer = new Customer("C001", "Bob");
        CustomerOrder order = new CustomerOrder("ORD-20260818", customer, 3);

        order.addItem(new OrderItem("鍵盤", 1200, 1));
        order.addItem(new OrderItem("滑鼠", 600, 2));

        order.printSummary();
    }
}