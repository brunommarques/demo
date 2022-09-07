package com.mastercard.example.demo.repositories;

import com.mastercard.example.demo.entities.Consumer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConsumerRepository extends JpaRepository<Consumer, Integer> {
}