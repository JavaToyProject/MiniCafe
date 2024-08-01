package orderlist.aggregate;

import java.io.Serializable;

public class OrderList implements Serializable {
    private int orderListNo;
    private String phoneNo;
    private MemberStatus memStatus;
    private boolean discount;
    private int totalPrice;
    private Payment pay;

    public OrderList() {
    }

    public OrderList(int orderListNo, String phoneNo, Payment pay) {
        this.orderListNo = orderListNo;
        this.phoneNo = phoneNo;
        this.pay = pay;
    }

    public OrderList(int orderListNo, String phoneNo, MemberStatus memStatus, boolean discount, int totalPrice, Payment pay) {
        this.orderListNo = orderListNo;
        this.phoneNo = phoneNo;
        this.memStatus = memStatus;
        this.discount = discount;
        this.totalPrice = totalPrice;
        this.pay = pay;
    }

    public int getOrderListNo() {
        return orderListNo;
    }

    public void setOrderListNo(int orderListNo) {
        this.orderListNo = orderListNo;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public MemberStatus getMemStatus() {
        return memStatus;
    }

    public void setMemStatus(MemberStatus memStatus) {
        this.memStatus = memStatus;
    }

    public boolean isDiscount() {
        return discount;
    }

    public void setDiscount(boolean discount) {
        this.discount = discount;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Payment getPay() {
        return pay;
    }

    public void setPay(Payment pay) {
        this.pay = pay;
    }

    @Override
    public String toString() {
        return "OrderList{" +
                "orderListNo=" + orderListNo +
                ", phoneNo='" + phoneNo + '\'' +
                ", memStatus=" + memStatus +
                ", discount=" + discount +
                ", totalPrice=" + totalPrice +
                ", pay=" + pay +
                '}';
    }
}
