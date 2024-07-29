package order_v1.service;

import beverage.aggregate.Beverage;
import order_v1.aggregate.Order;
import order_v1.aggregate.OrderStatus;
import order_v1.repository.OrderRepository;

import java.util.ArrayList;

public class OrderService {

    private final OrderRepository or = new OrderRepository();

    public OrderService() {
    }

    public Order registOrder(Order newOrder) {
        int lastOrdNo = or.selectLastOrderNo();
        newOrder.setOrderNo(lastOrdNo + 1);
        int result = or.insertOrder(newOrder);

        if (result == 1) {
            return newOrder;
        } else {
            return null;
        }
    }

    public ArrayList<Order> findAllOrders() {
        ArrayList<Order> findOrders = or.selectAllOrders();
        return findOrders;
    }

    public Order findOrderByOrdNo(Integer searchOrdNo) {
        Order selectOrder = or.selectOrderBy(searchOrdNo);
        return selectOrder;
    }

    public int removeOrder(int removeOrdNo) {
        int result = or.deleteOrder(removeOrdNo);
        return result;
    }

    public int completeOrder(int completeOrdNo) {
        // INCOMPLETE 상태의 주문인지 확인
        Order order = findOrderByOrdNo(completeOrdNo);
        if (order.getStatus() == OrderStatus.INCOMPLETE) {
            // INCOMPLETE라면 COMPLETE로 변경
            int result = or.modifyOrder(completeOrdNo);
            return result;
        } else {
            return -1; // CANCLE, COMPLETE 상태인 경우
        }
    }

    public int cancelOrder(int cancelOrdNo) {
        // INCOMPLETE라 상태의 주문인지 확인
        Order order = findOrderByOrdNo(cancelOrdNo);
        if (order.getStatus() == OrderStatus.INCOMPLETE) {
            // INCOMPLETE라면 CANCEL 변경
            int result = or.cancelOrder(cancelOrdNo);
            return result;
        } else {
            return -1; // CANCLE, COMPLETE 상태인 경우
        }
    }

    public ArrayList<Order> findIncompleteOrders(OrderStatus findStatus) {
        ArrayList<Order> findOrders = or.selectStatusOrders(findStatus);
        return findOrders;
    }
}
