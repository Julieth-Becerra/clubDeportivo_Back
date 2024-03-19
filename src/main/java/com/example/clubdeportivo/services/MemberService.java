package com.example.clubdeportivo.services;

import com.example.clubdeportivo.entities.Member;
import com.example.clubdeportivo.entities.SportDiscipline;
import com.example.clubdeportivo.repositories.MemberRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    public List<Member> findAll() {
        return memberRepository.findAll();
    }

    public Member findById(Integer id) {
        Optional<Member> optional = memberRepository.findById(id);
        return optional.orElse(null);
    }

    public Member saveMember(Member member) {
        // Verificar si el miembro ya está asociado a una disciplina deportiva
        if (memberRepository.existsBySportDisciplineNotNullAndId(member.getId())) {
            throw new IllegalStateException("El miembro ya está asociado a una disciplina deportiva.");
        }
        return memberRepository.save(member);
    }

    public void deleteMember(Member member) {
        memberRepository.delete(member);
    }

    public Member updateMember(Member member) {
        return memberRepository.save(member);
    }

    public boolean hasMembersForSportDiscipline(Integer sportDisciplineId) {
        return memberRepository.existsBySportDisciplineNotNullAndId(sportDisciplineId);
    }

    public Member findMemberBySportDiscipline(SportDiscipline sportDiscipline) {
        return memberRepository.findMemberBySportDiscipline(sportDiscipline);
    }

    @Transactional
    public void removeSportDisciplineReferences(Integer sportDisciplineId) {
        // Obtener todos los miembros asociados con la disciplina
        List<Member> members = memberRepository.findBySportDisciplineId(sportDisciplineId);

        // Establecer la disciplina como null en cada miembro
        for (Member member : members) {
            member.setSportDiscipline(null);
            memberRepository.save(member);
        }
    }
}
