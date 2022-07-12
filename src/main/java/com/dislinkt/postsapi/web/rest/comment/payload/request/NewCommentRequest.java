package com.dislinkt.postsapi.web.rest.comment.payload.request;

import lombok.Data;

@Data
public class NewCommentRequest {

    private String text;

    private String postUuid;
}
