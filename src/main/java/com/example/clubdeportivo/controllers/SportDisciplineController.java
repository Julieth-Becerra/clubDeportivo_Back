package com.example.clubdeportivo.controllers;

import com.example.clubdeportivo.entities.Member;
import com.example.clubdeportivo.entities.SportDiscipline;
import com.example.clubdeportivo.responses.ResponseHandler;
import com.example.clubdeportivo.services.MemberService;
import com.example.clubdeportivo.services.SportDisciplineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SportDisciplineController {

    @Autowired
    private SportDisciplineService sportDisciplineService;

    @Autowired
    private MemberService memberService;

    @GetMapping("/sportDisciplines")
    public ResponseEntity<Object> findAllSportDisciplines() {
        try {
            List<SportDiscipline> sportDisciplines = sportDisciplineService.findAll();
            return ResponseHandler.generateResponse("Success", HttpStatus.OK, sportDisciplines);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }

    @GetMapping("/sportDisciplines/{id}")
    public ResponseEntity<Object> findSportDisciplineById(@PathVariable Integer id) {
        try {
            SportDiscipline sportDiscipline = sportDisciplineService.findById(id);
            if (sportDiscipline != null) {
                return ResponseHandler.generateResponse("Success", HttpStatus.OK, sportDiscipline);
            }
            return ResponseHandler.generateResponse("Sport discipline not found", HttpStatus.NOT_FOUND, null);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }

    @PostMapping("/sportDisciplines")
    public ResponseEntity<Object> addSportDiscipline(@RequestBody SportDiscipline sportDiscipline) {
        try {
            SportDiscipline savedSportDiscipline = sportDisciplineService.saveSportDiscipline(sportDiscipline);
            return ResponseHandler.generateResponse("Sport discipline added successfully", HttpStatus.CREATED, savedSportDiscipline);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }

    @DeleteMapping("/sportDisciplines/{id}")
    public ResponseEntity<Object> deleteSportDiscipline(@PathVariable Integer id) {
        try {
            SportDiscipline sportDiscipline = sportDisciplineService.findById(id);
            if (sportDiscipline != null) {
                if (!memberService.hasMembersForSportDiscipline(id)) {
                    // Eliminar las referencias de la disciplina en los miembros
                    memberService.removeSportDisciplineReferences(id);

                    // Ahora puedes eliminar la disciplina
                    sportDisciplineService.deleteSportDiscipline(sportDiscipline);

                    return ResponseHandler.generateResponse("Sport discipline deleted successfully", HttpStatus.OK, sportDiscipline);
                }
                return ResponseHandler.generateResponse("Sport discipline has associated members", HttpStatus.CONFLICT, sportDiscipline);
            }
            return ResponseHandler.generateResponse("Sport discipline not found", HttpStatus.NOT_FOUND, null);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }



    @PutMapping("/sportDisciplines/{id}")
    public ResponseEntity<Object> updateSportDiscipline(@PathVariable Integer id, @RequestBody SportDiscipline sportDiscipline) {
        try {
            sportDiscipline.setId(id);
            SportDiscipline updatedSportDiscipline = sportDisciplineService.updateSportDiscipline(sportDiscipline);
            return ResponseHandler.generateResponse("Sport discipline updated successfully", HttpStatus.OK, updatedSportDiscipline);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }
}
