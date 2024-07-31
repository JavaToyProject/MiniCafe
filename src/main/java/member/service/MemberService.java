package member.service;

import member.aggregate.Member;
import member.repository.MemberRepository;

import java.util.ArrayList;

public class MemberService {

    private final MemberRepository mr = new MemberRepository();

    public MemberService() {}

    public void findAllMembers() {
        ArrayList<Member> findMembers = mr.selectAllMembers();

        for (Member member : findMembers) {
            System.out.println(member.toString());
        }
    }

    public void findOneMember(String memPhoneNo) {
        Member selectMember = mr.selectOneMember(memPhoneNo);
        if (selectMember != null)
            System.out.println(selectMember.toString());
        else
            System.out.println("존재하지 않는 회원 핸드폰번호입니다.");
    }

    public void registMember(Member member) {
        if (member == null) {
            System.out.println("회원가입에 실패했습니다. 이미 존재하는 번호입니다.");
        } else {
            int lastMemNo = mr.selectLastMemNo();
            member.setMemNo(lastMemNo + 1);
            int result = mr.insertMember(member);

            if (result == 1) {
                System.out.println(member.getName() + "님 회원가입 완료!!");
            } else {
                System.out.println("회원가입에 실패했습니다");
            }
        }
    }

    public void updateMember(Member member) {
        if (member == null) {
            System.out.println("회원정보 수정에 실패했습니다. 정확한 회원번호를 입력해 주세요.");
        } else {
            Member getMember = mr.selectOneMember(member.getPhone());
            System.out.println(getMember);

            int result = mr.updateMember(member);
            if (result == 1) {
                System.out.println(member.getName() + "님 회원정보 수정 완료!!");
            } else {
                System.out.println("회원정보 수정에 실패했습니다.");
            }
        }
    }

    public Member findOneMemberInfo(String phone) {
        Member selectMember = mr.selectOneMember(phone);
        if (selectMember != null)
            return selectMember;
        else {
            System.out.println("존재하지 않는 회원 핸드폰번호입니다.");
            return null;
        }
    }

    public void removeMember(String removePhone) {
        int result = mr.deleteMember(removePhone);
        if (result == 1)
            System.out.println("회원탈퇴 완료!! BYE!!");
        else
            System.out.println("잘못된 입력!!");
    }
}