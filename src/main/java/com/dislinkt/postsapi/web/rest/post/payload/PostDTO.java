package com.dislinkt.postsapi.web.rest.post.payload;

import com.dislinkt.postsapi.web.rest.base.BaseDTO;
import lombok.Data;

@Data
public class PostDTO extends BaseDTO {

    private String text;

    private Integer likesCount;

    private Integer dislikesCount;
}
