package com.ubs.interview.round1.OthelloGame;
import java.util.HashSet;
import java.util.Set;

class OthelloBoard{ 
    
    public class Position{
        int x, y;
        Position(int x, int y){
            this.x = x;
            this.y = y;
        }
        
           }
    
    private final char[][] board;
    int XScore, OScore, empty;
    private final char boardX[] = new char[]{'A','B','C','D','E','F','G','H'};
    
    public OthelloBoard(){ 
        board = new char[][]{
    			{'*','*','*','*','*','*','*','*',},
                {'*','*','*','*','*','*','*','*',},
                {'*','*','*','*','*','*','*','*',},
                {'*','*','*','X','O','*','*','*',},
                {'*','*','*','O','X','*','*','*',},
                {'*','*','*','*','*','*','*','*',},
                {'*','*','*','*','*','*','*','*',},
                {'*','*','*','*','*','*','*','*',},
        };  
    }
    
    private void findPlaceableLocations(char player, char opponent, HashSet<Position> placeablePositions){ 
        for(int i=0;i<8;++i){
            for(int j=0;j<8;++j){
                if(board[i][j] == opponent){
                    int I = i, J = j;  
                    if(i-1>=0 && j-1>=0 && board[i-1][j-1] == '*'){ 
                        i = i+1; j = j+1;
                        while(i<7 && j<7 && board[i][j] == opponent){i++;j++;}
                        if(i<=7 && j<=7 && board[i][j] == player) placeablePositions.add(new Position(I-1, J-1));
                    } 
                    i=I;j=J;
                    if(i-1>=0 && board[i-1][j] == '*'){
                        i = i+1;
                        while(i<7 && board[i][j] == opponent) i++;
                        if(i<=7 && board[i][j] == player) placeablePositions.add(new Position(I-1, J));
                    } 
                    i=I;
                    if(i-1>=0 && j+1<=7 && board[i-1][j+1] == '*'){
                        i = i+1; j = j-1;
                        while(i<7 && j>0 && board[i][j] == opponent){i++;j--;}
                        if(i<=7 && j>=0 && board[i][j] == player) placeablePositions.add(new Position(I-1, J+1));
                    }  
                    i=I;j=J;
                    if(j-1>=0 && board[i][j-1] == '*'){
                        j = j+1;
                        while(j<7 && board[i][j] == opponent)j++;
                        if(j<=7 && board[i][j] == player) placeablePositions.add(new Position(I, J-1));
                    }
                    j=J;
                    if(j+1<=7 && board[i][j+1] == '*'){
                        j=j-1;
                        while(j>0 && board[i][j] == opponent)j--;
                        if(j>=0 && board[i][j] == player) placeablePositions.add(new Position(I, J+1));
                    }
                    j=J;
                    if(i+1<=7 && j-1>=0 && board[i+1][j-1] == '*'){
                        i=i-1;j=j+1;
                        while(i>0 && j<7 && board[i][j] == opponent){i--;j++;}
                        if(i>=0 && j<=7 && board[i][j] == player) placeablePositions.add(new Position(I+1, J-1));
                    }
                    i=I;j=J;
                    if(i+1 <= 7 && board[i+1][j] == '*'){
                        i=i-1;
                        while(i>0 && board[i][j] == opponent) i--;
                        if(i>=0 && board[i][j] == player) placeablePositions.add(new Position(I+1, J));
                    }
                    i=I;
                    if(i+1 <= 7 && j+1 <=7 && board[i+1][j+1] == '*'){
                        i=i-1;j=j-1;
                        while(i>0 && j>0 && board[i][j] == opponent){i--;j--;}
                        if(i>=0 && j>=0 && board[i][j] == player)placeablePositions.add(new Position(I+1, J+1));
                    }
                    i=I;j=J;
                    }
                } 
        } 
    } 
    
    public void displayBoard(OthelloBoard b){  
        System.out.print("\n  ");
        for(int i=0;i<8;++i)System.out.print(boardX[i]+" ");
        System.out.println();
        for(int i=0;i<8;++i){
            System.out.print((i+1)+" ");
            for(int j=0;j<8;++j)
                System.out.print(b.board[i][j]+" ");
            System.out.println();
        }
        System.out.println(); 
    }
    
    public int gameResult(Set<Position> XPlaceableLocations, Set<Position> OPlaceableLocations){ 
        updateScores();
        if(empty == 0){
            if(XScore > OScore) return 1;
            else if(OScore > XScore) return -1;
            else return 0; //Draw
        }
        if(XScore==0 || OScore == 0){
            if(XScore > 0) return 1;
            else if(OScore > 0) return -1; 
        }  
        if(XPlaceableLocations.isEmpty() && OPlaceableLocations.isEmpty()){
            if(XScore > OScore) return 1;
            else if(OScore > XScore) return -1;
            else return 0; //Draw
        } 
        return -2;
    } 
    
    public HashSet<Position> getPlaceableLocations(char player, char opponent){ 
        HashSet<Position> placeablePositions = new HashSet<>();
        findPlaceableLocations(player, opponent, placeablePositions);
        return placeablePositions;
    }
     
    public void showPlaceableLocations(HashSet<Position> locations, char player, char opponent){
        for(Position p:locations)
            board[p.x][p.y]='_';
        displayBoard(this);
        for(Position p:locations)
            board[p.x][p.y]='_';
    }
    
    //Although we know that if W is player, O will be the opponent but still...
    public void placeMove(Position p, char player, char opponent){
        int i = p.x, j = p.y;
        board[i][j] = player; 
        int I = i, J = j;  
        
        if(i-1>=0 && j-1>=0 && board[i-1][j-1] == opponent){ 
            i = i-1; j = j-1;
            while(i>0 && j>0 && board[i][j] == opponent){i--;j--;}
            if(i>=0 && j>=0 && board[i][j] == player) {while(i!=I-1 && j!=J-1)board[++i][++j]=player;}
        } 
        i=I;j=J; 
        if(i-1>=0 && board[i-1][j] == opponent){
            i = i-1;
            while(i>0 && board[i][j] == opponent) i--;
            if(i>=0 && board[i][j] == player) {while(i!=I-1)board[++i][j]=player;}
        } 
        i=I; 
        if(i-1>=0 && j+1<=7 && board[i-1][j+1] == opponent){
            i = i-1; j = j+1;
            while(i>0 && j<7 && board[i][j] == opponent){i--;j++;}
            if(i>=0 && j<=7 && board[i][j] == player) {while(i!=I-1 && j!=J+1)board[++i][--j] = player;}
        }   
        i=I;j=J;
        if(j-1>=0 && board[i][j-1] == opponent){
            j = j-1;
            while(j>0 && board[i][j] == opponent)j--;
            if(j>=0 && board[i][j] == player) {while(j!=J-1)board[i][++j] = player;}
        }
        j=J; 
        if(j+1<=7 && board[i][j+1] == opponent){
            j=j+1;
            while(j<7 && board[i][j] == opponent)j++;
            if(j<=7 && board[i][j] == player) {while(j!=J+1)board[i][--j] = player;}
        }
        j=J; 
        if(i+1<=7 && j-1>=0 && board[i+1][j-1] == opponent){ 
            i=i+1;j=j-1;
            while(i<7 && j>0 && board[i][j] == opponent){i++;j--;}
            if(i<=7 && j>=0 && board[i][j] == player) {while(i!=I+1 && j!=J-1)board[--i][++j] = player;}
        }
        i=I;j=J; 
        if(i+1 <= 7 && board[i+1][j] == opponent){ 
            i=i+1;
            while(i<7 && board[i][j] == opponent) i++;
            if(i<=7 && board[i][j] == player) {while(i!=I+1)board[--i][j] = player;}
        }
        i=I;

        if(i+1 <= 7 && j+1 <=7 && board[i+1][j+1] == opponent){
            i=i+1;j=j+1;
            while(i<7 && j<7 && board[i][j] == opponent){i++;j++;}
            if(i<=7 && j<=7 && board[i][j] == player)while(i!=I+1 && j!=J+1)board[--i][--j] = player;}
    }  
    
    public void updateScores(){
        XScore = 0; OScore = 0; empty = 0;
        for(int i=0;i<8;++i){
            for(int j=0;j<8;++j){
                if(board[i][j]=='X')XScore++;
                else if(board[i][j]=='O')OScore++;
                else empty++;
            }
        }
    }
    
    public int coordinateX(char x){
        for(int i=0;i<8;++i)if(boardX[i]==Character.toLowerCase(x)||boardX[i]==Character.toUpperCase(x))return i;
        return -1; // Illegal move received
    }
} 

