import beverage.aggregate.Beverage;

import java.util.Scanner;

import static beverage.run.BeverageController.displayMainMenu;
import static beverage.run.BeverageController.handleMainMenu;
import static member.run.Application.displayMemberMenu;

public class Application {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n◤――― 카페 관리 ―――◥");
            System.out.println("| 1. 음료 관리          |");
            System.out.println("| 2. 회원 관리          |");
            System.out.println("| 3. 주문 관리          |");
            System.out.println("| 0. 프로그램 종료       |");
            System.out.println("◣―――――――――――――◢");
            System.out.print("원하는 메뉴를 선택해주세요: ");

            int input = sc.nextInt();
            System.out.println("―――――――――――――");
            System.out.println();

            switch (input) {
                case 1:
                    BeverageApplication();
                    break;
                case 2:
                    MemberApplication();
                case 3:
                    OrderApplication();
                case 0:                     // 프로그램 종료
                    System.out.println("음료 관리 프로그램을 종료합니다.");
                    return;
                default:
                    System.out.println("번호를 잘못 입력하셨습니다.");
            }
        }

    }

    private static void BeverageApplication() {
        while (true) {
            int input = displayMainMenu();
            if (handleMainMenu(input)) return;
        }
    }

    private static void MemberApplication() {
        displayMemberMenu();
    }

    private static void OrderApplication() {
    }

}
