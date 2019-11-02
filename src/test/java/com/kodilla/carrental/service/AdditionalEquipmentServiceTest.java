package com.kodilla.carrental.service;

import com.kodilla.carrental.dao.AdditionalEquipmentDao;
import com.kodilla.carrental.domain.AdditionalEquipment;
import com.kodilla.carrental.exception.AdditionalEquipmentNotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AdditionalEquipmentServiceTest {
    @InjectMocks
    private AdditionalEquipmentService additionalEquipmentService;

    @Mock
    private AdditionalEquipmentDao additionalEquipmentDao;

    @Test
    public void saveEquipment() {
        //Given
        AdditionalEquipment additionalEquipment = new AdditionalEquipment("cb", 20.0);
        AdditionalEquipment additionalEquipmentAfterSave = new AdditionalEquipment(
                1L, "cb", 20.0, new ArrayList<>());

        //When && Then
        when(additionalEquipmentDao.save(additionalEquipment)).thenReturn(additionalEquipmentAfterSave);
        AdditionalEquipment equipment = additionalEquipmentService.saveEquipment(additionalEquipment);
        //Then
        assertEquals(1L, equipment.getId().longValue());
        assertEquals("cb", equipment.getEquipment());
        assertEquals(20.0, equipment.getPrize(), 0.01);
        assertEquals(new ArrayList<>(), equipment.getCarsList());
    }

    @Test
    public void shouldGetAdditionalEquipment() throws AdditionalEquipmentNotFoundException {
        //Given
        Long id = 1L;
        AdditionalEquipment additionalEquipment = new AdditionalEquipment(1L, "towbar", 30.0, new ArrayList<>());

        //When && Then
        when(additionalEquipmentDao.findById(id)).thenReturn(Optional.of(additionalEquipment));
        AdditionalEquipment equipment = additionalEquipmentService.getEquipment(id).orElseThrow(AdditionalEquipmentNotFoundException::new);

        assertEquals(1L, equipment.getId().longValue());
        assertEquals("towbar", equipment.getEquipment());
        assertEquals(30, equipment.getPrize(), 0.01);
        assertEquals(new ArrayList<>(), equipment.getCarsList());
    }

    @Test
    public void shouldGetAdditionalEquipmentList()  {
        //Given
        AdditionalEquipment towbar = new AdditionalEquipment(1L, "towbar", 30.0, new ArrayList<>());
        AdditionalEquipment cbRadio = new AdditionalEquipment(2L, "cb-Radio", 35.0, new ArrayList<>());
        AdditionalEquipment railings = new AdditionalEquipment(3L, "railings", 32.50, new ArrayList<>());
        List<AdditionalEquipment> equipmentList = new ArrayList<>();
        equipmentList.add(towbar);
        equipmentList.add(cbRadio);
        equipmentList.add(railings);

        //When && Then
        when(additionalEquipmentService.getEquipmentList()).thenReturn(equipmentList);
        List<AdditionalEquipment> equipments = additionalEquipmentService.getEquipmentList();
        //Then
        assertEquals(3, equipments.size());
        assertEquals("towbar", equipments.get(0).getEquipment());
        assertEquals(1L, equipments.get(0).getId().longValue());
        assertEquals("cb-Radio", equipments.get(1).getEquipment());
        assertEquals("railings", equipments.get(2).getEquipment());
        assertEquals(32.5, equipments.get(2).getPrize(), 0.1);
    }

    @Test
    public void shouldDeleteEquipment() {
        //Given
        Long id = 1L;

        //When && Then
        additionalEquipmentService.deleteEquipment(id);
        verify(additionalEquipmentDao, times(1)).deleteById(id);
    }
}