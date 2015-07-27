package recr.parser;

import java.util.ArrayList;
import java.util.List;

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

    public List<Keywords> getRelatedKeywords(Keywords keyword) {
        List<Keywords> keywordses = new ArrayList<Keywords>();
        for (Synonyms synonym : Synonyms.values()){
            if (keyword.equals(synonym.getRelatedKeyword())) keywordses.add(keyword);
        }
        return  keywordses;
    }

}
