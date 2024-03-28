package kr.ac.kopo.oracledb0314.repository;

import kr.ac.kopo.oracledb0314.entity.Memo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MemoRepository extends JpaRepository<Memo, Long> {
    Page<Memo> findByMnoBetween(Long from, Long to, Pageable pageable);
    List<Memo> findByMnoBetweenOrderByMnoDesc(Long from, Long to);

//    특정 mno 값을 갖는 Memo Entity만 삭제할 때
    void deleteMemoByMno(Long mno);
//    Mno 미만인 Memo Entity를 삭제할 때
    void deleteMemoByMnoLessThan(Long mno);
//    Mno 이하인 Memo Entity를 삭제할 때
    void deleteMemoByMnoLessThanEqual(Long mno);
//    Query Native SQL문 실행방법
    @Query(value = "select * from tbl_memo where mno >= 70", nativeQuery = true)
    List<Memo> getNativeResult();

    @Query(value = "select  * from tbl_memo where mno >= 80", nativeQuery = true)
    List<Object[]> getNativeResult2();
}
