package by.itacademy.profiler.util;

import by.itacademy.profiler.persistence.model.Institution;
import by.itacademy.profiler.usecasses.dto.InstitutionResponseDto;

public class InstitutionsTestData {

    public static final String INSTITUTION_URL_TEMPLATE = "/api/v1/institutions";

    private InstitutionsTestData(){}

    public static Institution.InstitutionBuilder createInstitution(){
        return Institution.builder()
                .withId(34L)
                .withName("Francisk Skorina Gomel State University");
    }

    public static InstitutionResponseDto.InstitutionResponseDtoBuilder createInstitutionResponseDto(){
        return InstitutionResponseDto.builder()
                .withId(34L)
                .withName("Francisk Skorina Gomel State University");
    }

}
