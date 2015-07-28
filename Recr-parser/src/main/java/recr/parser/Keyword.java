package recr.parser;

public class Keyword {
    private String word;
    private Section relatedSection;

    public Keyword(String word, Section relatedSection) {
        this.word = word;
        this.relatedSection = relatedSection;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public Section getRelatedSection() {
        return relatedSection;
    }

    public void setRelatedSection(Section relatedSection) {
        this.relatedSection = relatedSection;
    }
}
