package recr.parser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {
    String fileName;
    List<Section> sections;
    List<Keyword> keywords;
    List<Synonym> synonyms;

    public Parser(String fileName) {
        this.fileName = fileName;

        // add sections
        Section descriptionSection = new Section("Description", 2);
        Section responsibilitySection = new Section("Responsibilities", 1);
        Section requirementSection = new Section("Requirements", 3);

        sections = new ArrayList<Section>();
        sections.add(descriptionSection);
        sections.add(responsibilitySection);
        sections.add(requirementSection);

        // add keywords
        Keyword kw1 = new Keyword("looking", descriptionSection);
        Keyword kw2 = new Keyword("talented", descriptionSection);
        Keyword kw3 = new Keyword("company", descriptionSection);

        Keyword kw4 = new Keyword("develop", responsibilitySection);
        Keyword kw5 = new Keyword("project", responsibilitySection);

        Keyword kw6 = new Keyword("degree", requirementSection);
        Keyword kw7 = new Keyword("commercial", requirementSection);
        Keyword kw8 = new Keyword("knowledge", requirementSection);
        Keyword kw9 = new Keyword("principle", requirementSection);

        keywords = new ArrayList<Keyword>();
        keywords.add(kw1);
        keywords.add(kw2);
        keywords.add(kw3);
        keywords.add(kw4);
        keywords.add(kw5);
        keywords.add(kw6);
        keywords.add(kw7);
        keywords.add(kw8);
        keywords.add(kw9);

        // add synonyms
        Synonym sn1 = new Synonym("companies", kw3);
        Synonym sn2 = new Synonym("development", kw4);
        Synonym sn3 = new Synonym("learning", kw8);

        synonyms = new ArrayList<Synonym>();
        synonyms.add(sn1);
        synonyms.add(sn2);
        synonyms.add(sn3);

    }


    public void parseJobDescription(){
        // get sentences into array of strings
        String[] sentences = getSentences(readFile(fileName));
        // parse array of string and get only sentences with keywords. also count repeating for keywords
        List<SentenceWithKeywords> sentencesWithKeywords = getSentencesWithKeywords(sentences);
        // get map with keywords related section and sorted
        List<ParsedJobDescription> parsedJobDescriptionList = getTotalCountKeywords(sentencesWithKeywords);
        for (ParsedJobDescription parsedJobDescr: parsedJobDescriptionList) {
            printParsedJobDescription(parsedJobDescr);
        }

        /*Map<Keyword, Integer> countKeywords = new TreeMap<Keyword, Integer>();
        for (Keyword keyword: keywords){
                countKeywords.put(keyword, countKeywordsInSentence(keyword));
        }
        for (Section sect: sections) {
            System.out.println(sect.getName());
            Integer topKeywords = sect.getTopKeywords();
            Integer numberKeywordsToShow = topKeywords;
            for (Map.Entry<Keyword, Integer> entry  : entriesSortedByValuesDesc(countKeywords)) {
                if (entry.getKey().getRelatedSection().equals(sect) && numberKeywordsToShow > 0){
                    System.out.println(entry.getKey().getWord()+ " : " +entry.getValue());
                    numberKeywordsToShow--;
                }
            }
        }*/
    }

    private List<ParsedJobDescription> getTotalCountKeywords(List<SentenceWithKeywords> sentencesWithKeywords) {
        List<ParsedJobDescription> listParsedJobDescription = new ArrayList<ParsedJobDescription>();
        for (Keyword keyword: keywords) {
            ParsedJobDescription parsedJobDescription = new ParsedJobDescription();
            parsedJobDescription.setKeyword(keyword);
            parsedJobDescription.setAmount(0);
            for (SentenceWithKeywords sentenceWithKeywords: sentencesWithKeywords){
                if (sentenceWithKeywords.getNumberRepeats().containsKey(keyword)) {
                    Integer oldAmount = parsedJobDescription.getAmount();
                    parsedJobDescription.setAmount(oldAmount + sentenceWithKeywords.getNumberRepeats().get(keyword).intValue() );
                }
            }
            if (parsedJobDescription.getAmount() > 0) listParsedJobDescription.add(parsedJobDescription);
        }
        return listParsedJobDescription;
    }

    private List<SentenceWithKeywords> getSentencesWithKeywords(String[] sentences) {
        List<SentenceWithKeywords> sentenceWithKeywordsList = new ArrayList<SentenceWithKeywords>();
        for (String sentence: sentences) {
            SentenceWithKeywords sentenceWithKeywords = null;
            for (Keyword keyword: keywords) {
                Integer countRepeats = countKeywordsInSentence(keyword, sentence);
                if (countRepeats > 0) {
                    sentenceWithKeywords = new SentenceWithKeywords();
                    sentenceWithKeywords.setSentence(sentence);
                    Map<Keyword, Integer> keywordIntegerMap = new HashMap<Keyword, Integer>();
                    keywordIntegerMap.put(keyword, countRepeats);
                    sentenceWithKeywords.setNumberRepeats(keywordIntegerMap);
                }
            }
            if (null != sentenceWithKeywords) sentenceWithKeywordsList.add(sentenceWithKeywords);
        }
        return sentenceWithKeywordsList;
    }

    private String[] getSentences(String fileAsString){
        String[] sentences = fileAsString.split("[\\.\\!\\?]");
        return  sentences;
    }

    private Integer countKeywordsInSentence(Keyword keyword, String sentence) {
        Pattern p = Pattern.compile(keyword.getWord(), Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(sentence);
        int count = 0;
        while (m.find()){
            count +=1;
        }
        // find synonym
        for(Synonym sn: getRelatedSynonyms(keyword)) {
            p = Pattern.compile(sn.getWord(), Pattern.CASE_INSENSITIVE);
            m = p.matcher(sentence);
            while (m.find()){
                count +=1;
            }
        }
        return  count;
    }

    private String readFile(String file) {
        BufferedReader br = null;
        StringBuffer result = new StringBuffer("");
        try {
            String sCurrentLine;
            br = new BufferedReader(new FileReader(file));
            while ((sCurrentLine = br.readLine()) != null) {
                result.append(sCurrentLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) br.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result.toString();
    }

    private List<Synonym> getRelatedSynonyms(Keyword keyword) {
        List<Synonym> result = new ArrayList<Synonym>();
        for (Synonym synonym : synonyms){
            if (keyword.equals(synonym.getRelatedKeyword())) result.add(synonym);
        }
        return  result;
    }

    private List<Keyword> getRelatedKeywords(Section section) {
        List<Keyword> result = new ArrayList<Keyword>();
        for (Keyword keyword : keywords){
            if (section.equals(keyword.getRelatedSection())) result.add(keyword);
        }
        return  result;
    }

    static <K,V extends Comparable<? super V>> SortedSet<Map.Entry<K,V>> entriesSortedByValuesDesc(Map<K,V> map) {
        SortedSet<Map.Entry<K,V>> sortedEntries = new TreeSet<Map.Entry<K,V>>(
                new Comparator<Map.Entry<K,V>>() {
                    @Override public int compare(Map.Entry<K,V> e1, Map.Entry<K,V> e2) {
                        int res = e2.getValue().compareTo(e1.getValue());
                        return res != 0 ? res : 1; // Special fix to preserve items with equal values
                    }
                }
        );
        sortedEntries.addAll(map.entrySet());
        return sortedEntries;
    }

    private void printParsedJobDescription (ParsedJobDescription description) {
        System.out.println(description.getKeyword().getWord() + " : " + description.getAmount() + " : " + description.getSentence());
    }

}
