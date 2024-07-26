package beverage.repository;

import beverage.aggregate.Beverage;
import beverage.aggregate.BeverageCategory;
import beverage.stream.MyObjectOutput;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

public class BeverageRepository {

    private ArrayList<Beverage> beverageArrayList = new ArrayList<>();

    /* 개인필기.
     *  1. 초기 음료 3개 저장 (객체 출력)
     *  2. 파일로부터 음료 정보 가져오기 (객체 입력)
     *  3. beverageArrayList에 음료 정보 저장
     * */
    public BeverageRepository() {
        File file = new File("src/main/java/beverage/db/beverageDB.dat");

        if (!file.exists()) {
            ArrayList<Beverage> defaultBeverage = new ArrayList<>();
            defaultBeverage.add(new Beverage(1, "아메리카노", 3500, 4, BeverageCategory.COFFEE, 120));
            defaultBeverage.add(new Beverage(2, "바닐라라떼", 4500, 280, BeverageCategory.LATTE, 80));
            defaultBeverage.add(new Beverage(3, "카페라떼", 4200, 240, BeverageCategory.LATTE, 80));
            defaultBeverage.add(new Beverage(4, "딸기스무디", 5300, 460, BeverageCategory.BLENDED, 30));
            defaultBeverage.add(new Beverage(5, "아이스티", 3800, 180, BeverageCategory.TEA, 40));

            saveBeverages(file, defaultBeverage);
        }

        loadBeverages(file);

//        for(Beverage beverage : beverageArrayList) { System.out.println("beverage = " + beverage);}
    }

    private void loadBeverages(File file) {
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(
                    new BufferedInputStream(
                            new FileInputStream(file)));
            while (true) {
                beverageArrayList.add((Beverage) ois.readObject());
            }
        } catch (EOFException e) {
            System.out.println("음료 정보 모두 로딩됨...");
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (ois != null) ois.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static void saveBeverages(File file, ArrayList<Beverage> beverages) {
        ObjectOutputStream oos = null;

        try {
            oos = new ObjectOutputStream(
                    new BufferedOutputStream(
                            new FileOutputStream(file)));

            for (Beverage beverage : beverages) {
                oos.writeObject(beverage);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (oos != null) oos.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public ArrayList<Beverage> selectAllBeverages() {
        return beverageArrayList;
    }

    public Beverage selectBeverageBy(Integer bevNo) {
        for (Beverage beverage : beverageArrayList) {
            if (bevNo == beverage.getBevNo()) {
                return beverage;
            }
        }
        return null;
    }

    public Beverage selectBeverageBy(String bevName) {
        for (Beverage beverage : beverageArrayList) {
            if (Objects.equals(beverage.getName(), bevName)) {
                return beverage;
            }
        }
        return null;
    }

    public int selectLastBeverageNo() {

        Beverage lastBeverage = beverageArrayList.get(beverageArrayList.size()-1);
        return lastBeverage.getBevNo();
    }

    public int insertBeverage(Beverage newBeverage) {

        /* 개인필기. 헤더 없이 파일에 추가하고, 수정된 파일로 beverageArrayList 업데이트해야 함 */

        int result = 0;
        File file = new File("src/main/java/beverage/db/beverageDB.dat");

        MyObjectOutput moo = null;

        try {
            moo = new MyObjectOutput(
                    new BufferedOutputStream(
                            new FileOutputStream(file, true)));

            moo.writeObject(newBeverage);
            beverageArrayList.add(newBeverage);
            result = 1;

        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (moo != null) moo.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        return result;
    }

    public int updateBeverage(Beverage reformBeverage) {
        for (Beverage beverage : beverageArrayList) {
            // null(0) 값이 아닌 필드를 확인(수정된 필드)하고 수정
            if (beverage.getBevNo() == reformBeverage.getBevNo()) {
                if (reformBeverage.getName() != null) beverage.setName(reformBeverage.getName());
                if (reformBeverage.getPrice() != 0) beverage.setPrice(reformBeverage.getPrice());
                if (reformBeverage.getCalorie() != 0) beverage.setCalorie(reformBeverage.getCalorie());
                if (reformBeverage.getCagetory() != null) beverage.setCagetory(reformBeverage.getCagetory());
                if (reformBeverage.getStock() != 0) beverage.setStock(reformBeverage.getStock());

                // 수정된 beverageArrayList 전체를 파일에 덮어씌우기
                File file = new File("src/main/java/beverage/db/beverageDB.dat");
                saveBeverages(file, beverageArrayList);

                return 1;
            }
        }
        return 0;
    }

    public int deleteBeverage(int removeBevNo) {
        for (int i = 0; i < beverageArrayList.size(); i++) {
            if (removeBevNo == beverageArrayList.get(i).getBevNo()) {
                beverageArrayList.remove(i);

                // 삭제된 beverageArrayList 전체를 파일에 덮어씌우기
                File file = new File("src/main/java/beverage/db/beverageDB.dat");
                saveBeverages(file, beverageArrayList);

                return 1;
            }
        }
        return 0;
    }

    public ArrayList<Beverage> selectBeverageListByCategory(BeverageCategory bc) {
        ArrayList<Beverage> filterBeverageList = new ArrayList<>();

        for (Beverage beverage : beverageArrayList) {
            if (bc == beverage.getCagetory()) {
                filterBeverageList.add(beverage);
            }
        }

        return filterBeverageList;
    }

    public ArrayList<Beverage> selectBeverageListByUpperPrice(int price) {
        ArrayList<Beverage> filterBeverageList = new ArrayList<>();

        for (Beverage beverage : beverageArrayList) {
            if (price <= beverage.getPrice()) {
                filterBeverageList.add(beverage);
            }
        }

        return filterBeverageList;
    }

    public ArrayList<Beverage> selectBeverageListByLowerPrice(int price) {
        ArrayList<Beverage> filterBeverageList = new ArrayList<>();

        for (Beverage beverage : beverageArrayList) {
            if (price >= beverage.getPrice()) {
                filterBeverageList.add(beverage);
            }
        }

        return filterBeverageList;
    }
}
