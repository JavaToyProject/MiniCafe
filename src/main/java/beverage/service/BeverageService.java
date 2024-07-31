package beverage.service;

import beverage.aggregate.Beverage;
import beverage.aggregate.BeverageCategory;
import beverage.repository.BeverageRepository;

import java.util.ArrayList;

public class BeverageService {

    private final BeverageRepository br = new BeverageRepository();

    public BeverageService() {
    }

    public ArrayList<Beverage> findAllBeverages() {
        ArrayList<Beverage> findBeverages = br.selectAllBeverages();
        return findBeverages;
    }

    public Beverage findBeverageByBevNo(int searchBevNo) { // 음료 번호로 검색하는 경우
        Beverage selectBeverage = br.selectBeverageBy(searchBevNo);
        return selectBeverage;
    }

    public Beverage findBeverageByBevName(String searchBevName) {
        Beverage selectBeverage = br.selectBeverageBy(searchBevName);
        return selectBeverage;
    }

    public Beverage registBeverage(Beverage newBeverage) {
        if (newBeverage == null) {
            return null;
        } else {
            int lastBevNo = br.selectLastBeverageNo();
            newBeverage.setBevNo(lastBevNo + 1);
            int result = br.insertBeverage(newBeverage);

            if (result == 1) {
                return newBeverage;
            } else {
                return null;
            }
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

    public ArrayList<Beverage> findBeverageListByCategory(int searchCategory) {
        ArrayList<Beverage> searchBeverageList = new ArrayList<>();

        BeverageCategory bc = null;

        switch (searchCategory) {
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
        return searchBeverageList;
    }

    public ArrayList<Beverage> findBeverageListByBevName(String searchBevName) {
        ArrayList<Beverage> searchBeverageList = new ArrayList<>();
        searchBeverageList.addAll(br.selectBeverageListbyBevName(searchBevName));
        return searchBeverageList;
    }
}
