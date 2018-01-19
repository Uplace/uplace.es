package com.arnaugarcia.uplace.service.util;

import org.apache.commons.lang3.RandomStringUtils;

/**
 * Utility class for generating random Strings.
 */
public final class RandomUtil {

    private static final int DEF_COUNT_DEFAULT = 20;
    private static final int DEF_COUNT_REFERENCE = 7;

    private RandomUtil() {
    }

    /**
     * Generate a password.
     *
     * @return the generated password
     */
    public static String generatePassword() {
        return RandomStringUtils.randomAlphanumeric(DEF_COUNT_DEFAULT);
    }

    /**
     * Generate an activation key.
     *
     * @return the generated activation key
     */
    public static String generateActivationKey() {
        return RandomStringUtils.randomNumeric(DEF_COUNT_DEFAULT);
    }

    /**
     * Generate a reset key.
     *
     * @return the generated reset key
     */
    public static String generateResetKey() {
        return RandomStringUtils.randomNumeric(DEF_COUNT_DEFAULT);
    }

    /**
     * Generate a reference of property.
     *
     * @return the generated reference
     */
    public static String generateReference() {
        return RandomStringUtils.randomAlphanumeric(DEF_COUNT_REFERENCE);
    }
}
