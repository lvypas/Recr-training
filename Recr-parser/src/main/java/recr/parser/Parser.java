package recr.parser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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

        // add synonyms
        Synonym sn1 = new Synonym("companies");
        Synonym sn2 = new Synonym("development");
        Synonym sn3 = new Synonym("learning");
        synonyms = Arrays.asList(sn1, sn2, sn3);

        // add keywords
        Keyword kw1 = new Keyword("looking", null);
        Keyword kw2 = new Keyword("talented", null);
        Keyword kw3 = new Keyword("company", Arrays.asList(sn1));

        Keyword kw4 = new Keyword("develop", Arrays.asList(sn2));
        Keyword kw5 = new Keyword("project", null);

        Keyword kw6 = new Keyword("degree", null);
        Keyword kw7 = new Keyword("commercial", null);
        Keyword kw8 = new Keyword("knowledge", Arrays.asList(sn3));
        Keyword kw9 = new Keyword("principle", null);
        keywords = Arrays.asList(kw1, kw2, kw3, kw4, kw5, kw6, kw7, kw8, kw9);

        // add sections
        Section descriptionSection = new Section("Description", 2, Arrays.asList(kw1, kw2, kw3));
        Section responsibilitySection = new Section("Responsibilities", 1, Arrays.asList(kw4, kw5));
        Section requirementSection = new Section("Requirements", 3, Arrays.asList(kw6, kw7, kw8, kw9));
        sections = Arrays.asList(descriptionSection, requirementSection, requirementSection);
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

    private String[] getSentences(String fileAsString){
        String[] sentences = fileAsString.split("[\\.\\!\\?]");
        return  sentences;
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


    private Integer countKeywordsInSentence(Keyword keyword, String sentence) {
        Pattern p = Pattern.compile(keyword.getWord(), Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(sentence);
        int count = 0;
        while (m.find()){
            count +=1;
        }
        // find synonym
        List<Synonym> synonymList = keyword.getListSynonyms();
        if (synonymList != null) {
            for(Synonym sn: keyword.getListSynonyms()) {
                p = Pattern.compile(sn.getWord(), Pattern.CASE_INSENSITIVE);
                m = p.matcher(sentence);
                while (m.find()){
                    count +=1;
                }
            }
        }
        return  count;
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

   /* private void printListSentencesWithKeywords (List<SentenceWithKeywords> sentenceWithKeywordsList){
        for (SentenceWithKeywords sentenceWithKeywords: sentenceWithKeywordsList) {
            System.out.println(sentenceWithKeywords.getSentence());
            for (Map<Keyword, Integer> map: sentenceWithKeywords.getNumberRepeats()){

            }
        }
    }*/

}
