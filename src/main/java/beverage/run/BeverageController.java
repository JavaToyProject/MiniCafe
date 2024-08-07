package beverage.run;

import beverage.aggregate.Beverage;
import beverage.aggregate.BeverageCategory;
import beverage.service.BeverageService;

import java.util.ArrayList;
import java.util.Scanner;

public class BeverageController {

    private static final BeverageService bs = new BeverageService();

    public static int displayMainMenu() {
        Scanner sc = new Scanner(System.in);

        System.out.println("\n◤――― 음료 관리 ―――◥");
        System.out.println("| 1. 음료 전체 조회    |");
        System.out.println("| 2. 음료 검색        |");
        System.out.println("| 3. 음료 등록        |");
        System.out.println("| 4. 음료 정보 수정    |");
        System.out.println("| 5. 음료 삭제        |");
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
                registBeverage();       // 음료 등록
                break;
            case 4:
                updateBeverage();       // 음료 정보 수정
                break;
            case 5:
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

    private static void searchBeverage() {
        Scanner sc = new Scanner(System.in);

        Beverage selectBeverage = new Beverage();
        ArrayList<Beverage> selectBeverageList = new ArrayList<>();

        int findType = displaySearchMenu();

        switch (findType) {
            case 1: // 음료 번호 조회 (1개)
                selectBeverage = bs.findBeverageByBevNo(chooseBevNo());
                if (selectBeverage != null) {
                    System.out.print("\n[조회한 음료 정보] ");
                    printBeverage(selectBeverage);
                } else {
                    System.out.println("해당하는 음료가 존재하지 않습니다.");
                }
                break;

            case 2: // 음료명 조회 (리스트)
                System.out.print("검색할 음료명을 입력해주세요: ");
                String searchBevName = sc.nextLine();
                if (searchBevName.length() < 2) {
                    System.out.println("검색어를 2글자 이상 입력해 주세요");
                } else {
                    selectBeverageList = bs.findBeverageListByBevName(searchBevName);
                }

                if (!selectBeverageList.isEmpty()) {
                    System.out.print("\n[조회한 음료 정보]\n");
                    for (Beverage beverage : selectBeverageList) {
                        printBeverage(beverage);
                    }
                } else {
                    System.out.println("해당하는 음료가 존재하지 않습니다.");
                }
                break;
            case 3: // 음료 카테고리로 검색 (리스트)
                System.out.print("검색할 카테고리를 입력해주세요(1: Coffee, 2: Latte, 3: Blended, 4: Tea) : ");
                int searchCategory = sc.nextInt();
                selectBeverageList = bs.findBeverageListByCategory(searchCategory);

                if (!selectBeverageList.isEmpty()) {
                    System.out.print("\n[조회한 음료 정보]\n");
                    for (Beverage beverage : selectBeverageList) {
                        printBeverage(beverage);
                    }
                } else {
                    System.out.println("해당하는 음료가 존재하지 않습니다.");
                }
                break;
            case 0:
                break;
            default:
                System.out.println("잘못된 번호를 입력하셨습니다.");
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

    private static int chooseBevNo() {
        Scanner sc = new Scanner(System.in);
        System.out.print("검색할 음료번호를 입력해주세요: ");
        Integer bevNo = sc.nextInt();
        System.out.println("――――――――――――――");
        return bevNo;
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
        System.out.println("| 3. 음료 카테고리 조회 |");
        System.out.println("| 0. 검색 완료        |");
        System.out.println("◣――――――――――――◢");
        System.out.print("검색할 기준을 입력해주세요: ");

        int searchType = sc.nextInt();
        return searchType;
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

        // 음료명 중복 확인 (존재하는 음료일 경우 추가 불가)
        if (bs.findBeverageByBevName(name) != null) {
            return null;
        }

        System.out.print(name + "의 가격을 입력해주세요: ");
        int price = sc.nextInt();

        System.out.print(name + "의 칼로리를 입력해주세요: ");
        int calorie = sc.nextInt();

        System.out.print(name + "의 카테고리를 입력해주세요(1: Coffee, 2: Latte, 3: Blended, 4: Tea) : ");
        int category = sc.nextInt();
        BeverageCategory bc = getBeverageCategory(category);

        beverage.setName(name);
        beverage.setPrice(price);
        beverage.setCalorie(calorie);
        beverage.setCagetory(bc);

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
                + "kcal / 카테고리: " + beverages.getCagetory());
    }
}

