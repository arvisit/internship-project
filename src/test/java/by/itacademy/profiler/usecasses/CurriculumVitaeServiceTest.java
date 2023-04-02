package by.itacademy.profiler.usecasses;

import by.itacademy.profiler.persistence.model.CurriculumVitae;
import by.itacademy.profiler.persistence.model.User;
import by.itacademy.profiler.persistence.repository.CountryRepository;
import by.itacademy.profiler.persistence.repository.CurriculumVitaeRepository;
import by.itacademy.profiler.persistence.repository.ImageRepository;
import by.itacademy.profiler.persistence.repository.PositionRepository;
import by.itacademy.profiler.persistence.repository.UserRepository;
import by.itacademy.profiler.usecasses.dto.CurriculumVitaeRequestDto;
import by.itacademy.profiler.usecasses.dto.CurriculumVitaeResponseDto;
import by.itacademy.profiler.usecasses.impl.CurriculumVitaeServiceImpl;
import by.itacademy.profiler.usecasses.mapper.CurriculumVitaeMapper;
import by.itacademy.profiler.usecasses.util.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static by.itacademy.profiler.util.CurriculumVitaeTestData.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CurriculumVitaeServiceTest {
    @Mock
    private CurriculumVitaeMapper curriculumVitaeMapper;
    @Mock
    private UserRepository userRepository;
    @Mock
    private ImageRepository imageRepository;
    @Mock
    private PositionRepository positionRepository;
    @Mock
    private CountryRepository countryRepository;
    @Mock
    private ImageService imageService;
    @Mock
    private CurriculumVitaeRepository curriculumVitaeRepository;
    @Mock
    private AuthService authService;
    @InjectMocks
    private CurriculumVitaeServiceImpl curriculumVitaeService;

    @BeforeEach
    void setUp() {
        when(authService.getUsername()).thenReturn(getUser().getEmail());
    }

    @Test
    void shouldReturnSavedCvResponseDtoWhenSendCvRequestDtoTo_Save() {

        CurriculumVitaeRequestDto curriculumVitaeRequestDto = getValidCvRequestDto();
        CurriculumVitaeResponseDto curriculumVitaeResponseDto = getCvResponseDtoByCvRequestDto(curriculumVitaeRequestDto);
        CurriculumVitae curriculumVitae = getCvByCvRequestDto(curriculumVitaeRequestDto);
        stubbingForSave(curriculumVitaeRequestDto, curriculumVitaeResponseDto, curriculumVitae);

        CurriculumVitaeResponseDto savedCurriculumVitaeResponseDto = curriculumVitaeService.save(curriculumVitaeRequestDto);

        assertEquals(curriculumVitaeRequestDto.imageUuid(), savedCurriculumVitaeResponseDto.imageUuid());
        assertEquals(curriculumVitaeRequestDto.name(), savedCurriculumVitaeResponseDto.name());
        assertEquals(curriculumVitaeRequestDto.surname(), savedCurriculumVitaeResponseDto.surname());
        assertEquals(curriculumVitaeRequestDto.positionId(), savedCurriculumVitaeResponseDto.positionId());
        assertNotNull(savedCurriculumVitaeResponseDto.position());
        assertEquals(curriculumVitae.getPosition().getName(), savedCurriculumVitaeResponseDto.position());
        assertEquals(curriculumVitaeRequestDto.countryId(), savedCurriculumVitaeResponseDto.countryId());
        assertNotNull(savedCurriculumVitaeResponseDto.country());
        assertEquals(curriculumVitae.getCountry().getCountryName(), savedCurriculumVitaeResponseDto.country());
        assertEquals(curriculumVitaeRequestDto.city(), savedCurriculumVitaeResponseDto.city());
        assertEquals(curriculumVitaeRequestDto.isReadyToRelocate(), savedCurriculumVitaeResponseDto.isReadyToRelocate());
        assertEquals(curriculumVitaeRequestDto.isReadyForRemoteWork(), savedCurriculumVitaeResponseDto.isReadyForRemoteWork());
        assertNotNull(savedCurriculumVitaeResponseDto.status());
        assertEquals(curriculumVitae.getStatus().name(), savedCurriculumVitaeResponseDto.status());
    }

    @Test
    void shouldCallRepositoryLevelAndMapperWhenSendCvRequestDtoTo_Save() {

        CurriculumVitaeRequestDto curriculumVitaeRequestDto = getValidCvRequestDto();
        CurriculumVitaeResponseDto curriculumVitaeResponseDto = getCvResponseDtoByCvRequestDto(curriculumVitaeRequestDto);
        CurriculumVitae curriculumVitae = getCvByCvRequestDto(curriculumVitaeRequestDto);
        stubbingForSave(curriculumVitaeRequestDto, curriculumVitaeResponseDto, curriculumVitae);

        curriculumVitaeService.save(curriculumVitaeRequestDto);

        verify(authService, times(1)).getUsername();
        verify(curriculumVitaeRepository, times(1)).save(any(CurriculumVitae.class));
        verify(curriculumVitaeMapper, times(1)).curriculumVitaeToCurriculumVitaeResponseDto(curriculumVitae);
        verify(userRepository, times(1)).findByEmail(getUser().getEmail());
        verify(countryRepository, times(1)).findById(curriculumVitaeRequestDto.countryId());
        verify(positionRepository, times(1)).findById(curriculumVitaeRequestDto.positionId());
        verify(imageRepository, times(1)).findByUuid(curriculumVitaeRequestDto.imageUuid());
        verify(imageRepository, times(1)).isImageBelongCurriculumVitae(curriculumVitaeRequestDto.imageUuid());
    }

    @Test
    void shouldReturnAllCvsWhen_GetAllCvsOfUser_Invoke() {
        List<CurriculumVitae> listOfCvsOfUser = getListOfCvsOfUser(4);
        List<CurriculumVitaeResponseDto> listOfCvResponseDtoFromCvList = getListOfCvResponseDtoFromCvList(listOfCvsOfUser);
        stubbingForGetAllCvsOfUser(listOfCvsOfUser, listOfCvResponseDtoFromCvList);

        List<CurriculumVitaeResponseDto> allCvOfUser = curriculumVitaeService.getAllCvOfUser();

        assertEquals(allCvOfUser.size(), listOfCvsOfUser.size());
        assertIterableEquals(listOfCvResponseDtoFromCvList, allCvOfUser);
    }

    @Test
    void shouldReturnAllCvsWhen_GetAllCvsOfUser_AndCallRepositoryAndMapper() {
        List<CurriculumVitae> listOfCvsOfUser = getListOfCvsOfUser(4);
        List<CurriculumVitaeResponseDto> listOfCvResponseDtoFromCvList = getListOfCvResponseDtoFromCvList(listOfCvsOfUser);
        stubbingForGetAllCvsOfUser(listOfCvsOfUser, listOfCvResponseDtoFromCvList);

        curriculumVitaeService.getAllCvOfUser();

        verify(authService, times(1)).getUsername();
        verify(curriculumVitaeRepository, times(1)).findByUsername(getUser().getEmail());
        verify(curriculumVitaeMapper, times(1)).curriculumVitaeListToCurriculumVitaeResponseDtoList(listOfCvsOfUser);
    }

    @Test
    void shouldReturnUpdatedCvResponseDtoWhenSendCvRequestDtoTo_Update() {
        CurriculumVitaeRequestDto curriculumVitaeRequestDto = getValidCvRequestDto();
        CurriculumVitaeResponseDto curriculumVitaeResponseDto = getCvResponseDtoByCvRequestDto(curriculumVitaeRequestDto);
        CurriculumVitae curriculumVitae = getCvByCvRequestDto(curriculumVitaeRequestDto);
        stubbingForUpdate(curriculumVitaeResponseDto, curriculumVitae);

        CurriculumVitaeResponseDto updatedCvResponseDto = curriculumVitaeService.update(curriculumVitae.getUuid(), curriculumVitaeRequestDto);

        assertEquals(curriculumVitaeRequestDto.imageUuid(), updatedCvResponseDto.imageUuid());
        assertEquals(curriculumVitaeRequestDto.name(), updatedCvResponseDto.name());
        assertEquals(curriculumVitaeRequestDto.surname(), updatedCvResponseDto.surname());
        assertEquals(curriculumVitaeRequestDto.positionId(), updatedCvResponseDto.positionId());
        assertNotNull(updatedCvResponseDto.position());
        assertEquals(curriculumVitae.getPosition().getName(), updatedCvResponseDto.position());
        assertEquals(curriculumVitaeRequestDto.countryId(), updatedCvResponseDto.countryId());
        assertNotNull(updatedCvResponseDto.country());
        assertEquals(curriculumVitae.getCountry().getCountryName(), updatedCvResponseDto.country());
        assertEquals(curriculumVitaeRequestDto.city(), updatedCvResponseDto.city());
        assertEquals(curriculumVitaeRequestDto.isReadyToRelocate(), updatedCvResponseDto.isReadyToRelocate());
        assertEquals(curriculumVitaeRequestDto.isReadyForRemoteWork(), updatedCvResponseDto.isReadyForRemoteWork());
        assertNotNull(updatedCvResponseDto.status());
        assertEquals(curriculumVitae.getStatus().name(), updatedCvResponseDto.status());
    }

    @Test
    void shouldReturnUpdatedCvResponseDtoWhenSendCvRequestDtoTo_Update_AndCallRepositoryAndMapper() {
        CurriculumVitaeRequestDto curriculumVitaeRequestDto = getValidCvRequestDto();
        CurriculumVitaeResponseDto curriculumVitaeResponseDto = getCvResponseDtoByCvRequestDto(curriculumVitaeRequestDto);
        CurriculumVitae curriculumVitae = getCvByCvRequestDto(curriculumVitaeRequestDto);
        stubbingForUpdate(curriculumVitaeResponseDto, curriculumVitae);

        curriculumVitaeService.update(curriculumVitae.getUuid(), curriculumVitaeRequestDto);

        verify(authService, times(1)).getUsername();
        verify(curriculumVitaeRepository, times(1)).findByUuidAndUsername(curriculumVitae.getUuid(), getUser().getEmail());
        verify(curriculumVitaeRepository, times(1)).save(curriculumVitae);
        verify(curriculumVitaeMapper, times(1)).curriculumVitaeToCurriculumVitaeResponseDto(curriculumVitae);
        verify(imageService, times(1)).isImageChanging(curriculumVitaeRequestDto.imageUuid(), curriculumVitae.getImage());
    }

    @Test
    void shouldReturnCvResponseDtoWhen_GetCvOfUser_Invoke() {
        CurriculumVitae curriculumVitae = getValidCurriculumVitae();
        CurriculumVitaeResponseDto curriculumVitaeResponseDto = getCvResponseDtoByCurriculumVitae(curriculumVitae);

        stubbingForGet(curriculumVitae, curriculumVitaeResponseDto);

        CurriculumVitaeResponseDto cvOfUser = curriculumVitaeService.getCvOfUser(curriculumVitae.getUuid());

        assertEquals(curriculumVitae.getUuid(), cvOfUser.uuid());
        assertEquals(curriculumVitae.getImage().getUuid(), cvOfUser.imageUuid());
        assertEquals(curriculumVitae.getName(), cvOfUser.name());
        assertEquals(curriculumVitae.getSurname(), cvOfUser.surname());
        assertEquals(curriculumVitae.getPosition().getId(), cvOfUser.positionId());
        assertEquals(curriculumVitae.getPosition().getName(), cvOfUser.position());
        assertEquals(curriculumVitae.getCountry().getId(), cvOfUser.countryId());
        assertEquals(curriculumVitae.getCountry().getCountryName(), cvOfUser.country());
        assertEquals(curriculumVitae.getCity(), cvOfUser.city());
        assertEquals(curriculumVitae.getIsReadyToRelocate(), cvOfUser.isReadyToRelocate());
        assertEquals(curriculumVitae.getIsReadyForRemoteWork(), cvOfUser.isReadyForRemoteWork());
        assertNotNull(cvOfUser.status());
        assertEquals(curriculumVitae.getStatus().name(), cvOfUser.status());
    }

    @Test
    void shouldReturnCvResponseDtoWhen_GetCvOfUser_InvokeAndCallRepositoryAndMapper() {
        CurriculumVitae curriculumVitae = getValidCurriculumVitae();
        CurriculumVitaeResponseDto curriculumVitaeResponseDto = getCvResponseDtoByCurriculumVitae(curriculumVitae);

        stubbingForGet(curriculumVitae, curriculumVitaeResponseDto);

        curriculumVitaeService.getCvOfUser(curriculumVitae.getUuid());

        verify(authService, times(1)).getUsername();
        verify(curriculumVitaeRepository, times(1)).findByUuidAndUsername(curriculumVitae.getUuid(), getUser().getEmail());
        verify(curriculumVitaeMapper, times(1)).curriculumVitaeToCurriculumVitaeResponseDto(curriculumVitae);
    }

    private void stubbingForGet(CurriculumVitae curriculumVitae, CurriculumVitaeResponseDto curriculumVitaeResponseDto) {
        when(curriculumVitaeRepository.findByUuidAndUsername(curriculumVitae.getUuid(), getUser().getEmail())).thenReturn(curriculumVitae);
        when(curriculumVitaeMapper.curriculumVitaeToCurriculumVitaeResponseDto(curriculumVitae)).thenReturn(curriculumVitaeResponseDto);
    }

    private void stubbingForUpdate(CurriculumVitaeResponseDto curriculumVitaeResponseDto, CurriculumVitae curriculumVitae) {
        when(curriculumVitaeRepository.findByUuidAndUsername(curriculumVitae.getUuid(), getUser().getEmail())).thenReturn(curriculumVitae);
        when(curriculumVitaeRepository.save(any(CurriculumVitae.class))).thenReturn(curriculumVitae);
        when(curriculumVitaeMapper.curriculumVitaeToCurriculumVitaeResponseDto(curriculumVitae)).thenReturn(curriculumVitaeResponseDto);
    }

    private void stubbingForGetAllCvsOfUser(List<CurriculumVitae> listOfCvsOfUser, List<CurriculumVitaeResponseDto> listOfCvResponseDtoFromCvList) {
        when(curriculumVitaeRepository.findByUsername(getUser().getEmail())).thenReturn(listOfCvsOfUser);
        when(curriculumVitaeMapper.curriculumVitaeListToCurriculumVitaeResponseDtoList(listOfCvsOfUser)).thenReturn(listOfCvResponseDtoFromCvList);
    }

    private void stubbingForSave(CurriculumVitaeRequestDto curriculumVitaeRequestDto, CurriculumVitaeResponseDto curriculumVitaeResponseDto, CurriculumVitae curriculumVitae) {
        User user = getUser();
        when(imageRepository.isImageBelongCurriculumVitae(curriculumVitaeRequestDto.imageUuid())).thenReturn(false);
        when(imageRepository.findByUuid(curriculumVitaeRequestDto.imageUuid())).thenReturn(Optional.of(getImageByImageUuid(curriculumVitaeRequestDto.imageUuid())));
        when(positionRepository.findById(curriculumVitaeRequestDto.positionId())).thenReturn(Optional.of(getPositionByPositionId(curriculumVitaeRequestDto.positionId())));
        when(countryRepository.findById(curriculumVitaeRequestDto.countryId())).thenReturn(Optional.of(getCountryByCountyId(curriculumVitaeRequestDto.countryId())));
        when(userRepository.findByEmail(user.getEmail())).thenReturn(user);
        when(curriculumVitaeRepository.save(any(CurriculumVitae.class))).thenReturn(curriculumVitae);
        when(curriculumVitaeMapper.curriculumVitaeToCurriculumVitaeResponseDto(any(CurriculumVitae.class))).thenReturn(curriculumVitaeResponseDto);
    }


}