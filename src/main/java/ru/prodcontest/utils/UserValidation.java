package ru.prodcontest.utils;

import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ru.prodcontest.domain.user.User;
import ru.prodcontest.dto.profile.PatchProfileRequest;
import ru.prodcontest.exception.country.CountryNotFoundException;
import ru.prodcontest.service.country.CountryService;

@Component
public class UserValidation {
    private final CountryService countryService;

    @Autowired
    public UserValidation(CountryService countryService) {
        this.countryService = countryService;
    }

    public boolean validate(User user) throws CountryNotFoundException {
        boolean checkedLogin = checkLogin(user.getLogin());
        boolean checkedEmail = checkEmail(user.getEmail());
        boolean checkedPassword = checkPassword(user.getPassword());
        boolean checkedCountryCode = checkCountryCode(user.getCountryCode());
        boolean checkedIsPublic = checkIsPublic(user.getIsPublic());
        boolean checkedPhone = checkPhone(user.getPhone());
        boolean checkedImage = checkImage(user.getImage());

        return checkedLogin && checkedEmail && checkedPassword && checkedCountryCode
                && checkedPhone && checkedImage && checkedIsPublic;
    }

    public boolean validatePatch(PatchProfileRequest request) throws CountryNotFoundException {
        boolean checkedCountryCode = checkCountryCodePatch(request.getCountryCode());
        boolean checkedPhone = checkPhone(request.getPhone());
        boolean checkedImage = checkImage(request.getImage());

        return checkedCountryCode && checkedPhone && checkedImage;
    }

    private boolean checkCountryCodePatch(String countryCode) throws CountryNotFoundException {
        if (null == countryCode)
            return true;

        return countryCode.matches("[a-zA-Z]{2}") &&
                countryService.getCountryByAlpha2(countryCode.toUpperCase()) != null;
    }

    public boolean validatePassword(String password) {
        return checkPassword(password);
    }

    private boolean checkLogin(String login) {
        return null != login &&
                login.matches("[a-zA-Z0-9-]{1,30}");
    }

    private boolean checkEmail(String email) {
        return null != email &&
                email.length() <= 50 &&
                EmailValidator.getInstance().isValid(email);
    }

    private boolean checkPassword(String password) {
        return null != password &&
                password.matches(
                "(?=.*[\\d])(?=.*[!@#$%^.&*])(?=.*[a-z])(?=.*[A-Z])[\\da-zA-Z!@#$%^.&*]{6,100}"
        );
    }

    private boolean checkCountryCode(String alpha2) throws CountryNotFoundException {
        return null != alpha2 &&
                alpha2.matches("[a-zA-Z]{2}") &&
                countryService.getCountryByAlpha2(alpha2.toUpperCase()) != null;
    }

    private boolean checkIsPublic(Boolean isPublic) {
        return isPublic != null;
    }

    private boolean checkPhone(String phone) {
        if (phone == null)
            return true;

        return phone.startsWith("+") &&
                phone.matches("\\+[\\d]{10,20}");
    }

    private boolean checkImage(String image) {
        if (null == image)
            return true;

        return image.length() <= 200;
    }
}
