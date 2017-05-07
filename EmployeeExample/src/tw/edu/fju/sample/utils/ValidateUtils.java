package tw.edu.fju.sample.utils;

public final class ValidateUtils {

    private ValidateUtils() {
    }

    /**
     * <p>檢查字串是否為空格、空白字元或 null</p>
     *
     * <pre>
     * ValidateUtils.isBlank(null)      = true
     * ValidateUtils.isBlank("")        = true
     * ValidateUtils.isBlank(" ")       = true
     * ValidateUtils.isBlank("bob")     = false
     * ValidateUtils.isBlank("  bob  ") = false
     * </pre>
     */
    public static boolean isBlank(String value) {
        return value == null || value.trim().length() == 0;
    }

    /**
     * <p>檢查字串是否為自然數(正整數)</p>
     */
    public static boolean isNaturalNumbers(String number) {
        return number.matches("\\d*") && Integer.parseInt(number) > 0;
    }

    /**
     * <p>檢查字串是否為正浮點數</p>
     */
    public static boolean isPositiveDouble(String number) {
        return number.matches("\\d*|\\d*\\.\\d*") && Double.parseDouble(number) > 0;
    }
}
