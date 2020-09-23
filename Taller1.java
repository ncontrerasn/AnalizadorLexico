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
                "tk_llave_izq", "tk_llave_der", "tk_par_izq", "tk_par_der", "tk_cor_izq", "tk_cor_der" "tk_mas", "tk_menos",
                "tk_mul", "tk_div", "tk_mod", "tk_coma", "tk_puntoycoma", "tk_dospuntos"
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
        Token newTok = null;

        while(scanner.hasNext())
        {
            String s = scanner.nextLine();
            s = s + '\n';

            for(int i = 0; i < s.length(); i++)
            {
                x++;
                int numeroActual = s.charAt(i);
                int numeroSiguiente = -1;
                char letra = s.charAt(i);
                
                if (numeroActual==35) //No tomar en cuenta comentarios
                {// #
                	break;
                }
                
                if(i < s.length() - 1)
                {
                    numeroSiguiente = s.charAt(i + 1);
                }

                //System.out.println("letra: " + letra);
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
                    x=0;
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
                    estado = 999; //Estado encargado del error lexico
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
                        else
                            estado = 999;
                        break;
                    case 12:
                        newTok = new Token("tk_igualdad", y, xToprint);
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
                        newTok = new Token("tk_div", y, xToprint);
                        newTok.PrintTokenWithoutLexema();
                        estado = 1;
                        i--;
                        x = x - fix(numeroActual);
                        break;
                    case 15:
                        newTok = new Token("tk_div_asig", y, xToprint);
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
                        newTok = new Token("tk_mul", y, xToprint);
                        newTok.PrintTokenWithoutLexema();
                        estado = 1;
                        i--;
                        x = x - fix(numeroActual);
                        break;
                    case 18:
                        newTok = new Token("tk_mul_asig", y, xToprint);
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
                        newTok = new Token("tk_dospuntos", y, xToprint);
                        newTok.PrintTokenWithoutLexema();
                        estado = 1;
                        i--;
                        x = x - fix(numeroActual);
                        break;
                    case 21:
                        newTok = new Token("tk_asignacion", y, xToprint);
                        newTok.PrintTokenWithoutLexema();
                        estado = 1;
                        break;
                    case 22:
                        lexemaActual += letra;
                        if ((numeroSiguiente >= 65 && numeroSiguiente <= 90) ||
                                (numeroSiguiente >= 97 && numeroSiguiente <= 122))
                            estado = 23;
                        else
                            estado = 999;
                        break;
                    case 23:
                        lexemaActual += letra;
                        if ((numeroSiguiente >= 65 && numeroSiguiente <= 90) ||
                                (numeroSiguiente >= 97 && numeroSiguiente <= 122) ||
                                (numeroSiguiente >= 48 && numeroSiguiente <= 57))
                            estado = 23;
                        else
                            estado = 24;
                        break;
                    case 24:
                        newTok = new Token("tk_fid",lexemaActual, y, xToprint);
                        newTok.PrintTokenWithLexema();
                        estado = 1;
                        lexemaActual = "";
                        i--;//solo con los de estrella
                        x = x - fix(numeroActual);//con asterisco, reinciar lexema cunado se haga un token
                        break;
                    case 25://
                        if (numeroSiguiente == 61)
                            estado = 27;
                        else
                            estado = 26;
                        break;
                    case 26://
                        newTok = new Token("tk_mod",lexemaActual, y, xToprint);
                        newTok.PrintTokenWithLexema();
                        estado = 1;
                        lexemaActual = "";
                        i--;//solo con los de estrella
                        x = x - fix(numeroActual);//con asterisco, reinciar lexema cunado se haga un token
                        break;
                    case 27:
                        newTok = new Token("tk_mod_asig", y, xToprint);
                        newTok.PrintTokenWithoutLexema();
                        estado = 1;
                        lexemaActual = "";
                        break;
                    case 28://meta
                        if (numeroSiguiente == 61)
                            estado = 30;
                        else
                            estado = 29;
                        break;
                    case 29:
                        newTok = new Token("tk_mayor", y, xToprint);
                        newTok.PrintTokenWithoutLexema();
                        estado = 1;
                        lexemaActual = "";
                        i--;//solo con los de estrella
                        x = x - fix(numeroActual);//con asterisco, reinciar lexema cunado se haga un token
                        break;
                    case 30:
                        newTok = new Token("tk_mayor_igual", y, xToprint);
                        newTok.PrintTokenWithoutLexema();
                        estado = 1;
                        lexemaActual = "";
                        break;
                    case 31://meta
                        if (numeroSiguiente == 61)
                            estado = 32;
                        break;
                    case 32:
                        newTok = new Token("tk_diferente", y, xToprint);
                        newTok.PrintTokenWithoutLexema();
                        estado = 1;
                        lexemaActual = "";
                        break;
                    case 33://meta
                        if (numeroSiguiente == 45)
                            estado = 34;
                        else if(numeroSiguiente == 61)
                            estado = 36;
                        else
                            estado = 35;
                        break;
                    case 34:
                        newTok = new Token("tk_decremento", y, xToprint);
                        newTok.PrintTokenWithoutLexema();
                        estado = 1;
                        lexemaActual = "";
                        break;
                    case 35:
                        newTok = new Token("tk_menos",lexemaActual, y, xToprint);
                        newTok.PrintTokenWithLexema();
                        estado = 1;
                        lexemaActual = "";
                        i--;//solo con los de estrella
                        x = x - fix(numeroActual);//con asterisco, reinciar lexema cunado se haga un token
                        break;
                    case 36:
                        newTok = new Token("tk_res_asig", y, xToprint);
                        newTok.PrintTokenWithoutLexema();
                        estado = 1;
                        lexemaActual = "";
                        break;
                    case 37:
                        switch (numeroActual){
                            case 40:
                                newTok = new Token("tk_par_izq", y, xToprint);
                                newTok.PrintTokenWithoutLexema();
                                break;
                            case 41:
                                newTok = new Token("tk_par_der", y, xToprint);
                                newTok.PrintTokenWithoutLexema();
                                break;
                            case 91:
                                newTok = new Token("tk_cor_izq", y, xToprint);
                                newTok.PrintTokenWithoutLexema();
                                break;
                            case 93:
                                newTok = new Token("tk_cor_der", y,xToprint);
                                newTok.PrintTokenWithoutLexema();
                                break;
                            case 123:
                                newTok = new Token("tk_llave_izq", y, xToprint);
                                newTok.PrintTokenWithoutLexema();
                                break;
                            case 125:
                                newTok = new Token("tk_llave_der", y,xToprint);
                                newTok.PrintTokenWithoutLexema();
                                break;
                            case 59:
                                newTok = new Token("tk_puntoycoma", y, xToprint);
                                newTok.PrintTokenWithoutLexema();
                                break;
                            case 44:
                                newTok = new Token("tk_coma", y, xToprint);
                                newTok.PrintTokenWithoutLexema();
                                break;
                        }
                        estado = 1;
                        lexemaActual = "";
                        break;
                    case 38://meta
                        if (numeroSiguiente == 61)
                            estado = 40;
                        else
                            estado = 39;
                        break;
                    case 39:
                        newTok = new Token("tk_menor", y, xToprint);
                        newTok.PrintTokenWithoutLexema();
                        estado = 1;
                        lexemaActual = "";
                        i--;//solo con los de estrella
                        x = x - fix(numeroActual);//con asterisco, reinciar lexema cunado se haga un token
                        break;
                    case 40:
                        newTok = new Token("tk_menor_igual", y, xToprint);
                        newTok.PrintTokenWithoutLexema();
                        estado = 1;
                        lexemaActual = "";
                        break;
                    case 999:
                        System.out.println("Error léxico(línea:"+y+",posición:"+xToprint+")");
                        return;
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
        {// del 0 al 9
            return 2;
        }
        else if ((n >= 65 && n <= 90) || (n >= 97 && n <= 122))
        {// del (A a la Z) o (a a la z)
            return 5;
        }
        else if (n == 43)
        {// +
            return 7;
        }
        else if (n == 61)
        {// =
            return 11;
        }
        else if (n == 47)
        {// /(Div)
            return 13;
        }
        else if (n == 42)
        {// *
            return 16;
        }
        else if (n == 58)
        {// :
            return 19;
        }
        else if (n == 64)
        {// @
            return 22;
        }
        else if (n == 37)
        {// %
            return 25;
        }
        else if (n == 62)
        {// >
            return 28;
        }
        else if (n == 60)
        {// <
            return 28;
        }
        else if (n == 33)
        {// !
            return 31;
        }
        else if (n == 45)
        {// -
            return 33;
        }
        else if ((n == 123)||(n==125)||(n==91)||(n==93)||(n==59)||(n==44)||(n==40)||(n==41))
        {// (),;[]{}
            return 37;
        }
        return 1;
    }
}