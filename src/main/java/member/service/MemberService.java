package member.service;

import member.aggregate.Member;
import member.repository.MemberRepository;

import java.util.ArrayList;

public class MemberService {

    private final MemberRepository mr = new MemberRepository();

    public MemberService() {}

    public ArrayList<Member> findAllMembers() {
        ArrayList<Member> findMembers = mr.selectAllMembers();
        return findMembers;
    }

    public Member findOneMember(String memPhoneNo) {
        Member selectMember = mr.selectOneMember(memPhoneNo);
        return selectMember;
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
            int result = mr.updateMember(member);
            if (result == 1) {
                System.out.println(member.getName() + "님 회원정보 수정 완료!!");
            } else {
                System.out.println("회원정보 수정에 실패했습니다.");
            }
        }
    }

    public int removeMember(String removePhone) {
        int result = mr.deleteMember(removePhone);
        return result;
    }
}