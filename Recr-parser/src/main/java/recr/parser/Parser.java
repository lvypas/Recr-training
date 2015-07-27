package recr.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {
    String jobDescription = "Responsibilities:\n" +
            "Participate in one or more SoftwareDevelopment CreateCode projects, working in a team of 3 to 10 developers\n" +
            "DevelopCode and debug program code, design system architecture, research into newest software technologies and development tools as well as project domain\n" +
            "Take part in DevelopCode project activities: requirements WriteCode analysis, release building, code review\n" +
            "Requirements:\n" +
            "BS/MS degree in Computer Science\n" +
            "3+ years of software development in commercial projects\n" +
            "Solid knowledge of fundamental data structures and algorithms\n" +
            "Good understanding of OOD/OOA principles\n" +
            "Knowledge of design patterns, experience in system architecture development\n" +
            "Knowledge of Java in scope of J2SE specification\n" +
            "English: intermediate level or above, enough for reading/writing technical documentation\n" +
            "Responsible, proactive, self-dependent person\n" +
            "Good communication skills, readiness for teamwork\n" +
            "Additional skills considered as an advantage:\n" +
            "Experience in development of software for financial or brokerage business\n" +
            "Understanding of parallel programming, experience in multithreaded application development\n" +
            "Understanding of relational databases design, SQL knowledge, working experience with Oracle and MySQL\n" +
            "Knowledge of 2 or more programming languages\n" +
            "Knowledge of Unix-family OS\n" +
            "Experience in development of distributed software systems with strong performance and reliability requirements\n" +
            "Experience in GUI design\n" +
            "Experience in Web and mobile application development\n" +
            "Knowledge of newest Java development technologies, libraries and frameworks (Spring, Hibernate, Swing, GWT, etc.)\n" +
            "Knowledge of methodologies and principles of application lifecycle management, experience with bug tracking, version control and requirements management systems";
    Sections sections;
    Keywords keywords;
    Synonyms synonyms;


    public void parseJobDescription(){
        Map<Keywords, Integer> countKeywords = new HashMap<Keywords, Integer>();
        for (Keywords keyword: Keywords.values()){
                countKeywords.put(keyword, countKeyword(keyword));
        }
        for (Keywords kw: countKeywords.keySet()) {
            System.out.println(kw.name() + ":" + countKeywords.get(kw).intValue());
        }
    }

    private Integer countKeyword(Keywords keyword) {
        String str = jobDescription;
        Pattern p = Pattern.compile(keyword.name());
        Matcher m = p.matcher(str);
        int count = 0;
        while (m.find()){
            count +=1;
        }
        // find synonyms
        for(Synonyms sn: getRelatedSynonyms(keyword)) {
            p = Pattern.compile(sn.name());
            m = p.matcher(str);
            while (m.find()){
                count +=1;
            }
        }
        return  count;
    }

    private List<Synonyms> getRelatedSynonyms(Keywords keyword) {
        List<Synonyms> synonymses = new ArrayList<Synonyms>();
        for (Synonyms synonym : Synonyms.values()){
            if (keyword.equals(synonym.getRelatedKeyword())) synonymses.add(synonym);
        }
        return  synonymses;
    }

    private List<Keywords> getRelatedKeywords(Sections section) {
        List<Keywords> keywordses = new ArrayList<Keywords>();
        for (Keywords keyword : Keywords.values()){
            if (section.equals(keyword.getRelatedSection())) keywordses.add(keyword);
        }
        return  keywordses;
    }

}
