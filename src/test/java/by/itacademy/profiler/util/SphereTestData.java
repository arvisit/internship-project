package by.itacademy.profiler.util;

import by.itacademy.profiler.persistence.model.Sphere;
import by.itacademy.profiler.usecasses.dto.SphereResponseDto;

public final class SphereTestData {

    public static final String SPHERE_URL_TEMPLATE = "/api/v1/spheres";
    public static final Integer EXPECTED_NUMBER_OF_SPHERES = 25;

    private SphereTestData() {
    }

    public static SphereResponseDto.SphereResponseDtoBuilder createSphereResponseDto() {
        return SphereResponseDto.builder().withId(1L).withName("IT");
    }

    public static Sphere.SphereBuilder createSphere(){
        return Sphere.builder().withId(1L).withName("IT");
    }

}
