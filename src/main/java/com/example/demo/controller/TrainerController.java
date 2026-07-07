package com.example.demo.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.entity.Trainer;
import com.example.demo.service.JwtService;
import com.example.demo.service.TrainerService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.security.access.prepost.PreAuthorize;




@RestController
public class TrainerController {
    private final TrainerService service;

    TrainerController(TrainerService service,PasswordEncoder encoder,JwtService jwtService){
        this.service = service;
    }

    @PostMapping("/addTrainer")
    public ResponseEntity<Trainer> add(@RequestBody Trainer trainer){
        return ResponseEntity.ok(service.addTrainer(trainer));
    }
    
    @GetMapping("/admin/fetchAllTrainers")
    public List<Trainer> getAll(){
        return service.getAll();
    }
    
    @GetMapping("/trainer/{id}")
    public Trainer getById(@PathVariable Long id){
        return service.getById(id);
    }

    @PutMapping("/update/{id}")
    public String updateTrainer(@PathVariable Long id, @RequestBody Trainer t) {
        return service.updateTrainer(id,t);
    }
    
    @DeleteMapping("/deleteTrainer/{id}")
    public String deleteTrainer(@PathVariable Long id){
        return  service.deleteTrainer(id);
    }
    
}
