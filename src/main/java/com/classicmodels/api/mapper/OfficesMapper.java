package com.classicmodels.api.mapper;

import com.classicmodels.api.model.OfficesRepModel;
import com.classicmodels.api.model.input.OfficesInput;
import com.classicmodels.domain.model.Offices;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Component
public class OfficesMapper {

    private ModelMapper modelMapper;

    public OfficesRepModel toModel(Offices offices) {
//        return new OfficesRepModel(offices);
        return modelMapper.map(offices, OfficesRepModel.class);
    }

    public List<OfficesRepModel> toCollectionModel(List<Offices> offices) {
        return offices.stream()
                .map(this::toModel)
                .collect(Collectors.toList());
    }

    public Offices toEntity(OfficesInput officesInput) {
        return modelMapper.map(officesInput, Offices.class);
    }

}
