package com.dislinkt.postsapi.service.account.payload;

import com.dislinkt.postsapi.web.rest.base.BaseDTO;
import lombok.Data;

@Data
public class AccountDTO extends BaseDTO {

    private String username;

    private String name;
}