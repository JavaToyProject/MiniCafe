package order.repository;

import order.aggregate.Order;
import order.stream.OrderObjectOutput;

import java.io.*;
import java.util.ArrayList;

public class OrderRepository {

    private ArrayList<Order> orders = new ArrayList<>();

    public OrderRepository() {
        File file = new File("src/main/java/order/db/order.dat");

        if (!file.exists()) {
            ArrayList<Order> defaultOrders = new ArrayList<>();
            defaultOrders.add(new Order(1, 1, 1, 2));
            defaultOrders.add(new Order(2, 1, 2, 1));
            defaultOrders.add(new Order(3, 1, 5, 1));

            defaultOrders.add(new Order(4, 2, 4, 3));
            defaultOrders.add(new Order(5, 2, 1, 1));
            defaultOrders.add(new Order(6, 2, 2, 2));

            defaultOrders.add(new Order(7, 3, 3, 1));
            defaultOrders.add(new Order(8, 3, 5, 1));
            defaultOrders.add(new Order(9, 3, 1, 1));

            saveOrders(file, defaultOrders);
        }

        loadOrders(file);
    }

    private void loadOrders(File file) {
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(
                    new BufferedInputStream(
                            new FileInputStream(file)));
            while (true) {
                orders.add((Order) ois.readObject());
            }
        } catch (EOFException e){
            System.out.println("주문목록 로딩 완료!!");
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

    private void saveOrders(File file, ArrayList<Order> orderLst) {

        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(
                    new BufferedOutputStream(
                            new FileOutputStream(file)));
            for(Order order : orderLst)
                oos.writeObject(order);
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

    public ArrayList<Order> selectAllOrder() {
        return orders;
    }

    public ArrayList<Order> selectOneOrder(int orderNo) {
        ArrayList<Order> selectedOrders = new ArrayList<>();
         for (Order order : orders) {
             if (order.getOrderNo() == orderNo)
                 selectedOrders.add(order);

         }
         return selectedOrders;
    }

    public int selectLastOrderNo() {
        Order lastBevOrder = orders.get(orders.size()-1);
        return lastBevOrder.getOrderNo();
    }

    public int insertOrder(Order newOrder) {
        OrderObjectOutput ooo = null;
        int result = 0;
        try {
            ooo = new OrderObjectOutput(
                    new BufferedOutputStream(
                            new FileOutputStream(
                                    "src/main/java/order/db/order.dat",
                                    true)));

            ooo.writeObject(newOrder);
            orders.add(newOrder);

            result = 1;
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (ooo != null) ooo.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return result;
    }

    public int deleteOrder(int removeOrderNo) {
        int result = 0;
        for (int i = 0; i < orders.size(); i++) {
            if (orders.get(i).getOrderNo() == removeOrderNo) {
                orders.remove(orders.get(i));
                result = 1;
            }
        }
        File file = new File("src/main/java/order/db/order.dat");
        saveOrders(file, orders);

        return result;
    }

}
