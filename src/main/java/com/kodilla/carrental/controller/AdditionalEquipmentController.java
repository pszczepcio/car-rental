package com.kodilla.carrental.controller;

import com.kodilla.carrental.domain.AdditionalEquipment;
import com.kodilla.carrental.dto.CreateEquipmentDto;
import com.kodilla.carrental.dto.EquipmentDto;
import com.kodilla.carrental.dto.GetEquipmentDto;
import com.kodilla.carrental.exception.AdditionalEquipmentNotFoundException;
import com.kodilla.carrental.mapper.AdditionalEquipmentMapper;
import com.kodilla.carrental.service.AdditionalEquipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;

@RestController
@RequestMapping("/v1")
@Transactional
public class AdditionalEquipmentController {

    @Autowired
    private AdditionalEquipmentService additionalEquipmentService;

    @Autowired
    private AdditionalEquipmentMapper additionalEquipmentMapper;

    @RequestMapping(method = RequestMethod.GET, value = "/equipments")
    public List<GetEquipmentDto> getEquipmentList() {
        return additionalEquipmentMapper.mapToEquipmentDtoList(additionalEquipmentService.getEquipmentDtoList());
    }

    @RequestMapping(method = RequestMethod.GET, value = "/equipments/{equipmentsId}")
    public EquipmentDto getEquipmentDto(@PathVariable Long equipmentsId) throws AdditionalEquipmentNotFoundException {
        return additionalEquipmentMapper.mapToEquipmentDto(additionalEquipmentService.getEquipment(equipmentsId).orElseThrow(AdditionalEquipmentNotFoundException::new));
    }

    @RequestMapping(method = RequestMethod.POST, value = "/equipments")
    public AdditionalEquipment createEquipment(@RequestBody CreateEquipmentDto createEquipmentDto) {
        return additionalEquipmentService.saveEquipment(additionalEquipmentMapper.mapToAdditionalEquipment(createEquipmentDto));
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/equipments/{equipmentId}")
    public void deleteEquipment(@PathVariable Long equipmentId) {
        additionalEquipmentService.deleteEquipment(equipmentId);
    }
}
