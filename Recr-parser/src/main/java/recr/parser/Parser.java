package recr.parser;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
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
        // populate keyword with sentences object
        List<KeywordWithSentences> keywordWithSentencesList = new ArrayList<KeywordWithSentences>();
        for (Keyword keyword: keywords) {
            KeywordWithSentences keywordWithSentences = new KeywordWithSentences();
            keywordWithSentences.setKeyword(keyword);
            keywordWithSentences.setCount(0);
            List<Sentence> sentenceList = new ArrayList<Sentence>();
            for (String sentence: sentences) {
                Integer count = countKeywordsInSentence(keyword, sentence);
                keywordWithSentences.setCount(keywordWithSentences.getCount() + count);
                if (count > 0) sentenceList.add(new Sentence(sentence));
            }
            keywordWithSentences.setSentenceList(sentenceList);
            if (keywordWithSentences.getCount() > 0) keywordWithSentencesList.add(keywordWithSentences);
        }
        // sort list by ascending
        Collections.sort(keywordWithSentencesList);

        //remove duplicated sentences that already reserved for another keyword
        removeDuplicateSentences(keywordWithSentencesList);

        Collections.reverse(keywordWithSentencesList);

        // print results
        printKeywordWithSentence(keywordWithSentencesList);
    }

    private String readFile(String file) {
        String content = null;
        try {
            content = Files.toString(new File(file), Charsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
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

    private void removeUnusedKeywords(List<KeywordWithSentences> keywordWithSentenceses){
        for (Section section: sections){

        }
    }

    private void removeDuplicateSentences(List<KeywordWithSentences> keywordWithSentencesList) {
        for (KeywordWithSentences keywordWithSentences: keywordWithSentencesList) {
            keywordWithSentences.getSentenceList()

        }
    }

    private void printKeywordWithSentence (List<KeywordWithSentences> keywordWithSentencesList) {
        for (KeywordWithSentences keywordWithSentences: keywordWithSentencesList) {
            System.out.println(keywordWithSentences.getKeyword().getWord() + " : " + keywordWithSentences.getCount()
                    + " : " + keywordWithSentences.getSentenceList().get(0).getText());
        }
    }

}
