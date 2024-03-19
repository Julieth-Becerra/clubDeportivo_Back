package com.example.clubdeportivo.controllers;

import com.example.clubdeportivo.entities.Member;
import com.example.clubdeportivo.entities.Participation;
import com.example.clubdeportivo.responses.ResponseHandler;
import com.example.clubdeportivo.services.MemberService;
import com.example.clubdeportivo.services.ParticipationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MemberController {

    @Autowired
    private MemberService memberService;

    @Autowired
    private ParticipationService participationService;

    @GetMapping("/members")
    public ResponseEntity<Object> findAllMembers() {
        try {
            List<Member> members = memberService.findAll();
            return ResponseHandler.generateResponse("Success", HttpStatus.OK, members);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }

    @GetMapping("/members/{id}")
    public ResponseEntity<Object> findMemberById(@PathVariable Integer id) {
        try {
            Member member = memberService.findById(id);
            if (member != null) {
                return ResponseHandler.generateResponse("Success", HttpStatus.OK, member);
            }
            return ResponseHandler.generateResponse("Member not found", HttpStatus.NOT_FOUND, null);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }

    @PostMapping("/members")
    public ResponseEntity<Object> addMember(@RequestBody Member member) {
        try {
            Member savedMember = memberService.saveMember(member);
            return ResponseHandler.generateResponse("Member added successfully", HttpStatus.CREATED, savedMember);
        } catch (IllegalStateException e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
        } catch (Exception e) {
            return ResponseHandler.generateResponse("An error occurred while adding the member", HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }

    @DeleteMapping("/members/{id}")
    public ResponseEntity<Object> deleteMember(@PathVariable Integer id) {
        try {
            Member member = memberService.findById(id);
            if (member != null) {
                List<Participation> participations = member.getParticipations();
                if (!participations.isEmpty()) {
                    // Establecer el miembro en null en cada participación
                    for (Participation participation : participations) {
                        participation.setMember(null);
                        // Actualizar la participación individualmente
                        participationService.update(participation);
                    }
                }
                // Finalmente, eliminar el miembro
                memberService.deleteMember(member);
                return ResponseHandler.generateResponse("Member deleted successfully", HttpStatus.OK, member);
            }
            return ResponseHandler.generateResponse("Member not found", HttpStatus.NOT_FOUND, null);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }



    @PutMapping("/members/{id}")
    public ResponseEntity<Object> updateMember(@PathVariable Integer id, @RequestBody Member member) {
        try {
            member.setId(id);
            Member updatedMember = memberService.updateMember(member);
            return ResponseHandler.generateResponse("Member updated successfully", HttpStatus.OK, updatedMember);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }
}
