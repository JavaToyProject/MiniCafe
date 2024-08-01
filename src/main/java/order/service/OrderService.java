package order.service;

import beverage.service.BeverageService;
import order.aggregate.Order;
import order.repository.OrderRepository;

import java.util.ArrayList;

public class OrderService {
    private final OrderRepository or = new OrderRepository();
    private final BeverageService bs = new BeverageService();

    public void findAllOrder() {
        ArrayList<Order> allOrder = or.selectAllOrder();
        for (Order order : allOrder) {
            System.out.println(order.toString());
        }
    }

    public ArrayList<Order> findOrderByOrdNo(int orderNo) {
        ArrayList<Order> selectdOrder = or.selectOneOrder(orderNo);
        if (selectdOrder != null) {
            System.out.println(selectdOrder);
            return selectdOrder;
        } else {
            System.out.println("존재하지 않는 주문번호입니다.");
            return null;
        }
    }

    public void registOrder(ArrayList<Order> newOrder) {
        for (Order order : newOrder) {
            order.setOrderNo(or.selectLastOrderNo() + 1);
            or.insertOrder(order);
        }
    }

    public void updateOrder(ArrayList<Order> changedOrder) {
        or.deleteOrder(changedOrder.get(0).getOrderNo());
        registOrder(changedOrder);
    }


    public void removeOrder(int removeOrderNo) {
        int result = or.deleteOrder(removeOrderNo);
        if (result == 1)
            System.out.println("주문번호: " + removeOrderNo + ", 취소 완료!!");
        else
            System.out.println("존재하지 않는 주문번호입니다.");
    }

    public int getTotalPrice(int orderNo) {
        ArrayList<Order> getOrders = or.selectOneOrder(orderNo);
        int totPrice = 0;
        for (Order order : getOrders) {
            totPrice += bs.findBeverageByBevNo(order.getBevCode()).getPrice();
        }
        return totPrice;
    }

    public int getTotalCount(int orderNo) {
        ArrayList<Order> getOrders = or.selectOneOrder(orderNo);
        int totCnt = 0;
        for (Order order : getOrders)
            totCnt += order.getCount();
        return totCnt;
    }
}