package com.mastercard.example.demo.entities;

import lombok.*;

import javax.persistence.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "CONSUMER_ADDRESS")
public class ConsumerAddress {
    private ConsumerAddressId id;

    private Address address;

    private Consumer consumer;

    @EmbeddedId
    public ConsumerAddressId getId() {
        return id;
    }

    @MapsId("addressId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ADDRESS_ID", nullable = false)
    public Address getAddress() {
        return address;
    }

    @MapsId("consumerId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "CONSUMER_ID", nullable = false)
    public Consumer getConsumer() {
        return consumer;
    }

}