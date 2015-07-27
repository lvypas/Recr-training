package recr.parser;

public enum Sections {
    Responsibilities(3),
    Requirements(4),
    AdditionalSkills(5);

    private final Integer topKeywords;

    Sections(Integer topKeywords) {
        this.topKeywords = topKeywords;
    }

    public Integer getTopKeywords() {
        return  this.topKeywords;
    }
}
