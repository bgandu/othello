package com.ubs.interview.round1.OthelloGame;

import java.util.HashSet;
import java.util.Scanner;

public class Othello{
    public static void Play(OthelloBoard b){  
        Scanner scan = new Scanner(System.in);
        OthelloBoard.Position move = b.new Position(-1, -1); 
        System.out.println("O Moves first"); 
        
        int result;
        Boolean skip;
        String input;
        
        while(true){ 
            skip = false;
            
            HashSet<OthelloBoard.Position> OPlaceableLocations = b.getPlaceableLocations('O', 'X');
            HashSet<OthelloBoard.Position> XPlaceableLocations = b.getPlaceableLocations('X', 'O');

            b.showPlaceableLocations(OPlaceableLocations, 'O', 'X'); 
            result = b.gameResult(XPlaceableLocations, OPlaceableLocations);
            
            if(result == 0){System.out.println("It is a draw.");break;}
            else if(result==1){System.out.println("X wins: "+b.XScore+":"+b.OScore);break;}
            else if(result==-1){System.out.println("O wins: "+b.OScore+":"+b.XScore);break;}

            if(OPlaceableLocations.isEmpty()){ 
                    System.out.println("O needs to skip... Passing to X");
                    skip = true; 
            }

            if(!skip){
                System.out.println("Place move (O): ");
                input = scan.next();
                move.y = b.coordinateX(input.charAt(0));
                move.x = (Integer.parseInt(input.charAt(1)+"")-1); 
                
                while(!OPlaceableLocations.contains(move)){
                    System.out.println("Invalid move!\n\nPlace move (O): ");
                    input = scan.next();
                    move.y = b.coordinateX(input.charAt(0)); 
                    move.x = Integer.parseInt((input.charAt(1)+""))-1;  
                }
                b.placeMove(move, 'O', 'X');
                b.updateScores();
                System.out.println("\nO: "+b.OScore+" X: "+b.XScore);
            }
            skip = false;

            XPlaceableLocations = b.getPlaceableLocations('X', 'O');
            OPlaceableLocations = b.getPlaceableLocations('O', 'X');

            b.showPlaceableLocations(XPlaceableLocations, 'X', 'O');
            result = b.gameResult(XPlaceableLocations, OPlaceableLocations);

            if(result==0){System.out.println("It is a draw.");break;} 
            else if(result==1){System.out.println("X wins: "+b.XScore+":"+b.OScore);break;}
            else if(result==-1){System.out.println("O wins: "+b.OScore+":"+b.XScore);break;}

            if(XPlaceableLocations.isEmpty()){  
                    System.out.println("X needs to skip... Passing to O");
                    skip = true; 
            }

            if(!skip){ 
            System.out.println("Place move (X): ");
            input = scan.next();
            move.y = b.coordinateX(input.charAt(0));
            move.x = (Integer.parseInt(input.charAt(1)+"")-1);

            while(!XPlaceableLocations.contains(move)){
                System.out.println("Invalid move!\n\nPlace move (X): ");
                input = scan.next();
                move.y = b.coordinateX(input.charAt(0));
                move.x = (Integer.parseInt(input.charAt(1)+"")-1);
            }  
            b.placeMove(move, 'X', 'O');   
            b.updateScores();
            System.out.println("\nX: "+b.XScore+" O: "+b.OScore);
            }
        }
    }
        
    public static void main(String[] args){
    	OthelloBoard b = new OthelloBoard(); 
        Play(b); 
    }
}