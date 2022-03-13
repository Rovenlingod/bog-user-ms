package com.example.boguserms.mapper;

import com.example.boguserms.domain.User;
import com.example.boguserms.dto.LoginSearchResponseDTO;
import com.example.boguserms.dto.OAuthUserDTO;
import com.example.boguserms.dto.UserRequestDTO;
import com.example.boguserms.dto.UserResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Mapper(componentModel = "spring")
public abstract class UserMapper {

    @Autowired
    protected PasswordEncoder passwordEncoder;


    @Mapping(target = "userId", expression = "java(user.getUserId().toString())")
    public abstract UserResponseDTO UserToUserResponseDTO(User user);

    @Mappings({
            @Mapping(target = "id", expression = "java(user.getUserId().toString())"),
            @Mapping(target = "encryptedPassword", source = "password")
    })
    public abstract LoginSearchResponseDTO toLoginDTO(User user);

    @Mapping(target = "password", source = "password", qualifiedByName = "encryptPassword")
    public abstract User UserRequestDTOToUser(UserRequestDTO userRequestDTO);

    @Mappings({
            @Mapping(target = "login", source = "email"),
            @Mapping(target = "firstName", source = "name"),
            @Mapping(target = "password", expression = "java(this.encryptPassword(java.util.UUID.randomUUID().toString()))")
    })
    public abstract User oAuthDTOToUser(OAuthUserDTO oAuthUserDTO);

    @Named("encryptPassword")
    protected String encryptPassword(String password) {
        return passwordEncoder.encode(password);
    }
}
