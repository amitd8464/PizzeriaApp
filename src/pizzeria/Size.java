package pizzeria;

/**
 * enum for size
 * @author Andrew Ho, Amit Deshpande
 */
public enum Size {
    /**
     * small size
     */
    SMALL,
    /**
     * medium size
     */
    MEDIUM,
    /**
     * large size
     */
    LARGE;

    /**
     * String to enum size
     * @param s String of the size
     * @return instance of Size enum
     */
    public static Size getValue(String s){
        return switch (s.toLowerCase()) {
            case "small" -> SMALL;
            case "medium" -> MEDIUM;
            case "large" -> LARGE;
            default -> null;
        };
    }
}
