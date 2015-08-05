package recr.parser;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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
        // populate keyword with sentences multimap
        List<KeywordWithSentences> keywordWithSentencesList = new ArrayList<KeywordWithSentences>();
        for (Keyword keyword: keywords) {
            KeywordWithSentences keywordWithSentences = new KeywordWithSentences();
            keywordWithSentences.setKeyword(keyword);
            keywordWithSentences.setCount(0);
            keywordWithSentences.setSentenceList(Arrays.asList());
            for (String sentence: sentences) {
                Integer count = countKeywordsInSentence(keyword, sentence);
                keywordWithSentences.setCount(keywordWithSentences.getCount() + count);
                keywordWithSentences.setSentenceList(Arr);


            }

        }


    }

    private String readFile(String file) {
        String content = null;
        try {
            content = Files.toString(new File("JobDescription.txt"), Charsets.UTF_8);
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

    /*private List<ParsedJobDescription> getTotalCountKeywords(List<SentenceWithKeywords> sentencesWithKeywords) {
        List<ParsedJobDescription> listParsedJobDescription = new ArrayList<ParsedJobDescription>();
        for (SentenceWithKeywords sentenceWithKeywords: sentencesWithKeywords){
            for (Keyword keyword: keywords) {
                ParsedJobDescription parsedJobDescription = new ParsedJobDescription();
                parsedJobDescription.setKeyword(keyword);
                parsedJobDescription.setAmount(0);
                if (sentenceWithKeywords.getNumberRepeats().containsKey(keyword)) {
                    Integer oldAmount = parsedJobDescription.getAmount();
                    parsedJobDescription.setAmount(oldAmount + sentenceWithKeywords.getNumberRepeats().get(keyword).intValue() );
                }
                if (parsedJobDescription.getAmount() > 0) listParsedJobDescription.add(parsedJobDescription);
            }
        }
        return listParsedJobDescription;
    }

    private void printParsedJobDescription (ParsedJobDescription description) {
        System.out.println(description.getKeyword().getWord() + " : " + description.getAmount() + " : " + description.getSentence());
    }*/

}
