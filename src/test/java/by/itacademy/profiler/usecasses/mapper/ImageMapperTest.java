package by.itacademy.profiler.usecasses.mapper;

import by.itacademy.profiler.persistence.model.Image;
import by.itacademy.profiler.usecasses.dto.ImageDto;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static by.itacademy.profiler.util.ImageTestData.createImage;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ImageMapperTest {

    private final ImageMapper imageMapper = Mappers.getMapper(ImageMapper.class);

    @Test
    void shouldMapCorrectlyAllFieldsWhenInvokeFromEntityToDto() {
        Image image = createImage();
        ImageDto imageDto = imageMapper.imageToImageDto(image);

        assertEquals(image.getUuid(), imageDto.uuid());
    }
}
