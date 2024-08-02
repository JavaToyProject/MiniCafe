package orderlist.repository;

import order.stream.OrderObjectOutput;
import orderlist.aggregate.MemberStatus;
import orderlist.aggregate.OrderList;
import orderlist.aggregate.Payment;

import java.io.*;
import java.util.ArrayList;

public class OrderListRepository {

    private ArrayList<OrderList> orderLists = new ArrayList<>();

    public OrderListRepository() {
        File file = new File("src/main/java/orderlist/db/orderlistDB.dat");

//        if (!file.exists()) {
//            ArrayList<OrderList> defaultOrderList = new ArrayList<>();
//            defaultOrderList.add(new OrderList(1, "01012345678", MemberStatus.isMember, true, 13000, Payment.CASH));
//            defaultOrderList.add(new OrderList(2, "01098765432", MemberStatus.notMember, false, 9400, Payment.NAVERPAY));
//            defaultOrderList.add(new OrderList(3, "01056325478", MemberStatus.isMember, true, 31800, Payment.CARD));
//
//            saveOrderList(file, defaultOrderList);
//        }

        loadOrderList(file);
    }

    private void loadOrderList(File file) {
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(
                    new BufferedInputStream(
                            new FileInputStream(file)));
            while (true) {
                orderLists.add((OrderList) ois.readObject());
            }
        } catch (EOFException e){
            System.out.println("주문리스트목록 로딩 완료!!");
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

    private void saveOrderList(File file, ArrayList<OrderList> orderList) {

        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(
                    new BufferedOutputStream(
                            new FileOutputStream(file)));
            for(OrderList order : orderList)
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

    public ArrayList<OrderList> selectAllOrderList() {
        return orderLists;
    }

    public OrderList selectOneOrderList(String phoneNo) {
         for (OrderList order : orderLists) {
             if (phoneNo.compareTo(order.getPhoneNo())==1)
                 return order;
         }
         return null;
    }

    public OrderList selectOneOrderList(int orderListNo) {
        for (OrderList order : orderLists) {
            if (orderListNo == order.getOrderListNo())
                return order;
        }
        return null;
    }

    public int selectLastOrderListNo() {
        OrderList lastOrderList = orderLists.get(orderLists.size()-1);
        return lastOrderList.getOrderListNo();
    }

    public int insertOrderList(OrderList newOrderList) {
        OrderObjectOutput ooo = null;
        int result = 0;
        try {
            ooo = new OrderObjectOutput(
                    new BufferedOutputStream(
                            new FileOutputStream(
                                    "src/main/java/orderlist/db/orderlistDB.dat",
                                    true)));
            ooo.writeObject(newOrderList);
            orderLists.add(newOrderList);
            for (OrderList ol : orderLists)
                System.out.println(ol.toString());
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

    public int updateOrderList(OrderList orderList) {
        for (int i = 0; i < orderLists.size(); i++) {
            OrderList beforeOrderList = orderLists.get(i);
            if (orderList.getOrderListNo() == beforeOrderList.getOrderListNo()) {
                System.out.println("====== 변경 후 주문정보 ======");
                System.out.println(orderList.toString());
                System.out.println();

                File file = new File("src/main/java/orderlist/db/orderlistDB.dat");
                orderLists.set(i, orderList);
                saveOrderList(file, orderLists);

                return 1;
            }
        }
        return 0;
    }

    public int deleteOrderList(int removeOrderListNo) {
        for (int i = 0; i < orderLists.size(); i++) {
            if (orderLists.get(i).getOrderListNo() == removeOrderListNo) {
                orderLists.remove(i);

                File file = new File("src/main/java/orderlist/db/orderlistDB.dat");
                saveOrderList(file, orderLists);
                return 1;
            }
        }
        return 0;
    }

}
