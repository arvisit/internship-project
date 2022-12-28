package by.itacademy.profiler.usecasses.mapper;


import by.itacademy.profiler.persistence.model.UserProfile;
import by.itacademy.profiler.usecasses.dto.UserProfileDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserProfileMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "position", ignore = true)
    @Mapping(target = "phoneCode", ignore = true)
    @Mapping(target = "country", ignore = true)
    @Mapping(target = "position.id", source = "positionId")
    @Mapping(target = "phoneCode.id", source = "phoneCodeId")
    @Mapping(target = "country.id", source = "countryId")
    UserProfile userProfileDtoToUserProfile(UserProfileDto userProfileDto);
}
