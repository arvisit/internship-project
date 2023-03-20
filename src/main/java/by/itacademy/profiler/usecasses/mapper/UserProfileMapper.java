package by.itacademy.profiler.usecasses.mapper;


import by.itacademy.profiler.persistence.model.Country;
import by.itacademy.profiler.persistence.model.Position;
import by.itacademy.profiler.persistence.model.UserProfile;
import by.itacademy.profiler.usecasses.dto.UserProfileDto;
import by.itacademy.profiler.usecasses.dto.UserProfileResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserProfileMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "phoneCode", ignore = true)
    @Mapping(target = "position", expression = "java(idToPosition(userProfileDto.positionId()))")
    @Mapping(target = "phoneCode.id", source = "phoneCodeId")
    @Mapping(target = "country", expression = "java(idToCountry(userProfileDto.countryId()))")
    @Mapping(target = "profileImage.uuid", source = "profileImageUuid")
    UserProfile userProfileDtoToUserProfile(UserProfileDto userProfileDto);

    @Mapping(target = "profileImageUuid", source = "profileImage.uuid")
    @Mapping(target = "countryId", source = "country.id")
    @Mapping(target = "country", source = "country.countryName")
    @Mapping(target = "phoneCodeId", source = "phoneCode.id")
    @Mapping(target = "phoneCode", source = "phoneCode.code")
    @Mapping(target = "positionId", source = "position.id")
    @Mapping(target = "position", source = "position.name")
    UserProfileResponseDto userProfileToUserProfileResponseDto(UserProfile userProfile);

    default Country idToCountry(Long countryId) {
        Country country = new Country();
        country.setId(countryId);
        return countryId != null ? country : null;
    }

    default Position idToPosition(Long positionId) {
        Position position = new Position();
        position.setId(positionId);
        return positionId != null ? position : null;
    }
}