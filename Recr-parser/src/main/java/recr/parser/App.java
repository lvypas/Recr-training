package recr.parser;

public class App
{
    public static void main( String[] args )
    {
        Parser parser = new Parser(args[0]);
        parser.parseJobDescription();
    }
}
