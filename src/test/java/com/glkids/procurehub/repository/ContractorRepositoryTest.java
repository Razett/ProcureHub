package com.glkids.procurehub.repository;

import com.glkids.procurehub.entity.Contractor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ContractorRepositoryTest {

    @Autowired
    private ContractorRepository contractorRepository;

    @Test
    public void initSave() {
        contractorRepository.save(Contractor.builder().corno(4618400054L).name("A 회사").phone("0313892800").address1("경기 안양시 동안구 학의로 282(관양동,금강펜테리움IT타워)").address2("12층 1227호").mngrName("김철수").mngrPhone("01052648548").mngrAddress("asd184@gmail.com").build());
        contractorRepository.save(Contractor.builder().corno(3678103535L).name("B 주식회사").phone("025804200").address1("서울특별시 금천구 가산디지털2로 24(가산동)").mngrName("신짱구").mngrPhone("01094873876").mngrAddress("dfg134@naver.com").build());
        contractorRepository.save(Contractor.builder().corno(1078602808L).name("C 물류").phone("0316954230").address1("경기도 화성시 우정읍 평밭길 92-9").mngrName("이훈이").mngrPhone("01020053374").mngrAddress("vacd3562@gmail.com").build());
        contractorRepository.save(Contractor.builder().corno(5210302597L).name("D 컴퍼니").phone("025193200").address1("서울 강남구 역삼로 228(역삼동)").address2("403호").mngrName("사유리").mngrPhone("01045859952").mngrAddress("thecx522@gmail.com").build());
        contractorRepository.save(Contractor.builder().corno(3018630037L).name("E 회사").phone("0432294200").address1("충청북도 괴산군 괴산읍 문무로 78").mngrName("김맹구").mngrPhone("01063866723").mngrAddress("ked44@naver.com").build());
        contractorRepository.save(Contractor.builder().corno(5338703115L).name("F 로지스").phone("0533502900").address1("대구광역시 북구 한강로8길 14-2(금호동)").mngrName("나미리").mngrPhone("01072842271").mngrAddress("sad7284@gmail.com").build());
        contractorRepository.save(Contractor.builder().corno(1248171120L).name("Geeks").phone("0312157341").address1("경기도 수원시 권선구 산업로155번길 111-0(고색동)").mngrName("김종현").mngrPhone("01021517384").mngrAddress("Geeks545@gmail.com").build());
        contractorRepository.save(Contractor.builder().corno(8730502527L).name("H 상사").phone("0336399200").address1("강원 속초시 중앙로 48-1(교동)").address2("2층").mngrName("이속초").mngrPhone("01025758789").mngrAddress("Sokgang481@gmail.com").build());
        contractorRepository.save(Contractor.builder().corno(5658103027L).name("IDE").phone("0230116200").address1("서울 서초구 서초중앙로 56(서초동, 블루타워").address2("2층").mngrName("손나은").mngrPhone("01050114674").mngrAddress("hand4674@gmail.com").build());
        contractorRepository.save(Contractor.builder().corno(4141400394L).name("Java").phone("0512400200").address1("부산광역시 영도구 해양로65번길 27").mngrName("김상술").mngrPhone("01021713527").mngrAddress("sangsuritree414@naver.com").build());
        contractorRepository.save(Contractor.builder().corno(1348143029L).name("keds").phone("0221142200").address1("경기도 안산시 단원구 정왕천동로30번길 12(성곡동)").mngrName("김광한").mngrPhone("01014955805").mngrAddress("changjo25@gmail.com").build());
        contractorRepository.save(Contractor.builder().corno(1362528445L).name("Love").phone("0318807265").address1("경기도 하남시 미사강변한강로 270-1").mngrName("영케이").mngrPhone("01010044458").mngrAddress("jypyoung@naver.com").build());
        contractorRepository.save(Contractor.builder().corno(1878600107L).name("Monster").phone("027654321").address1("서울특별시 금천구 시흥대로 97").address2("26동 304호").mngrName("김아현").mngrPhone("01065485125").mngrAddress("pharmacy@gmail.com").build());
        contractorRepository.save(Contractor.builder().corno(3052252929L).name("Nobrand").phone("0422298210").address1("대전광역시 중구 문화로46번길 9").mngrName("이창덕").mngrPhone("01033118812").mngrAddress("nojam@naver.com").build());
        contractorRepository.save(Contractor.builder().corno(5098603056L).name("Orange").phone("0617205834").address1("전라남도 순천시 해룡면 율촌산단1로 19-50(해룡면)").mngrName("김현대").mngrPhone("01061023634").mngrAddress("dragon032@naver.com").build());



    }
}
