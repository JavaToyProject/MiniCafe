package beverage.aggregate;


import java.io.Serializable;
import java.util.Arrays;

public class Beverage implements Serializable {
    private int BevNo;
    private String name;
    private int price;
    private int calorie;
    private BeverageCategory cagetory;  // 음료카테고리(COFFEE, LATTE, BLENDED, TEA)

    public Beverage() {
    }

    public Beverage(int bevNo, String name, int price, int calorie, BeverageCategory cagetory) {
        BevNo = bevNo;
        this.name = name;
        this.price = price;
        this.calorie = calorie;
        this.cagetory = cagetory;
    }

    public int getBevNo() {
        return BevNo;
    }

    public void setBevNo(int bevNo) {
        BevNo = bevNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getCalorie() {
        return calorie;
    }

    public void setCalorie(int calorie) {
        this.calorie = calorie;
    }

    public BeverageCategory getCagetory() {
        return cagetory;
    }

    public void setCagetory(BeverageCategory cagetory) {
        this.cagetory = cagetory;
    }

    @Override
    public String toString() {
        return "Beverage{" +
                "BevNo=" + BevNo +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", calorie=" + calorie +
                ", cagetory=" + cagetory +
                '}';
    }
}
