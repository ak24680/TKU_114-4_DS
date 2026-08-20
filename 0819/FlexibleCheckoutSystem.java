interface PricingPolicy {
    int finalPrice(int originalPrice);
}

class RegularPricing implements PricingPolicy {
    @Override
    public int finalPrice(int originalPrice) {
        return Math.max(0, originalPrice);
    }
}

class Vip85Pricing implements PricingPolicy {
    @Override
    public int finalPrice(int originalPrice) {
        return (int) (Math.max(0, originalPrice) * 0.85);
    }
}

class Discount300Over2000Pricing implements PricingPolicy {
    @Override
    public int finalPrice(int originalPrice) {
        int price = Math.max(0, originalPrice);
        return price >= 2000 ? price - 300 : price;
    }
}

interface NotificationChannel {
    boolean send(String receiver, String message);
}

class EmailChannel implements NotificationChannel {
    @Override
    public boolean send(String receiver, String message) {
        if (receiver == null || !receiver.contains("@")) {
            return false;
        }
        System.out.println("EMAIL to " + receiver + " -> " + message);
        return true;
    }
}

class SmsChannel implements NotificationChannel {
    @Override
    public boolean send(String receiver, String message) {
        if (receiver == null || receiver.isBlank()) {
            return false;
        }
        System.out.println("SMS to " + receiver + " -> " + message);
        return true;
    }
}

class ConsoleChannel implements NotificationChannel {
    @Override
    public boolean send(String receiver, String message) {
        System.out.println("CONSOLE " + receiver + " -> " + message);
        return true;
    }
}

class CheckoutResult {
    private final String orderId;
    private final int originalPrice;
    private final int finalPrice;
    private final boolean notificationStatus;

    public CheckoutResult(String orderId, int originalPrice, int finalPrice, boolean notificationStatus) {
        this.orderId = orderId;
        this.originalPrice = originalPrice;
        this.finalPrice = finalPrice;
        this.notificationStatus = notificationStatus;
    }

    @Override
    public String toString() {
        return String.format("CheckoutResult[orderId=%s, orig=%d, final=%d, notified=%b]",
                orderId, originalPrice, finalPrice, notificationStatus);
    }
}

class FlexibleCheckoutService {
    private final PricingPolicy pricing;
    private final NotificationChannel channel;

    public FlexibleCheckoutService(PricingPolicy pricing, NotificationChannel channel) {
        this.pricing = pricing;
        this.channel = channel;
    }

    public CheckoutResult checkout(String orderId, int originalPrice, String receiver) {
        int finalPrice = pricing.finalPrice(originalPrice);
        boolean status = channel.send(receiver, "Order " + orderId + " total: " + finalPrice);
        return new CheckoutResult(orderId, originalPrice, finalPrice, status);
    }
}

public class FlexibleCheckoutSystem {
    public static void main(String[] args) {
        PricingPolicy p1 = new RegularPricing();
        PricingPolicy p2 = new Vip85Pricing();
        PricingPolicy p3 = new Discount300Over2000Pricing();

        NotificationChannel c1 = new EmailChannel();
        NotificationChannel c2 = new SmsChannel();
        NotificationChannel c3 = new ConsoleChannel();

        FlexibleCheckoutService[] services = {
            new FlexibleCheckoutService(p1, c1),
            new FlexibleCheckoutService(p1, c2),
            new FlexibleCheckoutService(p2, c1),
            new FlexibleCheckoutService(p2, c3),
            new FlexibleCheckoutService(p3, c2),
            new FlexibleCheckoutService(p3, c3)
        };

        for (int i = 0; i < services.length; i++) {
            CheckoutResult result = services[i].checkout("ORD-10" + i, 2500, "user" + i + "@example.com");
            System.out.println(result);
            System.out.println("--------------------------------------------------");
        }
    }
}