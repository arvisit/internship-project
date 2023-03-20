package by.itacademy.profiler.usecasses.impl;

import by.itacademy.profiler.persistence.model.PhoneCode;
import by.itacademy.profiler.persistence.repository.PhoneCodeRepository;
import by.itacademy.profiler.usecasses.PhoneCodeService;
import by.itacademy.profiler.usecasses.dto.PhoneCodeDto;
import by.itacademy.profiler.usecasses.mapper.PhoneCodeMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class PhoneCodeServiceImpl implements PhoneCodeService {

    private final PhoneCodeRepository phoneCodeRepository;
    private final PhoneCodeMapper phoneCodeMapper;

    @Override
    public List<PhoneCodeDto> getPhoneCodes() {
        List<PhoneCode> phoneCodes = phoneCodeRepository.findAll();
        List<PhoneCodeDto> phoneCodeDtos = phoneCodeMapper.toDto(phoneCodes);
        log.debug("Getting {} phone codes from database", phoneCodeDtos.size());
        return phoneCodeDtos;
    }

    @Override
    public boolean isPhoneCodeExist(Long id) {
        return phoneCodeRepository.existsById(id);
    }
}
