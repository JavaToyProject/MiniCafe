package beverage.service;

import beverage.aggregate.Beverage;
import beverage.aggregate.BeverageCategory;
import beverage.repository.BeverageRepository;

import java.util.ArrayList;

public class BeverageService {

    private final BeverageRepository br = new BeverageRepository();

    public BeverageService() {
    }

    public void findAllBeverages() {
        ArrayList<Beverage> findBeverages = br.selectAllBeverages();

        System.out.println("[모든 음료 정보]");
        for (Beverage beverages : findBeverages) {
            System.out.print("[" + beverages.getBevNo() + "번 음료 정보] 음료명: " );
            printBeverage(beverages);
        }

    }

    public void findBeverageByBevNo(Integer searchBevNo) { // 음료 번호로 검색하는 경우
        Beverage selectBeverage = new Beverage();
        selectBeverage = br.selectBeverageBy(searchBevNo);

        if (selectBeverage != null) {
            System.out.print("\n[조회한 음료 정보] ");
            printBeverage(selectBeverage);
        } else {
            System.out.println("해당하는 음료가 존재하지 않습니다.");
        }
    }

    public void findBeverageByBevName(String searchBevName) {
        Beverage selectBeverage = new Beverage();
        selectBeverage = br.selectBeverageBy(searchBevName);

        if (selectBeverage != null) {
            System.out.print("[조회한 음료 정보] ");
            printBeverage(selectBeverage);
        } else {
            System.out.println("해당하는 음료가 존재하지 않습니다.");
        }
    }

    public void registBeverage(Beverage newBeverage) {
        int lastBevNo = br.selectLastBeverageNo();
        newBeverage.setBevNo(lastBevNo + 1);
        int result = br.insertBeverage(newBeverage);

        if (result == 1) {
            System.out.println("\n음료를 성공적으로 등록했습니다.");
            System.out.print("[등록한 음료 정보] ");
            printBeverage(newBeverage);
        } else {
            System.out.println("음료 등록을 실패했습니다.");
        }

    }

    public void modifyBeverage(Beverage reformBeverage) {
        int result = br.updateBeverage(reformBeverage);

        if (result == 1) {
            System.out.println("움료 정보 수정이 완료되었습니다.");
        } else {
            System.out.println("음료 정보 수정에 실패했습니다.");
        }
    }

    public void removeBeverage(int removeBevNo) {
        int result = br.deleteBeverage(removeBevNo);

        if (result == 1) {
            System.out.println("음료 삭제가 완료되었습니다.");
        } else {
            System.out.println("음료 삭제에 실패했습니다.");
        }
    }

    public ArrayList<Beverage> findBeveragListBy(int[] filterArr) {
        ArrayList<Beverage> filterBeverageList = new ArrayList<>();
        int filterType = filterArr[0];
        int value = filterArr[1];
        // selectBeverageListByCategory
        switch (filterType) {
            case 1: // 특정 카테고리 음료만 조회
                BeverageCategory bc = null;
                switch (value) {
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
                filterBeverageList.addAll(br.selectBeverageListByCategory(bc)); // 깊은 복사
                break;

            case 2: // 특정 금액 이상의 음료만 조회
                filterBeverageList.addAll(br.selectBeverageListByUpperPrice(value));
                break;
            case 3: // 특정 금액 이하의 음료만 조회
                filterBeverageList.addAll(br.selectBeverageListByLowerPrice(value));
                break;
            case 0: // 메인 화면으로 돌아가기
                break;
        }

        for (Beverage beverage : filterBeverageList) {
            printBeverage(beverage);
        }
        return filterBeverageList;
    }


    private static void printBeverage(Beverage beverages) {
        System.out.println("음료명: " + beverages.getName() + " / 가격: " + beverages.getPrice() + "원 / 칼로리: " + beverages.getCalorie()
                + "kcal / 카테고리: " + beverages.getCagetory() + " / 재고: " + beverages.getStock() + "잔");
    }
}
