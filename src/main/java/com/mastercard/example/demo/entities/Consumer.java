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
@Table(name = "CONSUMER")
public class Consumer {
    private Integer id;

    private String firstName;

    private String lastName;

    private Set<ConsumerAddress> consumerAddresses = new LinkedHashSet<>();

    @OneToMany(mappedBy = "consumer")
    public Set<ConsumerAddress> getConsumerAddresses() {
        return consumerAddresses;
    }

    @Id
    @Column(name = "ID", nullable = false)
    public Integer getId() {
        return id;
    }

    @Column(name = "\"firstName\"", nullable = false, length = 1048576)
    public String getFirstName() {
        return firstName;
    }

    @Column(name = "\"lastName\"", nullable = false, length = 1048576)
    public String getLastName() {
        return lastName;
    }

}