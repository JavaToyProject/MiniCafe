package order.aggregate;

import beverage.aggregate.Beverage;

import java.io.Serializable;

public class Order implements Serializable {
    private int orderNo;            // 주문번호
    private Beverage orderBev;      // 주문 음료
    private int quantity;           // 주문수량
    private int totalPrice;         // 주문 전체 금액 (주문음료 금액 * 주문수량)
    private OrderStatus status;     // 주문상태 (CANCEL, COMPLETE, INCOMPLETE)
//    private Member orderMem;        // 주문회원

    public Order() {
    }

    public Order(int orderNo, Beverage orderBev, int quantity, int totalPrice, OrderStatus status) {
        this.orderNo = orderNo;
        this.orderBev = orderBev;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.status = status;
    }
//    public Order(int orderNo, Beverage orderBev, int quantity, int totalPrice, OrderStatus status, Member orderMem) {
//        this.orderNo = orderNo;
//        this.orderBev = orderBev;
//        this.quantity = quantity;
//        this.totalPrice = totalPrice;
//        this.status = status;
//        this.orderMem = orderMem;
//    }

    public int getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(int orderNo) {
        this.orderNo = orderNo;
    }

    public Beverage getOrderBev() {
        return orderBev;
    }

    public void setOrderBev(Beverage orderBev) {
        this.orderBev = orderBev;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

//    public Member getOrderMem() {
//        return orderMem;
//    }
//
//    public void setOrderMem(Member orderMem) {
//        this.orderMem = orderMem;
//    }

    @Override
    public String toString() {
        return "Order{" +
                "orderNo=" + orderNo +
                ", orderBev=" + orderBev +
                ", quantity=" + quantity +
                ", totalPrice=" + totalPrice +
                ", status=" + status +
                '}';
    }

//    @Override
//    public String toString() {
//        return "Order{" +
//                "orderNo=" + orderNo +
//                ", orderBev=" + orderBev +
//                ", quantity=" + quantity +
//                ", totalPrice=" + totalPrice +
//                ", status=" + status +
//                ", orderMem=" + orderMem +
//                '}';
//    }
}
