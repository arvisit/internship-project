package by.itacademy.profiler.util;

import by.itacademy.profiler.persistence.model.Country;
import by.itacademy.profiler.persistence.model.Image;
import by.itacademy.profiler.persistence.model.PhoneCode;
import by.itacademy.profiler.persistence.model.Position;
import by.itacademy.profiler.persistence.model.User;
import by.itacademy.profiler.persistence.model.UserProfile;
import by.itacademy.profiler.usecasses.dto.UserProfileDto;
import by.itacademy.profiler.usecasses.dto.UserProfileResponseDto;

import java.util.Collections;
import java.util.Map;

public class UserProfileTestData {
    private static final String USER_NAME = "user@mail.com";
    private static final String NAME = "Tim";
    private static final String SURNAME = "Roth";
    private static final long COUNTRY_ID = 1;
    private static final String COUNTRY = "Belarus";
    private static final String EMAIL = "tim.roth@itbootcamp.org";
    private static final long PHONE_CODE_ID = 3;
    private static final int PHONE_CODE = 380;
    private static final String CELL_PHONE = "257056517";
    private static final long POSITION_ID = 1;
    private static final String POSITION = "Java developer";
    private static final String PROFILE_IMAGE_UUID = "946de4eb-20d8-47fd-ad8a-73df30a9444a";
    private static final String UNIQUE_STUDENT_INDENTIFIER = "94449";
    private static final Map<Long, String> COUNTRY_MAP = Collections.singletonMap(COUNTRY_ID, COUNTRY);
    private static final Map<Long, Integer> PHONE_CODE_MAP = Collections.singletonMap(PHONE_CODE_ID, PHONE_CODE);
    private static final Map<Long, String> POSITION_MAP = Collections.singletonMap(POSITION_ID, POSITION);

    public static UserProfileDto getValideUserProfileDto() {
        return  new UserProfileDto(
                NAME,
                SURNAME,
                COUNTRY_ID,
                EMAIL,
                PHONE_CODE_ID,
                CELL_PHONE,
                POSITION_ID,
                PROFILE_IMAGE_UUID);
    }

    public static UserProfileResponseDto getUserProfileResponseDto(UserProfileDto userProfileDto) {
        return new UserProfileResponseDto(
                userProfileDto.name(),
                userProfileDto.surname(),
                userProfileDto.countryId(),
                COUNTRY_MAP.get(userProfileDto.countryId()),
                userProfileDto.email(),
                userProfileDto.phoneCodeId(),
                PHONE_CODE_MAP.get(userProfileDto.phoneCodeId()),
                userProfileDto.cellPhone(),
                userProfileDto.positionId(),
                POSITION_MAP.get(userProfileDto.positionId()),
                userProfileDto.profileImageUuid(),
                UNIQUE_STUDENT_INDENTIFIER);
    }

    public static UserProfile getUserProfile(UserProfileDto userProfileDto) {
        UserProfile userProfile = new UserProfile();
        userProfile.setId(getUser().getId());
        userProfile.setName(userProfileDto.name());
        userProfile.setSurname(userProfileDto.surname());
        userProfile.setCountry(getCountry());
        userProfile.setEmail(userProfileDto.email());
        userProfile.setPhoneCode(getPhoneCode());
        userProfile.setCellPhone(userProfileDto.cellPhone());
        userProfile.setPosition(getPosition());
        userProfile.setProfileImage(getImage());
        userProfile.setUniqueStudentIdentifier(UNIQUE_STUDENT_INDENTIFIER);

        return userProfile;
    }

    public static Country getCountry() {
        Country country = new Country();
        country.setId(COUNTRY_ID);
        country.setCountryName(COUNTRY_MAP.get(country.getId()));
        return country;
    }

    public static PhoneCode getPhoneCode() {
        PhoneCode phoneCode = new PhoneCode();
        phoneCode.setId(PHONE_CODE_ID);
        phoneCode.setCode(PHONE_CODE_MAP.get(phoneCode.getId()));
        return phoneCode;
    }

    public static Position getPosition() {
        Position position = new Position();
        position.setId(POSITION_ID);
        position.setName(POSITION_MAP.get(position.getId()));
        return position;
    }

    public static User getUser() {
        User user = new User();
        user.setEmail(USER_NAME);
        return user;
    }

    public static Image getImage() {
        Image image = new Image();
        image.setUuid(PROFILE_IMAGE_UUID);
        return image;
    }

    public static String getJsonUserProfileRequestDto(){
        return """
                {
                    "name": "Tim",
                    "surname": "Roth",
                    "countryId": 1,
                    "email": "tim.roth@itbootcamp.org",
                    "phoneCodeId": 3,
                    "cellPhone": "257056517",
                    "positionId": 1,
                    "profileImageUuid": "946de4eb-20d8-47fd-ad8a-73df30a9444a"
                }
                """;
    }

    public static String getInvalidedNameInJsonUserProfileRequestDto(){
        return """
                {
                    "name": "Тим",
                    "surname": "Roth",
                    "countryId": 1,
                    "email": "tim.roth@itbootcamp.org",
                    "phoneCodeId": 3,
                    "cellPhone": "257056517",
                    "positionId": 1,
                    "profileImageUuid": "946de4eb-20d8-47fd-ad8a-73df30a9444a"
                }
                """;
    }
    public static String getInvalidedSurnameInJsonUserProfileRequestDto(){
        return """
                {
                    "name": "Tim",
                    "surname": "Роз",
                    "countryId": 1,
                    "email": "tim.roth@itbootcamp.org",
                    "phoneCodeId": 3,
                    "cellPhone": "257056517",
                    "positionId": 1,
                    "profileImageUuid": "946de4eb-20d8-47fd-ad8a-73df30a9444a"
                }
                """;
    }

    public static String getInvalidedEmailInJsonUserProfileRequestDto() {
        return """
                {
                    "name": "Tim",
                    "surname": "Roth",
                    "countryId": 1,
                    "email": "tim.rothitbootcamp.org",
                    "phoneCodeId": 3,
                    "cellPhone": "257056517",
                    "positionId": 1,
                    "profileImageUuid": "946de4eb-20d8-47fd-ad8a-73df30a9444a"
                }
                """;
    }

    public static String getInvalidedCellPhoneInJsonUserProfileRequestDto() {
        return """
                {
                    "name": "Tim",
                    "surname": "Roth",
                    "countryId": 1,
                    "email": "tim.roth@itbootcamp.org",
                    "phoneCodeId": 3,
                    "cellPhone": "257s056517",
                    "positionId": 1,
                    "profileImageUuid": "946de4eb-20d8-47fd-ad8a-73df30a9444a"
                }
                """;
    }
}
