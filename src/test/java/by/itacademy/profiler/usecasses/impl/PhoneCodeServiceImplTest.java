package by.itacademy.profiler.usecasses.impl;

import by.itacademy.profiler.persistence.model.PhoneCode;
import by.itacademy.profiler.persistence.repository.PhoneCodeRepository;
import by.itacademy.profiler.usecasses.dto.PhoneCodeDto;
import by.itacademy.profiler.usecasses.mapper.PhoneCodeMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static by.itacademy.profiler.util.PhoneCodeTestData.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PhoneCodeServiceImplTest {

    @Mock
    private PhoneCodeMapper phoneCodeMapper;

    @Mock
    private PhoneCodeRepository phoneCodeRepository;

    @InjectMocks
    private PhoneCodeServiceImpl phoneCodeService;

    @Test
    void shouldReturnNotEmptyPhoneCodeDtoListWhenGettingPhoneCodeList() {
        List<PhoneCode> phoneCodeList = List.of(createPhoneCode());
        List<PhoneCodeDto> phoneCodeDtoList = List.of(createPhoneCodeDto(1L, 375).build());

        when(phoneCodeRepository.findAll()).thenReturn(phoneCodeList);
        when(phoneCodeMapper.toDto(phoneCodeList)).thenReturn(phoneCodeDtoList);

        List<PhoneCodeDto> responseList = phoneCodeService.getPhoneCodes();
        assertEquals(1, responseList.size());
    }
}