package recr.parser;

import java.util.Map;

public class SentenceWithKeywords {
    private String Sentence;
    private Map<Keyword, Integer> numberRepeats;

    public String getSentence() {
        return Sentence;
    }

    public void setSentence(String sentence) {
        Sentence = sentence;
    }

    public Map<Keyword, Integer> getNumberRepeats() {
        return numberRepeats;
    }

    public void setNumberRepeats(Map<Keyword, Integer> numberRepeats) {
        this.numberRepeats = numberRepeats;
    }
}
