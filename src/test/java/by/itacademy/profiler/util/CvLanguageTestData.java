package by.itacademy.profiler.util;

import by.itacademy.profiler.persistence.model.CvLanguage;
import by.itacademy.profiler.persistence.model.LanguageProficiencyEnum;
import by.itacademy.profiler.usecasses.dto.CvLanguageRequestDto;
import by.itacademy.profiler.usecasses.dto.CvLanguageResponseDto;

import java.util.ArrayList;
import java.util.List;

import static by.itacademy.profiler.util.LanguageTestData.createLanguage;

public final class CvLanguageTestData {

    private CvLanguageTestData() {}

    public static CvLanguage.CvLanguageBuilder createCvLanguage() {
        return CvLanguage.builder()
                .withLanguage(createLanguage().build())
                .withProficiency(LanguageProficiencyEnum.A2)
                .withCertificateUrl("Something")
                .withId(1L);
    }

    public static List<CvLanguageRequestDto> createCvLanguageRequestList() {
        List<CvLanguageRequestDto> cvLanguageRequestDtos = new ArrayList<>();

        cvLanguageRequestDtos.add(CvLanguageRequestDto.builder()
                .withId(1L)
                .withLanguageProficiency(LanguageProficiencyEnum.B1)
                .withCertificateUrl("http://example.com/url1")
                .build());

        cvLanguageRequestDtos.add(CvLanguageRequestDto.builder()
                .withId(2L)
                .withLanguageProficiency(LanguageProficiencyEnum.A1)
                .withCertificateUrl("http://example.com/url2")
                .build());

        cvLanguageRequestDtos.add(CvLanguageRequestDto.builder()
                .withId(3L)
                .withLanguageProficiency(LanguageProficiencyEnum.NATIVE)
                .withCertificateUrl("http://example.com/url3")
                .build());
        return cvLanguageRequestDtos;
    }

    public static List<CvLanguageResponseDto> createCvLanguageResponseList() {
        List<CvLanguageResponseDto> cvLanguageResponseDtos = new ArrayList<>();

        cvLanguageResponseDtos.add(CvLanguageResponseDto.builder()
                .withId(1L)
                .withName("English")
                .withLanguageProficiency(LanguageProficiencyEnum.B1)
                .withCertificateUrl("http://example.com/url1")
                .build());

        cvLanguageResponseDtos.add(CvLanguageResponseDto.builder()
                .withId(2L)
                .withName("Spain")
                .withLanguageProficiency(LanguageProficiencyEnum.A1)
                .withCertificateUrl("http://example.com/url2")
                .build());

        cvLanguageResponseDtos.add(CvLanguageResponseDto.builder()
                .withId(3L)
                .withName("Russian")
                .withLanguageProficiency(LanguageProficiencyEnum.NATIVE)
                .withCertificateUrl("http://example.com/url3")
                .build());
        return cvLanguageResponseDtos;
    }

    public static CvLanguageRequestDto createCvLanguageRequestDto(LanguageProficiencyEnum proficiency) {
        return CvLanguageRequestDto.builder()
                .withId(1L)
                .withLanguageProficiency(proficiency)
                .withCertificateUrl("http://example.com/url")
                .build();
    }

    public static CvLanguageRequestDto createCvLanguageRequestDtoWithIdNull() {
        return CvLanguageRequestDto.builder()
                .withId(null)
                .withLanguageProficiency(LanguageProficiencyEnum.A1)
                .withCertificateUrl("http://example.com/url")
                .build();
    }

    static CvLanguageRequestDto createCvLanguageRequestDto() {
        return CvLanguageRequestDto.builder()
                .withId(1L)
                .withLanguageProficiency(LanguageProficiencyEnum.NATIVE)
                .withCertificateUrl("link")
                .build();
    }

    public static String getInvalidJsonCompetencesRequestDto(){
        return "{\"languages\":" +
                "[{\"id\":1,\"languageProficiency\":\"invalid\",\"certificateUrl\":\"http://example.com/rndm-url\"}," +
                "{\"id\":2,\"languageProficiency\":\"A2\",\"certificateUrl\":\"http://example.com/rndm-url2\"}]," +
                "\"skills\":" +
                "[1, 2]}";
    }
}
