//Alunos: Fernando Castro; Wellington Maciel; Nicolas de Oliveira.

package campominado;

import java.util.Random;
import java.util.Scanner;

public class CampoMinado {

	public static void main(String[] args) {
		game();
	}

	public static void game() {       
		//declaração de variáveis
		Scanner scan = new Scanner(System.in);
		int tabuleiro[][];
		int mascara[][];
		int jogoAtivo[] = new int[3]; //vetor de 3 posicoes
		int winCondition = 2;

		//Gera um menu com diversas opcoes, cada opcao esta amarrado a um if condicionando a quantidade de linhas, colunas e bombas de acordo com a opcao selecionada.
		jogoAtivo = menu();

		//Gera um tabuleiro utilizando os valores do vetor jogoAtivo como parametros para qtd de linha; colunas e bombas.
		tabuleiro = initializeTabuleiro(jogoAtivo);
		mascara = initializeMascara(jogoAtivo);

		while (jogoAtivo[0] != 7) {

			print(mascara);

			System.out.println("\nEscolha a posicao para jogar (lin col):");
			int lin = scan.nextInt();

			int col = scan.nextInt();

			//verifica se o jogador digitou uma posicao valida, caso afirmativo, abre a mascara para o valor da posicao digitada
			if(step(tabuleiro,lin,col)) {
				System.out.println("Voce digitou uma posicao invalido, tente novamente.\n");
			} else {
				mascara = abrirMascara(mascara, tabuleiro, lin, col);
			}

			//verifica se o jogo terminou
			winCondition = status(mascara,tabuleiro,jogoAtivo);
			if (winCondition == 0) {
				System.out.println("\nVoce perdeu!!");
				print(mascara);
				break;
			}
			if(winCondition == 1) {
				System.out.println("\nVocê ganhou.");
				print(mascara);
				break;
			}

			System.out.println();
		}
	}

	//funções 
	private static int[][] initializeTabuleiro(int jogoAtivo[]) {
		//System.out.println("initializeTabuleiro");
		Random random = new Random();
		int totalBombas = 0;
		//atribuindo cada elemento do vetor jogoAtivo a seu inteiro correspondente para facilidade de entendimento do código
		int qtdLin = jogoAtivo[0];
		int qtdCol = jogoAtivo[1];
		int qtdBombas = jogoAtivo[2];      
		//System.out.println(qtdLin);
		int tabuleiro[][] = new int[qtdLin][qtdCol];
		/*
        Aqui � inicializada uma matriz maior que o que iremos usar(criando uma borda em toda adjacencia da matriz)
         para que possa exister uma margem de seguran�a e a distribui��o de bombas nao caia em uma posi�ao 
         invalida!
		 */
		for (int i = 0; i < tabuleiro.length; i++) {
			for (int j = 0; j < tabuleiro[0].length; j++) {
				//geracao de bombas em posi��es aleatorias.
				while (qtdBombas > 0) {
					if(tabuleiro[i][j]!=-1) {
						//REVISAR FORMULA DE PREENCHIMENTO ALEATORIO DE BOMBAS, NAO ESTA RETORNANDO O VALOR CERTO
						int randomLin = random.nextInt(qtdLin - 2)+1;
						int randomCol = random.nextInt(qtdCol - 2)+1;
						while (tabuleiro[randomLin][randomCol] != -1){
							tabuleiro[randomLin][randomCol] = -1;
							qtdBombas--;
							totalBombas++;
						}
					}
				}
			}
		}
		//chamamento da funcao adjacencia para calcular as adjacencias dos elementos proximos as bombas.
		adjacencia(tabuleiro);
		System.out.println("\nTotal de Bombas: "+ totalBombas);
		return tabuleiro;
	}

	private static int[][] initializeMascara(int jogoAtivo[]) {
		//atribuindo cada elemento do vetor jogoAtivo a seu inteiro correspondente para facilidade de entendimento do codigo
		//System.out.println("initializeMascara");
		int qtdLin = jogoAtivo[0];
		int qtdCol = jogoAtivo[1];    

		int mascara[][] = new int[qtdLin][qtdCol];
		for (int i = 0; i < mascara.length; i++) {
			for (int j = 0; j < mascara[0].length; j++) {
				mascara[i][j] = 9;
			}
		}        
		return mascara;
	}

	private static int[][] abrirMascara(int mascara[][], int tabuleiro[][], int lin, int col) {
		//System.out.println("abrirMascara");
		for (int m = 0; m < mascara.length; m++) {
			for (int n = 0; n < mascara[0].length; n++) {

				if (m == lin && n == col){

					mascara[m][n] = tabuleiro[m][n];                                        
				}                           
			}        
		}               
		return mascara;
	}

	private static void print(int[][] matriz) {
		//System.out.println("print");
		System.out.println();

		for (int i = 0; i < matriz.length; i++) {
			for (int j = 0; j < matriz[0].length; j++) {

				if (matriz[i][j] == 9) System.out.printf("[   ]");

				else System.out.printf("[%3d]",matriz[i][j]);

			}
			System.out.println();

		}

	}

	private static int[][] adjacencia(int[][] tabuleiro){

		for (int m = 0; m < tabuleiro.length; m++) {
			for (int n = 0; n < tabuleiro[0].length; n++) {
				//verifica se o elemento da matriz em questao nao tem nenhuma bomba e, caso verdadeiro, incrementa os valores adjacentes a� bomba.
				if(tabuleiro[m][n] == -1){
					if(tabuleiro[m-1][n] != -1) tabuleiro[m-1][n]++;                     
					if(tabuleiro[m-1][n+1] != -1) (tabuleiro[m-1][n+1])++;           
					if(tabuleiro[m][n+1] != -1) tabuleiro[m][n+1]++; 
					if(tabuleiro[m+1][n+1] != -1) tabuleiro[m+1][n+1]++; 
					if(tabuleiro[m+1][n] != -1) tabuleiro[m+1][n]++; 
					if(tabuleiro[m+1][n-1] != -1) tabuleiro[m+1][n-1]++; 
					if(tabuleiro[m][n-1] != -1) tabuleiro[m][n-1]++; 
					if(tabuleiro[m-1][n-1] != -1) tabuleiro[m-1][n-1]++;    
				}
			}
		}
		return tabuleiro;
	}

	private static int[] menu(){
		Scanner lerMenu = new Scanner(System.in);
		int option; //variavel de entrada que capturara a opcao de jogo desejada pelo usuário.
		int valoresTabuleiro[] = new int[3]; //vetor de saída que devolverá a quantidade de linhas, colunas e bombas em função da opção selecionada pelo usuário.

		System.out.println("\nEscolha um modo de jogo:\n ");
		System.out.println(
				"[1]-Easy\n"
						+ "[2]-Medium\n"
						+ "[3]-Hard\n"
						+ "[4]-Monster\n"
						+ "[5]-Modo personalisado\n"
						+ "[6]-Manual do game\n\n"
						+ "[7]-Quit Game !\n");
		System.out.println("Digite a opcao desejada:");

		option = lerMenu.nextInt();
		//condicional de controle que impede o usuario inserir um numero fora do range permitido.
		if (option > 7 || option < 1) {
			System.out.println("Voce digitou um valor invalido, tente novamente.\n");
			menu();                 
		}
		//opções de jogo
		if (option == 1) {
			valoresTabuleiro[0] = 5;//adiciona sempre -2 (trabalhando apenas no miolo da matriz,entre a segunda posic�o ate a penultima)
			valoresTabuleiro[1] = 5;//adiciona sempre -2 (trabalhando apenas no miolo da matriz,entre a segunda posic�o ate a penultima)
			valoresTabuleiro[2] = 3;//bombas
		}
		if (option == 2) {
			valoresTabuleiro[0] = 12;//adiciona sempre -2 (trabalhando apenas no miolo da matriz,entre a segunda posic�o ate a penultima)
			valoresTabuleiro[1] = 12;//adiciona sempre -2 (trabalhando apenas no miolo da matriz,entre a segunda posic�o ate a penultima)
			valoresTabuleiro[2] = 8;//bombas
		}
		if (option == 3) {
			valoresTabuleiro[0] = 16;//adiciona sempre -2 (trabalhando apenas no miolo da matriz,entre a segunda posic�o ate a penultima)
			valoresTabuleiro[1] = 16;//adiciona sempre -2 (trabalhando apenas no miolo da matriz,entre a segunda posic�o ate a penultima)
			valoresTabuleiro[2] = 24;//bombas
		}
		if (option == 4) {
			valoresTabuleiro[0] = 34;//adiciona sempre -2 (trabalhando apenas no miolo da matriz)
			valoresTabuleiro[1] = 34;//adiciona sempre -2 (trabalhando apenas no miolo da matriz)
			valoresTabuleiro[2] = 100;//bombas
		}
		if (option == 5) { //modo customizado de jogo
			System.out.printf("Quantas linhas voce deseja: ");
			valoresTabuleiro[0] = lerMenu.nextInt();//linhas
			valoresTabuleiro[0] +=2;//trabalhando apenas no miolo da matriz(entre a segunda posic�o ate a penultima)
			System.out.printf("Quantas colunas voce deseja: ");
			valoresTabuleiro[1] = lerMenu.nextInt();//colunas
			valoresTabuleiro[1]+=2;//trabalhando apenas no miolo da matriz(entre a segunda posic�o ate a penultima)
			System.out.printf("Quantas bombas voce deseja: ");
			valoresTabuleiro[2] = lerMenu.nextInt();//bombas

		}
		if (option == 6) { //instrucoes
			System.out.println("Digite o valor da linha e coluna (ambos começando em 0). O jogo termina se você abrir uma casa com o valor -1");
			menu();
		}
		if (option == 7) {
			valoresTabuleiro[0] = 7;
			System.out.println("Obrigado por jogar.");
		}

		return valoresTabuleiro;
	}

	//verifica status -> verifica se o jogo terminou ou nao.    
	public static int status (int Mascara[][], int Tabuleiro[][], int jogoAtivo[]){
		int control = 0;
		int isWin = 0;
		int sumTab = 0;
		int result = 2;
		int x = 0;

		while (x != 1) {
			for(int i = 0; i < Tabuleiro.length; i++) {
				for(int j = 0; j < Tabuleiro[0].length;j++) {
					sumTab = sumTab + Tabuleiro[i][j];
				}
			}
			x = 1;
		}
		for(int i = 0; i < Mascara.length; i++) {
			for(int j = 0; j < Mascara[0].length;j++){
				if(Mascara[i][j] == -1) { //se jogador acertar uma bomba..
					result = 0;	
				}
				if (Mascara[i][j] != 9) {
					control = control+Mascara[i][j];
				}
			}                      
		}
		if ((control-jogoAtivo[2]) == sumTab){
			result = 1;
		} 
		return result;
	} 

	public static boolean step (int Tabuleiro[ ][ ], int lin, int col){

		if (lin > Tabuleiro.length-1) return true;
		else if (col > Tabuleiro[0].length-1) return true;
		else return false;

	}

}