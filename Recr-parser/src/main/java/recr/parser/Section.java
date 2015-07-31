package recr.parser;

import java.util.List;

public class Section {
    private String name;
    private Integer topKeywords;
    private List<Keyword> listKeywords;

    public Section(String name, Integer topKeywords, List<Keyword> listKeywords) {
        this.name = name;
        this.topKeywords = topKeywords;
        this.listKeywords = listKeywords;
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

    public List<Keyword> getListKeywords() {
        return listKeywords;
    }

    public void setListKeywords(List<Keyword> listKeywords) {
        this.listKeywords = listKeywords;
    }
}
