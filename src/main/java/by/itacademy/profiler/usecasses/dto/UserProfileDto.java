package by.itacademy.profiler.usecasses.dto;


import jakarta.validation.constraints.Pattern;

public record UserProfileDto(@Pattern(regexp = "^(?=.{1,50}$)[a-zA-Z]+(?:[-' ][a-zA-Z]+)*$",
                                     message = "The user name was entered incorrectly, it is allowed to use Latin letters, dashes and spaces.")
                             String name,
                             @Pattern(regexp = "^(?=.{1,50}$)[a-zA-Z]+(?:[-' ][a-zA-Z]+)*$",
                                     message = "The surname of the user was entered incorrectly, it is allowed to use Latin letters, dashes and spaces.")
                             String surname,
                             Long countryId,
                             @Pattern(regexp = "^[\\w ]+(?:\\w+[!#$%&'()*,\\-./:;<=>?\\[\\]^_{}]?)+\\w+@[a-zA-Z0-9-_]+\\.+([a-zA-Z]{2,6}+[ +]*)$",
                                     message = "The email of the user was entered incorrectly. Email address must have four parts: Recipient name, @ symbol, Domain name, Top-level domain")
                             String email,
                             Long phoneCodeId,
                             @Pattern(regexp = "^(?=.{1,25}$)[0-9]*$",
                                     message = "The user's phone number was entered incorrectly. It is not allowed to use a dashes and a spaces between numbers.")
                             String cellPhone,
                             Long positionId) {
}