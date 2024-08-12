package com.glkids.procurehub.service;

import com.glkids.procurehub.entity.Emp;
import com.glkids.procurehub.repository.EmpRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

/**
 * 사원번호와 비밀번호만 접근할 수 있는 객체를 리턴하기에 더 이상 사용되지 않습니다.
 */
@Deprecated
@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private EmpRepository empRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String empno) throws UsernameNotFoundException {
        Emp emp = empRepository.findByEmpno(Long.valueOf(empno));
        if (emp == null) {
            throw new UsernameNotFoundException("User not found with empno: " + empno);
        }

        // 여기서 비밀번호를 비교할 필요 없이, Spring Security가 알아서 처리하도록 함
        return new User(emp.getEmpno().toString(), emp.getPw(), Collections.emptyList());
    }

    // 비밀번호 변경 메서드 추가
    public void updatePassword(Long empno, String rawPassword) {
        Emp emp = empRepository.findByEmpno(empno);
        if (emp != null) {
            emp.setPw(passwordEncoder.encode(rawPassword));
            empRepository.save(emp);
        }
    }
}
