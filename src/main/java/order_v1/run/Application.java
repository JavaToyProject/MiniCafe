package order_v1.run;

import beverage.aggregate.Beverage;
import beverage.service.BeverageService;
import member.aggregate.Member;
import member.service.MemberService;
import order_v1.aggregate.Order;
import order_v1.aggregate.OrderStatus;
import order_v1.service.OrderService;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

public class Application {

    private static final OrderService os = new OrderService();
    private static final BeverageService bs = new BeverageService();
    private static final MemberService ms = new MemberService();

    public static void main(String[] args) {
        while (true) {
            int input = displayOrderMenu();
            if (handleOrderMenu(input)) return;
        }
    }

    private static boolean handleOrderMenu(int input) {
        switch (input) {
            case 1:
                getAllOrders();
                break;
            case 2:
                registOrder();
                break;
            case 3:
                searchOrder();
                break;
            case 4:
                updateOrder();
                break;
            case 5:
                cancelOrder();
                break;
            case 6:
                deleteOrder();
                break;
            case 7:
                getIncompleteOrders();
                break;
            case 8:
                getCompleteOrders();
                break;
            case 0:
                System.out.println("음료 관리 프로그램을 종료합니다.");
                return true;
            default: System.out.println("번호를 잘못 입력하셨습니다.");
        }
        return false;
    }

    private static int displayOrderMenu() {
        Scanner sc = new Scanner(System.in);

        System.out.println("\n◤――― 주문 관리 ―――◥");
        System.out.println("| 1. 주문 전체 조회    |");
        System.out.println("| 2. 주문 등록        |");
        System.out.println("| 3. 주문 검색        |");
        System.out.println("| 4. 주문 완료하기     |");
        System.out.println("| 5. 주문 취소하기     |");
        System.out.println("| 6. 주문 삭제        |");
        System.out.println("| 7. 미완료 주문 조회  |");
        System.out.println("| 8. 완료 주문 조회    |");
        System.out.println("| 0. 프로그램 종료     |");
        System.out.println("◣――――――――――――◢");
        System.out.print("원하는 메뉴를 선택해주세요: ");

        int input = sc.nextInt();
        System.out.println("―――――――――――――");
        System.out.println();
        return input;
    }

    private static void getAllOrders() {
        ArrayList<Order> findOrders = os.findAllOrders();

        System.out.println("[모든 음료 정보]");
        for (Order orders : findOrders) {
            System.out.print("[" + orders.getOrderNo() + "번 주문 정보] " );
            printOrder(orders);
        }
    }

    private static void registOrder() {
        Order newOrder = os.registOrder(registOrd());

        if (newOrder != null) {
            System.out.println("\n주문에 성공했습니다");
            System.out.print("[주문 정보] ");
            printOrder(newOrder);
        } else {
            System.out.println("음료 등록을 실패했습니다.");
        }
    }

    private static void searchOrder() {
        Order selectOrder = os.findOrderByOrdNo(chooseOrdNo());

        if (selectOrder != null) {
            System.out.print("\n[조회한 주문 정보] ");
            printOrder(selectOrder);
        } else {
            System.out.println("해당하는 주문이 존재하지 않습니다.");
        }

    }

    private static void updateOrder() {
        int result = os.completeOrder(getCompleteOrdNo());

        if (result == 1) {
            System.out.println("주문이 처리되었습니다.");
        } else if (result == 0) {
            System.out.println("주문 처리에 실패했습니다.");
        } else {
            System.out.println("이미 완료되었거나 취소된 주문입니다.");
        }
    }

    private static void cancelOrder() {
        int result = os.cancelOrder(getCompleteOrdNo());

        if (result == 1) {
            System.out.println("주문이 취소되었습니다.");
        } else if (result == 0) {
            System.out.println("주문 취소에 실패했습니다.");
        } else {
            System.out.println("이미 취소되었거나 완료된 주문입니다.");
        }
    }

    private static void deleteOrder() {
        int result = os.removeOrder(chooseRemoveOrdNo());

        if (result == 1) {
            System.out.println("주문 삭제가 완료되었습니다.");
        } else {
            System.out.println("주문 삭제에 실패했습니다.");
        }
    }

    private static void getIncompleteOrders() {
        ArrayList<Order> incompleteOrders = os.findIncompleteOrders(OrderStatus.INCOMPLETE);

        System.out.println("[미완료 음료 정보]");
        for (Order orders : incompleteOrders) {
            System.out.print("[" + orders.getOrderNo() + "번 주문 정보] " );
            printOrder(orders);
        }
    }

    private static void getCompleteOrders() {
        ArrayList<Order> incompleteOrders = os.findIncompleteOrders(OrderStatus.COMPLETE);

        System.out.println("[완료 음료 정보]");
        for (Order orders : incompleteOrders) {
            System.out.print("[" + orders.getOrderNo() + "번 주문 정보] " );
            printOrder(orders);
        }
    }



    private static Order registOrd() {
        Order order = new Order();

        Scanner sc = new Scanner(System.in);
        System.out.println("주문 내용을 입력해주세요 ");
        System.out.print("주문할 음료 번호를 입력해주세요: "); // 음료 번호 또는 음료명
        int bevNo = sc.nextInt();

        System.out.print("주문할 음료 개수를 입력해주세요: ");
        int quantity = sc.nextInt();

        sc.nextLine(); // 버퍼 clear

        System.out.print("회원이십니까? (Y/n): ");
        String isMember = sc.nextLine().toUpperCase(Locale.ROOT);

        Member orderMember = new Member();


        if (isMember.equals("Y")) {
            System.out.print("회원번호를 입력해 주세요: ");
//            orderMember = ms.findOneMember(sc.nextLine()); // Member 리턴하도록 메소드 변경 필요
        } else if (isMember.equals("N")) {
            orderMember.setMemNo(0);
        } else {
            System.out.println("잘못된 응답입니다. 비회원으로 주문됩니다.");
            orderMember.setMemNo(0);
        }

        Beverage orderBeverage = bs.findBeverageByBevNo(bevNo);
        order.setOrderBev(orderBeverage);
        order.setQuantity(quantity);
        order.setTotalPrice(orderBeverage.getPrice() * quantity);
        order.setStatus(OrderStatus.INCOMPLETE);
        order.setOrderMem(orderMember);

        System.out.println(order);
        return order;
    }

    private static int getCompleteOrdNo() {
        Scanner sc = new Scanner(System.in);
        System.out.print("처리완료한 주문번호를 입력해주세요: ");
        Integer bevNo = sc.nextInt();
        System.out.println("――――――――――――――");
        return bevNo;
    }

    private static int chooseOrdNo() {
        Scanner sc = new Scanner(System.in);
        System.out.print("검색할 주문번호를 입력해주세요: ");
        int bevNo = sc.nextInt();
        System.out.println("――――――――――――――");
        return bevNo;
    }

    private static int chooseRemoveOrdNo () {
        Scanner sc = new Scanner(System.in);
        System.out.print("삭제할 주문번호를 입력해주세요: ");
        int bevNo = sc.nextInt();
        System.out.println("――――――――――――――");
        return bevNo;
    }

    private static void printOrder(Order newOrder) {
        System.out.print("주문번호: " + newOrder.getOrderNo() + " / 주문음료: " + newOrder.getOrderBev().getName()
                + " / 주문수량: " + newOrder.getQuantity() + "잔 / 주문금액: " + newOrder.getTotalPrice()
                + "원 / 주문상태: " + newOrder.getStatus());
        if (newOrder.getOrderMem().getPhone() != null){
            System.out.print(" / 회원 전화번호: " + newOrder.getOrderMem().getPhone() + "\n");
        } else {
            System.out.print(" / 비회원\n");
        }

    }
}
