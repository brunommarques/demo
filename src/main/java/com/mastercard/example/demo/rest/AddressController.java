package com.mastercard.example.demo.rest;

import com.mastercard.example.demo.entities.Address;
import com.mastercard.example.demo.exceptions.AddressNotFoundException;
import com.mastercard.example.demo.repositories.AddressRepository;
import com.mastercard.example.demo.repositories.ConsumerAddressRepository;
import com.mastercard.example.demo.repositories.ConsumerRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AddressController {
    ConsumerRepository consumerRepository;
    AddressRepository addressRepository;
    ConsumerAddressRepository consumerAddressRepository;

    public AddressController(ConsumerRepository consumerRepository, AddressRepository addressRepository, ConsumerAddressRepository consumerAddressRepository) {
        this.consumerRepository = consumerRepository;
        this.addressRepository = addressRepository;
        this.consumerAddressRepository = consumerAddressRepository;

    }


    // Single item
    @GetMapping("/Addresses/{id}")
    Address one(@PathVariable Integer id) {
        return addressRepository.findById(id)
                .orElseThrow(() -> new AddressNotFoundException(id));
    }

}