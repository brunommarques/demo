package com.mastercard.example.demo.entities;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(onlyExplicitlyIncluded = true)
@Embeddable
public class ConsumerAddressId implements Serializable {
    private static final long serialVersionUID = -7675988057017431718L;
    private Integer addressId;

    private Integer consumerId;

    private Integer addressTypeId;

    @Column(name = "ADDRESS_ID", nullable = false)
    public Integer getAddressId() {
        return addressId;
    }

    @Column(name = "CONSUMER_ID", nullable = false)
    public Integer getConsumerId() {
        return consumerId;
    }

    @Column(name = "ADDRESS_TYPE_ID", nullable = false)
    public Integer getAddressTypeId() {
        return addressTypeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ConsumerAddressId entity = (ConsumerAddressId) o;
        return Objects.equals(this.addressTypeId, entity.addressTypeId) &&
                Objects.equals(this.consumerId, entity.consumerId) &&
                Objects.equals(this.addressId, entity.addressId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(addressTypeId, consumerId, addressId);
    }

}