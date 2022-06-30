package com.dislinkt.postsapi.domain.post;

import com.dislinkt.postsapi.domain.account.Account;
import com.dislinkt.postsapi.domain.base.BaseEntity;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
@Entity
public class Post extends BaseEntity {

    @NotNull
    @Size(max = 2048)
    private String text;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    private Account author;

    @NotNull
    @Min(0)
    private Integer likesCount;

    @ManyToMany
    @JoinTable(name = "post_likes",
            uniqueConstraints = @UniqueConstraint(name = "UniqueLike",
                    columnNames = {"post_id", "account_id"}),
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "account_id"))
    private Set<Account> likes;

    @NotNull
    @Min(0)
    private Integer dislikesCount;

    @ManyToMany
    @JoinTable(name = "post_dislikes",
            uniqueConstraints = @UniqueConstraint(name = "UniqueDislike",
                    columnNames = {"post_id", "account_id"}),
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "account_id"))
    private Set<Account> dislikes;
}
