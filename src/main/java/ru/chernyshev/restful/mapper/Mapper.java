package ru.chernyshev.restful.mapper;

public interface Mapper<ENTITY, DTO> {

    DTO toDto(ENTITY entity);

}
