import java.util.Arrays;
import java.util.Scanner;

public class Taller1 {

    public static class Token {

        public String tipo;
        public String lexema;
        public int fila;
        public int columna;

        /*
        palabras reservadas:
                "num", "bool", "function", "fid", "when", "do", "while", "true", "false", "return",
                "if", "else", "print", "end", "break", "next", "and", "or", "var", "unless",
                "until", "not", "loop", "for"

         tokens:
                "tk_num", "tk_mayor", "tk_mayor_igual", "tk_menor", "tk_menor_igual",
                "tk_asignacion", "tk_sum_asig", "tk_res_asig", "tk_mul_asig", "tk_div_asig",
                "tk_mod_asig", "tk_igualdad", "tk_diferente", "tk_incremento", "tk_decremento",
                "tk_llave_izq", "tk_llave_der", "tk_par_izq", "tk_par_der", "tk_mas", "tk_menos",
                "tk_mul", "tk_div", "tk_mod", "tk_coma", "tk_puntoycoma", "tk_dospuntos"

                newTok = new Token("tk_num", "A", y, x);
                newTok = new Token("tk_mayor", "A", y, x);
                newTok = new Token("tk_mayor_igual", "A", y, x);
                newTok = new Token("tk_menor", "A", y, x);
                newTok = new Token("tk_menor_igual", "A", y, x);
                newTok = new Token("tk_asignacion", "A", y, x);
                newTok = new Token("tk_sum_asig", "A", y, x);
                newTok = new Token("tk_res_asig", "A", y, x);
                newTok = new Token("tk_mul_asig", "A", y, x);
                newTok = new Token("tk_div_asig", "A", y, x);
                newTok = new Token("tk_mod_asig", "A", y, x);
                newTok = new Token("tk_igualdad", "A", y, x);
                newTok = new Token("tk_diferente", "A", y, x);
                newTok = new Token("tk_incremento", "A", y, x);
                newTok = new Token("tk_decremento", "A", y, x);
                newTok = new Token("tk_llave_izq", "A", y, x);
                newTok = new Token("tk_llave_der", "A", y, x);
                newTok = new Token("tk_par_izq", "A", y, x);
                newTok = new Token("tk_par_der", "A", y, x);
                newTok = new Token("tk_mas", "A", y, x);
                newTok = new Token("tk_menos", "A", y, x);
                newTok = new Token("tk_mul", "A", y, x);
                newTok = new Token("tk_div", "A", y, x);
                newTok = new Token("tk_mod", "A", y, x);
                newTok = new Token("tk_coma", "A", y, x);
                newTok = new Token("tk_puntoycoma", "A", y, x);
                newTok = new Token("tk_dospuntos", "A", y, x);

         */

        public Token(String tipo, String lexema, int fila, int columna ) {
            this.tipo = tipo;
            this.lexema = lexema;
            this.fila = fila;
            this.columna = columna;
        }

        public Token(String tipo, int fila, int columna ) {
            this.tipo = tipo;
            this.fila = fila;
            this.columna = columna;
        }

        public void PrintTokenWithoutLexema() {
            System.out.println("<"+this.tipo+", "+this.fila+", "+this.columna+">");
        }

        public void PrintTokenWithLexema() {
            System.out.println("<"+this.tipo+", "+this.lexema+", "+this.fila+", "+this.columna+">");
        }
    }

    public static void main(String[] args) 
    {
        String palabrasReservadas[] = {"num", "bool", "function", "when", "do", "while", "true", "false", "return",
                "if", "else", "print", "end", "break", "next", "and", "or", "var", "unless", "until", "not", "loop", "for"};
        
        Scanner scanner = new Scanner(System.in);
        
        int x = 0;
        int y = 1;
        int xToprint = 1;
        int estado = 1;
        String lexemaActual = "";
        Token newTok;
        
        while(scanner.hasNext())
        {
            String s = scanner.nextLine();
            
            for(int i = 0; i < s.length(); i++)
            {
            	x++;
                int numeroActual = s.charAt(i);
                int numeroSiguiente = -1;
                
                if(i < s.length() - 1)
                {
                    numeroSiguiente = s.charAt(i + 1);
                }
                
                char letra = s.charAt(i);
                if(estado == 1)
                {
                    estado = estadoAFD(numeroActual);
                    xToprint = x;
                }
                
                //System.out.println("Estado: " + estado);
                //System.out.println("numeroActual: " + numeroActual);
                //System.out.println("numeroSiguiente: " + numeroSiguiente);
                //System.out.println("x: " + x);
                if(numeroActual == 10)
                {
                    y++;
                    x=1;
                }
                else if(numeroActual == 9)
                {
                    x += 3;//si es que tab vale por 4 espacios (3 + 1 del x++ al comienzo)
                }
                else if(numeroActual == 32)
                {
                    continue;
                }
                else if (estado==1)
                {
                	System.out.println("Error léxico(línea:"+x+",posición:"+y+")");
                	return;
                }
                switch (estado)
                {
                    case 2://num
                        lexemaActual += letra;
                        if (numeroSiguiente >= 48 && numeroSiguiente <= 57)
                        {
                            estado = 2;
                        } 
                        else if (numeroSiguiente == 46) 
                        {
                            estado = 3;
                        } 
                        else
                            estado = 4;
                        break;
                    case 3://numero con punto
                        lexemaActual += letra;
                        if (numeroSiguiente >= 48 && numeroSiguiente <= 57)
                        {
                            estado = 3;
                        } 
                        else
                            estado = 4;
                        break;
                    case 4:
                        newTok = new Token("tk_num", lexemaActual, y, xToprint);
                        newTok.PrintTokenWithLexema();
                        estado = 1;
                        lexemaActual="";
                        i--;
                        x = x - fix(numeroActual);
                        break;
                    case 5:
                    	lexemaActual += letra;
                        if ((numeroSiguiente >= 65 && numeroSiguiente <= 90) ||
                            (numeroSiguiente >= 97 && numeroSiguiente <= 122) ||
                            (numeroSiguiente >= 48 && numeroSiguiente <= 57)) 
                        {
                            estado = 5;
                        }
                        else
                            estado = 6;
                        break;
                    case 6:
                    	if (Arrays.asList(palabrasReservadas).contains(lexemaActual)) 
                    	{
                    		newTok = new Token(lexemaActual, y, xToprint);
                            newTok.PrintTokenWithoutLexema();
                    	}
                    	else 
                    	{
                    		newTok = new Token("tk_id", lexemaActual, y, xToprint);
                            newTok.PrintTokenWithLexema();
                    	}
                        estado = 1;
                        lexemaActual="";
                        i--;
                        x = x - fix(numeroActual);
                        break;
                    case 7:
                        if (numeroSiguiente == 43) 
                        {
                            estado = 8;
                        } 
                        else if (numeroSiguiente == 61)
                            estado = 10;
                        else
                            estado = 9;
                        break;
                    case 8:
                        newTok = new Token("tk_incremento", y, xToprint);
                        newTok.PrintTokenWithoutLexema();
                        estado = 1;
                        break;
                    case 9:
                        newTok = new Token("tk_mas", y, xToprint);
                        newTok.PrintTokenWithoutLexema();
                        estado = 1;
                        i--;
                        x = x - fix(numeroActual);
                        break;
                    case 10:
                        newTok = new Token("tk_sum_asig", y, xToprint);
                        newTok.PrintTokenWithoutLexema();
                        estado = 1;
                        break;
                    case 11:
                        if (numeroSiguiente == 61)
                            estado = 12;
                        break;
                    case 12:
                        newTok = new Token("tk_igualdad", y, x);
                        newTok.PrintTokenWithoutLexema();
                        estado = 1;
                        break;
                    case 13:
                        if (numeroSiguiente == 61)
                            estado = 15;
                        else
                            estado = 14;
                        break;
                    case 14:
                        newTok = new Token("tk_div", y, x);
                        newTok.PrintTokenWithoutLexema();
                        estado = 1;
                        break;
                    case 15:
                        newTok = new Token("tk_div_asig", y, x);
                        newTok.PrintTokenWithoutLexema();
                        estado = 1;
                        break;
                    case 16:
                        if (numeroSiguiente == 61)
                            estado = 18;
                        else
                            estado = 17;
                        break;
                    case 17:
                        newTok = new Token("tk_mul", y, x);
                        newTok.PrintTokenWithoutLexema();
                        estado = 1;
                        break;
                    case 18:
                        newTok = new Token("tk_mul_asig", y, x);
                        newTok.PrintTokenWithoutLexema();
                        estado = 1;
                        break;
                    case 19:
                        if (numeroSiguiente == 61)
                            estado = 21;
                        else
                            estado = 20;
                        break;
                    case 20:
                        newTok = new Token("tk_dospuntos", y, x);
                        newTok.PrintTokenWithoutLexema();
                        estado = 1;
                        break;
                    case 21:
                        newTok = new Token("tk_asignacion", y, x);
                        newTok.PrintTokenWithoutLexema();
                        estado = 1;
                        break;
                    case 22:
                        if ((numeroSiguiente >= 65 && numeroSiguiente <= 90) ||
                                (numeroSiguiente >= 97 && numeroSiguiente <= 122))
                            estado = 23;
                        break;
                    case 23:
                        if ((numeroSiguiente >= 65 && numeroSiguiente <= 90) ||
                                (numeroSiguiente >= 97 && numeroSiguiente <= 122))
                            estado = 23;
                        else
                            estado = 24;
                        break;
                    case 24:
                        newTok = new Token("tk_fid", y, x);
                        newTok.PrintTokenWithoutLexema();
                        estado = 1;
                        break;
                    case 25:
                        if (numeroSiguiente == 61)
                            estado = 27;
                        else
                            estado = 26;
                        break;
                    case 26:
                        newTok = new Token("tk_mod", y, x);
                        newTok.PrintTokenWithoutLexema();
                        estado = 1;
                        break;
                    case 27:
                        newTok = new Token("tk_mod_asig", y, x);
                        newTok.PrintTokenWithoutLexema();
                        estado = 1;
                        break;
                    case 28://meta
                        if (numeroSiguiente == 61)
                            estado = 27;
                        else
                            estado = 26;
                        break;
                }
            }
        }
    }
    
    static int fix(int numAct) 
    {
    	if(numAct==9) 
        {
        	return 4;
        }
        return 1;
    }

    static int estadoAFD(int n)
    {
        if (n >= 48 && n <= 57)
        {//del 0 al 9
            return 2;
        }
        else if ((n >= 65 && n <= 90) || (n >= 97 && n <= 122))
        {//del (A a la Z) o (a a la z)
            return 5;
        }
        else if (n == 43)
        {//+
        	return 7;
        }
        return 1;
    }
}