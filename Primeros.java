import java.util.Arrays;
import java.util.Scanner;
import java.util.ArrayList; 

public class Primeros {
	
	static ArrayList<String> stmt = new ArrayList<String>();
	static ArrayList<String> lexpr = new ArrayList<String>();
	static ArrayList<String> nexpr = new ArrayList<String>();
	static ArrayList<String> rexpr = new ArrayList<String>();
	static ArrayList<String> simple_expr = new ArrayList<String>();
	static ArrayList<String> factor = new ArrayList<String>();
	static ArrayList<String> stmt_block = new ArrayList<String>();
	static ArrayList<String> term = new ArrayList<String>();
	
	public static void main(String[] args)
    {
		String palabrasReservadas[] = {"num", "bool", "function", "when", "do", "while", "true", "false", "return",
            "if", "else", "print", "end", "break", "next", "and", "or", "var", "unless", "until", "not", "loop", "for", "repeat","input"};
		
		String NoTerminales[] = {"stmt","lexpr","nexpr","rexpr","simple_expr","term","factor","stmt_block"};
		
		ArrayList<String> grammar = new ArrayList<String>(); 
		
		Scanner scanner = new Scanner(System.in);

		String s = "";
		
		while(true)
	    {
			s = scanner.nextLine();
			grammar.add(s);
			if (s.equals("end"))
			{
				break;
			}
	    }
		System.out.println(grammar);
		int i = 0;
		String NT = "";
		String pk = "";
		while(true)
		{
			System.out.println(grammar.indexOf(""));
			s = grammar.get(i);
			if (s.equals("end"))
			{
				break;
			}
			//System.out.println(s);
			for(int j = 0; j < s.length(); j++) 
			{
				if(s.charAt(j) == 58)
                {
                    NT = s.substring(0, j);
                    s = s.substring(j+2, s.length());
                    break;
                }
			}
			for(int j = 1; j < s.length(); j++) 
			{
				if(s.charAt(j) == 32)
                {
                    pk = s.substring(0, j);
                    s = s.substring(j+1, s.length());
                    break;
                }
			}
			if (!Arrays.asList(NoTerminales).contains(pk)) 
			{
				//System.out.println(NT);
				//System.out.println(pk);
				AddToFirsts(NT,pk);
			}else 
			{
				i = IndexOfNT(NT,grammar) - 1;
				//funcion
				//
			}
			i++;
		}
		System.out.println(stmt);
    }
	
	static void AddToFirsts(String NT,String pk)
    {
		switch(NT) 
		{
		case "stmt": 
			if(!stmt.contains(pk)) 
			{
				stmt.add(pk);
			}
			break;
		case "lexpr": 
			if(!lexpr.contains(pk)) 
			{
				lexpr.add(pk);
			}
			break;
		case "nexpr": 
			if(!nexpr.contains(pk)) 
			{
				nexpr.add(pk);
			}
			break;
		case "rexpr": 
			if(!rexpr.contains(pk)) 
			{
				rexpr.add(pk);
			}
			break;
		case "simple_expr": 
			if(!simple_expr.contains(pk)) 
			{
				simple_expr.add(pk);
			}
			break;
		case "factor": 
			if(!factor.contains(pk)) 
			{
				factor.add(pk);
			}
			break;
		case "stmt_block": 
			if(!stmt_block.contains(pk)) 
			{
				stmt_block.add(pk);
			}
			break;
		case "term": 
			if(!term.contains(pk)) 
			{
				term.add(pk);
			}
			break;
		}
    }
	
	static int IndexOfNT(String NTb, ArrayList<String> grammar) 
	{
		String s = "";
		String NT = "";
		for (int i=0; i<grammar.size(); i++)
		{
			s = grammar.get(i);
			for(int j = 0; j < s.length(); j++) 
			{
				if(s.charAt(j) == 58)
                {
                    NT = s.substring(0, j);
                    if (NT.equals(NTb)) 
                    {
                    	return i;
                    }
                    break;
                }
			}
		}
		return -1;
	}
	
}