package by.itacademy.profiler.usecasses;

import by.itacademy.profiler.usecasses.dto.PhoneCodeDto;

import java.util.List;

public interface PhoneCodeService {

    List<PhoneCodeDto> getPhoneCodes();

    boolean isPhoneCodeExist(Long id);
}
