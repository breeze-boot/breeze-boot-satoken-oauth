package com.breeze.boot.core.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GrantedAuthority implements Serializable {

    @Serial
    private static final long serialVersionUID = -4085833706868746461L;

    private String authority;
}