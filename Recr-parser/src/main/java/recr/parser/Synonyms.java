package recr.parser;

public enum Synonyms {
    WriteCode(Keywords.DevelopCode),
    CreateCode(Keywords.DevelopCode);

    private final Keywords relatedKeyword;

    Synonyms(Keywords relatedKeyword) {
        this.relatedKeyword = relatedKeyword;
    }

    public Keywords getRelatedKeyword() {
        return  this.relatedKeyword;
    }
}
