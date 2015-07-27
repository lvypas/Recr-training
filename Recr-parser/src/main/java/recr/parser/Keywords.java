package recr.parser;

import java.util.ArrayList;
import java.util.List;

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

    public List<Sections> getRelatedSections(Sections section) {
        List<Sections> sectionses = new ArrayList<Sections>();
        for (Keywords keyword : Keywords.values()){
            if (section.equals(keyword.getRelatedSection())) sectionses.add(section);
        }
        return  sectionses;
    }
}
