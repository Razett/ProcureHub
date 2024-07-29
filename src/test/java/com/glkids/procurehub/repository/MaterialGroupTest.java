package com.glkids.procurehub.repository;

import com.glkids.procurehub.entity.MaterialGroup;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MaterialGroupTest {

    @Autowired
    private MaterialGroupRepository materialGroupRepository;

    @Test
    public void initSave() {
        materialGroupRepository.save(MaterialGroup.builder().grpcode("BODY").name("바디").depth(0).build());
        materialGroupRepository.save(MaterialGroup.builder().grpcode("WING").name("날개").depth(0).build());
        materialGroupRepository.save(MaterialGroup.builder().grpcode("ETC").name("기타").depth(0).build());

        // BODY
        materialGroupRepository.save(MaterialGroup.builder().grpcode("BODY_PCB").pGrpcode("BODY").depth(1).name("기판").build());
        materialGroupRepository.save(MaterialGroup.builder().grpcode("BODY_MOD").pGrpcode("BODY").depth(1).name("모듈").build());
        materialGroupRepository.save(MaterialGroup.builder().grpcode("BODY_EXT").pGrpcode("BODY").depth(1).name("외관").build());
        materialGroupRepository.save(MaterialGroup.builder().grpcode("BODY_ETC").pGrpcode("BODY").depth(1).name("기타").build());

        // WING
        materialGroupRepository.save(MaterialGroup.builder().grpcode("WING_MOV").pGrpcode("WING").depth(1).name("동력").build());
        materialGroupRepository.save(MaterialGroup.builder().grpcode("WING_EXT").pGrpcode("WING").depth(1).name("외관").build());
        materialGroupRepository.save(MaterialGroup.builder().grpcode("WING_ETC").pGrpcode("WING").depth(1).name("기타").build());

        materialGroupRepository.save(MaterialGroup.builder().grpcode("ETC_SCREW").pGrpcode("ETC").depth(1).name("나사").build());

        materialGroupRepository.save(MaterialGroup.builder().grpcode("BODY_PCB_FC").pGrpcode("BODY_PCB").depth(2).name("전장부품").build());
        materialGroupRepository.save(MaterialGroup.builder().grpcode("BODY_PCB_PDB").pGrpcode("BODY_PCB").depth(2).name("전원공급장치").build());
        materialGroupRepository.save(MaterialGroup.builder().grpcode("BODY_PCB_ESC").pGrpcode("BODY_PCB").depth(2).name("ESC").build());
        materialGroupRepository.save(MaterialGroup.builder().grpcode("BODY_PCB_TRS").pGrpcode("BODY_PCB").depth(2).name("수신기").build());
        materialGroupRepository.save(MaterialGroup.builder().grpcode("BODY_PCB_TS").pGrpcode("BODY_PCB").depth(2).name("텔레메트리 시스템").build());

        materialGroupRepository.save(MaterialGroup.builder().grpcode("BODY_MOD_CAM").pGrpcode("BODY_MOD").depth(2).name("카메라").build());
        materialGroupRepository.save(MaterialGroup.builder().grpcode("BODY_MOD_BAT").pGrpcode("BODY_MOD").depth(2).name("배터리").build());
        materialGroupRepository.save(MaterialGroup.builder().grpcode("BODY_MOD_LED").pGrpcode("BODY_MOD").depth(2).name("LED").build());
        materialGroupRepository.save(MaterialGroup.builder().grpcode("BODY_MOD_AUD").pGrpcode("BODY_MOD").depth(2).name("오디오 픽업").build());
        materialGroupRepository.save(MaterialGroup.builder().grpcode("BODY_MOD_CAS").pGrpcode("BODY_MOD").depth(2).name("충돌 방지 시스템").build());

        materialGroupRepository.save(MaterialGroup.builder().grpcode("BODY_EXT_GBL").pGrpcode("BODY_EXT").depth(2).name("짐벌").build());
        materialGroupRepository.save(MaterialGroup.builder().grpcode("BODY_EXT_LDG").pGrpcode("BODY_EXT").depth(2).name("착륙장치").build());

        materialGroupRepository.save(MaterialGroup.builder().grpcode("WING_MOV_MOV").pGrpcode("WING_MOV").depth(2).name("동력계").build());
        materialGroupRepository.save(MaterialGroup.builder().grpcode("WING_EXT_BLADE").pGrpcode("WING_EXT").depth(2).name("프로펠러").build());
        materialGroupRepository.save(MaterialGroup.builder().grpcode("WING_EXT_ANT").pGrpcode("WING_EXT").depth(2).name("안테나").build());

    }
}
