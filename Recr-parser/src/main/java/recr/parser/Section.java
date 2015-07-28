package recr.parser;

public class Section {
    private String name;
    private Integer topKeywords;

    public Section(String name, Integer topKeywords) {
        this.name = name;
        this.topKeywords = topKeywords;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getTopKeywords() {
        return topKeywords;
    }

    public void setTopKeywords(Integer topKeywords) {
        this.topKeywords = topKeywords;
    }
}
