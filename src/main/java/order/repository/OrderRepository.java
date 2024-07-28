package order.repository;

import order.aggregate.Order;

import java.io.*;
import java.util.ArrayList;

public class OrderRepository {

    private ArrayList<Order> orderArrayList = new ArrayList<>();

    File file = new File("src/main/java/order/orderDB.dat");
    public OrderRepository() {
        File file = new File("src/main/java/com/cafe/db/cafeDB.dat");
        loadOrder(file);
    }

    private void loadOrder(File file) {
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(
                    new BufferedInputStream(
                            new FileInputStream(file)));
            while (true) {
                orderArrayList.add((Order) ois.readObject());
            }
        } catch (EOFException e) {
            System.out.println("주문 정보 모두 로딩됨...");
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (ois != null) ois.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void saveOrder(File file, ArrayList<Order> orders) {
        ObjectOutputStream oos = null;

        try {
            oos = new ObjectOutputStream(
                    new BufferedOutputStream(
                            new FileOutputStream(file)));

            for (Order order : orders) {
                oos.writeObject(order);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (oos != null) oos.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }


}
