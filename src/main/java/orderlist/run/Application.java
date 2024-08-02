package orderlist.run;

import order.aggregate.Order;
import order.service.OrderService;
import orderlist.aggregate.OrderList;
import orderlist.aggregate.Payment;
import orderlist.service.OrderListService;

import java.util.ArrayList;
import java.util.Scanner;

public class Application {

    private static final OrderListService ols = new OrderListService();
    private static final OrderService os = new OrderService();

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("====== 주문 메뉴 ====== ");
            System.out.println("1. 음료 주문");
            System.out.println("2. 주문 조회");
            System.out.println("3. 주문 변경");
            System.out.println("4. 주문 취소");
            System.out.println("9. 주문 완료");
            System.out.print("주문 선택: ");

            int input = sc.nextInt();

            switch (input) {
                case 1:
                    ols.registOrderList(makeOrder());        // 음료 주문
                    break;
                case 2:
                    ols.findOrderListByOrdNo(getOrderNo());       // 주문 조회
                    break;
                case 3:
                    ols.updateOrderList(changedOrder());     // 주문 변경
                    break;
                case 4:
                    ols.removeOrderList(getOrderNo());       // 주문 취소
                    break;
                case 9:
                    System.out.println("주문 완료!!");
                    break;
                default:
                    System.out.println("잘못된 입력!!");
            }
            if (input == 9) break;
        }

    }


    private static OrderList changedOrder() {
        OrderList changeOrderList;
        ArrayList<Order> changeOrder = new ArrayList<>();
        Payment pay = null;

        int inputNo = 0;

        Scanner sc = new Scanner(System.in);

        System.out.print("변경할 주문번호 입력: ");
        int ordeListrNo = sc.nextInt();

        changeOrderList = ols.findOrderListByOrdNo(ordeListrNo);
        changeOrder = os.findOrderByOrdNo(ordeListrNo);
        int oldOrderCnt = os.getTotalCount(ordeListrNo);

        if (changeOrderList != null) {
            while (true) {
                System.out.println("====== 주문 변경 ====== ");
                System.out.println("1. 음료 추가");
                System.out.println("2. 음료 삭제");
                System.out.println("3. 결제방식");
                System.out.println("9. 주문 변경 완료");
                System.out.print("변경 할 항목 선택: ");
                inputNo = sc.nextInt();
                sc.nextLine();

                int bevCode;
                int cnt;

                switch (inputNo) {
                    case 1:
                        System.out.print("추가 할 음료코드 입력: ");
                        bevCode = sc.nextInt();

                        System.out.print("추가 할 음료 수량 입력: ");
                        cnt = sc.nextInt();

                        Order addOrder = new Order(bevCode, cnt);
                        addOrder.setOrderListNo(changeOrderList.getOrderListNo());
                        changeOrder.add(addOrder);

                        break;
                    case 2:
                        System.out.print("삭제 할 음료코드 입력: ");
                        bevCode = sc.nextInt();

                        for (int i = 0; i<changeOrder.size(); i++) {
                            if (changeOrder.get(i).getBevCode() == bevCode)
                                changeOrder.remove(i);
                        }
                        break;
                    case 3:
                        System.out.println("=== 결제 방식 ===");
                        System.out.println("1. 현금");
                        System.out.println("2. 신용카드");
                        System.out.println("3. 네이버페이");
                        System.out.println("4. 페이코");
                        System.out.println("5. 카카오페이");
                        System.out.print("변경 할 결제방식 선택: ");
                        int cp = sc.nextInt();
                        switch (cp) {
                            case 1:
                                pay = Payment.CASH;
                                break;
                            case 2:
                                pay = Payment.CARD;
                                break;
                            case 3:
                                pay = Payment.NAVERPAY;
                                break;
                            case 4:
                                pay = Payment.PAYCO;
                                break;
                            case 5:
                                pay = Payment.KAKAOPAY;
                                break;
                            default:
                                System.out.println("잘못된 입력!!!");
                        }
                        changeOrderList.setPay(pay);
                    case 9:
                        System.out.println("주문 변경 완료!!");
                        break;
                    default:
                        System.out.println("잘못된 입력!!");
                }
                if (inputNo == 9) break;
            }

        }
        os.updateOrder(changeOrder);

        int changedOrdCnt = os.getTotalCount(ordeListrNo);
        ols.updateStamp(ordeListrNo, changedOrdCnt-oldOrderCnt);
        return changeOrderList;
    }

    private static int getOrderNo() {
        Scanner sc = new Scanner(System.in);
        System.out.print("주문번호 입력: ");
        return sc.nextInt();
    }

    private static OrderList makeOrder() {

        OrderList newOrderList = null;

        int orderNo = ols.getLastOrderListNo();

        Scanner sc = new Scanner(System.in);

        ols.showBeverageList();

        ArrayList<Order> newOrders = new ArrayList<>();


        while (true) {
            System.out.print("주문할 음료코드 입력: ");
            int bevCode = sc.nextInt();

            System.out.print("주문할 음료 수량 입력: ");
            int cnt = sc.nextInt();

            newOrders.add(new Order(bevCode, cnt));

            System.out.print("음료 추가(Y): ");
            char yn = sc.next().toUpperCase().charAt(0);
            sc.nextLine();

            if (yn != 'Y') break;

        }

        System.out.print("핸드폰번호 입력('-'생략): ");
        String phone = sc.nextLine();

        System.out.println("=== 결제 방식 ===");
        System.out.println("1. 현금");
        System.out.println("2. 신용카드");
        System.out.println("3. 네이버페이");
        System.out.println("4. 페이코");
        System.out.println("5. 카카오페이");
        System.out.print("선택: ");
        int cp = sc.nextInt();
        Payment pay = null;
        switch (cp) {
            case 1:
                pay = Payment.CASH;
                break;
            case 2:
                pay = Payment.CARD;
                break;
            case 3:
                pay = Payment.NAVERPAY;
                break;
            case 4:
                pay = Payment.PAYCO;
                break;
            case 5:
                pay = Payment.KAKAOPAY;
                break;
            default:
                System.out.println("잘못된 입력!!!");
        }

        int newOrdLstNo = ols.getLastOrderListNo()+1;

        newOrderList = new OrderList(newOrdLstNo, phone, pay);

        for (int i = 0; i<newOrders.size(); i++)
            newOrders.get(i).setOrderListNo(newOrdLstNo);

        ols.setOrders(newOrders);
        ols.addStamp(newOrdLstNo, phone);

        return newOrderList;
    }

}