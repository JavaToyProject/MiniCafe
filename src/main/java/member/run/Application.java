package member.run;

import member.aggregate.Member;
import member.service.MemberService;

import java.util.Scanner;

public class Application {

    private static final MemberService ms = new MemberService();

    public static void main(String[] args) {
        displayMemberMenu();
    }

    public static void displayMemberMenu() {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("====== 회원 관리 ====== ");
            System.out.println("1. 모든 회원 정보 보기");
            System.out.println("2. 회원 찾기");     // by 회원
            System.out.println("3. 회원 가입");
            System.out.println("4. 회원정보 수정");
            System.out.println("5. 회원 탈퇴");
            System.out.println("9. 프로그램 종료");
            System.out.print("메뉴 선택: ");

            int input = sc.nextInt();

            switch (input) {
                case 1: ms.findAllMembers();                break;
                case 2: ms.findOneMember(inputPhoneNo());   break;
                case 3: ms.registMember(signUp());          break;
                case 4: ms.registMember(updateInfo());      break;
                case 5: ms.removeMember(inputPhoneNo());    break;
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
        String[] beverages = null;

        int inputNo = 0;

        Scanner sc = new Scanner(System.in);

        System.out.print("수정할 회원 핸드폰번호 입력: ");
        String phone = sc.nextLine();

        updateMember = ms.findOneMemberInfo(phone);
        System.out.println(updateMember);

        if (updateMember != null) {
            while (true) {
                System.out.println("====== 회원정보 수정 ====== ");
                System.out.println("1. 이름");
                System.out.println("2. 닉네임");
                System.out.println("3. 찜 메뉴");
                System.out.println("9. 수정 완료");

                System.out.print("수정 할 항목 입력: ");
                inputNo = sc.nextInt();
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
                    case 3:
                        while (true) {
                            System.out.println("====== 찜 메뉴 수정 ======");
                            System.out.println("1. 찜 메뉴 추가");
                            System.out.println("2. 찜 메뉴 삭제");
                            System.out.println("9. 수정 완료");
                            int chNo = sc.nextInt();
                            sc.nextLine();
                            switch (chNo) {
                                case 1:
                                    System.out.print("추가 할 찜 메뉴 입력: ");
                                    String addMenu = sc.nextLine();
                                    int len = updateMember.getBeverages().length;
                                    String[] after = new String[len+1];
                                    for (int i = 0; i < len+1; i++) {
                                        if (i == len) {
                                            after[i] = addMenu;
                                            break;
                                        }
                                        after[i] = updateMember.getBeverages()[i];
                                    }
                                    updateMember.setBeverages(after);
                                    break;
                                case 2:
                                    System.out.print("삭제 할 찜 메뉴 입력: ");
                                    String removeMenu = sc.nextLine();
                                    int rlen = updateMember.getBeverages().length;
                                    String[] remove = new String[rlen-1];
                                    int j = 0;
                                    for (int i = 0; i < rlen; i++) {
                                        if (removeMenu.compareTo(updateMember.getBeverages()[i]) != 0){
                                            remove[j] = updateMember.getBeverages()[i];
                                            j += 1;
                                        }
                                    }
                                    System.out.println(remove.toString());
                                    updateMember.setBeverages(remove);
                                    break;
                                case 9:
                                    System.out.println("찜 메뉴 수정 종료");
                                    break;
                                default:
                                    System.out.println("잘못된 입력!!");
                            }
                            if (chNo == 9) break;
                        }
                        break;//
                    case 9:
                        System.out.println("회원정보 수정 종료");   break;
                    default:
                        System.out.println("잘못된 입력!!");
                }
                if (inputNo == 9) break;
            }
        }
        return updateMember;
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

        System.out.print("찜 할 음료 개수: ");
        int length = sc.nextInt();
        sc.nextLine();  // 버퍼의 개행문자 처리용(엔터 입력 제거)

        String[] beverages = new String[length];
        for (int i = 0; i < length; i++) {
            System.out.print((i+1) + "번째 찜 음료 입력: ");
            String input = sc.nextLine();
            beverages[i] = input;
        }

        newMember = new Member(name, nick, phone, beverages);

        newMember.setStamps(0);

        return newMember;
    }

    private static String inputPhoneNo() {
        Scanner sc = new Scanner(System.in);
        System.out.print("회원 핸드폰번호 입력: ");
        return sc.nextLine();
    }
}
