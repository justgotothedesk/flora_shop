package com.example.flora_shop.controller;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressForm {
    private String useSameAddress;
    private Long itemId;
    private Long userId;
    private Long quantity;
    private String postcode;
    private String roadAddress;
    private String jibunAddress;
    private String extraAddress;
    private String detailAddress;
}
