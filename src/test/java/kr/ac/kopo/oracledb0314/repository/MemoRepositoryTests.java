package kr.ac.kopo.oracledb0314.repository;

import kr.ac.kopo.oracledb0314.entity.Memo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
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

    @Test
    public void testpageDefault(){
//        페이지당 10개의 엔티티 처리
        Pageable pageable = PageRequest.of(0, 10);

        Page<Memo> result = memoRepository.findAll(pageable);

        System.out.println(result);

        for (Memo memo : result.getContent()){
            System.out.println(memo);
        }

        System.out.println("================================");
        System.out.println("Total Pages: " + result.getTotalPages());
        System.out.println("Total Count: " + result.getTotalElements());
        System.out.println("Page Number: " + result.getNumber());
        System.out.println("Page Size: " + result.getSize());
        System.out.println("Has next page?: " + result.hasNext());
        System.out.println("Is first page?:" + result.isFirst());
    }

    @Test
     public void testSort(){

        Pageable pageable = PageRequest.of(0, 10, Sort.by("mno").descending());

        Page<Memo> result = memoRepository.findAll(pageable);

        result.get().forEach(memo -> {
            System.out.println("number: " + memo.getMno() + ",l content: " + memo.getMemoText());
                });
    }

    @Test
    public void testQueryMethod1(){
        List<Memo> result = memoRepository.findByMnoBetweenOrderByMnoDesc(20L, 30L);
        for (Memo memo : result){
            System.out.println(memo.toString());
        }
    }

    @Test
    public void testQueryMethod2(){
        Pageable pageable = PageRequest.of(0, 10, Sort.by("mno").descending());
        Page<Memo> result = memoRepository.findByMnoBetween(20L, 60L, pageable);

        for (Memo memo : result){
            System.out.println(memo.toString());
        }

        System.out.println("======================");

        pageable = PageRequest.of(0, 18);
        result = memoRepository.findByMnoBetween(20L, 60L, pageable);
        result.get().forEach(memo -> {
            System.out.println(memo);
        });
    }

    @Transactional
    @Commit
    @Test
    public void testQueryMethod3(){
        memoRepository.deleteMemoByMnoLessThan(5L);
        testpageDefault();
    }

    @Test
    public void testQueryAnnotationNative(){
        List<Memo> result = memoRepository.getNativeResult();
        for (Memo memo : result){
            System.out.println(memo);
        }
    }

    @Test
    public void testQueryAnnotationNative2(){
        List<Object[]> result = memoRepository.getNativeResult2();
        for (Object[] memoObj : result){
            System.out.println(memoObj[1]);
        }
    }
}
