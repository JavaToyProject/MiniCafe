package member.aggregate;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

public class Member implements Serializable {
    private int memNo;
    private String name;
    private String nickName;
    private String phone;
    private int stamps;
    private ArrayList<Integer> beverages;

    public Member() {}

    public Member(String name, String nickName, String phone, ArrayList<Integer> beverages) {
        this.name = name;
        this.nickName = nickName;
        this.phone = phone;
        this.beverages = beverages;
    }

    public Member(int memNo, String name, String nickName, String phone, int stamps, ArrayList<Integer> beverages) {
        this.memNo = memNo;
        this.name = name;
        this.nickName = nickName;
        this.phone = phone;
        this.stamps = stamps;
        this.beverages = beverages;
    }

    public int getMemNo() {
        return memNo;
    }

    public void setMemNo(int memNo) {
        this.memNo = memNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getStamps() {
        return stamps;
    }

    public void setStamps(int stamps) {
        this.stamps = stamps;
    }

    public ArrayList<Integer> getBeverages() {
        return beverages;
    }

    public void setBeverages(ArrayList<Integer> beverages) {
        this.beverages = beverages;
    }

    @Override
    public String toString() {
        return "Member{" +
                "memNo=" + memNo +
                ", name='" + name + '\'' +
                ", nickName='" + nickName + '\'' +
                ", phone='" + phone + '\'' +
                ", stamps=" + stamps +
                ", beverages=" + beverages +
                '}';
    }
}
