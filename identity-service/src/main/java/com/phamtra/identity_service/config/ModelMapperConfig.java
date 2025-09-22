package com.phamtra.identity_service.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        // Cấu hình chiến lược matching (tùy chọn)
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT) // STRICT, STANDARD, hoặc LOOSE
                .setFieldMatchingEnabled(true) // Cho phép matching các trường
                .setSkipNullEnabled(true); // Bỏ qua nếu giá trị null

        // Thêm cấu hình mapping tùy chỉnh (nếu cần)
        // Example: Custom Converter
        modelMapper.addConverter(context -> {
            // Logic chuyển đổi tùy chỉnh
            return context.getSource() == null ? null : context.getSource().toString();
        });

        return modelMapper;
    }
}
