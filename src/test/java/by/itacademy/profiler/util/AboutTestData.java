package by.itacademy.profiler.util;

import by.itacademy.profiler.usecasses.dto.AboutDto;

import static by.itacademy.profiler.util.CurriculumVitaeTestData.CV_UUID;

public final class AboutTestData {

    public static final String ABOUTS_URL_TEMPLATE = String.format("/api/v1/cvs/%s/about", CV_UUID);

    private AboutTestData(){
    }

    public static AboutDto.AboutDtoBuilder createAboutDto(){
        return AboutDto.builder()
                .withDescription("Hi everyone! My name is Kate. I'm a beginner UX")
                .withSelfPresentation("https://docs.google.com/rndm");
    }
}
