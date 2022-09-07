package com.mastercard.example.demo.entities;

import lombok.*;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "ADDRESS")
public class Address {
    private Integer id;

    private String street;

    private String city;

    private String state;

    private String zip;

    private Set<ConsumerAddress> consumerAddresses = new LinkedHashSet<>();

    @OneToMany(mappedBy = "address")
    public Set<ConsumerAddress> getConsumerAddresses() {
        return consumerAddresses;
    }

    @Id
    @Column(name = "ID", nullable = false)
    public Integer getId() {
        return id;
    }

    @Column(name = "STREET", length = 1048576)
    public String getStreet() {
        return street;
    }

    @Column(name = "CITY", nullable = false, length = 1048576)
    public String getCity() {
        return city;
    }

    @Column(name = "STATE", length = 1048576)
    public String getState() {
        return state;
    }

    @Column(name = "ZIP", nullable = false, length = 1048576)
    public String getZip() {
        return zip;
    }

}