package com.example.boguserms.mapper;

import com.example.boguserms.domain.User;
import com.example.boguserms.dto.UserResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "userId", expression = "java(user.getId().toString())")
    UserResponseDTO UserToUserResponseDTO(User user);
}
