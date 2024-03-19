package com.example.clubdeportivo.repositories;

import com.example.clubdeportivo.entities.Member;
import com.example.clubdeportivo.entities.SportDiscipline;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberRepository extends JpaRepository<Member, Integer> {
    boolean existsBySportDisciplineNotNullAndId(Integer id);

    Member findMemberBySportDiscipline(SportDiscipline sportDiscipline);

    List<Member> findBySportDisciplineId(Integer sportDisciplineId);
}
