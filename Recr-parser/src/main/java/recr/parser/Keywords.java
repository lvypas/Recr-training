package recr.parser;

public enum  Keywords {
    SoftwareDevelopment(Sections.Responsibilities),
    DevelopCode(Sections.Responsibilities),
    DebugCode(Sections.Responsibilities);

    private final Sections relatedSection;

    Keywords(Sections relatedSection) {
        this.relatedSection = relatedSection;
    }

    public Sections getRelatedSection() {
        return  this.relatedSection;
    }
}
