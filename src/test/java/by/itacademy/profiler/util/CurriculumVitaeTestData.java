package by.itacademy.profiler.util;

import by.itacademy.profiler.persistence.model.Country;
import by.itacademy.profiler.persistence.model.CurriculumVitae;
import by.itacademy.profiler.persistence.model.CvStatus;
import by.itacademy.profiler.persistence.model.Image;
import by.itacademy.profiler.persistence.model.Position;
import by.itacademy.profiler.persistence.model.User;
import by.itacademy.profiler.usecasses.dto.CurriculumVitaeRequestDto;
import by.itacademy.profiler.usecasses.dto.CurriculumVitaeResponseDto;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Stream;

public class CurriculumVitaeTestData {

    public static final String CV_UUID = "0a5a28ca-e960-420c-af53-50e6f6e80bf2";
    public static final String IMAGE_UUID = "95f5f3cf-ac52-4638-aad1-12d1e836fdd1";
    private static final String NAME = "Name";
    private static final String SURNAME = "Surname";
    private static final long POSITION_ID = 1L;
    private static final String POSITION_NAME = "Some position";
    private static final long COUNTRY_ID = 1L;
    private static final String COUNTRY_NAME = "Some-country";
    private static final String CITY = "City";
    private static final boolean IS_READY_TO_RELOCATE = true;
    private static final boolean IS_READY_FOR_REMOTE_WORK = true;
    private static final Map<Long, String> POSITIONS = Collections.singletonMap(POSITION_ID, POSITION_NAME);
    private static final Map<Long, String> COUNTRIES = Collections.singletonMap(COUNTRY_ID, COUNTRY_NAME);
    private static final boolean IS_CONTACTS_EXISTS = false;
    private static final boolean IS_ABOUT_EXISTS = false;
    private static final String STATUS = CvStatus.DRAFT.name();
    private static final String USER_MAIL_COM = "user@mail.com";
    private static final long CV_ID = 1L;

    public static CurriculumVitaeRequestDto getValidCvRequestDto() {
        return new CurriculumVitaeRequestDto(
                IMAGE_UUID,
                NAME,
                SURNAME,
                POSITION_ID,
                COUNTRY_ID,
                CITY,
                IS_READY_TO_RELOCATE,
                IS_READY_FOR_REMOTE_WORK);
    }

    public static CurriculumVitaeResponseDto getCvResponseDtoByCvRequestDto(CurriculumVitaeRequestDto curriculumVitaeRequestDto) {
        return new CurriculumVitaeResponseDto(
                CV_UUID,
                curriculumVitaeRequestDto.imageUuid(),
                curriculumVitaeRequestDto.name(),
                curriculumVitaeRequestDto.surname(),
                curriculumVitaeRequestDto.positionId(),
                POSITIONS.get(curriculumVitaeRequestDto.positionId()),
                curriculumVitaeRequestDto.countryId(),
                COUNTRIES.get(curriculumVitaeRequestDto.countryId()),
                curriculumVitaeRequestDto.city(),
                curriculumVitaeRequestDto.isReadyToRelocate(),
                curriculumVitaeRequestDto.isReadyForRemoteWork(),
                IS_CONTACTS_EXISTS,
                IS_ABOUT_EXISTS,
                STATUS);
    }

    public static CurriculumVitaeResponseDto getCvResponseDtoByCurriculumVitae(CurriculumVitae curriculumVitae) {
        return new CurriculumVitaeResponseDto(
                curriculumVitae.getUuid(),
                curriculumVitae.getImage().getUuid(),
                curriculumVitae.getName(),
                curriculumVitae.getSurname(),
                curriculumVitae.getPosition().getId(),
                curriculumVitae.getPosition().getName(),
                curriculumVitae.getCountry().getId(),
                curriculumVitae.getCountry().getCountryName(),
                curriculumVitae.getCity(),
                curriculumVitae.getIsReadyToRelocate(),
                curriculumVitae.getIsReadyForRemoteWork(),
                curriculumVitae.getContacts() != null,
                curriculumVitae.getAbout() != null,
                curriculumVitae.getStatus().toString());
    }

    public static CurriculumVitae getCvByCvRequestDto(CurriculumVitaeRequestDto curriculumVitaeRequestDto) {
        CurriculumVitae curriculumVitae = new CurriculumVitae();
        curriculumVitae.setId(CV_ID);
        curriculumVitae.setUuid(CV_UUID);
        curriculumVitae.setUser(getUser());
        curriculumVitae.setImage(getImageByImageUuid(curriculumVitaeRequestDto.imageUuid()));
        curriculumVitae.setName(curriculumVitaeRequestDto.name());
        curriculumVitae.setSurname(curriculumVitaeRequestDto.surname());
        curriculumVitae.setPosition(getPositionByPositionId(curriculumVitaeRequestDto.positionId()));
        curriculumVitae.setCountry(getCountryByCountyId(curriculumVitaeRequestDto.countryId()));
        curriculumVitae.setCity(curriculumVitaeRequestDto.city());
        curriculumVitae.setIsReadyToRelocate(curriculumVitaeRequestDto.isReadyToRelocate());
        curriculumVitae.setIsReadyForRemoteWork(curriculumVitaeRequestDto.isReadyForRemoteWork());
        curriculumVitae.setStatus(getCvStatusByName());
        return curriculumVitae;
    }

    public static CurriculumVitae getValidCurriculumVitae() {
        CurriculumVitae curriculumVitae = new CurriculumVitae();
        curriculumVitae.setId(CV_ID);
        curriculumVitae.setUuid(CV_UUID);
        curriculumVitae.setUser(getUser());
        curriculumVitae.setImage(getImageByImageUuid(IMAGE_UUID));
        curriculumVitae.setName(NAME);
        curriculumVitae.setSurname(SURNAME);
        curriculumVitae.setPosition(getPositionByPositionId(POSITION_ID));
        curriculumVitae.setCountry(getCountryByCountyId(COUNTRY_ID));
        curriculumVitae.setCity(CITY);
        curriculumVitae.setIsReadyToRelocate(IS_READY_TO_RELOCATE);
        curriculumVitae.setIsReadyForRemoteWork(IS_READY_FOR_REMOTE_WORK);
        curriculumVitae.setStatus(getCvStatusByName());
        return curriculumVitae;
    }

    public static List<CurriculumVitae> getListOfCvsOfUser(int listSize) {
        return Stream
                .generate(CurriculumVitaeTestData::getCvWithRandomFields)
                .limit(listSize)
                .toList();
    }

    public static List<CurriculumVitaeResponseDto> getListOfCvResponseDtoFromCvList(List<CurriculumVitae> curriculumVitaeList) {
        return curriculumVitaeList.stream()
                .map(CurriculumVitaeTestData::getCvResponseDtoByCurriculumVitae)
                .toList();
    }

    public static Image getImageByImageUuid(String uuid) {
        Image image = new Image();
        image.setUuid(uuid);
        image.setUser(getUser());
        return image;
    }

    public static User getUser() {
        User user = new User();
        user.setEmail(USER_MAIL_COM);
        return user;
    }

    public static Position getPositionByPositionId(Long id) {
        Position position = new Position();
        position.setId(id);
        position.setName(POSITIONS.get(id));
        return position;
    }

    public static Country getCountryByCountyId(Long id) {
        Country country = new Country();
        country.setId(id);
        country.setCountryName(COUNTRIES.get(id));
        return country;
    }

    private static CvStatus getCvStatusByName() {
        return CvStatus.valueOf(CurriculumVitaeTestData.STATUS);
    }

    private static CurriculumVitae getCvWithRandomFields() {
        CurriculumVitae curriculumVitae = new CurriculumVitae();
        curriculumVitae.setId(new Random().nextLong(1, 50));
        curriculumVitae.setUuid(UUID.randomUUID().toString());
        curriculumVitae.setUser(getUser());
        curriculumVitae.setImage(getImageByImageUuid(UUID.randomUUID().toString()));
        curriculumVitae.setName(getRandomAlphabeticString());
        curriculumVitae.setSurname(getRandomAlphabeticString());
        curriculumVitae.setPosition(getPositionByPositionId(POSITION_ID));
        curriculumVitae.setCountry(getCountryByCountyId(COUNTRY_ID));
        curriculumVitae.setCity(getRandomAlphabeticString());
        curriculumVitae.setIsReadyToRelocate(IS_READY_TO_RELOCATE);
        curriculumVitae.setIsReadyForRemoteWork(IS_READY_FOR_REMOTE_WORK);
        curriculumVitae.setStatus(getCvStatusByName());
        return curriculumVitae;
    }

    private static String getRandomAlphabeticString() {
        int leftLimit = 97;
        int rightLimit = 123;
        int maxSize = 10;
        return new Random().ints(leftLimit, rightLimit)
                .limit(maxSize)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }
}
