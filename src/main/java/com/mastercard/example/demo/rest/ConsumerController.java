package com.mastercard.example.demo.rest;

import com.mastercard.example.demo.constants.AddressType;
import com.mastercard.example.demo.entities.Address;
import com.mastercard.example.demo.entities.Consumer;
import com.mastercard.example.demo.entities.ConsumerAddress;
import com.mastercard.example.demo.entities.ConsumerAddressId;
import com.mastercard.example.demo.exceptions.AddressNotFoundException;
import com.mastercard.example.demo.exceptions.ConsumerAlreadyHasThisAddressForThisTypeException;
import com.mastercard.example.demo.exceptions.ConsumerNotFoundException;
import com.mastercard.example.demo.repositories.AddressRepository;
import com.mastercard.example.demo.repositories.ConsumerAddressRepository;
import com.mastercard.example.demo.repositories.ConsumerRepository;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class ConsumerController {
    ConsumerRepository consumerRepository;
    AddressRepository addressRepository;
    ConsumerAddressRepository consumerAddressRepository;

    public ConsumerController(ConsumerRepository consumerRepository, AddressRepository addressRepository, ConsumerAddressRepository consumerAddressRepository) {
        this.consumerRepository = consumerRepository;
        this.addressRepository = addressRepository;
        this.consumerAddressRepository = consumerAddressRepository;

    }

    @GetMapping("/Consumers")
    List<Consumer> all() {
        return consumerRepository.findAll();
    }


    @PostMapping(value = "/Consumers", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    Consumer newConsumer(@RequestBody Consumer newConsumer) {
        return consumerRepository.save(newConsumer);

    }

    // Single item
    @GetMapping("/Consumers/{id}")
    Consumer one(@PathVariable Integer id) {
        return consumerRepository.findById(id)
                .orElseThrow(() -> new ConsumerNotFoundException(id));
    }

    @GetMapping("/Consumers/{id}/BillingAddresses")
    List<Address> getBillingAddresses(@PathVariable Integer id) {
        List<ConsumerAddress> consumerAddressList = consumerRepository.findById(id).map(consumer -> {
                    return consumer.getConsumerAddresses().stream().filter(
                                    ca -> ca.getId().getAddressTypeId() == AddressType.BILLING.ordinal())
                            .collect(Collectors.toList());
                })
                .orElseThrow(() -> new ConsumerNotFoundException(id));

        List<Address> billingAddresses = new ArrayList<>();

        consumerAddressList.forEach(addr -> billingAddresses.add(addr.getAddress()));

        return billingAddresses;

    }

    @GetMapping("/Consumers/{id}/ShippingAddresses")
    List<Address> getShippingAddresses(@PathVariable Integer id) {
        List<ConsumerAddress> consumerAddressList = consumerRepository.findById(id).map(consumer -> {
                    return consumer.getConsumerAddresses().stream().filter(
                                    ca -> ca.getId().getAddressTypeId() == AddressType.SHIPPING.ordinal())
                            .collect(Collectors.toList());
                })
                .orElseThrow(() -> new ConsumerNotFoundException(id));

        List<Address> shippingAddresses = new ArrayList<>();

        consumerAddressList.forEach(addr -> shippingAddresses.add(addr.getAddress()));

        return shippingAddresses;

    }

    @PutMapping(value = "/Consumers/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    Consumer replaceConsumer(@RequestBody Consumer newConsumer, @PathVariable Integer id) {
        return consumerRepository.findById(id)
                .map(Consumer -> {
                    Consumer.setFirstName(newConsumer.getFirstName());
                    Consumer.setLastName(newConsumer.getLastName());
                    return consumerRepository.save(Consumer);
                })
                .orElseGet(() -> {
                    newConsumer.setId(id);
                    return consumerRepository.save(newConsumer);
                });
    }


    @DeleteMapping("/Consumers/{id}")
    @Transactional(rollbackFor = RuntimeException.class)
    void deleteConsumer(@PathVariable Integer id) {
        Optional<Consumer> consumer = consumerRepository.findById(id);

        if (consumer.isEmpty()) {
            throw new ConsumerNotFoundException(id);
        }

        consumerAddressRepository.deleteAll(consumer.get().getConsumerAddresses());
        consumerRepository.deleteById(id);
    }


    @PostMapping(value = "/Consumers/{consumerId}/AddBillingAddress", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    Consumer addBillingAddressToConsumer(@RequestBody Address newAddressForConsumer, @PathVariable Integer consumerId) {
        return addAddressToConsumer(newAddressForConsumer, consumerId, AddressType.BILLING.ordinal());
    }

    @PostMapping(value = "/Consumers/{consumerId}/AddShippingAddress", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    Consumer addShippingAddressToConsumer(@RequestBody Address newAddressForConsumer, @PathVariable Integer consumerId) {
        return addAddressToConsumer(newAddressForConsumer, consumerId, AddressType.SHIPPING.ordinal());
    }

    @Transactional(rollbackFor = RuntimeException.class)
    Consumer addAddressToConsumer(Address newAddressForConsumer, Integer consumerId, Integer addressTypeId) {
        return consumerRepository.findById(consumerId)
                .map(consumer -> {
                    int addressIdToAssociate;

                    if (newAddressForConsumer.getId() == null) {
                        Address newAddressToCreate = new Address();
                        newAddressToCreate.setCity(newAddressForConsumer.getCity());
                        newAddressToCreate.setZip(newAddressForConsumer.getZip());
                        newAddressToCreate.setState(newAddressForConsumer.getState());
                        newAddressToCreate.setStreet(newAddressForConsumer.getStreet());
                        addressRepository.save(newAddressToCreate);
                        addressRepository.flush();
                        addressIdToAssociate = newAddressToCreate.getId();
                    } else {
                        addressIdToAssociate = newAddressForConsumer.getId();
                        if (!addressRepository.existsById(addressIdToAssociate)) {
                            throw new AddressNotFoundException(addressIdToAssociate);
                        }
                    }

                    ConsumerAddressId consumerAddressPK = new ConsumerAddressId();
                    consumerAddressPK.setAddressId(addressIdToAssociate);
                    consumerAddressPK.setConsumerId(consumer.getId());
                    consumerAddressPK.setAddressTypeId(addressTypeId);

                    if (!consumerAddressRepository.existsById(consumerAddressPK)) {

                        ConsumerAddress newConsumerAddress = new ConsumerAddress();
                        ConsumerAddressId newConsumerAddressId = new ConsumerAddressId();
                        newConsumerAddressId.setConsumerId(consumerId);
                        newConsumerAddressId.setAddressId(addressIdToAssociate);
                        newConsumerAddressId.setAddressTypeId(addressTypeId);
                        newConsumerAddress.setId(newConsumerAddressId);
                        consumer.getConsumerAddresses().add(newConsumerAddress);
                        newConsumerAddress.setConsumer(consumer);
                        newConsumerAddress.setAddress(addressRepository.getReferenceById(addressIdToAssociate));
                        consumerAddressRepository.save(newConsumerAddress);
                        consumerAddressRepository.flush();
                    } else {
                        throw new ConsumerAlreadyHasThisAddressForThisTypeException(newAddressForConsumer.getId());
                    }

                    return consumerRepository.save(consumer);

                })
                .orElseThrow(() -> new ConsumerNotFoundException(consumerId));
    }

}