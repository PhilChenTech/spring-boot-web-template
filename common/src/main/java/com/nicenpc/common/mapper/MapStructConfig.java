package com.nicenpc.common.mapper;

import org.mapstruct.MapperConfig;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValueMappingStrategy;

/**
 * MapStruct 全域配置
 * 定義所有 Mapper 的共同配置規則
 */
@MapperConfig(
    componentModel = "default",
    unmappedTargetPolicy = ReportingPolicy.ERROR,
    nullValueCheckStrategy = NullValueCheckStrategy.ON_IMPLICIT_CONVERSION,
    nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL
)
public interface MapStructConfig {
}
