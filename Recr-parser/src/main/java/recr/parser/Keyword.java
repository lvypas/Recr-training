package recr.parser;

import java.util.List;

public class Keyword {
    private String word;
    private List<Synonym> listSynonyms;

    public Keyword(String word, List<Synonym> listSynonyms) {
        this.word = word;
        this.listSynonyms = listSynonyms;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public List<Synonym> getListSynonyms() {
        return listSynonyms;
    }

    public void setListSynonyms(List<Synonym> listSynonyms) {
        this.listSynonyms = listSynonyms;
    }
}
