package com.kodilla.carrental.mapper;

import com.kodilla.carrental.domain.AdditionalEquipment;
import com.kodilla.carrental.dto.CreateEquipmentDto;
import com.kodilla.carrental.dto.EquipmentDto;
import com.kodilla.carrental.dto.GetEquipmentDto;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class AdditionalEquipmentMapper {

    public AdditionalEquipment mapToAdditionalEquipment (final CreateEquipmentDto createEquipmentDto){
        return new AdditionalEquipment(
                createEquipmentDto.getEquipment(),
                createEquipmentDto.getPrize()
                );
    }

    public List<GetEquipmentDto> mapToEquipmentDtoList (final List<AdditionalEquipment> additionalEquipmentList) {
        return additionalEquipmentList.stream()
                .map(e -> new GetEquipmentDto(
                        e.getId(),
                        e.getEquipment(),
                        e.getPrize()))
                .collect(Collectors.toList());
    }

    public EquipmentDto mapToEquipmentDto (final AdditionalEquipment additionalEquipment) {
        return new EquipmentDto(
                additionalEquipment.getId(),
                additionalEquipment.getEquipment(),
                additionalEquipment.getPrize(),
                additionalEquipment.getCarsList().stream()
                        .map(e->e.getId())
                        .collect(Collectors.toList()));
    }
}
