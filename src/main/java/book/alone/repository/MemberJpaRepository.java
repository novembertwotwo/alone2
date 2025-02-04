package book.alone.repository;

import book.alone.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MemberJpaRepository extends JpaRepository<Member,Long> {

    @Query("select m from Member m where m.username = :name")
    public List<Member> memberByCover(String name);

}
