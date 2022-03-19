package com.example.boguserms.mapper;

import com.example.boguserms.domain.User;
import com.example.boguserms.dto.*;
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

    @Mappings({
            @Mapping(target = "username", source = "login"),
            @Mapping(target = "uuid", expression = "java(user.getUserId().toString())"),
            @Mapping(target = "password", source = "password"),
            @Mapping(target = "rolesAndAuthorities", expression = "java(java.util.List.of(new org.springframework.security.core.authority.SimpleGrantedAuthority(\"ROLE_USER\")))"),
            @Mapping(target = "accountNonExpired", constant = "true"),
            @Mapping(target = "accountNonLocked", constant = "true"),
            @Mapping(target = "credentialsNonExpired", constant = "true"),
            @Mapping(target = "enabled", constant = "true")
    })
    public abstract UserDetailsDTO userToUserDetailsDTO(User user);

    @Named("encryptPassword")
    protected String encryptPassword(String password) {
        return passwordEncoder.encode(password);
    }
}
