package lk.ijse.spring.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lk.ijse.spring.dto.DriverDTO;
import lk.ijse.spring.entity.Driver;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();

        // Skip id when mapping DriverDTO → Driver
        mapper.addMappings(new PropertyMap<DriverDTO, Driver>() {
            @Override
            protected void configure() {
                skip(destination.getId());
            }
        });

        return mapper;
    }
}
