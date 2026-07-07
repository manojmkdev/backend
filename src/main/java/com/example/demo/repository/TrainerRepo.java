package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.entity.Trainer;

public interface TrainerRepo extends JpaRepository<Trainer,Long>{
}
