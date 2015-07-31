package recr.parser;

import java.util.Map;

public class Sentence {
    private String text;
    private Map<Keyword, Integer> keywordMap;

    public Sentence(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Map<Keyword, Integer> getKeywordMap() {
        return keywordMap;
    }

    public void setKeywordMap(Map<Keyword, Integer> keywordMap) {
        this.keywordMap = keywordMap;
    }
}
