package jpabook.jpashop.domain;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Embeddable // JPA 내장 타입
@Getter @Setter
public class Address {

    private String city;
    private String street;
    private String zipcode;
}
