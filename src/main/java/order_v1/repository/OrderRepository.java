package order_v1.repository;

import beverage.stream.MyObjectOutput;
import order_v1.aggregate.Order;
import order_v1.aggregate.OrderStatus;

import java.io.*;
import java.util.ArrayList;

public class OrderRepository {

    private ArrayList<Order> orderArrayList = new ArrayList<>();

    public OrderRepository() {
        File file = new File("src/main/java/order_v1/db/orderDB.dat");

        if (!file.exists()) {
            ArrayList<Order> defaultOrder = new ArrayList<>();
            saveOrder(file, defaultOrder);
        }

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


    public int selectLastOrderNo() {

        if (orderArrayList.size() == 0) {
            return 0;
        } else {
            Order lastOrder = orderArrayList.get(orderArrayList.size()-1);
            return lastOrder.getOrderNo();
        }
    }

    public int insertOrder(Order newOrder) {
        int result = 0;
        File file = new File("src/main/java/order_v1/db/orderDB.dat");

        MyObjectOutput moo = null;

        try {
            moo = new MyObjectOutput(
                    new BufferedOutputStream(
                            new FileOutputStream(file, true)));

            moo.writeObject(newOrder);
            orderArrayList.add(newOrder);
            result = 1;

        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (moo != null) moo.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        return result;
    }

    public ArrayList<Order> selectAllOrders() {
        return orderArrayList;
    }


    public Order selectOrderBy(Integer searchOrdNo) {
        for (Order order: orderArrayList) {
            if (searchOrdNo == order.getOrderNo()) {
                return order;
            }
        }
        return null;
    }

    public int deleteOrder(int removeOrdNo) {
        for (int i = 0; i<orderArrayList.size(); i++) {
            if (removeOrdNo == orderArrayList.get(i).getOrderNo()) {
                orderArrayList.remove(i);

                File file = new File("src/main/java/order_v1/db/orderDB.dat");
                saveOrder(file, orderArrayList);
                return 1;
            }
        }
        return 0;
    }

    public int modifyOrder(int completeOrdNo) {
        for (Order order : orderArrayList) {
            if (completeOrdNo == order.getOrderNo()) {
                order.setStatus(OrderStatus.COMPLETE);

                File file = new File("src/main/java/order_v1/db/orderDB.dat");
                saveOrder(file, orderArrayList);
                return 1;
            }
        }
        return 0;
    }

    public int cancelOrder(int cancelOrdNo) {
        for (Order order : orderArrayList) {
            if (cancelOrdNo == order.getOrderNo()) {
                order.setStatus(OrderStatus.CANCEL);

                File file = new File("src/main/java/order_v1/db/orderDB.dat");
                saveOrder(file, orderArrayList);
                return 1;
            }
        }
        return 0;
    }

    public ArrayList<Order> selectStatusOrders(OrderStatus status) {
        ArrayList<Order> incompleteOrders = new ArrayList<>();

        for (Order order : orderArrayList) {
            if (order.getStatus() == status) {
                incompleteOrders.add(order);
            }
        }
        return incompleteOrders;
    }
}
