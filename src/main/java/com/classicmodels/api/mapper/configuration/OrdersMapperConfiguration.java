package com.classicmodels.api.mapper.configuration;

import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Configuration;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

@Configuration
public class OrdersMapperConfiguration {

//    public ModelMapper modelMapper() {
//        ModelMapper modelMapper = new ModelMapper();
//
//        Converter<String, OffsetDateTime> stringOffsetDateTimeConverter = new AbstractConverter<String, OffsetDateTime>() {
//            @Override
//            protected OffsetDateTime convert(String s) {
//                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//                return OffsetDateTime.parse(s, formatter);
//            }
//        };
//
//        modelMapper.addConverter(stringOffsetDateTimeConverter);
//
//        return modelMapper;
//    }
}
