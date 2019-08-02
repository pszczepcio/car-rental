package com.kodilla.carrental.domain;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AdditionalEquipmentTest {

    @Test
    public void AdditionalEqupmnetConstructorTest(){
        //Given
        AdditionalEquipment additionalEquipment = new AdditionalEquipment("cb", 20.0 );
        //When

        //Then
        assertEquals("cb", additionalEquipment.getEquipment());
        assertEquals(20.0, additionalEquipment.getPrize(),0.1);
    }
}
