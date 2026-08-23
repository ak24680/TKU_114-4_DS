import java.util.ArrayList;
import java.util.List;

class Repository<T> {
    private final List<T> items = new ArrayList<>();

    public void add(T item) {
        if (item != null) {
            items.add(item);
        }
    }

    public T get(int index) {
        if (index >= 0 && index < items.size()) {
            return items.get(index);
        }
        return null;
    }

    public boolean remove(T item) {
        return items.remove(item);
    }

    public int size() {
        return items.size();
    }

    public void printAll() {
        System.out.println("Repository Contents (size=" + size() + "):");
        for (int i = 0; i < items.size(); i++) {
            System.out.println("  [" + i + "] " + items.get(i));
        }
    }
}

class Product {
    private final String id;
    private final String name;
    private final int price;

    public Product(String id, String name, int price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    @Override
    public String toString() {
        return "Product{id='" + id + "', name='" + name + "', price=" + price + "}";
    }
}

public class GenericRepositorySystem {
    public static void main(String[] args) {
        System.out.println("--- Testing Repository<String> ---");
        Repository<String> stringRepo = new Repository<>();
        stringRepo.add("Java");
        stringRepo.add("Python");
        stringRepo.add("C++");
        stringRepo.printAll();

        System.out.println("Get index 1: " + stringRepo.get(1));
        stringRepo.remove("Python");
        stringRepo.printAll();

        System.out.println("\n--- Testing Repository<Product> ---");
        Repository<Product> productRepo = new Repository<>();
        Product p1 = new Product("P01", "Laptop", 35000);
        Product p2 = new Product("P02", "Mouse", 800);
        
        productRepo.add(p1);
        productRepo.add(p2);
        productRepo.printAll();

        productRepo.remove(p1);
        productRepo.printAll();
    }
}