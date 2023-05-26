package by.itacademy.profiler.usecasses;

import by.itacademy.profiler.persistence.model.Industry;
import by.itacademy.profiler.usecasses.dto.IndustryResponseDto;

import java.util.List;

public interface IndustryService {

    List<IndustryResponseDto> getIndustries();

    Industry getIndustryById(Long id);

    boolean isIndustryExist(Long id);

}
