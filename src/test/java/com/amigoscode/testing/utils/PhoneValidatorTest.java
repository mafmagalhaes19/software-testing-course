package com.amigoscode.testing.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class PhoneValidatorTest {

    private PhoneNumberValidator underTest;

    @BeforeEach
    void setUp() {
        underTest = new PhoneNumberValidator();
    }

    @ParameterizedTest
    @CsvSource({
            "+351910000000, true",
            "+35191000000000, false",
            "+321910000000, false"})
    void itShouldValidatePhoneNumber(String phoneNumber, String expected) {
        //Given

        //When
        boolean isValid = underTest.test(phoneNumber);

        //Then
        assertThat(isValid).isEqualTo(Boolean.valueOf(expected));
    }

    //Not needed anymore
    @Test
    @DisplayName("Should fail when length is bigger than 13")
    void itShouldValidatePhoneNumberWhenIncorrectLength() {
        //Given
        String phoneNumber = "+35191000000000";

        //When
        boolean isValid = underTest.test(phoneNumber);

        //Then
        assertThat(isValid).isFalse();
    }

    //Not needed anymore
    @Test
    @DisplayName("Should fail when it doesn't start with +351")
    void itShouldValidatePhoneNumberWhenIncorrectBeginning() {
        //Given
        String phoneNumber = "+321910000000";

        //When
        boolean isValid = underTest.test(phoneNumber);

        //Then
        assertThat(isValid).isFalse();
    }

}
