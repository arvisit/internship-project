package by.itacademy.profiler.usecasses.annotation;

import by.itacademy.profiler.persistence.repository.CountryRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CountryValidator implements ConstraintValidator<CountryValidation, Long> {

    private final CountryRepository countryRepository;

    public boolean isValid(Long id, ConstraintValidatorContext cxt) {
        return countryRepository.findById(id).isPresent();
    }
}