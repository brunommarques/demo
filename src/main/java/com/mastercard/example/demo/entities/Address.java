package com.mastercard.example.demo.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
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
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Table(name = "ADDRESS")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Address {
    private Integer id;

    private String street;

    private String city;

    private String state;

    private String zip;

    private Set<ConsumerAddress> consumerAddresses = new LinkedHashSet<>();

    @OneToMany(mappedBy = "address")
    @JsonIgnore
    public Set<ConsumerAddress> getConsumerAddresses() {
        return consumerAddresses;
    }

    @Id
    @GeneratedValue
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