package annotations;

public enum AnnotationType {
    HIGHLIGHT("Highlight"),
    STRIKEOUT("Strikeout"),
    UNDERLINE("Underline"),
    SQUIGGLY("Squiggly");

    private final String value;

    AnnotationType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}

