package com.example.demo.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.entity.Trainer;
import com.example.demo.exception.TrainerNotFoundException;
import com.example.demo.repository.TrainerRepo;

@Service
public class TrainerService {

    @Autowired
    TrainerRepo repo;

    public Trainer addTrainer(Trainer trainer) {
        return repo.save(trainer);
    }

    public List<Trainer> getAll() {
        return repo.findAll();
    }

    public Trainer getById(Long id) {
       return repo.findById(id).orElseThrow(()->new TrainerNotFoundException("Trainer not found"));
    }

    public String updateTrainer(Long id, Trainer t) {
        Trainer past = repo.findById(id).orElse(null);
        if(past != null){
            past.setName(t.getName());
            past.setEmail(t.getEmail());
            repo.save(past);
            return "Trainer details updated successfully !!";
        }   
        else{
            return "Trainer not Found !!";
        }
    }

    public String deleteTrainer(Long id) {
       if(repo.existsById(id)){
        repo.deleteById(id);
        return "Trainer details deleted successfully";
       }
       return "Trainer not Found";
    }

    public String update_email(Long id,String email) {
        Trainer past = repo.findById(id).orElse(null);       
        if(past!=null)
        {
            past.setEmail(email);
             repo.save(past);
            return "Trainer details updated successfully !!";
        }
        else
            return "Trainer not found";
    }

   
    

}
