package com.kodilla.carrental.service;

import com.kodilla.carrental.dao.AdditionalEquipmentDao;
import com.kodilla.carrental.domain.AdditionalEquipment;
import com.kodilla.carrental.exception.AdditionalEquipmentNotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AdditionalEquipmentServiceTest {

    @Autowired
    private AdditionalEquipmentService additionalEquipmentService;

    @Test
    public void saveEquipment() {
        //Given
        AdditionalEquipment additionalEquipment = new AdditionalEquipment("cb", 20.0);
        //When
        additionalEquipmentService.saveEquipment(additionalEquipment);
        AdditionalEquipment equipmentAfterSave = additionalEquipmentService.saveEquipment(additionalEquipment);
        //Then
        assertEquals("cb", equipmentAfterSave.getEquipment());
        assertEquals(20.0, equipmentAfterSave.getPrize(), 0.01);
        assertNotNull(equipmentAfterSave.getId());

        //Cleanup
        additionalEquipmentService.deleteEquipment(additionalEquipment.getId());
    }

    @Test
    public void shouldGetAdditionalEquipment() throws AdditionalEquipmentNotFoundException {
        //Given
        AdditionalEquipment additionalEquipment = new AdditionalEquipment("towbar", 30.0);

        //When
        additionalEquipmentService.saveEquipment(additionalEquipment);
        AdditionalEquipment equipmentAfterSave = additionalEquipmentService.getEquipment(additionalEquipment.getId())
                .orElseThrow(AdditionalEquipmentNotFoundException::new);
        String equipment = equipmentAfterSave.getEquipment();
        double prize = equipmentAfterSave.getPrize();
        Long equipmentId = equipmentAfterSave.getId();

        //Then
        assertEquals("towbar", equipment);
        assertEquals(30.0, prize, 0.1);
        assertEquals(additionalEquipment.getId(), equipmentId);
        assertNotNull(equipmentId);

        //Cleanup
        additionalEquipmentService.deleteEquipment(additionalEquipment.getId());
    }

    @Test
    public void shouldGetAdditionalEquipmentList()  {
        //Given
        AdditionalEquipment towbar = new AdditionalEquipment("towbar", 30.0);
        AdditionalEquipment cbRadio = new AdditionalEquipment("cb-Radio", 35.0);
        AdditionalEquipment railings = new AdditionalEquipment("railings", 32.50);
        List<AdditionalEquipment> equipmentList;

        //When
         additionalEquipmentService.saveEquipment(towbar);
         additionalEquipmentService.saveEquipment(cbRadio);
         additionalEquipmentService.saveEquipment(railings);
         equipmentList = additionalEquipmentService.getEquipmentList();
         int sizeList = equipmentList.size();
         String equipmentOne = equipmentList.get(0).getEquipment();
         double prizeOne = equipmentList.get(0).getPrize();
         Long idOne = equipmentList.get(0).getId();

        String equipmentThree = equipmentList.get(2).getEquipment();
        double prizeThree = equipmentList.get(2).getPrize();
        Long idThree = equipmentList.get(2).getId();

        //Then
        assertEquals(3, sizeList);
        assertEquals("towbar", equipmentOne);
        assertEquals(30.0, prizeOne, 0.1);
        assertEquals(towbar.getId(), idOne);
        assertEquals("railings", equipmentThree);
        assertEquals(32.5, prizeThree, 0.1);
        assertEquals(railings.getId(), idThree);

        //Cleanup
        additionalEquipmentService.deleteEquipment(railings.getId());
        additionalEquipmentService.deleteEquipment(cbRadio.getId());
        additionalEquipmentService.deleteEquipment(towbar.getId());
    }

    @Test
    public void shouldDeleteAdditionalEquipmen()  {
        //Given
        AdditionalEquipment towbar = new AdditionalEquipment("towbar", 30.0);
        AdditionalEquipment cbRadio = new AdditionalEquipment("cb-Radio", 35.0);
        AdditionalEquipment railings = new AdditionalEquipment("railings", 32.50);
        List<AdditionalEquipment> equipmentList;
        List<AdditionalEquipment> equipmentListAfterDelete;

        //When
        additionalEquipmentService.saveEquipment(towbar);
        additionalEquipmentService.saveEquipment(cbRadio);
        additionalEquipmentService.saveEquipment(railings);
        equipmentList = additionalEquipmentService.getEquipmentList();
        int equipmentSize = equipmentList.size();
        Long idOne = towbar.getId();
        Long idTwo = cbRadio.getId();
        Long idThree = railings.getId();

        //Then
        assertEquals(3, equipmentSize);
        additionalEquipmentService.deleteEquipment(idOne);
        additionalEquipmentService.deleteEquipment(idTwo);
        additionalEquipmentService.deleteEquipment(idThree);
        equipmentListAfterDelete = additionalEquipmentService.getEquipmentList();
        int equipmentListSizeAfterDelete = equipmentListAfterDelete.size();
        assertEquals(0, equipmentListSizeAfterDelete);
    }
}