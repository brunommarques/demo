package com.mastercard.example.demo.repositories;

import com.mastercard.example.demo.entities.ConsumerAddress;
import com.mastercard.example.demo.entities.ConsumerAddressId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConsumerAddressRepository extends JpaRepository<ConsumerAddress, ConsumerAddressId> {
}