package by.itacademy.profiler.usecasses.dto;

import by.itacademy.profiler.usecasses.annotation.PhoneCodeValidation;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;

import static by.itacademy.profiler.usecasses.util.ValidationConstants.REGEXP_VALIDATE_CELL_PHONE;
import static by.itacademy.profiler.usecasses.util.ValidationConstants.REGEXP_VALIDATE_EMAIL;
import static by.itacademy.profiler.usecasses.util.ValidationConstants.REGEXP_VALIDATE_RECOMMENDATION_COMPANY_POSITION;
import static by.itacademy.profiler.usecasses.util.ValidationConstants.REGEXP_VALIDATE_RECOMMENDATION_FULL_NAME;
import static java.util.Objects.nonNull;

@Builder(setterPrefix = "with")
public record RecommendationRequestDto(

        @Pattern(regexp = REGEXP_VALIDATE_RECOMMENDATION_FULL_NAME, message = "Invalid full name")
        @Length(max = 40, message = "Name is too long, the max number of symbols is 40")
        @Schema(defaultValue = "Full Name", description = "Full Name")
        String fullName,
        @Pattern(regexp = REGEXP_VALIDATE_RECOMMENDATION_COMPANY_POSITION, message = "Invalid company name")
        @Length(max = 40, message = "Company name is too long, the max number of symbols is 40")
        @Schema(defaultValue = "Company Name", description = "Company Name")
        String company,
        @Pattern(regexp = REGEXP_VALIDATE_RECOMMENDATION_COMPANY_POSITION, message = "Invalid position name")
        @Length(max = 40, message = "Position name is too long, the max number of symbols is 40")
        @Schema(defaultValue = "Position name", description = "Position name")
        String position,
        @PhoneCodeValidation
        @Schema(defaultValue = "1", description = "Phone code id")
        Long phoneCodeId,
        @Length(max = 25, message = "Phone number is too long, the max number of symbols is 25")
        @Schema(defaultValue = "291112233", description = "Phone number")
        @Pattern(regexp = REGEXP_VALIDATE_CELL_PHONE, message = "Invalid phone number")
        String phoneNumber,
        @Length(max = 50, message = "The email is too long, the max number of symbols is 50")
        @Pattern(regexp = REGEXP_VALIDATE_EMAIL, message = "Invalid email")
        @Schema(defaultValue = "user@gmail.com", description = "Email address")
        String email,
        @Length(max = 255, message = "The link is too long, the max number of symbols is 255")
        @Schema(defaultValue = "http://example.com/link", description = "Link to recommender's account on Linkedln")
        String linkedIn,
        @Length(max = 50, message = "The link is too long, the max number of symbols is 50")
        @Schema(defaultValue = "@user", description = "Link to recommender's Telegram")
        String telegram,
        @Length(max = 50, message = "The link is too long, the max number of symbols is 50")
        @Schema(defaultValue = "UserUser", description = "Link to recommender's Viber")
        String viber,
        @Length(max = 50, message = "The link is too long, the max number of symbols is 50")
        @Schema(defaultValue = "UserUser", description = "Link to recommender's WhatsApp")
        String whatsApp,
        @Length(max = 255, message = "The link is too long, the max number of symbols is 255")
        @Schema(defaultValue = "http://example.com/link", description = "Link to recommendations")
        String recommendations
) implements Serializable {

    @AssertTrue(message = "Field `fullName`, `company`, `position` and one of contact`s fields should not be empty when at least one of the above fields was filled")
    private boolean isFullNameCompanyPositionOneOfContactsNotEmpty() {
        if (fullName != null || company != null || position != null ||
                phoneNumber != null || email != null ||
                linkedIn != null || telegram != null ||
                viber != null || whatsApp != null) {
            return nonNull(fullName) && nonNull(company) && nonNull(position) &&
                    (nonNull(phoneNumber) || nonNull(email) ||
                            nonNull(linkedIn) || nonNull(telegram) ||
                            nonNull(viber) || nonNull(whatsApp));
        }
        return true;
    }

    @AssertTrue(message = "Field `company` should not be empty if field `recommendations` is not empty")
    private boolean isCompanyAndRecommendationsNotEmpty() {
        if (recommendations != null) {
            return nonNull(company);
        }
        return true;
    }
}
