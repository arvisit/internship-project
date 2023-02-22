package by.itacademy.profiler.usecasses;

import by.itacademy.profiler.usecasses.dto.AboutDto;

public interface AboutService {

    AboutDto save(String uuid, AboutDto aboutDto);

    AboutDto update(String uuid, AboutDto aboutDto);

    AboutDto getAbout(String uuid);
}
