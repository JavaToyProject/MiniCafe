package minicafe.member.repository;

import minicafe.member.aggregate.Member;
import minicafe.member.stream.MyObjectOutput;

import java.io.*;
import java.util.ArrayList;

public class MemberRepository {

    private ArrayList<Member> memberList = new ArrayList<>();

    public MemberRepository() {
        File file = new File("src/main/java/minicafe/member/db/member.dat");

        if (!file.exists()) {
            ArrayList<Member> defaultMembers = new ArrayList<>();

            defaultMembers.add(new Member(1, "유관순", "대한독립만세", "01012345678", 8, new String[]{"아메리카노"}));
            defaultMembers.add(new Member(2, "홍길동", "동에번쩍", "01098765432", 3, new String[]{"모카프라푸치노", "에스프레소"}));
            defaultMembers.add(new Member(3, "신사임당", "오만원", "01056325478", 10, new String[]{"녹차라테", "자몽에이드"}));

            saveMembers(file, defaultMembers);
        }

        loadMembers(file);
    }

    private void loadMembers(File file) {
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(
                    new BufferedInputStream(
                            new FileInputStream(file)));
            while (true) {
                memberList.add((Member) ois.readObject());
            }
        } catch (EOFException e){
            System.out.println("회원정보 로딩 완료!!");
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

    private void saveMembers(File file, ArrayList<Member> members) {

        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(
                    new BufferedOutputStream(
                            new FileOutputStream(file)));
            for(Member member : members)
                oos.writeObject(member);
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

    public ArrayList<Member> selectAllMembers() {
        return memberList;
    }

    public Member selectOneMember(String memPhoneNo) {
         for (Member member : memberList) {
             if (memPhoneNo.compareTo(member.getPhone())==0)
                 return member;
         }
         return null;
    }

    public int selectLastMemNo() {
        Member lastMember = memberList.get(memberList.size()-1);
        return lastMember.getMemNo();
    }

    public int insertMember(Member newMember) {
        MyObjectOutput moo = null;
        int result = 0;
        try {
            moo = new MyObjectOutput(
                    new BufferedOutputStream(
                            new FileOutputStream(
                                    "src/main/java/minicafe/member/db/member.dat",
                                    true)));
            moo.writeObject(newMember);
            memberList.add(newMember);
            System.out.println(memberList);
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

    public int updateMember(Member member) {
        for (int i = 0; i < memberList.size(); i++) {
            Member beforeMem = memberList.get(i);
            if (member.getPhone().compareTo(beforeMem.getPhone())==0) {
                System.out.println("====== 수정 후 회원정보 ======");
                System.out.println(member.toString());
                System.out.println();

                memberList.set(i, member);

                File file = new File("src/main/java/minicafe/member/db/member.dat");
                saveMembers(file, memberList);

                if (beforeMem.equals(member)) return 1;
            }
        }
        return 0;
    }

    public int deleteMember(String removePhone) {
        for (int i = 0; i < memberList.size(); i++) {
            if (memberList.get(i).getPhone().compareTo(removePhone)==0) {
                memberList.remove(i);

                File file = new File("src/main/java/minicafe/member/db/member.dat");
                saveMembers(file, memberList);
                return 1;
            }
        }
        return 0;
    }
}
