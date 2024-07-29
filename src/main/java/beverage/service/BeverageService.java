package minicafe.beverage.service;

import minicafe.beverage.aggregate.Beverage;
import minicafe.beverage.aggregate.BeverageCategory;
import minicafe.beverage.repository.BeverageRepository;

import java.util.ArrayList;

public class BeverageService {

    private final BeverageRepository br = new BeverageRepository();

    public BeverageService() {
    }

    public ArrayList<Beverage> findAllBeverages() {
        ArrayList<Beverage> findBeverages = br.selectAllBeverages();
        return findBeverages;
    }

    public Beverage findBeverageByBevNo(Integer searchBevNo) { // 음료 번호로 검색하는 경우
        Beverage selectBeverage = br.selectBeverageBy(searchBevNo);
        return selectBeverage;
    }

    public Beverage findBeverageByBevName(String searchBevName) {
        Beverage selectBeverage = br.selectBeverageBy(searchBevName);
        return selectBeverage;
    }

    public Beverage registBeverage(Beverage newBeverage) {
        int lastBevNo = br.selectLastBeverageNo();
        newBeverage.setBevNo(lastBevNo + 1);
        int result = br.insertBeverage(newBeverage);

        if (result == 1) {
            return newBeverage;
        } else {
            return null;
        }
    }

    public int modifyBeverage(Beverage reformBeverage) {
        int result = br.updateBeverage(reformBeverage);
        return result;
    }

    public int removeBeverage(int removeBevNo) {
        int result = br.deleteBeverage(removeBevNo);
        return result;
    }

    public ArrayList<Beverage> findBeverageListBy(int[] filterArr) {
        ArrayList<Beverage> searchBeverageList = new ArrayList<>();
        int filterType = filterArr[0];
        int value = filterArr[1];

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
                searchBeverageList.addAll(br.selectBeverageListByCategory(bc)); // 깊은 복사
                break;

            case 2: // 특정 금액 이상의 음료만 조회
                searchBeverageList.addAll(br.selectBeverageListByUpperPrice(value));
                break;
            case 3: // 특정 금액 이하의 음료만 조회
                searchBeverageList.addAll(br.selectBeverageListByLowerPrice(value));
                break;
            case 0: // 메인 화면으로 돌아가기
                break;
        }
        return searchBeverageList;
    }

}
