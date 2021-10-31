package com.saltpay.test.DTO;

import com.saltpay.test.models.Account;
import lombok.Data;

@Data
public class ResponseDTO{
    private Integer responseCode;
    private String responseDescription;
}
