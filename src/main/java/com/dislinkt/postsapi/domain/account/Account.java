package com.dislinkt.postsapi.domain.account;

import com.dislinkt.postsapi.domain.base.BaseEntity;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Entity
public class Account extends BaseEntity {

    @NotNull
    @Size(max = 36)
    @Column(unique = true)
    private String username;

    @NotNull
    @Size(max = 128)
    private String name;
}
