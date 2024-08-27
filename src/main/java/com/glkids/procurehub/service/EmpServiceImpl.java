package com.glkids.procurehub.service;

import com.glkids.procurehub.dto.EmpCountDTO;
import com.glkids.procurehub.dto.UserDTO;
import com.glkids.procurehub.entity.Emp;
import com.glkids.procurehub.repository.EmpRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmpServiceImpl implements EmpService, UserDetailsService {

    private final EmpRepository empRepository;

    private final PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String empno) throws UsernameNotFoundException {
        Emp emp = empRepository.findByEmpno(Long.valueOf(empno));
        if (emp == null) {
            throw new UsernameNotFoundException("User not found with empno: " + empno);
        }

        // 여기서 비밀번호를 비교할 필요 없이, Spring Security가 알아서 처리하도록 함
        return new UserDTO(emp);
    }

    // 비밀번호 변경 메서드 추가
    public void updatePassword(Long empno, String rawPassword) {
        Emp emp = empRepository.findByEmpno(empno);
        if (emp != null) {
            emp.setPw(passwordEncoder.encode(rawPassword));
            empRepository.save(emp);
        }
    }

    @Override
    public List<String> list() {
        List<Emp> emps = empRepository.findAll();
        List<String> empDTOS = new ArrayList<>();
        emps.forEach(x -> empDTOS.add(x.getName()));
        return empDTOS;
    }

    @Override
    public List<EmpCountDTO> getEmpCounts() {
        return empRepository.findEmpCounts();
    }

}
