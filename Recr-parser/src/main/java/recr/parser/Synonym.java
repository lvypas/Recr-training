package recr.parser;

public class Synonym {
    private String word;
    private Keyword relatedKeyword;

    public Synonym(String word, Keyword relatedKeyword) {
        this.word = word;
        this.relatedKeyword = relatedKeyword;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public Keyword getRelatedKeyword() {
        return relatedKeyword;
    }

    public void setRelatedKeyword(Keyword relatedKeyword) {
        this.relatedKeyword = relatedKeyword;
    }
}
