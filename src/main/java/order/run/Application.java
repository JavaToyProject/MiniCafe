package order.run;

import beverage.aggregate.Beverage;
import beverage.service.BeverageService;
import order.aggregate.Order;
import order.service.OrderService;

import java.util.Locale;
import java.util.Scanner;

public class Application {

    private static final OrderService os = new OrderService();
    private static final BeverageService bs = new BeverageService();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("\n◤――― 주문 관리 ―――◥");
            System.out.println("| 1. 주문 전체 조회    |");
            System.out.println("| 2. 주문 검색        |");
            System.out.println("| 3. 주문 목록 검색    |");
            System.out.println("| 4. 주문 등록        |");
            System.out.println("| 5. 주문 정보 수정    |");
            System.out.println("| 6. 주문 삭제        |");
            System.out.println("| 0. 프로그램 종료     |");
            System.out.println("◣――――――――――――◢");
            System.out.print("원하는 메뉴를 선택해주세요: ");

            int input = sc.nextInt();
            System.out.println("―――――――――――――");
            System.out.println();

            switch (input) {
                case 1: break;
                case 2: break;
                case 3: break;
                case 4:
                    os.registOrder(registOrd());
                    break;
                case 5: break;
                case 6: break;
                case 0: break;
                default: System.out.println("번호를 잘못 입력하셨습니다.");
            }
        }
    }

    private static Object registOrd() {
        Order order = new Order();

        Scanner sc = new Scanner(System.in);
        System.out.println("주문 내용을 입력해주세요 ");
        System.out.print("주문할 음료 번호를 입력해주세요: "); // 음료 번호 또는 음료명
        int bevNo = sc.nextInt();

        System.out.print("주문할 음료 개수를 입력해주세요: ");
        int quantity = sc.nextInt();

        System.out.println("회원이십니까? (Y/n)");
        String isMember = sc.nextLine().toUpperCase(Locale.ROOT);
        int memNo = 0;
        if (isMember == "Y") {
            System.out.print("회원번호를 입력해 주세요: ");
            memNo = sc.nextInt();
        } else if (isMember == "N") {

        } else {
            System.out.println("잘못된 응답입니다. 주문이 종료됩니다.");
        }
    }
}
