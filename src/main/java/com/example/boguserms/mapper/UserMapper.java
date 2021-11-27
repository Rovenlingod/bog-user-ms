package com.example.boguserms.mapper;

import com.example.boguserms.domain.User;
import com.example.boguserms.dto.UserRequestDTO;
import com.example.boguserms.dto.UserResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "userId", expression = "java(user.getUserId().toString())")
    UserResponseDTO UserToUserResponseDTO(User user);

    User UserRequestDTOToUser(UserRequestDTO userRequestDTO);
}
