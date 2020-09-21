import java.util.Scanner;

public class Taller1 {

    public static class Token {

        public int tipo;
        public String lexema;
        public int fila;
        public int columna;

        public static final int
                t_num          = 1,
                t_id           = 2,
                t_function     = 3,
                t_fid          = 4,
                t_when         = 5,
                t_do           = 6,
                t_while        = 7,
                t_true         = 8,
                t_false        = 9,
                t_return       = 10,
                t_if           = 11,
                t_else         = 12,
                t_print        = 13,
                t_end          = 14,
                t_break        = 15,
                t_next         = 16,
                t_and          = 17,
                t_or           = 18,
                t_var          = 19,
                t_unless       = 20,
                t_until        = 21,
                t_not          = 22,
                t_loop         = 23,
                t_for          = 24,
                tk_num         = 25,
                tk_bool        = 26,
                tk_mayor       = 27,
                tk_mayor_igual = 28,
                tk_menor       = 29,
                tk_menor_igual = 30,
                tk_asignacion  = 31,
                tk_sum_asig    = 32,
                tk_res_asig    = 33,
                tk_mul_asig    = 34,
                tk_div_asig    = 35,
                tk_mod_asig    = 36,
                tk_igualdad    = 37,
                tk_diferente   = 38,
                tk_incremento  = 39,
                tk_decremento  = 40,
                tk_llave_izq   = 41,
                tk_llave_der   = 42,
                tk_par_izq     = 43,
                tk_par_der     = 44,
                tk_mas         = 45,
                tk_menos       = 46,
                tk_mul         = 47,
                tk_div         = 48,
                tk_mod         = 49,
                tk_coma        = 50,
                tk_puntoycoma  = 51,
                tk_dospuntos   = 52;

        public Token(int tipo, String lexema, int fila, int columna ) {
        	this.tipo = tipo;
        	this.lexema = lexema;
        	this.fila = fila;
        	this.columna = columna;
        }

        public Token(int tipo, int fila, int columna ) {
        	this.tipo = tipo;
        	this.fila = fila;
        	this.columna = columna;
        }

        public void PrintTokenWithoutLexema() {
            System.out.println("<"+this.tipo+","+this.fila+","+this.columna+">");
         }

        public void PrintTokenWithLexema() {
            System.out.println("<"+this.tipo+","+this.lexema+","+this.fila+","+this.columna+">");
         }
    }

    public static void main(String[] args) {
	String palabrasReservadas[] = {"num", "id", "function", "fid", "when", "do", "while", "true", "false", "return", "if", "else", "print", "end", "break", "next", "and", "or", "var", "unless", "until", "not", "loop", "for"};
        Scanner scanner = new Scanner(System.in);
        int x = 1;
        int y = 1;
        Token newTok;
        while(scanner.hasNext()){
            String s = scanner.nextLine();
            for(int i = 0; i < s.length(); i++){
                switch (s.charAt(i)){
                    case '\n':
                        y++;
                        x=1;
                        break;
                    case ' ':
                        x++;
                        break;
                    case '\t':
                        x++;
                        break;
                    case ';':
                    	newTok = new Token(51,y,x);
                    	newTok.PrintTokenWithoutLexema();
                    	x++;
                    	break;
                    case 'A':
                    	newTok = new Token(2,"A",y,x);
                    	newTok.PrintTokenWithLexema();
                    	x++;
                    	break;
                }
            }
        }
    }
} 