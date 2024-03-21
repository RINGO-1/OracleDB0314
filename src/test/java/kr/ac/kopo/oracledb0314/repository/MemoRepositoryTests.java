package kr.ac.kopo.oracledb0314.repository;

import kr.ac.kopo.oracledb0314.entity.Memo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
public class MemoRepositoryTests {

    @Autowired
    MemoRepository memoRepository;

    @Test
    public void testClass(){
        System.out.println(memoRepository.getClass().getName());
    }

    @Test
    public void TestInsertDummies(){
//      MemoRepository의 save(Memo Entity 객체의 참조값)를 호출해서 insert한다.
        IntStream.rangeClosed(1, 100).forEach(i ->{
            Memo memo = Memo.builder().memoText("Dummy Data Test" + i).build();
            memoRepository.save(memo);
        });
    }

    @Test
    public void testSelect(){
//      MemoRepository의 findById(Memo Entity 객체의 Id로 설정된 값)를 사용해서 select한다.
//      findById()호출되면 select문을 실행함
//      findById()는 NullPointerException이 발생되지 않도록 Null 체크를 한다
        Long mno =99L;

        Optional<Memo> result = memoRepository.findById(mno);

        System.out.println("=============================================");

        if (result.isPresent()){
            Memo memo = result.get();
            System.out.println(memo);
        }
    }

    @Test
    @Transactional
    public void testSelect2(){
//      getOne()을 통해 select문을 실행함 구식 메소드이기에 @Transactional을 기입해야 사용가능
//      get
        Long mno =99L;

        Memo memo = memoRepository.getOne(mno);

        System.out.println("=============================================");

        System.out.println(memo);

    }

    @Test
    public void testUpdate(){
//      MemoRepository의 save(Memo Entity의 객체 참조값)를 호출해서 update한다.
//      save()는 호출하면 먼저 select를 하기 때문에 기존에 Entity가 있을때 update를 실행한다
        Memo memo = Memo.builder().mno(99L).memoText("Update Dummy Data 99").build();

        Memo memo1 = memoRepository.save(memo);

        System.out.println(memo1);
    }

    @Test
    public void testDelete(){
//      MemoRepository의 deleteById(MemoEntity의 mno값)를 호출해서 delete한다.
        Long mno = 98L;
        memoRepository.deleteById(mno);
    }
}
