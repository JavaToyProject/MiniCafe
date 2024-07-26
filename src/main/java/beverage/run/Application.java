package beverage.run;

import beverage.aggregate.Beverage;
import beverage.aggregate.BeverageCategory;
import beverage.service.BeverageService;

import java.util.Scanner;

public class Application {
    // 임시
    private static final BeverageService bs = new BeverageService();

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("\n◤――― 음료 관리 ―――◥");
            System.out.println("| 1. 음료 전체 조회    |");
            System.out.println("| 2. 음료 검색        |");
            System.out.println("| 3. 음료 목록 검색    |"); // 특정 기준에 따라 음료 조회
            System.out.println("| 4. 음료 등록        |");
            System.out.println("| 5. 음료 정보 수정    |");
            System.out.println("| 6. 음료 삭제        |");
            System.out.println("| 0. 프로그램 종료     |");
            System.out.println("◣――――――――――――◢");
            System.out.print("원하는 메뉴를 선택해주세요: ");

            int input = sc.nextInt();
            System.out.println("―――――――――――――");

            System.out.println();

            switch (input) {
                case 1:
                    bs.findAllBeverages();
                    break;
                case 2:
                    bs.findBeverageBy(chooseBevField());
                    break;
                case 3:
                    bs.findBeveragListBy(chooseBevFilter());
                    break;
                case 4:
                    bs.registBeverage(regist());
                    break;
                case 5:
                    bs.modifyBeverage(reform());
                    break;
                case 6:
                    bs.removeBeverage(chooseRemoveBevNo());
                    break;
                case 0:
                    System.out.println("음료 관리 프로그램을 종료합니다.");
                    return;
                default:
                    System.out.println("번호를 잘못 입력하셨습니다.");
            }
        }
    }

    private static Object chooseBevField() {
        // 가격, 칼로리, 카테고리는 중복된 값이 있을 수 있기 때문에 단일 검색에서 제외
        Scanner sc = new Scanner(System.in);
        System.out.println("◤――― 음료 검색 ―――◥");
        System.out.println("| 1. 음료 번호 조회    |");
        System.out.println("| 2. 음료명 조회       |");
        System.out.println("◣――――――――――――◢");
        System.out.print("검색할 기준을 입력해주세요: ");

        int searchType = sc.nextInt();
        Object result = null;

        switch (searchType) {
            case 1: // 음료 번호로 조회
                System.out.print("검색할 음료번호를 입력해주세요: ");
                int bevNo = sc.nextInt();
                result = bevNo;
                break;
            case 2: // 음료명으로 조회
                sc.nextLine();
                System.out.print("검색할 음료명을 입력해주세요: ");
                String bevName = sc.nextLine();
                result = bevName;
                break;
            default:
                System.out.println("잘못된 번호를 입력하셨습니다.");
        }
        System.out.println("――――――――――――――");
        return result;
    }

    private static int[] chooseBevFilter() {
        Scanner sc = new Scanner(System.in);
        System.out.println("◤――――― 음료 목록 검색 ―――――◥");
//        System.out.println("1. 가격이 낮은 순으로 정렬");
//        System.out.println("2. 가격이 높은 순으로 정렬");
        System.out.println("| 1. 특정 카테고리 음료만 조회      |");
        System.out.println("| 2. 특정 금액 이상의 음료만 조회    |");
        System.out.println("| 3. 특정 금액 이하의 음료만 조회    |");
        System.out.println("| 0. 메인 화면으로 돌아가기         |");
        System.out.println("◣―――――――――――――――――――◢");
        System.out.print("검색할 기준을 입력해주세요: ");

        int input = sc.nextInt();
        int[] filterArr = {0, 0}; // 필터의 종류, 추가 정보를 저장 -> 배열 리턴

        switch (input) {
            case 1:
                System.out.print("검색할 카테고리를 입력해주세요(1: Coffee, 2: Latte, 3: Blended, 4: Tea) : ");
                int category = sc.nextInt();
                filterArr[0] = 1;
                filterArr[1] = category;
                break;
            case 2:
                System.out.print("검색할 금액을 입력해주세요: ");
                int upperPrice = sc.nextInt();
                filterArr[0] = 2;
                filterArr[1] = upperPrice;
                break;
            case 3:
                System.out.print("검색할 금액을 입력해주세요: ");
                int lowerPrice = sc.nextInt();
                filterArr[0] = 3;
                filterArr[1] = lowerPrice;
                break;
            case 0:
                return filterArr;
            default:
                System.out.println("잘못된 번호를 입력하셨습니다.");
        }
        System.out.println("―――――――――――――――――");

        return filterArr;
    }

    private static Beverage reform() {
        Beverage reformBev = new Beverage();

        Scanner sc = new Scanner(System.in);

        System.out.print("수정할 음료의 번호를 입력해주세요: ");
        reformBev.setBevNo(sc.nextInt());

        while (true) {
            System.out.println("\n◤――――― 음료 정보 수정 ―――――◥");
            System.out.println("| 1. 음료 이름                   |");
            System.out.println("| 2. 음료 가격                   |");
            System.out.println("| 3. 음료 칼로리                 |");
            System.out.println("| 4. 음료 카테고리                |");
            System.out.println("| 5. 음료 재고                   |");
            System.out.println("| 0. 수정 완료(메인 메뉴로 돌아가기) |");
            System.out.println("◣―――――――――――――――――――◢");
            System.out.print("수정할 정보를 선택해주세요: ");

            int input = sc.nextInt();

            sc.nextLine();

            switch (input) {
                case 1:
                    System.out.print("수정할 음료 이름 입력: ");
                    reformBev.setName(sc.nextLine());
                    break;
                case 2:
                    System.out.print("수정할 음료 가격 입력: ");
                    reformBev.setPrice(sc.nextInt());
                    break;
                case 3:
                    System.out.print("수정할 음료 칼로리 입력: ");
                    reformBev.setCalorie(sc.nextInt());
                    break;
                case 4:
                    System.out.print("수정할 카테고리를 입력해주세요(1: Coffee, 2: Latte, 3: Blended, 4: Tea) : ");
                    int category = sc.nextInt();
                    reformBev.setCagetory(resetBeverageCagetory(category));
                    break;
                case 5:
                    System.out.print("수정할 음료 재고 입력: ");
                    reformBev.setStock(sc.nextInt());
                    break;
                case 0:
                    return reformBev;
                default:
                    System.out.println("잘못된 번호를 입력하셨습니다.");
            }
        }
    }

    private static Beverage regist() {
        Beverage beverage = new Beverage();

        Scanner sc = new Scanner(System.in);
        System.out.print("등록할 음료의 이름을 입력해주세요: ");
        String name = sc.nextLine();

        System.out.print(name + "의 가격을 입력해주세요: ");
        int price = sc.nextInt();

        System.out.print(name + "의 칼로리를 입력해주세요: ");
        int calorie = sc.nextInt();

        System.out.print(name + "의 카테고리를 입력해주세요(1: Coffee, 2: Latte, 3: Blended, 4: Tea) : ");
        int category = sc.nextInt();
        BeverageCategory bc = resetBeverageCagetory(category);

        System.out.print(name + "의 재고를 입력해주세요: ");
        int stock = sc.nextInt();

        beverage.setName(name);
        beverage.setPrice(price);
        beverage.setCalorie(calorie);
        beverage.setCagetory(bc);
        beverage.setStock(stock);

        return beverage;
    }

    private static BeverageCategory resetBeverageCagetory(int category) {

        BeverageCategory bc = null;
        switch (category) {
            case 1:
                bc = BeverageCategory.COFFEE;
                break;
            case 2:
                bc = BeverageCategory.LATTE;
                break;
            case 3:
                bc = BeverageCategory.BLENDED;
                break;
            case 4:
                bc = BeverageCategory.TEA;
                break;
        }
        return bc;
    }

    private static int chooseRemoveBevNo() {
        Scanner sc = new Scanner(System.in);
        System.out.print("삭제할 음료의 번호를 입력해주세요: ");
        return sc.nextInt();
    }
}
