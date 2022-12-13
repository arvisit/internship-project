package by.itacademy.profiler.usecasses.mapper;

import by.itacademy.profiler.persistence.model.Role;
import by.itacademy.profiler.persistence.model.RoleNameEnum;
import by.itacademy.profiler.persistence.model.User;
import by.itacademy.profiler.usecasses.dto.AuthenticationUserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")

public interface AuthenticationUserMapper {

    @Mapping(target = "roleNames", expression = "java(rolesToRoleNames(user.getRoles()))")
    AuthenticationUserDto userToAuthenticationUserDto(User user);

    default Set<RoleNameEnum> rolesToRoleNames(Set<Role> roles) {
        return roles.stream().map(Role::getName).collect(Collectors.toSet());
    }
}
