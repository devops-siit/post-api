package com.dislinkt.postsapi.web.rest.comment.payload;

import com.dislinkt.postsapi.service.account.payload.AccountDTO;
import com.dislinkt.postsapi.web.rest.base.BaseDTO;
import lombok.Data;

@Data
public class CommentDTO extends BaseDTO {

    private String text;

    private AccountDTO author;
}
