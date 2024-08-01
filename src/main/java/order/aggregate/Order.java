package order.aggregate;

public class Order {
    private int orderNo;
    private int orderListNo;
    private int bevCode;
    private int count;

    public Order() {
    }

    public Order(int bevCode, int count) {
        this.bevCode = bevCode;
        this.count = count;
    }

    public Order(int orderNo, int orderListNo, int bevCode, int count) {
        this.orderNo = orderNo;
        this.orderListNo = orderListNo;
        this.bevCode = bevCode;
        this.count = count;
    }

    public int getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(int orderNo) {
        this.orderNo = orderNo;
    }

    public int getOrderListNo() {
        return orderListNo;
    }

    public void setOrderListNo(int orderListNo) {
        this.orderListNo = orderListNo;
    }

    public int getBevCode() {
        return bevCode;
    }

    public void setBevCode(int bevCode) {
        this.bevCode = bevCode;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderNo=" + orderNo +
                ", orderListNo=" + orderListNo +
                ", bevCode=" + bevCode +
                ", count=" + count +
                '}';
    }
}
