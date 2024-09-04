import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class Main {

    // ConcurrentHashMap for inventory management
    private static ConcurrentMap<String, Integer> inventory = new ConcurrentHashMap<>();

    // CopyOnWriteArrayList for managing online users
    private static List<String> onlineUsers = new CopyOnWriteArrayList<>();

    public static void main(String[] args) {

        // Initialize inventory with some items
        inventory.put("Apple", 50);
        inventory.put("Banana", 30);
        inventory.put("Orange", 20);

        // Create threads to simulate users updating inventory
        Thread thread1 = new Thread(() -> updateInventory("Apple", 5));
        Thread thread2 = new Thread(() -> updateInventory("Banana", -10));
        Thread thread3 = new Thread(() -> updateInventory("Orange", 15));

        // Create threads to simulate users coming online and going offline
        Thread thread4 = new Thread(() -> userLogin("User1"));
        Thread thread5 = new Thread(() -> userLogin("User2"));
        Thread thread6 = new Thread(() -> userLogout("User1"));

        // Start all threads
        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();
        thread5.start();
        thread6.start();

        // Wait for all threads to finish
        try {
            thread1.join();
            thread2.join();
            thread3.join();
            thread4.join();
            thread5.join();
            thread6.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Print final inventory and online users
        System.out.println("Final Inventory: " + inventory);
        System.out.println("Online Users: " + onlineUsers);
    }

    // Method to update inventory
    private static void updateInventory(String item, int quantity) {
        inventory.merge(item, quantity, Integer::sum);
        System.out.println("Updated Inventory: " + inventory);
    }

    // Method for user login
    private static void userLogin(String user) {
        onlineUsers.add(user);
        System.out.println(user + " logged in. Online Users: " + onlineUsers);
    }

    // Method for user logout
    private static void userLogout(String user) {
        onlineUsers.remove(user);
        System.out.println(user + " logged out. Online Users: " + onlineUsers);
    }
}
