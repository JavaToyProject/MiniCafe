package orderlist.service;

import beverage.aggregate.Beverage;
import beverage.service.BeverageService;
import order.aggregate.Order;
import member.aggregate.Member;
import member.service.MemberService;
import order.service.OrderService;
import orderlist.aggregate.MemberStatus;
import orderlist.aggregate.OrderList;
import orderlist.repository.OrderListRepository;

import java.util.ArrayList;


public class OrderListService {
    private final OrderListRepository olr = new OrderListRepository();
    private final MemberService ms = new MemberService();
    private final OrderService os = new OrderService();
    private final BeverageService bs = new BeverageService();

    public OrderListService() {
    }

    public void findAllOrderList() {
        ArrayList<OrderList> allOrderList = olr.selectAllOrderList();
        ArrayList<Order> allOrder;
        for (OrderList orderList : allOrderList) {
            System.out.println(orderList.toString());
            allOrder = os.findOrderByOrdNo(orderList.getOrderListNo());
            System.out.println("=== 주문하신 음료 ===");
            for (Order order : allOrder) {
                System.out.println(order.toString());
            }
        }
    }

    public OrderList findOrderListByOrdNo(int searchOrdListNo) {
        OrderList sOrdLst = olr.selectOneOrderList(searchOrdListNo);
        if (sOrdLst != null) {
            System.out.println(sOrdLst.toString());
            return sOrdLst;
        } else {
            System.out.println("존재하지 않는 주문번호입니다.");
            return null;
        }
    }

    public int getLastOrderListNo() {
        return olr.selectLastOrderListNo();
    }

    public int registOrderList(OrderList newOrderList) {
        int newOrdLstNo = 0;
        OrderList getOrderList = olr.selectOneOrderList(newOrderList.getPhoneNo());
        Member fMem = ms.findOneMember(newOrderList.getPhoneNo());


        if (os.findOrderByOrdNo(getOrderList.getOrderListNo()) == null) {

            if (fMem != null) {
                newOrderList.setMemStatus(MemberStatus.isMember);
                newOrderList.setDiscount(useStamp(newOrderList.getOrderListNo()));
            } else
                getOrderList.setMemStatus(MemberStatus.notMember);

            int totPrice = os.getTotalPrice(newOrderList.getOrderListNo());
            if (newOrderList.isDiscount()) totPrice -= 2000;

            newOrderList.setTotalPrice(totPrice);
            newOrdLstNo = olr.selectLastOrderListNo() + 1;
            newOrderList.setOrderListNo(newOrdLstNo);

            olr.insertOrderList(newOrderList);

            System.out.println("주문번호: " + newOrderList.getOrderListNo() + ", 주문 완료!!");
        } else
            System.out.println("존재하는 주문번호 입니다.");
        return newOrdLstNo;
    }

    public void updateOrderList(OrderList changedOrdList) {
        OrderList getOrderList = olr.selectOneOrderList(changedOrdList.getPhoneNo());
        Member fMem = ms.findOneMember(changedOrdList.getPhoneNo());

        int totPrice = os.getTotalPrice(changedOrdList.getOrderListNo());
        if (changedOrdList.isDiscount()) totPrice -= 2000;

        changedOrdList.setTotalPrice(totPrice);

        olr.insertOrderList(changedOrdList);

        System.out.println("주문번호: " + changedOrdList.getOrderListNo() + ", 주문변경 완료!!");

    }

    public void removeOrderList(int removeOrdListNo) {
        if (olr.selectOneOrderList(removeOrdListNo).getMemStatus() == MemberStatus.isMember)
            resetStamp(removeOrdListNo);
        int result = olr.deleteOrderList(removeOrdListNo);
        if (result == 1)
            System.out.println("주문번호: " + removeOrdListNo + ", 취소 완료!!");
        else
            System.out.println("존재하지 않는 주문번호입니다.");
    }

    public void showBeverageList() {
        ArrayList<Beverage> bevList = bs.findAllBeverages();
        for (Beverage b : bevList)
            System.out.println(b.toString());
    }

    public ArrayList<Order> getOrders(int orderListNo) {
        return os.findOrderByOrdNo(orderListNo);
    }

    public boolean useStamp(int orderListNo) {
        Member tmpMem = ms.findOneMember(olr.selectOneOrderList(orderListNo).getPhoneNo());
        if (tmpMem.getStamps() >= 10 ){
            tmpMem.setStamps(tmpMem.getStamps() - 10);
            ms.updateMember(tmpMem);
            return true;
        }
        return false;
    }

    public void addStamp(int ordListNo, int subNum) {
        Member tmpMem = ms.findOneMember(olr.selectOneOrderList(ordListNo).getPhoneNo());
        tmpMem.setStamps(tmpMem.getStamps()+os.getTotalCount(ordListNo)+subNum);
        ms.updateMember(tmpMem);
    }

    public void resetStamp(int ordListNo) {
        Member tmpMem = ms.findOneMember(olr.selectOneOrderList(ordListNo).getPhoneNo());
        OrderList rstOrdLst = olr.selectOneOrderList(ordListNo);
        int tmpCnt = 0;
        if (rstOrdLst.isDiscount())
            tmpCnt += 10;
        tmpCnt -= os.getTotalCount(ordListNo);
        tmpMem.setStamps(tmpMem.getStamps()+tmpCnt);
        ms.updateMember(tmpMem);
    }

    public void setOrders(ArrayList<Order> newOrders) {
        os.registOrder(newOrders);
    }

}