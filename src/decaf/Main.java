package decaf;

import java.io.*;
//import antlr.Token;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import java6035.tools.CLI.*;

class Main {
    public static void main(String[] args) {
        try {
        	CLI.parse (args, new String[0]);

        	InputStream inputStream = args.length == 0 ?
                    System.in : new java.io.FileInputStream(CLI.infile);

        	if (CLI.target == CLI.SCAN)
        	{
        		DecafLexer lexer = new DecafLexer(new ANTLRInputStream(inputStream));
        		Token token;
        		boolean done = false;
        		while (!done)
        		{
        			try
        			{
		        		for (token=lexer.nextToken(); token.getType()!=Token.EOF; token=lexer.nextToken())
		        		{
		        			String type = "";
							String charLiteral = "";
							String hexlit = "";
		        			String text = token.getText();
							String str = "";
						//System.out.println("\nTESTESTESTESTES TEXT = " + text + "\n");
		        			switch (token.getType())
		        			{
		        			    case DecafLexer.ID:
                                    type = " IDENTIFIER";
                                    break;
								case DecafLexer.CHARLITERAL:
									charLiteral = " CHARLITERAL ";
									break;
								case  DecafLexer.HEXLIT:
									hexlit = " INTLITERAL ";
									break;
								case DecafLexer.STRING:
									str = " STRINGLITERAL ";
									break;
		        			}
							
							String typeToken = type + charLiteral + hexlit + str;
		        			System.out.println (token.getLine() + typeToken + text);
		        		}
		        		done = true;
        			} catch(Exception e) {
        	        	// print the error:
        	            System.out.println(CLI.infile+" "+e);
        	            lexer.skip();
        	        }
        		}
        	}
        	else if (CLI.target == CLI.PARSE || CLI.target == CLI.DEFAULT)
        	{
        		DecafLexer lexer = new DecafLexer(new ANTLRInputStream(inputStream));
				CommonTokenStream tokens = new CommonTokenStream(lexer);
        		DecafParser parser = new DecafParser(tokens);
                parser.program();
        	}
        	
        } catch(Exception e) {
        	// print the error:
            System.out.println(CLI.infile+" "+e);
        }
    }

    public static void Error(Token token) {
        System.out.println("Error: " + token.getText());
    }
}

