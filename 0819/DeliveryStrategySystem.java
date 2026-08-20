interface DeliveryMethod {
    int calculateFee(int weightKg);
    String getEstimatedDays();
    String getName();
}

class HomeDelivery implements DeliveryMethod {
    @Override
    public int calculateFee(int weightKg) {
        int w = Math.max(0, weightKg);
        return 100 + w * 10;
    }

    @Override
    public String getEstimatedDays() {
        return "1-2 天";
    }

    @Override
    public String getName() {
        return "宅配";
    }
}

class StorePickup implements DeliveryMethod {
    @Override
    public int calculateFee(int weightKg) {
        return 60;
    }

    @Override
    public String getEstimatedDays() {
        return "2-3 天";
    }

    @Override
    public String getName() {
        return "超商取貨";
    }
}

class SelfPickup implements DeliveryMethod {
    @Override
    public int calculateFee(int weightKg) {
        return 0;
    }

    @Override
    public String getEstimatedDays() {
        return "當日可取";
    }

    @Override
    public String getName() {
        return "自取";
    }
}

class OrderService {
    private final DeliveryMethod deliveryMethod;

    OrderService(DeliveryMethod deliveryMethod) {
        this.deliveryMethod = deliveryMethod;
    }

    public void processOrder(String orderId, int weightKg) {
        int fee = deliveryMethod.calculateFee(weightKg);
        String days = deliveryMethod.getEstimatedDays();
        System.out.printf("訂單 %s [%s] - 運費: %d 元, 預估時間: %s%n",
                orderId, deliveryMethod.getName(), fee, days);
    }
}

public class DeliveryStrategySystem {
    public static void main(String[] args) {
        OrderService homeService = new OrderService(new HomeDelivery());
        OrderService storeService = new OrderService(new StorePickup());
        OrderService selfService = new OrderService(new SelfPickup());

        homeService.processOrder("ORD-001", 5);
        storeService.processOrder("ORD-002", 3);
        selfService.processOrder("ORD-003", 10);
    }
}