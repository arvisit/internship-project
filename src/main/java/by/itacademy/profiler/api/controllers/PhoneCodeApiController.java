package by.itacademy.profiler.api.controllers;

import by.itacademy.profiler.usecasses.PhoneCodeService;
import by.itacademy.profiler.usecasses.dto.PhoneCodeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/phonecodes")
public class PhoneCodeApiController {

    private final PhoneCodeService phoneCodeService;

    @GetMapping
    public ResponseEntity<List<PhoneCodeDto>> getPhoneCodes() {
        List<PhoneCodeDto> phoneCodeDtos = phoneCodeService.getPhoneCodes();
        return ResponseEntity.ok(phoneCodeDtos);
    }
}
