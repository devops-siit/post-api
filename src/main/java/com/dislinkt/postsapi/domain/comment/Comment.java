package com.dislinkt.postsapi.domain.comment;

import com.dislinkt.postsapi.domain.account.Account;
import com.dislinkt.postsapi.domain.base.BaseEntity;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Entity
public class Comment extends BaseEntity {

    @NotNull
    @Size(max = 36)
    @Column(unique = true)
    private String text;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    private Account author;
}
