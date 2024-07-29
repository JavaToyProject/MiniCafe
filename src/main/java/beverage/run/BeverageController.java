package minicafe.beverage.run;

import minicafe.beverage.aggregate.Beverage;
import minicafe.beverage.aggregate.BeverageCategory;
import minicafe.beverage.service.BeverageService;

import java.util.ArrayList;
import java.util.Scanner;

public class BeverageController {

    private static final BeverageService bs = new BeverageService();

    public static int displayMainMenu() {
        Scanner sc = new Scanner(System.in);

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
        return input;
    }
    public static boolean handleMainMenu(int input) {
        switch (input) {
            case 1:
                getAllBeverages();      // 음료 전체 조회
                break;
            case 2:
                searchBeverage();       // 음료 검색
                break;
            case 3:
                searchBeverageList();   // 음료 목록 검색
                break;
            case 4:
                registBeverage();       // 음료 등록
                break;
            case 5:
                updateBeverage();       // 음료 정보 수정
                break;
            case 6:
                deleteBeverage();       // 음료 삭제
                break;
            case 0:                     // 프로그램 종료
                System.out.println("음료 관리 프로그램을 종료합니다.");
                return true;
            default:
                System.out.println("번호를 잘못 입력하셨습니다.");
        }
        return false;
    }

    private static void deleteBeverage() {
        int result = bs.removeBeverage(chooseRemoveBevNo());

        if (result == 1) {
            System.out.println("음료 삭제가 완료되었습니다.");
        } else {
            System.out.println("음료 삭제에 실패했습니다.");
        }
    }

    private static void updateBeverage() {
        int result = bs.modifyBeverage(reformBev());

        if (result == 1) {
            System.out.println("움료 정보 수정이 완료되었습니다.");
        } else {
            System.out.println("음료 정보 수정에 실패했습니다.");
        }
    }

    private static void registBeverage() {
        Beverage newBeverage = bs.registBeverage(registBev());

        if (newBeverage != null) {
            System.out.println("\n음료를 성공적으로 등록했습니다.");
            System.out.print("[등록한 음료 정보] ");
            printBeverage(newBeverage);
        } else {
            System.out.println("음료 등록을 실패했습니다.");
        }
    }

    private static void searchBeverageList() {
        ArrayList<Beverage> searchBeverageList = bs.findBeverageListBy(searchBevList());

        for (Beverage beverage : searchBeverageList) {
            printBeverage(beverage);
        }

    }

    private static void searchBeverage() {
        Beverage selectBeverage = new Beverage();
        int findType = displaySearchMenu();
        switch (findType) {
            case 1: selectBeverage = bs.findBeverageByBevNo(chooseBevNo()); break;
            case 2: selectBeverage = bs.findBeverageByBevName(chooseBevName()); break;
            default:
                System.out.println("잘못된 번호를 입력하셨습니다.");
        }

        if (selectBeverage != null) {
            System.out.print("\n[조회한 음료 정보] ");
            printBeverage(selectBeverage);
        } else {
            System.out.println("해당하는 음료가 존재하지 않습니다.");
        }

    }

    private static void getAllBeverages() {
        ArrayList<Beverage> findBeverages = bs.findAllBeverages();

        System.out.println("[모든 음료 정보]");
        for (Beverage beverages : findBeverages) {
            System.out.print("[" + beverages.getBevNo() + "번 음료 정보] 음료명: " );
            printBeverage(beverages);
        }
    }

    private static Integer chooseBevNo() {
        Scanner sc = new Scanner(System.in);
        System.out.print("검색할 음료번호를 입력해주세요: ");
        Integer bevNo = sc.nextInt();
        System.out.println("――――――――――――――");
        return bevNo;
    }

    private static String chooseBevName() {
        Scanner sc = new Scanner(System.in);
        System.out.print("검색할 음료명을 입력해주세요: ");
        String bevName = sc.nextLine();
        System.out.println("――――――――――――――");
        return bevName;
    }

    private static int chooseRemoveBevNo () {
        Scanner sc = new Scanner(System.in);
        System.out.print("삭제할 음료번호를 입력해주세요: ");
        return sc.nextInt();
    }

    private static int displaySearchMenu() {
        // 가격, 칼로리, 카테고리는 중복된 값이 있을 수 있기 때문에 단일 검색에서 제외
        Scanner sc = new Scanner(System.in);
        System.out.println("◤――― 음료 검색 ―――◥");
        System.out.println("| 1. 음료 번호 조회    |");
        System.out.println("| 2. 음료명 조회       |");
        System.out.println("◣――――――――――――◢");
        System.out.print("검색할 기준을 입력해주세요: ");

        int searchType = sc.nextInt();
        return searchType;
    }

    private static int[] searchBevList() {
        int input = displaySearchCriteriaMenu();
        int[] citeriaArr = {0, 0}; // 필터의 종류와 추가 정보를 저장

        citeriaArr = handleSearchCriteriaMenu(input, citeriaArr);
        if (citeriaArr != null) return citeriaArr;

        return citeriaArr;
    }

    private static int displaySearchCriteriaMenu() {
        Scanner sc = new Scanner(System.in);

        System.out.println("◤――――― 음료 목록 검색 ―――――◥");
        System.out.println("| 1. 특정 카테고리 음료만 조회      |");
        System.out.println("| 2. 특정 금액 이상의 음료만 조회    |");
        System.out.println("| 3. 특정 금액 이하의 음료만 조회    |");
        System.out.println("| 0. 메인 화면으로 돌아가기         |");
        System.out.println("◣―――――――――――――――――――◢");
        System.out.print("검색할 기준을 입력해주세요: ");

        int input = sc.nextInt();
        return input;
    }

    private static int[] handleSearchCriteriaMenu(int input, int[] citeriaArr) {
        Scanner sc = new Scanner(System.in);

        switch (input) {
            case 1:
                System.out.print("검색할 카테고리를 입력해주세요(1: Coffee, 2: Latte, 3: Blended, 4: Tea) : ");
                citeriaArr[0] = 1;
                citeriaArr[1] = sc.nextInt();
                break;
            case 2:
                System.out.print("검색할 금액을 입력해주세요: ");
                citeriaArr[0] = 2;
                citeriaArr[1] = sc.nextInt();
                break;
            case 3:
                System.out.print("검색할 금액을 입력해주세요: ");
                citeriaArr[0] = 3;
                citeriaArr[1] = sc.nextInt();
                break;
            case 0:
                return citeriaArr;
            default:
                System.out.println("잘못된 번호를 입력하셨습니다.");
        }
        System.out.println("―――――――――――――――――");
        return citeriaArr;
    }


    private static Beverage reformBev() {
        Beverage reformBev = new Beverage();
        Scanner sc = new Scanner(System.in);

        System.out.print("수정할 음료의 번호를 입력해주세요: ");
        reformBev.setBevNo(sc.nextInt());
        sc.nextLine(); // 버퍼 clear

        while (true) {
            int input = displayReformMenu(sc);

            switch (input) {
                case 1:
                    sc.nextLine(); // 버퍼 clear
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
                    reformBev.setCagetory(getBeverageCategory(category));
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

    private static int displayReformMenu(Scanner sc) {
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
        return input;
    }

    private static Beverage registBev () {
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
        BeverageCategory bc = getBeverageCategory(category);

        System.out.print(name + "의 재고를 입력해주세요: ");
        int stock = sc.nextInt();

        beverage.setName(name);
        beverage.setPrice(price);
        beverage.setCalorie(calorie);
        beverage.setCagetory(bc);
        beverage.setStock(stock);

        return beverage;
    }

    private static BeverageCategory getBeverageCategory ( int category){

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

    private static void printBeverage(Beverage beverages) {
        System.out.println("음료명: " + beverages.getName() + " / 가격: " + beverages.getPrice() + "원 / 칼로리: " + beverages.getCalorie()
                + "kcal / 카테고리: " + beverages.getCagetory() + " / 재고: " + beverages.getStock() + "잔");
    }
}

