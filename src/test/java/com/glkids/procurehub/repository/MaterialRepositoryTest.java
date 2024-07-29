package com.glkids.procurehub.repository;

import com.glkids.procurehub.entity.Material;
import com.glkids.procurehub.entity.MaterialGroup;
import com.glkids.procurehub.entity.MaterialWarehouse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MaterialRepositoryTest {

    @Autowired
    MaterialRepository materialRepository;

    @Test
    public void initSave(){
        MaterialGroup group = MaterialGroup.builder().grpcode("BODY_PCB_FC").build();
        MaterialWarehouse house = MaterialWarehouse.builder().wrhscode("A-S1-01").build();
        materialRepository.save(Material.builder().materialWarehouse(house).materialGroup(group).name("Sparky").quantity(10L).standard("47.0*59.5*4.5mm 1EA").status(0).build());
        materialRepository.save(Material.builder().materialWarehouse(house).materialGroup(group).name("ARM-2.6").quantity(8L).standard("48.0*60.5*5.5mm 1EA").status(0).build());
        materialRepository.save(Material.builder().materialWarehouse(house).materialGroup(group).name("ARM-2.8").quantity(6L).standard("48.0*60.5*5.5mm 1EA").status(0).build());
        group = MaterialGroup.builder().grpcode("BODY_PCB_PDB").build();
        house = MaterialWarehouse.builder().wrhscode("A-S1-02").build();
        materialRepository.save(Material.builder().materialWarehouse(house).materialGroup(group).name("PDB50W").quantity(15L).standard("7.0*13.0*3.5mm 50W 1EA").status(0).build());
        materialRepository.save(Material.builder().materialWarehouse(house).materialGroup(group).name("PDB30W").quantity(10L).standard("7.0*13.0*3.5mm 30W 1EA").status(0).build());
        materialRepository.save(Material.builder().materialWarehouse(house).materialGroup(group).name("PDB25W").quantity(5L).standard("6.5*12.2*3.5mm 25W 1EA").status(0).build());
        group = MaterialGroup.builder().grpcode("WING_MOV_MOV").build();
        house = MaterialWarehouse.builder().wrhscode("A-S1-03").build();
        materialRepository.save(Material.builder().materialWarehouse(house).materialGroup(group).name("DC200R").quantity(7L).standard("25.5*25.5*50.0mm 1EA").status(0).build());
        materialRepository.save(Material.builder().materialWarehouse(house).materialGroup(group).name("DC150R").quantity(8L).standard("23.0*23.0*45.0mm 1EA").status(0).build());
        materialRepository.save(Material.builder().materialWarehouse(house).materialGroup(group).name("DC100R").quantity(7L).standard("18.5*18.5*42.5mm 1EA").status(0).build());
        materialRepository.save(Material.builder().materialWarehouse(house).materialGroup(group).name("BLDC250R").quantity(5L).standard("25.5*25.5*50.0mm 1EA").status(0).build());
        materialRepository.save(Material.builder().materialWarehouse(house).materialGroup(group).name("BLDC200R").quantity(7L).standard("23.0*23.0*45.0mm 1EA").status(0).build());
        materialRepository.save(Material.builder().materialWarehouse(house).materialGroup(group).name("BLDC180R").quantity(12L).standard("18.5*18.5*42.5mm 1EA").status(0).build());
        group = MaterialGroup.builder().grpcode("WING_EXT_ANT").build();
        house = MaterialWarehouse.builder().wrhscode("A-S2-01").build();
        materialRepository.save(Material.builder().materialWarehouse(house).materialGroup(group).name("ANT5G90").quantity(11L).standard("90.0*5.0*2.0mm 1EA").status(0).build());
        materialRepository.save(Material.builder().materialWarehouse(house).materialGroup(group).name("ANT5G70").quantity(8L).standard("70.0*5.0*2.0mm 1EA").status(0).build());
        materialRepository.save(Material.builder().materialWarehouse(house).materialGroup(group).name("ANT2G90").quantity(10L).standard("90.0*5.0*2.0mm 1EA").status(0).build());
        materialRepository.save(Material.builder().materialWarehouse(house).materialGroup(group).name("ANT2G70").quantity(5L).standard("70.0*5.0*1.5mm 1EA").status(0).build());
        group = MaterialGroup.builder().grpcode("BODY_MOD_CAM").build();
        house = MaterialWarehouse.builder().wrhscode("A-S2-02").build();
        materialRepository.save(Material.builder().materialWarehouse(house).materialGroup(group).name("CAM0001").quantity(30L).standard("20.0*20*19.0mm  1080p 1EA").status(0).build());
        materialRepository.save(Material.builder().materialWarehouse(house).materialGroup(group).name("CAM0002").quantity(11L).standard("30.0*25*20.0mm 1100p 1EA").status(0).build());
        group = MaterialGroup.builder().grpcode("BODY_MOD_BAT").build();
        house = MaterialWarehouse.builder().wrhscode("A-S3-01").build();
        materialRepository.save(Material.builder().materialWarehouse(house).materialGroup(group).name("BAT100D").quantity(15L).standard("68.0*15.0*136.0mm 1EA NiCd-10000mAh").status(0).build());
        materialRepository.save(Material.builder().materialWarehouse(house).materialGroup(group).name("BAT50D").quantity(16L).standard("67.7*117*10.9mm 1EA NiCd-5000mAh").status(0).build());
        materialRepository.save(Material.builder().materialWarehouse(house).materialGroup(group).name("BAT25D").quantity(15L).standard("60.0*30.0*72.0mm 1EA NiCd-2500mAh").status(0).build());
        materialRepository.save(Material.builder().materialWarehouse(house).materialGroup(group).name("BAT100H").quantity(16L).standard("68.0*15.0*136.0mm 1EA NiCh-10000mAh").status(0).build());
        materialRepository.save(Material.builder().materialWarehouse(house).materialGroup(group).name("BAT50H").quantity(15L).standard("67.7*117*10.9mm 1EA NiCh-5000mAh").status(0).build());
        materialRepository.save(Material.builder().materialWarehouse(house).materialGroup(group).name("BAT25H").quantity(16L).standard("60.0*30.0*72.0mm 1EA NiCh-2500mAh").status(0).build());
        group = MaterialGroup.builder().grpcode("BODY_PCB_ESC").build();
        house = MaterialWarehouse.builder().wrhscode("A-S3-02").build();
        materialRepository.save(Material.builder().materialWarehouse(house).materialGroup(group).name("ESC0001").quantity(10L).standard("25.0*15.0*5.0mm 20A 1EA").status(0).build());
        materialRepository.save(Material.builder().materialWarehouse(house).materialGroup(group).name("ESC0002").quantity(18L).standard("35.0*20.0*10mm 30A 1EA").status(0).build());
        group = MaterialGroup.builder().grpcode("ETC_SCREW").build();
        house = MaterialWarehouse.builder().wrhscode("A-S3-03").build();
        materialRepository.save(Material.builder().materialWarehouse(house).materialGroup(group).name("SCREW0001").quantity(50L).standard("2.0*2.5*3.0mm 10PCS 1EA").status(0).build());
        materialRepository.save(Material.builder().materialWarehouse(house).materialGroup(group).name("SCREW0002").quantity(100L).standard("3.0*4.5*5.0mm 10PCS 1EA").status(0).build());
        group = MaterialGroup.builder().grpcode("BODY_PCB").build();
        house = MaterialWarehouse.builder().wrhscode("A-S3-04").build();
        materialRepository.save(Material.builder().materialWarehouse(house).materialGroup(group).name("PCB0001").quantity(5L).standard("76.0*102.0*127.0mm 1EA").status(0).build());
        materialRepository.save(Material.builder().materialWarehouse(house).materialGroup(group).name("PCB0002").quantity(80L).standard("152.0*203.0*254.0mm 1EA").status(0).build());
        group = MaterialGroup.builder().grpcode("BODY_MOD").build();
        house = MaterialWarehouse.builder().wrhscode("A-S3-05").build();
        materialRepository.save(Material.builder().materialWarehouse(house).materialGroup(group).name("GPS0001").quantity(40L).standard("40.0*15.0mm 1EA").status(0).build());
        materialRepository.save(Material.builder().materialWarehouse(house).materialGroup(group).name("GPS0002").quantity(30L).standard("38.0*13.0mm 1EA").status(0).build());
        group = MaterialGroup.builder().grpcode("BODY_PCB_TRS").build();
        house = MaterialWarehouse.builder().wrhscode("B-S1-01").build();
        materialRepository.save(Material.builder().materialWarehouse(house).materialGroup(group).name("TRS0001").quantity(20L).standard("45.0*23.0*10.0mm 2.4GHz 1EA").status(0).build());
        materialRepository.save(Material.builder().materialWarehouse(house).materialGroup(group).name("TRS0002").quantity(60L).standard("40.0*20.0*10.0mm 5.8GHz 1EA").status(0).build());
        group = MaterialGroup.builder().grpcode("BODY_EXT_GBL").build();
        house = MaterialWarehouse.builder().wrhscode("B-S1-02").build();
        materialRepository.save(Material.builder().materialWarehouse(house).materialGroup(group).name("GBL0001").quantity(20L).standard("120.0*90.0*90mm 200g").status(0).build());
        materialRepository.save(Material.builder().materialWarehouse(house).materialGroup(group).name("GBL0002").quantity(38L).standard("150.0*100.0*100mm 250g").status(0).build());
        group = MaterialGroup.builder().grpcode("BODY_MOD_LED").build();
        house = MaterialWarehouse.builder().wrhscode("B-S1-03").build();
        materialRepository.save(Material.builder().materialWarehouse(house).materialGroup(group).name("LED0001").quantity(17L).standard("5.0*5.0mm 5V").status(0).build());
        materialRepository.save(Material.builder().materialWarehouse(house).materialGroup(group).name("LED0002").quantity(18L).standard("3.5*2.8mm 12V").status(0).build());
        group = MaterialGroup.builder().grpcode("BODY_EXT_LDG").build();
        house = MaterialWarehouse.builder().wrhscode("B-S1-04").build();
        materialRepository.save(Material.builder().materialWarehouse(house).materialGroup(group).name("LDG0001").quantity(7L).standard("100.0*120.0*150.0mm").status(0).build());
        materialRepository.save(Material.builder().materialWarehouse(house).materialGroup(group).name("LDG0002").quantity(81L).standard("110.0*125.0*152.0mm").status(0).build());
        group = MaterialGroup.builder().grpcode("BODY_PCB_TS").build();
        house = MaterialWarehouse.builder().wrhscode("B-S1-05").build();
        materialRepository.save(Material.builder().materialWarehouse(house).materialGroup(group).name("TS0001").quantity(28L).standard("30.0*15.0*10mm 9600bps 433MHz").status(0).build());
        materialRepository.save(Material.builder().materialWarehouse(house).materialGroup(group).name("TS0002").quantity(50L).standard("40.0*20.0*15.0mm 57600bps 868MHz").status(0).build());
        group = MaterialGroup.builder().grpcode("BODY_MOD_AUD").build();
        house = MaterialWarehouse.builder().wrhscode("B-S1-06").build();
        materialRepository.save(Material.builder().materialWarehouse(house).materialGroup(group).name("AUD0001").quantity(28L).standard("10.0*20.0*5.0mm 2V 20Hz").status(0).build());
        materialRepository.save(Material.builder().materialWarehouse(house).materialGroup(group).name("AUD0002").quantity(50L).standard("20.0*10.0mm 5V 20kHz").status(0).build());
        group = MaterialGroup.builder().grpcode("BODY_MOD_CAS").build();
        house = MaterialWarehouse.builder().wrhscode("B-S2-01").build();
        materialRepository.save(Material.builder().materialWarehouse(house).materialGroup(group).name("CAS0001").quantity(28L).standard("100.0*50.0*50.0mm 200g").status(0).build());
        materialRepository.save(Material.builder().materialWarehouse(house).materialGroup(group).name("CAS0002").quantity(50L).standard("120.0*60.0*60.0mm 250g").status(0).build());




    }
}
