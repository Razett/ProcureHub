package com.glkids.procurehub.dto;

import com.glkids.procurehub.entity.Emp;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.List;

/**
 * 로그인한 Emp 정보를 담는 객체
 */
@Getter
@Setter
@ToString
public class UserDTO extends User {

    private Emp emp;

    public UserDTO(Emp emp) {
        super(String.valueOf(emp.getEmpno()), emp.getPw(), List.of(new SimpleGrantedAuthority("ROLE_USER")));

        this.emp = emp;
    }
}
