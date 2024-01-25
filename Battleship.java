package Project2SolutionF23;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;

public class Battleship {

    private static int shipsSunk = 0;
    private static int guessCount = 0;

    public static void main(String[] args) {

        //set up variables
        char[][] board = new char[10][10];
        Ship[][] shipLocations = new Ship[10][10];
        Scanner input = new Scanner(System.in);

        //fill board with not guessed
        for(int row = 0; row < 10; row++) {
            for(int col = 0; col < 10; col++) {
                board[row][col] = ' ';
            }
        }

        //place ships
        placeShip(new Ship("Carrier", 5), shipLocations);
        placeShip(new Ship("Battleship", 4), shipLocations);
        placeShip(new Ship("Cruiser", 3), shipLocations);
        placeShip(new Ship("Submarine", 3), shipLocations);
        placeShip(new Ship("Destroyer", 2), shipLocations);

        //run game
        createWindow(board, shipLocations);






    }

    //displays the board to the console
    private static void printBoard(char[][] board) {
        System.out.println(" 1 2 3 4 5 6 7 8 9 10");
        for(int row = 0; row < 10; row++) {
            System.out.print((char)(row + 'A'));
            for(int col = 0; col < 10; col++) {
                System.out.print(board[row][col] + " ");
            }
            System.out.println();
        }
    }

    //randomly places a ship on the board, avoiding conflicts with other ships
    private static void placeShip(Ship ship, Ship[][] shipLocations) {
        boolean conflict;
        int startRow, startCol;

        //horizontal ship placement
        if(Math.random() < 0.5) {
            do {
                conflict = false;
                startRow = (int)(Math.random() * 10);
                startCol = (int)(Math.random() * (10 - ship.getSize()));
                for(int i = 0; i < ship.getSize(); i++) {
                    if(shipLocations[startRow][startCol + i] != null) {
                        conflict = true;
                    }
                }
            }while(conflict);
            for(int i = 0; i < ship.getSize(); i++) {
                shipLocations[startRow][startCol + i] = ship;
            }

            //vertical ship placement
        }else {
            do {
                conflict = false;
                startRow = (int)(Math.random() * (10 - ship.getSize()));
                startCol = (int)(Math.random() * 10);
                for(int i = 0; i < ship.getSize(); i++) {
                    if(shipLocations[startRow + i][startCol] != null) {
                        conflict = true;
                    }
                }
            }while(conflict);
            for(int i = 0; i < ship.getSize(); i++) {
                shipLocations[startRow + i][startCol] = ship;
            }
        }
    }

    //Method used to create the games window.
    private static void createWindow(char[][] board, Ship[][] shipLocations){

        //Creates the frame and its specifications
        JFrame frame = new JFrame("BattleShip");
        frame.setVisible(true);
        frame.setSize(800, 800);
        frame.setLayout(new BorderLayout());
        frame.setResizable(false);

        JPanel shipPanel = new JPanel();
        shipPanel.setLayout(new GridLayout(10, 10));

        JPanel lettersPanel = new JPanel();
        lettersPanel.setLayout(new GridLayout(11, 1));
        lettersPanel.setBackground(Color.gray);

        JPanel numbersPanel = new JPanel();
        numbersPanel.setLayout(new GridLayout(1, 11));
        numbersPanel.setBackground(Color.gray);


        //Creates each individual button and IDs them. Then creates the guessing implementation of each button.
        for(int i = 0; i<100; i++){
            JButton shipSpot = new JButton();
            shipSpot.setName(String.valueOf(i));
            shipSpot.setOpaque(true);
            shipSpot.setBorderPainted(false);
            shipSpot.setBackground(Color.cyan);
            shipSpot.setText(".");
            shipSpot.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    if (shipsSunk <5) {


                        //input guess and process string
                        int guess = Integer.parseInt(shipSpot.getName());
                        int guessRow = guess/10;
                        int guessCol = guess%10;
                        String guessString = numbersToLetters(guessRow) + (guessCol+1);



                        //check for repeat guess
                        if(board[guessRow][guessCol] != ' ') {
                            System.out.println("You already guessed that space!");
                            JOptionPane.showMessageDialog(null, "You already chose that spot silly!");
                            //if not repeat guess
                        }else {
                            guessCount++;
                            Ship shipHit = shipLocations[guessRow][guessCol];

                            //miss
                            if(shipHit == null) {
                                board[guessRow][guessCol] = '.';
                                System.out.println("Miss!");
                                shipSpot.setBackground(Color.WHITE);
                                JOptionPane.showMessageDialog(null, guessString + " was a miss!");


                                //hit
                            }else {
                                board[guessRow][guessCol] = 'X';
                                System.out.println("Hit!");
                                shipHit.hit();
                                shipSpot.setBackground(Color.RED);
                                JOptionPane.showMessageDialog(null, guessString + " was a hit!");


                                //check if ship sunk
                                if(shipHit.isSunk()) {
                                    shipsSunk++;
                                    System.out.println("You sunk the " + shipHit + "!");
                                    JOptionPane.showMessageDialog(null, "You just sunk the "+ shipHit + "!");
                                }
                            }
                        }
                    }

                    if(shipsSunk==5){
                        JOptionPane.showMessageDialog(null, "You won!!! You sunk all of the ships in " + guessCount+" guesses!");
                        frame.dispose();
                    }

                    printBoard(board);


                }
            });
            shipPanel.add(shipSpot);
        }

        lettersPanel.add(new JLabel("A"));
        lettersPanel.add(new JLabel("B"));
        lettersPanel.add(new JLabel("C"));
        lettersPanel.add(new JLabel("D"));
        lettersPanel.add(new JLabel("E"));
        lettersPanel.add(new JLabel("F"));
        lettersPanel.add(new JLabel("G"));
        lettersPanel.add(new JLabel("H"));
        lettersPanel.add(new JLabel("I"));
        lettersPanel.add(new JLabel("J"));


        numbersPanel.add(new JLabel("           1 "));
        numbersPanel.add(new JLabel("           2 "));
        numbersPanel.add(new JLabel("           3 "));
        numbersPanel.add(new JLabel("           4 "));
        numbersPanel.add(new JLabel("          5 "));
        numbersPanel.add(new JLabel("          6 "));
        numbersPanel.add(new JLabel("          7 "));
        numbersPanel.add(new JLabel("          8 "));
        numbersPanel.add(new JLabel("          9 "));
        numbersPanel.add(new JLabel("         10 "));

        frame.add(numbersPanel, BorderLayout.NORTH);
        frame.add(lettersPanel, BorderLayout.WEST);
        frame.add(shipPanel, BorderLayout.CENTER);
        frame.setVisible(true);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private static String numbersToLetters(int row){
        if(row == 0){
            return "A";
        }
        if(row == 1){
            return "B";
        }
        if(row == 2){
            return "C";
        }
        if(row == 3){
            return "D";
        }
        if(row == 4){
            return "E";
        }
        if(row == 5){
            return "F";
        }
        if(row == 6){
            return "G";
        }
        if(row == 7){
            return "H";
        }
        if(row == 8){
            return "I";
        }
        if(row == 9){
            return "J";
        }
        else return "";
    }

}
