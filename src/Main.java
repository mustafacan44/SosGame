import java.util.Random;
import java.util.Scanner;

public class Main {

    //Oyun panelinin boyutu için bir değişken oluşturulur.
    static int size;


    //Oyun paneli için dizi oluşturulur.
    static String[][] board;
    static Random random = new Random();
    static boolean turn = random.nextBoolean();
    // Bilgisayar puanlarını tutmak için bir değişken oluşturulur.
    static int computerScore;
    // Kullanıcı puanlarını tutmak için bir değişken oluşturulur.
    static int playerScore;
    static int sosScore;
    static boolean character=random.nextBoolean();

    static String playerCharacter;
    static String computerCharacter;

    public static void main(String[] args) {

        characterController();
        gameBoard();
        playBoard(board);
        while (gameOver()) {
            if (turn) {
                userControl(board);
                playBoard(board);

            } else {
                computerControl(board);
                playBoard(board);
                System.out.println("Bilgisayar oynadı.");
            }

        }
    }


    public static void gameBoard() {

        System.out.println("Oyun için panel boyutunu girin : ");

        Scanner scan = new Scanner(System.in);

        size = scan.nextInt();

        if (!(size >= 3 && size <= 7)) { // Oyun paneli kontrol edilir.

            System.out.println("Oyun paneli 3 ile 7 arasında olmalıdır.");
        } else {

            board = new String[size][size];
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {

                    board[i][j] = "- ";

                }

            }
        }
    }

    //Oyun paneli ekrana yazdırılır.
    public static void playBoard(String[][] board) {

        for (String[] arr : board) {
            for (String panelArr : arr) {
                System.out.print(panelArr);
            }
            System.out.println();
        }
        System.out.println("Player score : " + playerScore);
        System.out.println("Computer score :" + computerScore);
    }

    //Hücrelerin boş olup olmadığı kontrol edilir.
    public static boolean isEmptyControl(String[][] board, int row, int column) {
        boolean result = false;
        if (board[row][column].equals("- ")) {
            result = true;
        }

        return result;
    }

    // Oyun içerisinde kullancının yapacağı hareketler kontrol edilir.
    public static void userControl(String[][] board) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Satır numarasını giriniz");
        int row = scanner.nextInt();
        System.out.println("Sütun numarasını giriniz");
        int column = scanner.nextInt();

        if (isEmptyControl(board, row - 1, column - 1)) {

            board[row - 1][column - 1] = playerCharacter;
            sosControl(board);
            turn = !turn;
        } else {
            System.out.println("Girdiğiniz hücre doludur. Yeni bir hücre giriniz.");
            userControl(board);
        }

    }
    // Oyun içerisinde bilgisayarın  yapacağı hareketler kontrol edilir.
    public static void computerControl(String[][] board) {
        int row = (int) (Math.random() * size);
        int column = (int) (Math.random() * size);

        if (!(isEmptyControl(board, row, column)))
            computerControl(board);
        else {
            try {
                board[row][column] = computerCharacter;
                sosControl(board);
                turn = !turn;
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println(e.getMessage());
            }

        }
    }
    //Sos kontrolü yapılır.
    public static void sosControl(String[][] board) {

        int verticalScore = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size - 2; j++) {
                if (board[i][j].equals("S ") && board[i][j + 1].equals("O ") && board[i][j + 2].equals("S ")) {
                    verticalScore++;
                }
            }
        }


        int horizantalScore = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size - 2; j++) {
                if (board[j][i].equals("S ") && board[j + 1][i].equals("O ") && board[j + 2][i].equals("S ")) {
                    horizantalScore++;
                }
            }
        }

        int diagonalScore = 0;
        for (int i = 0; i < size - 2; i++) {
            for (int j = 0; j < size - 2; j++) {
                if (board[i][j].equals("S ") && board[i + 1][j + 1].equals("O ") && board[i + 2][j + 2].equals("S ")) {
                    diagonalScore++;
                }
            }
        }
        for (int i = 0; i < size - 2; i++) {
            for (int j = size - 1; j > 1; j--) {
                if (board[i][j].equals("S ") && board[i + 1][j - 1].equals("O ") && board[i + 2][j - 2].equals("S ")) {
                    diagonalScore++;
                }
            }
        }
        int totalScore = verticalScore + horizantalScore + diagonalScore;
        if (sosScore != totalScore) {
            int score = totalScore - sosScore;
            sosScore = totalScore;
            if (turn) {
                playerScore += score;
                turn = false;
            } else {
                computerScore += score;
                turn = true;
            }
        }
    }
    //Oyunun bitip bitmediğine dair kontrol yapılır.
    public static boolean gameOver() {
        int count = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (board[i][j].equals("S ") || board[i][j].equals("O ")) {
                    count++;
                }
            }
        }
        if (count == (size * size)) {
            System.out.println("Oyun bitti");
            System.out.println("Kazanan : "+winner(playerScore,computerScore));
            return false;
        } else {
            return true;
        }
    }
    //Oyunun kazananı belirlenir.
    public static String winner(int playerScore,int computerScore){

        if(playerScore>computerScore){
            return "Player";
        }else if(playerScore==computerScore){
            return "Berabere";
        }else {
            return "Computer";
        }

    }
    //Oyun başladığında kimin hangi harf ile başlayacağı belirlenir.
    public static void characterController(){

        if(character){
            playerCharacter="S ";
            computerCharacter="O ";
        }else{
            playerCharacter="O ";
            computerCharacter="S ";
        }
    }


}