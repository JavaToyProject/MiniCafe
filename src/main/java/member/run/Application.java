package member.run;

import beverage.aggregate.Beverage;
import beverage.service.BeverageService;
import member.aggregate.Member;
import member.service.MemberService;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Application {

    private static final MemberService ms = new MemberService();
    private static final BeverageService bs = new BeverageService();

    public static void main(String[] args) {
        displayMemberMenu();
    }

    public static void displayMemberMenu() {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n◤―――― 회원 관리 ――――◥");
            System.out.println("| 1. 모든 회원 정보 보기   |");
            System.out.println("| 2. 회원 찾기            |");
            System.out.println("| 3. 회원 가입            |");
            System.out.println("| 4. 회원정보 수정         |");
            System.out.println("| 5. 회원 탈퇴            |");
            System.out.println("| 0. 프로그램 종료         |");
            System.out.println("◣――――――――――――――◢");
            System.out.print("원하는 메뉴를 선택해주세요: ");

            int input = sc.nextInt();
            System.out.println("―――――――――――――");
            System.out.println();

            switch (input) {
                case 1:
                    ArrayList<Member> findMembers = ms.findAllMembers();
                    for(Member member : findMembers) printMember(member);
                    break;
                case 2:
                    Member selectMember = ms.findOneMember(inputPhoneNo());
                    if (selectMember != null)
                        printMember(selectMember);
                    else
                        System.out.println("존재하지 않는 회원입니다.");
                    break;
                case 3: ms.registMember(signUp());          break;
                case 4: ms.updateMember(updateInfo());      break;
                case 5:
                    int result = ms.removeMember(inputPhoneNo());
                    if (result == 1)
                        System.out.println("회원탈퇴 완료!! BYE!!");
                    else
                        System.out.println("잘못된 입력!!");
                    break;
                case 9: return;
                default:
                    System.out.println("잘못된 입력!!");
            }
            if (input == 9) break;
        }
    }

    private static Member updateInfo() {
        Member updateMember = null;
        String name = null;
        String nick = null;
        String phoneNo = null;
        ArrayList<ImageIcon> beverages = null;

        int inputNo = 0;

        Scanner sc = new Scanner(System.in);

        System.out.print("수정할 회원 핸드폰번호 입력: ");
        String phone = sc.nextLine();

        updateMember = ms.findOneMember(phone);

        if (updateMember != null) {
            while (true) {
                System.out.println("\n◤――― 회원정보 수정 ―――◥");
                System.out.println("| 1. 이름                |");
                System.out.println("| 2. 닉네임              |");
                System.out.println("| 3. 찜 메뉴             |");
                System.out.println("| 0. 수정 완료            |");
                System.out.println("◣――――――――――――――◢");
                System.out.print("수정할 항목을 선택해주세요: ");

                inputNo = sc.nextInt();
                System.out.println("―――――――――――――");

                sc.nextLine();
                switch (inputNo) {
                    case 1:
                        System.out.print("이름 입력: ");
                        name = sc.nextLine();
                        updateMember.setName(name);
                        break;
                    case 2:
                        System.out.print("닉네임 입력: ");
                        nick = sc.nextLine();
                        updateMember.setNickName(nick);
                        break;
                    case 3:
                        updateFavoriteBev(sc, updateMember);
                        break;//
                    case 0:
                        System.out.println("회원정보 수정 종료"); break;
                    default:
                        System.out.println("잘못된 입력!!");
                }
                if (inputNo == 0) break;
            }
        }
        return updateMember;
    }

    private static void updateFavoriteBev(Scanner sc, Member updateMember) {
        while (true) {
            System.out.println("\n◤――― 찜 메뉴 수정 ―――◥");
            System.out.println("| 1. 찜 메뉴 추가         |");
            System.out.println("| 2. 찜 메뉴 삭제         |");
            System.out.println("| 0. 찜 메뉴 수정 완료     |");
            System.out.println("◣――――――――――――――◢");
            System.out.print("수정할 항목을 선택해주세요: ");


            int chNo = sc.nextInt();
            sc.nextLine();
            ArrayList<Integer> updateBeverageList = updateMember.getBeverages(); // 업데이트된 찜 메뉴 목록 관리
            switch (chNo) {
                case 1:
                    // 음료 목록 출력
                    printBeverageList("====== 음료 목록 ======");

                    // 추가할 찜 메뉴 번호 입력받기
                    System.out.print("추가 할 찜 메뉴 번호 입력: ");
                    int addBeverageNo = sc.nextInt();

                    // 입력받은 번호가 음료 목록에 존재하는지 확인
                    Beverage addBeverage = bs.findBeverageByBevNo(addBeverageNo);
                    if(addBeverage != null) {
                        // 현재 찜 목록에 존재하지 않는지 확인 (중복 체크)
                        if (!updateBeverageList.contains(Integer.valueOf(addBeverageNo))) {
                            updateBeverageList.add(addBeverage.getBevNo()); // updateBeverageList에 추가
                        } else {
                            System.out.println("현재 찜 목록에 존재하는 음료입니다");
                        }
                    } else {
                        System.out.println("해당 음료는 목록에 존재하지 않습니다.");
                    }
                    break;
                case 2:
                    // 현재 찜 메뉴 출력
                    System.out.println("====== 현재 찜한 음료 목록 ======");
                    System.out.println("삭제할 음료의 번호를 선택해주세요");
                    for (int beverageNo : updateBeverageList) {
                        printBeverage(bs.findBeverageByBevNo(beverageNo));
                    }

                    // 삭제할 찜 메뉴 번호 입력받기
                    System.out.print("삭제 할 찜 메뉴 입력: ");
                    int removeBeverageNo = sc.nextInt();

                    // 입력받은 번호가 음료 목록에 존재하는지 확인
                    Beverage removeBeverage = bs.findBeverageByBevNo(removeBeverageNo);
                    if(removeBeverage != null) {
                        updateBeverageList.remove(Integer.valueOf(removeBeverageNo)); // 존재한다면 updateBeverageList에서 삭제
                    } else {
                        System.out.println("해당 음료는 목록에 존재하지 않습니다.");
                    }

                    break;
                case 0:
                    System.out.println("찜 메뉴 수정 종료");
                    break;
                default:
                    System.out.println("잘못된 입력!!");
            }
            if (chNo == 0) break;
        }
    }

    private static Member signUp() {
        Member newMember = null;

        Scanner sc = new Scanner(System.in);

        System.out.print("이름 입력: ");
        String name = sc.nextLine();

        System.out.print("닉네임 입력: ");
        String nick = sc.nextLine();

        System.out.print("핸드폰번호 입력('-'생략): ");
        String phone = sc.nextLine();
        if (ms.findOneMember(phone) != null) { // 핸드폰번호 중복체크 (존재하는 경우 가입X)
            return null;
        }

        // 음료 목록 출력
        System.out.print("찜 할 음료 개수: ");
        int length = sc.nextInt();
        sc.nextLine();  // 버퍼의 개행문자 처리용(엔터 입력 제거)
        printBeverageList("====== 찜할 음료 목록 ======");

        ArrayList<Integer> favoriteBeverages = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            System.out.print((i+1) + "번째 찜 음료 입력: ");
            int favoriteBeverageNo = sc.nextInt();

            // 입력받은 번호가 음료 목록에 존재하는지 확인
            Beverage favoriteBeverage = bs.findBeverageByBevNo(favoriteBeverageNo);
            if(favoriteBeverage != null) {
                favoriteBeverages.add(favoriteBeverage.getBevNo());
            } else {
                System.out.println("해당 음료는 목록에 존재하지 않습니다.");
                i--; // 다시 입력받기
            }
        }

        newMember = new Member(name, nick, phone, favoriteBeverages);

        newMember.setStamps(0);

        return newMember;
    }

    private static void printBeverageList(String message) {
        System.out.println(message);
        System.out.println("찜할 음료의 번호를 선택해주세요");
        ArrayList<Beverage> beverageArrayList = bs.findAllBeverages();
        for (Beverage beverage : beverageArrayList) {
            printBeverage(beverage);
        }
    }

    private static String inputPhoneNo() {
        Scanner sc = new Scanner(System.in);
        System.out.print("회원 핸드폰번호 입력: ");
        return sc.nextLine();
    }

    private static void printBeverage(Beverage beverages) {
        System.out.println("[" + beverages.getBevNo() + "번 음료] " + beverages.getName() + " / 가격: " + beverages.getPrice() + "원 / 칼로리: " + beverages.getCalorie()
                + "kcal / 카테고리: " + beverages.getCagetory());
    }

    private static void printMember(Member member) {
        System.out.print("[" + member.getMemNo() + "번 회원] 이름: " + member.getName() + " / 닉네임: " + member.getNickName()
                + " / 핸드폰번호: " + member.getPhone() + " / 스탬프개수: " + member.getStamps()
                + "개 / 찜한 음료 목록: ");
        for(int bevNo : member.getBeverages()) {
            System.out.print(bs.findBeverageByBevNo(bevNo).getName() + " ");
        }
        System.out.println();
    }
}
