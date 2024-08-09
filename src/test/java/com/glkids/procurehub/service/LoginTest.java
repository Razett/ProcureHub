package com.glkids.procurehub.service;

import com.glkids.procurehub.entity.Emp;
import com.glkids.procurehub.repository.EmpRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
public class LoginTest {
    @Autowired
    private EmpRepository empRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void test() {
        String pw ="1234";
        System.out.println(passwordEncoder.encode(pw).length());
        System.out.println(passwordEncoder.encode(pw));
        System.out.println(passwordEncoder.encode(pw));

    }
}
