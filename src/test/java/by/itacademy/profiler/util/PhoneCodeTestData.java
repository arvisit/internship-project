package by.itacademy.profiler.util;

import by.itacademy.profiler.persistence.model.Country;
import by.itacademy.profiler.persistence.model.PhoneCode;
import by.itacademy.profiler.usecasses.dto.PhoneCodeDto;

import java.util.List;


public class PhoneCodeTestData {

    public static final String PHONE_CODE_URL_TEMPLATE = "/api/v1/phonecodes";

    private PhoneCodeTestData() {
    }

    public static PhoneCode createPhoneCode(){
        PhoneCode phoneCode = new PhoneCode();
        phoneCode.setId(1L);
        phoneCode.setCountry(createCountry());
        phoneCode.setCode(375);
        return phoneCode;
    }

    public static PhoneCodeDto.PhoneCodeDtoBuilder createPhoneCodeDto(Long id, int code){
        return PhoneCodeDto.builder()
                .withId(id)
                .withCountry(createCountry())
                .withCode(375);
    }

    public static List<PhoneCodeDto> createPhoneCodeDtoList(){
        return List.of(createPhoneCodeDto(1L,375).build(),createPhoneCodeDto(2L,44).build());
    }

    private static Country createCountry(){
        Country country = new Country();
        country.setId(1L);
        country.setCountryName("Belarus");
        return country;
    }
}
