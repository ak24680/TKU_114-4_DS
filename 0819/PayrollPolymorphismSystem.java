abstract class Employee {
    private final String id;
    private final String name;

    Employee(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public abstract int calculatePay();
}

class MonthlyEmployee extends Employee {
    private final int monthlySalary;

    MonthlyEmployee(String id, String name, int monthlySalary) {
        super(id, name);
        this.monthlySalary = Math.max(0, monthlySalary);
    }

    @Override
    public int calculatePay() {
        return monthlySalary;
    }
}

class HourlyEmployee extends Employee {
    private final int hours;
    private final int hourlyRate;

    HourlyEmployee(String id, String name, int hours, int hourlyRate) {
        super(id, name);
        this.hours = Math.max(0, hours);
        this.hourlyRate = Math.max(0, hourlyRate);
    }

    @Override
    public int calculatePay() {
        return hours * hourlyRate;
    }
}

class SalesEmployee extends Employee {
    private final int baseSalary;
    private final int salesAmount;
    private final double commissionRate;

    SalesEmployee(String id, String name, int baseSalary, int salesAmount, double commissionRate) {
        super(id, name);
        this.baseSalary = Math.max(0, baseSalary);
        this.salesAmount = Math.max(0, salesAmount);
        this.commissionRate = Math.max(0.0, commissionRate);
    }

    @Override
    public int calculatePay() {
        return baseSalary + (int) (salesAmount * commissionRate);
    }
}

public class PayrollPolymorphismSystem {
    public static void main(String[] args) {
        Employee[] employees = {
            new MonthlyEmployee("E01", "Alice", 50000),
            new HourlyEmployee("E02", "Bob", 120, 200),
            new SalesEmployee("E03", "Charlie", 30000, 200000, 0.05),
            new MonthlyEmployee("E04", "David", 65000)
        };

        int totalPay = 0;
        Employee highestPaid = null;

        for (Employee emp : employees) {
            int pay = emp.calculatePay();
            System.out.println(emp.getId() + " " + emp.getName() + " 薪資: " + pay);
            totalPay += pay;

            if (highestPaid == null || pay > highestPaid.calculatePay()) {
                highestPaid = emp;
            }
        }

        System.out.println("------------------------");
        System.out.println("薪資總額: " + totalPay);
        if (highestPaid != null) {
            System.out.println("最高薪資員工: " + highestPaid.getName() + " (" + highestPaid.calculatePay() + ")");
        }
    }
}