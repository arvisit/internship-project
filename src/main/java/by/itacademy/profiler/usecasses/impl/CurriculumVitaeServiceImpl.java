package by.itacademy.profiler.usecasses.impl;

import by.itacademy.profiler.api.exception.BadRequestException;
import by.itacademy.profiler.persistence.model.Course;
import by.itacademy.profiler.persistence.model.CurriculumVitae;
import by.itacademy.profiler.persistence.model.CvLanguage;
import by.itacademy.profiler.persistence.model.Experience;
import by.itacademy.profiler.persistence.model.Image;
import by.itacademy.profiler.persistence.model.MainEducation;
import by.itacademy.profiler.persistence.model.Skill;
import by.itacademy.profiler.persistence.model.User;
import by.itacademy.profiler.persistence.repository.CountryRepository;
import by.itacademy.profiler.persistence.repository.CurriculumVitaeRepository;
import by.itacademy.profiler.persistence.repository.ImageRepository;
import by.itacademy.profiler.persistence.repository.PositionRepository;
import by.itacademy.profiler.persistence.repository.UserRepository;
import by.itacademy.profiler.usecasses.CurriculumVitaeService;
import by.itacademy.profiler.usecasses.ImageService;
import by.itacademy.profiler.usecasses.dto.CurriculumVitaeRequestDto;
import by.itacademy.profiler.usecasses.dto.CurriculumVitaeResponseDto;
import by.itacademy.profiler.usecasses.mapper.CurriculumVitaeMapper;
import by.itacademy.profiler.usecasses.util.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static java.util.Objects.nonNull;

@Service
@RequiredArgsConstructor
public class CurriculumVitaeServiceImpl implements CurriculumVitaeService {

    @Value("${curriculum-vitae.number-of-user-curriculum-vitae}")
    private int numberOfUserCv;

    private final UserRepository userRepository;
    private final CurriculumVitaeRepository curriculumVitaeRepository;
    private final CurriculumVitaeMapper curriculumVitaeMapper;
    private final PositionRepository positionRepository;
    private final CountryRepository countryRepository;
    private final ImageRepository imageRepository;
    private final ImageService imageService;
    private final AuthService authService;

    @Override
    @Transactional
    public CurriculumVitaeResponseDto save(CurriculumVitaeRequestDto curriculumVitaeRequestDto) {
        String username = authService.getUsername();
        CurriculumVitae curriculumVitae = curriculumVitaeRequestDtoToCurriculumVitae(curriculumVitaeRequestDto);
        User user = userRepository.findByEmail(username);
        curriculumVitae.setUser(user);
        curriculumVitae.setUuid(UUID.randomUUID().toString());
        CurriculumVitae savedCurriculumVitae = curriculumVitaeRepository.save(curriculumVitae);
        return curriculumVitaeMapper.curriculumVitaeToCurriculumVitaeResponseDto(savedCurriculumVitae);
    }

    @Override
    public List<CurriculumVitaeResponseDto> getAllCvOfUser() {
        String username = authService.getUsername();
        List<CurriculumVitae> curriculumVitaeList = curriculumVitaeRepository.findByUsername(username);
        return curriculumVitaeMapper.curriculumVitaeListToCurriculumVitaeResponseDtoList(curriculumVitaeList);
    }

    @Override
    @Transactional
    public CurriculumVitaeResponseDto update(String curriculumVitaeUuid,
                                             CurriculumVitaeRequestDto curriculumVitaeDto) {
        String username = authService.getUsername();
        CurriculumVitae curriculumVitae = curriculumVitaeRepository.findByUuidAndUsername(curriculumVitaeUuid, username);
        updateCurriculumVitaeByRequestDto(curriculumVitae, curriculumVitaeDto);
        CurriculumVitae updatedCurriculumVitae = curriculumVitaeRepository.save(curriculumVitae);
        return curriculumVitaeMapper.curriculumVitaeToCurriculumVitaeResponseDto(updatedCurriculumVitae);
    }

    @Override
    public CurriculumVitaeResponseDto getCvOfUser(String uuid) {
        String username = authService.getUsername();
        CurriculumVitae curriculumVitae = curriculumVitaeRepository.findByUuidAndUsername(uuid, username);
        return curriculumVitaeMapper.curriculumVitaeToCurriculumVitaeResponseDto(curriculumVitae);
    }

    @Override
    @Transactional
    public void saveSkillsToCv(String cvUuid, List<Skill> skills) {
        CurriculumVitae curriculumVitae = curriculumVitaeRepository.getReferenceByUuid(cvUuid);
        curriculumVitae.setSkills(skills);
        curriculumVitaeRepository.save(curriculumVitae);
    }

    @Override
    @Transactional
    public void saveLanguagesToCv(String cvUuid, List<CvLanguage> languages) {
        CurriculumVitae curriculumVitae = curriculumVitaeRepository.getReferenceByUuid(cvUuid);
        List<CvLanguage> cvLanguages = curriculumVitae.getLanguages();
        cvLanguages.clear();
        cvLanguages.addAll(languages);
        curriculumVitaeRepository.save(curriculumVitae);
    }

    @Override
    public List<CvLanguage> getCvLanguagesByCvUuid(String cvUuid) {
        return curriculumVitaeRepository.findAllCvLanguagesByCVUuid(cvUuid);
    }

    @Override
    public List<Skill> getCvSkillsByCvUuid(String cvUuid) {
        return curriculumVitaeRepository.findAllCvSkillsByCvUuid(cvUuid);
    }

    @Override
    @Transactional
    public List<Experience> saveExperienceToCv(String cvUuid, List<Experience> experience) {
        CurriculumVitae curriculumVitae = curriculumVitaeRepository.getReferenceByUuid(cvUuid);
        List<Experience> curriculumVitaeExperience = curriculumVitae.getExperience();
        curriculumVitaeExperience.clear();
        curriculumVitaeExperience.addAll(experience);
        CurriculumVitae savedCv = curriculumVitaeRepository.save(curriculumVitae);
        return savedCv.getExperience();
    }

    @Override
    public List<Experience> getCvExperienceByCvUuid(String cvUuid) {
        return curriculumVitaeRepository.findAllCvExperienceByCvUuid(cvUuid);
    }

    @Override
    @Transactional
    public List<MainEducation> saveMainEducationsToCv(String cvUuid, List<MainEducation> mainEducations) {
        CurriculumVitae curriculumVitae = curriculumVitaeRepository.getReferenceByUuid(cvUuid);
        List<MainEducation> curriculumVitaeMainEducations = curriculumVitae.getMainEducations();
        curriculumVitaeMainEducations.clear();
        curriculumVitaeMainEducations.addAll(mainEducations);
        CurriculumVitae savedCv = curriculumVitaeRepository.save(curriculumVitae);
        return savedCv.getMainEducations();
    }

    @Override
    @Transactional
    public List<Course> saveCoursesToCv(String cvUuid, List<Course> courses) {
        CurriculumVitae curriculumVitae = curriculumVitaeRepository.getReferenceByUuid(cvUuid);
        List<Course> curriculumVitaeCourses = curriculumVitae.getCourses();
        curriculumVitaeCourses.clear();
        curriculumVitaeCourses.addAll(courses);
        CurriculumVitae savedCv = curriculumVitaeRepository.save(curriculumVitae);
        return savedCv.getCourses();
    }

    public Long getAllCvByUser() {
        String username = authService.getUsername();
        return curriculumVitaeRepository.findCountByUsername(username);
    }

    public boolean isCreationCvAvailable() {
        return getAllCvByUser() < numberOfUserCv;
    }

    private CurriculumVitae curriculumVitaeRequestDtoToCurriculumVitae(CurriculumVitaeRequestDto curriculumVitaeRequestDto) {
        String imageUuid = curriculumVitaeRequestDto.imageUuid();
        CurriculumVitae curriculumVitae = new CurriculumVitae();
        if (nonNull(imageUuid) && imageRepository.isImageBelongCurriculumVitae(imageUuid)) {
            throw new BadRequestException(String.format("Image with UUID %s is already in use", imageUuid));
        } else {
            imageRepository.findByUuid(imageUuid).ifPresent(curriculumVitae::setImage);
        }
        curriculumVitae.setName(curriculumVitaeRequestDto.name());
        curriculumVitae.setSurname(curriculumVitaeRequestDto.surname());
        positionRepository.findById(curriculumVitaeRequestDto.positionId()).ifPresent(curriculumVitae::setPosition);
        countryRepository.findById(curriculumVitaeRequestDto.countryId()).ifPresent(curriculumVitae::setCountry);
        curriculumVitae.setCity(curriculumVitaeRequestDto.city());
        curriculumVitae.setIsReadyToRelocate(curriculumVitaeRequestDto.isReadyToRelocate());
        curriculumVitae.setIsReadyForRemoteWork(curriculumVitaeRequestDto.isReadyForRemoteWork());
        return curriculumVitae;
    }

    private void updateCurriculumVitaeByRequestDto(CurriculumVitae curriculumVitae,
                                                   CurriculumVitaeRequestDto curriculumVitaeRequestDto) {
        String incomingImageUuid = curriculumVitaeRequestDto.imageUuid();
        Image storedImage = curriculumVitae.getImage();
        if (imageService.isImageChanging(incomingImageUuid, storedImage)) {
            String imageUuid = imageService.replaceImage(incomingImageUuid, storedImage);
            Image image = imageRepository.findByUuid(imageUuid).orElse(null);
            curriculumVitae.setImage(image);
        }
        if (!curriculumVitaeRequestDto.name().equals(curriculumVitae.getName())) {
            curriculumVitae.setName(curriculumVitaeRequestDto.name());
        }
        if (!curriculumVitaeRequestDto.surname().equals(curriculumVitae.getSurname())) {
            curriculumVitae.setSurname(curriculumVitaeRequestDto.surname());
        }
        if (!curriculumVitaeRequestDto.positionId().equals(curriculumVitae.getPosition().getId())) {
            positionRepository.findById(curriculumVitaeRequestDto.positionId())
                    .ifPresent(curriculumVitae::setPosition);
        }
        if (!curriculumVitaeRequestDto.countryId().equals(curriculumVitae.getCountry().getId())) {
            countryRepository.findById(curriculumVitaeRequestDto.countryId())
                    .ifPresent(curriculumVitae::setCountry);
        }
        if (!curriculumVitaeRequestDto.city().equals(curriculumVitae.getCity())) {
            curriculumVitae.setCity(curriculumVitaeRequestDto.city());
        }
            curriculumVitae.setIsReadyToRelocate(curriculumVitaeRequestDto.isReadyToRelocate());
            curriculumVitae.setIsReadyForRemoteWork(curriculumVitaeRequestDto.isReadyForRemoteWork());
    }

    @Override
    public boolean isCurriculumVitaeExists(String uuid) {
        return curriculumVitaeRepository
                .existsByUuidAndUsername(uuid, authService.getUsername());
    }
}
