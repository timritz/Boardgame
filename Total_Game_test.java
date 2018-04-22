// Player Operated Test
import java.util.*;
import java.awt.*;
import java.util.Arrays;

public class Total_Game_test {
   public static void main(String[] args) {
      Random rand = new Random();
      Scanner console = new Scanner(System.in);
      
      
      //Setting up the board dimensions and desired tile configuration
      int boardHeight = 800;
      int boardWidth = 800;
      int tilesHorizontal = 8;
      int tilesVertical = 8;
      
      //Makes Board    
      DrawingPanel playingBoard = new DrawingPanel(boardHeight,boardWidth);
      Graphics g = playingBoard.getGraphics();
      
      //Make Tiles on Board
      makeBoardTiles(boardHeight,boardWidth,tilesHorizontal,tilesVertical,g);
      
     // Number of Characters
      int[] characterTeam = teamSelect(console);
     
     // Number/Type of Enemies
      int[] enemyTeam = enemySelect(console);
     
      int[] turnOrder = determineTurnOrder(characterTeam,enemyTeam,rand);
     
     // Places characters and enemies in starting positions on the board
      int[][] characterPieces = putPiecesOnTheBoard(characterTeam, turnOrder,boardHeight,boardWidth,tilesHorizontal,tilesVertical,g,rand);
      int[][] enemyPieces = putEnemyPiecesOnTheBoard(enemyTeam,turnOrder,boardHeight,boardWidth,tilesHorizontal,tilesVertical,g,rand);
     

      // Prints consolidated Character/Piece info 
      // Output: (piece # / x-coord y-coord / base card(#)... / atk def health move vigor(characters only)
      printCharacterPiecesInfo(characterPieces,characterTeam);
      printEnemyPiecesInfo(enemyPieces,enemyTeam);
      
      
      //Move Enemy Piece action
      moveEnemyPiece(51,characterPieces,enemyPieces,turnOrder,tilesHorizontal,tilesVertical, boardHeight, boardWidth, g);    
     
                  
   }
   
   //Aquires Target and Moves an enemy piece
   public static int[][] moveEnemyPiece (int currentPiece, int[][] characterPieces, int[][] enemyPieces, int [] turnOrder, int tilesHorizontal, int tilesVertical, int boardHeight, int boardWidth, Graphics g){
      int currentPieceRow = -1;
      for (int i = 0; i<= 3; i++){
         if (enemyPieces[i][0] == currentPiece){
            currentPieceRow = i;
         }
      }
      
      
      int x = enemyPieces[currentPieceRow][1];
      int y = enemyPieces[currentPieceRow][2];
      int h = boardWidth/tilesHorizontal;
      int v = boardHeight/tilesVertical;
      
//       size(characterPieces);
//       size(enemyPieces);
      
//       System.out.println(a + " " + b);
//       System.out.println(c + " " + d);
      
      int a = 4;
      int c = 4;
      
      
      //Aquire target
      int distance = tilesHorizontal + tilesVertical;
      int minDistance = distance;
      int targetX = 0;
      int targetY = 0;
      
      
      
      //Creates Binary Board Grid
      int [][] boardGrid = new int [tilesVertical][tilesHorizontal];
      
      
      System.out.println();
      
      //only works for 4player & 4enemy
      for (int i = 0; i <= 3; i++){
//          System.out.println((characterPieces[i][1]-(boardWidth/tilesHorizontal)/3)/h);
//          System.out.println((characterPieces[i][2]-(boardHeight/tilesVertical)/2)/v);
      
      
         boardGrid[(characterPieces[i][2]-(boardHeight/tilesVertical)/2)/v][(characterPieces[i][1]-(boardWidth/tilesHorizontal)/3)/h] = 1;
         boardGrid[(enemyPieces[i][2]-(boardHeight/tilesVertical)/2)/v][(enemyPieces[i][1]-(boardWidth/tilesHorizontal)/3)/h] = 1;         

      
      }
      
      //Prints Binary Board Grid
      for (int i = 0; i<= tilesVertical-1; i++){
         for (int j = 0; j<= tilesHorizontal-1; j++){
            System.out.print(boardGrid[i][j]);
         }
         System.out.println();
      }
      
      System.out.println();
      
      //Checks distance to all characters and keeps closest coordinates
      for (int i = 0; i<= a-1 ; i++){
      
//         System.out.println((x-characterPieces[i][1]));
         
         distance = Math.abs((x - characterPieces[i][2]-(boardHeight/tilesVertical)/2)/v) + Math.abs((y - characterPieces[i][1]-(boardWidth/tilesHorizontal)/3)/h);         
//         System.out.println(distance);
                  
         if (distance<minDistance){
            minDistance = distance;
            targetX = characterPieces[i][1];
            targetY = characterPieces[i][2];         
         }
                  
      }
      
      System.out.println("Enemy: Y " + y + " X " + x);
      System.out.println("Target: Y " + targetY + " X " + targetX);
      System.out.println();
      
      
      
      
      if (x<targetX & y<targetY){
         h = h;
         v = v;
      } else if (x>targetX & y<targetY){
         h = -h;
         v = v;
      } else if (x<targetX & y>targetY){
         h = h;
         v = -v;
      } else if (x>targetX & y>targetY){
         h = -h;
         v = -v;
      } else if (x==targetX & y<targetY){
         h = h;
         v = v;
      } else if (x==targetX & y>targetY){
         h = h;
         v = -v;
      } else if (x<targetX & y==targetY){
         h = h;
         v = v;
      } else if (x>targetX & y==targetY){
         h = -h;
         v = v;
      }
      
      System.out.println("H: " + h + " V: " + v);
      
      //Move towards target
      
      if (minDistance == 1){
        //Already in position 
      } else if ((minDistance == 2) & (x != targetX) & (y != targetY)){
         //Already in Position
      } else if ((minDistance == 2) & (y == targetY)){
         if (boardGrid[y][x + h] == 0){

            boardGrid[y][x] = 0;
            x = x+h;
            boardGrid[y][x] = 1;

            g.drawString("Grunt",y,x);
         }else if ((boardGrid[y - v][x + h] == 0) & (boardGrid[y - v][x] == 0)){
            
            boardGrid[y][x] = 0;           
            y = y - v;
            x = x + h;
            boardGrid[y][x] = 1;
                        
            g.drawString("Grunt",y,x);
         }else if ((boardGrid[y + v][x + h] == 0) & (boardGrid[y+v][x] == 0)){

            boardGrid[y][x] = 0;
            y = y - v;
            x = x + h;
            boardGrid[y][x] = 1;

            g.drawString("Grunt",y,x);
         }
            
      } else if ((minDistance == 2) & (x == targetX)){
         if (boardGrid[y + v][x] == 0){

            boardGrid[y][x] = 0;
            y = y + v;
            boardGrid[y][x] = 1;

            g.drawString("Grunt",y,x);
         }else if ((boardGrid[y + v][x + h] == 0) & (boardGrid[y][x+h] == 0)){
            
            boardGrid[y][x] = 0;           
            x = x + h;
            y = y + v;            
            boardGrid[y][x] = 1;
                        
            g.drawString("Grunt",y,x);
         }else if ((boardGrid[y + v][x - h] == 0) & (boardGrid[y][x-h] == 0)){

            boardGrid[y][x] = 0;
            y = y + v;
            x = x - h;
            boardGrid[y][x] = 1;

            g.drawString("Grunt",y,x);
         }
            
      }
         
      
    return boardGrid;
      
      
      
   }
   
   
   
   public static void printCharacterPiecesInfo (int[][] characterPieces,int[] characterTeam){
      for(int i=0;i<=characterTeam[0]+characterTeam[1]+characterTeam[2]+characterTeam[3]-1;i++){
        for(int j=0;j<=0;j++){
            System.out.print(characterPieces[i][j]+ " ");
        }
        System.out.print("/ ");
        for(int j=1;j<=2;j++){
            System.out.print(characterPieces[i][j]+ " ");
        }
        System.out.print("/ ");
        for(int j=3;j<=4;j++){
            System.out.print(characterPieces[i][j]+ " ");
        }
        System.out.print("/ ");
        for(int j=5;j<=9;j++){
            System.out.print(characterPieces[i][j]+ " ");
        }
        System.out.println();
     }   
   }
   
   public static void printEnemyPiecesInfo (int[][] enemyPieces,int[] enemyTeam){
     for(int i=0;i<=enemyTeam[0] + enemyTeam[1] + enemyTeam[2] + enemyTeam[3] -1;i++){
        for(int j=0;j<=0;j++){
            System.out.print(enemyPieces[i][j]+ " ");
        }
        System.out.print("/ ");
        for(int j=1;j<=2;j++){
            System.out.print(enemyPieces[i][j]+ " ");
        }
        System.out.print("/ ");
        for(int j=3;j<=3;j++){
            System.out.print(enemyPieces[i][j]+ " ");
        }
        System.out.print("/ ");
        for(int j=4;j<=7;j++){
            System.out.print(enemyPieces[i][j]+ " ");
        }
        System.out.println();
     }    
   }   
   
   public static int[] determineTurnOrder(int[] characterTeam, int[] enemyTeam,Random rand){
      int [][] turnOrder = new int[characterTeam.length + enemyTeam.length][2];
      int[] characterTeamTemp = Arrays.copyOf(characterTeam,characterTeam.length);
      int[] enemyTeamTemp = Arrays.copyOf(enemyTeam,enemyTeam.length);
      int piece = 0;
      int [] finalTurnOrder = new int[characterTeam.length + enemyTeam.length];
      
      //tests for all characters
      for (int character = 1; character <= 4; character++){
         int[] characterStats = createCharacterStats(character);
         int characterCounter = 1;
         
         //Checks for multiple of a single character type & puts into unified turnOrder array
         // 11-14 => warrior 1-4
         // 21-24 => mage 1-4
         // 31-34 => rogue 1-4
         // 41-44 => bard 1-4
         
         while (characterTeamTemp[character-1]>0){
            turnOrder[piece][0] = 10*character + characterCounter;
            turnOrder[piece][1] = characterStats[4];
            
//            System.out.print(turnOrder[piece][0]+ " ");
//            System.out.println(turnOrder[piece][1]);
            
//            System.out.println(characterTeam[character-1]);
//           System.out.println(characterTeamTemp[character-1]);
            
            characterTeamTemp[character-1] = characterTeamTemp[character-1]-1;
            
//            System.out.println(characterTeam[character-1]);
//            System.out.println(characterTeamTemp[character-1]);
            
            characterCounter++;
            piece++;
         }
      }
      
      //tests for all enemy types
      for (int enemy = 1; enemy <= 4; enemy++){
         int[] enemyStats = createEnemyStats(enemy);
         int enemyCounter = 1;
         
         //checks for multiple of a single enemy type & puts into unified turnOrder array
         // 51-54 => grunt 1-4
         // 61-64 => captain 1-4
         // 71-74 => assassin 1-4
         // 81-84 => boss 1-4
         
         while (enemyTeamTemp[enemy-1]>0){
            turnOrder[piece][0] = 40 + 10*enemy + enemyCounter;
            turnOrder[piece][1] = enemyStats[4];
            
//            System.out.print(turnOrder[piece][0]+ " ");
//           System.out.println(turnOrder[piece][1]);
            
            enemyTeamTemp[enemy-1] = enemyTeamTemp[enemy-1] -1;
            enemyCounter++;
            piece++;         
         }
      }
      
      
      //Reorders character and enemies together based on turn order
      int j=0;
      int k=0;
      for(int i= 0; i<= characterTeam.length + enemyTeam.length -1; i++){
         if (turnOrder[i][1] == 2){
            finalTurnOrder[characterTeam.length + enemyTeam.length -1 - j] = turnOrder[i][0];            
            j++;
         }
      }
      for(int i= characterTeam.length + enemyTeam.length -1; i>= 0; i--){
         if (turnOrder[i][1] == 3){
            finalTurnOrder[k] = turnOrder[i][0];            
            k++;
         }
      }
      for(int i= 0; i<= characterTeam.length + enemyTeam.length -1; i++){
         System.out.println(finalTurnOrder[i]);      
         
      }
      
      System.out.println();
      
      //returns reordered list for turn order purposes            
      return finalTurnOrder;                 
   }
   
   
   
   
   
   
   
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// 
   public static int[][] putEnemyPiecesOnTheBoard(int[] enemyTeam, int[] turnOrder, int boardHeight, int boardWidth,int tilesHorizontal, int tilesVertical, Graphics g,Random rand){
      int sumEnemyTeam = sumTeam(enemyTeam);
      int[] enemyTeamTemp = Arrays.copyOf(enemyTeam,enemyTeam.length);
      
      int[] oneEnemy = {(boardWidth/tilesHorizontal)*((tilesHorizontal/2)-2) +(boardWidth/tilesHorizontal)/3 , (boardHeight/tilesVertical)/2 + (boardHeight/tilesVertical)};
      int[] twoEnemy = {(boardWidth/tilesHorizontal)*((tilesHorizontal/2)-1) +(boardWidth/tilesHorizontal)/3 , (boardHeight/tilesVertical)/2};
      int[] threeEnemy = {(boardWidth/tilesHorizontal)*((tilesHorizontal/2)) +(boardWidth/tilesHorizontal)/3 , (boardHeight/tilesVertical)/2};
      int[] fourEnemy = {(boardWidth/tilesHorizontal)*((tilesHorizontal/2)+1)+(boardWidth/tilesHorizontal)/3 , (boardHeight/tilesVertical)/2 + (boardHeight/tilesVertical)};
   
      int position = 1;
      int[][] enemyPositions = new int[sumEnemyTeam][8];
      int [] conversion = {0,0};
      int j= 0;
      
      int enemyHand = 0;
      int[] enemyStats = new int[5];
      
      g.setColor(Color.RED);
      if (sumEnemyTeam == 4){
         
         for(int i = 0; i<= enemyTeamTemp.length -1; i++){
            j=0;
            
            while (enemyTeamTemp[i]>0 & position<=sumEnemyTeam){
               if (position == 1){
                   conversion = oneEnemy;
               }else if (position == 2){
                   conversion = fourEnemy;
               }else if (position == 3){
                   conversion = twoEnemy;               
               }else if (position == 4){
                   conversion = threeEnemy;
               }
               
               if (i == 0){
                  g.drawString("Grunt",conversion[0],conversion[1] );
               }else if (i == 1){
                  g.drawString("Cap",conversion[0],conversion[1] );
               }else if (i == 2){
                  g.drawString("Ass",conversion[0],conversion[1] );
               }else if (i == 3){
                  g.drawString("Boss",conversion[0],conversion[1] );
               }
               
               if (i==0){
                  while (turnOrder[j]>60 | turnOrder[j]<=50){
                     j++;
                  }                                       
               }else if (i == 1){
                  while (turnOrder[j]>70 | 60>turnOrder[j]){
                     j++;
                  }
               }else if (i == 2){
                  while (turnOrder[j]>80 | 70>turnOrder[j]){
                     j++;
                  }
               }else if (i == 3){
                  while (turnOrder[j]>90 | 80>turnOrder[j]){
                     j++;
                  }
               }
               
               enemyHand = dealEnemyHand(i,rand);
               enemyStats = createEnemyStats(i+1);
               
               
               
               enemyTeamTemp[i]=enemyTeamTemp[i]-1;
               enemyPositions[position-1][0] = turnOrder[j];
               enemyPositions[position-1][1] = conversion[0];
               enemyPositions[position-1][2] = conversion[1];
               enemyPositions[position-1][3] = enemyHand;
               enemyPositions[position-1][4] = enemyStats[1];
               enemyPositions[position-1][5] = enemyStats[2];
               enemyPositions[position-1][6] = enemyStats[3];
               enemyPositions[position-1][7] = enemyStats[4];
               position = position +1;
               j++;
               
            }                                
               
         }              
         
      }else if (sumEnemyTeam == 3){
         
         for(int i = 0; i<= enemyTeamTemp.length -1; i++){
            j=0;
            while (enemyTeamTemp[i]>0 & position<=sumEnemyTeam){
               if (position == 1){
                   conversion = oneEnemy;
               }else if (position == 2){
                   conversion = threeEnemy;               
               }else if (position == 3){
                   conversion = twoEnemy;
               }
               
               if (i == 0){
                  g.drawString("Grunt",conversion[0],conversion[1] );
               }else if (i == 1){
                  g.drawString("Cap",conversion[0],conversion[1] );
               }else if (i == 2){
                  g.drawString("Ass",conversion[0],conversion[1] );
               }else if (i == 3){
                  g.drawString("Boss",conversion[0],conversion[1] );
               }
               
               if (i==0){
                  while (turnOrder[j]>60 | turnOrder[j]<=50){
                     j++;
                  }                                       
               }else if (i == 1){
                  while (turnOrder[j]>70 | 60>turnOrder[j]){
                     j++;
                  }
               }else if (i == 2){
                  while (turnOrder[j]>80 | 70>turnOrder[j]){
                     j++;
                  }
               }else if (i == 3){
                  while (turnOrder[j]>90 | 80>turnOrder[j]){
                     j++;
                  }
               }
               
               
               enemyHand = dealEnemyHand(i,rand);
               enemyStats = createEnemyStats(i+1);
               enemyTeamTemp[i]=enemyTeamTemp[i]-1;
               enemyPositions[position-1][0] = turnOrder[j];
               enemyPositions[position-1][1] = conversion[0];
               enemyPositions[position-1][2] = conversion[1];
               enemyPositions[position-1][3] = enemyHand;
               enemyPositions[position-1][4] = enemyStats[1];
               enemyPositions[position-1][5] = enemyStats[2];
               enemyPositions[position-1][6] = enemyStats[3];
               enemyPositions[position-1][7] = enemyStats[4];
               position = position +1;
               j++;
            }                                
               
         }
      }else if (sumEnemyTeam == 2 | sumEnemyTeam == 1){
         
         for(int i = 0; i<= enemyTeamTemp.length -1; i++){
            j=0;
            while (enemyTeamTemp[i]>0 & position<=sumEnemyTeam){
               if (position == 1){
                   conversion = twoEnemy;
               }else if (position == 2){
                   conversion = threeEnemy;               
               }
               
               if (i == 0){
                  g.drawString("Grunt",conversion[0],conversion[1] );
               }else if (i == 1){
                  g.drawString("Cap",conversion[0],conversion[1] );
               }else if (i == 2){
                  g.drawString("Ass",conversion[0],conversion[1] );
               }else if (i == 3){
                  g.drawString("Boss",conversion[0],conversion[1] );
               }
               
               if (i==0){
                  while (turnOrder[j]>60 | turnOrder[j]<=80){
                     j++;
                  }                                       
               }else if (i == 1){
                  while (turnOrder[j]>70 | 60>turnOrder[j]){
                     j++;
                  }
               }else if (i == 2){
                  while (turnOrder[j]>80 | 70>turnOrder[j]){
                     j++;
                  }
               }else if (i == 3){
                  while (turnOrder[j]>90 | 80>turnOrder[j]){
                     j++;
                  }
               }
               
               enemyHand = dealEnemyHand(i,rand);
               enemyStats = createEnemyStats(i+1);
               enemyTeamTemp[i]=enemyTeamTemp[i]-1;
               enemyPositions[position-1][0] = turnOrder[j];
               enemyPositions[position-1][1] = conversion[0];
               enemyPositions[position-1][2] = conversion[1];
               enemyPositions[position-1][3] = enemyHand;
               enemyPositions[position-1][4] = enemyStats[1];
               enemyPositions[position-1][5] = enemyStats[2];
               enemyPositions[position-1][6] = enemyStats[3];
               enemyPositions[position-1][7] = enemyStats[4];
               position = position +1;
               j++;
            }                                
               
         }
         }
   return enemyPositions;
   }
      
   
   public static int[][] putPiecesOnTheBoard(int[] characterTeam, int[] turnOrder, int boardHeight, int boardWidth,int tilesHorizontal, int tilesVertical, Graphics g, Random rand){
      int sumCharacterTeam = sumTeam(characterTeam);
      int[] characterTeamTemp = Arrays.copyOf(characterTeam,characterTeam.length);
      int handSize = 2;
      int[] characterHand = new int[handSize];
      int[] characterStats = new int[6];
      
      
      int[] one = {(boardWidth/tilesHorizontal)*((tilesHorizontal/2)-2) +(boardWidth/tilesHorizontal)/3 , boardHeight - (boardHeight/tilesVertical)/2};
      int[] two = {(boardWidth/tilesHorizontal)*((tilesHorizontal/2)-1) +(boardWidth/tilesHorizontal)/3 , boardHeight - (boardHeight/tilesVertical)/2};
      int[] three = {(boardWidth/tilesHorizontal)*((tilesHorizontal/2)) +(boardWidth/tilesHorizontal)/3 , boardHeight - (boardHeight/tilesVertical)/2};
      int[] four = {(boardWidth/tilesHorizontal)*((tilesHorizontal/2)+1)+(boardWidth/tilesHorizontal)/3 , boardHeight - (boardHeight/tilesVertical)/2};
      
      
      int position = 1;
      int[][] characterPositions = new int[sumCharacterTeam][10];

      
      int [] conversion = {0,0};         
      
      g.setColor(Color.BLUE);
      int j;
      
      if (sumCharacterTeam == 4){
         
         for(int i = 0; i<= characterTeamTemp.length -1; i++){
            j=0;
            while (characterTeamTemp[i]>0  & position<=sumCharacterTeam){
               if (position == 1){
                   conversion = one;
               }else if (position == 2){
                   conversion = four;
               }else if (position == 3){
                   conversion = two;               
               }else if (position == 4){
                   conversion = three;
               }
               
               if (i == 0){
                  g.drawString("War",conversion[0],conversion[1] );
               }else if (i == 1){
                  g.drawString("Mage",conversion[0],conversion[1] );
               }else if (i == 2){
                  g.drawString("Rog",conversion[0],conversion[1] );
               }else if (i == 3){
                  g.drawString("Bard",conversion[0],conversion[1] );
               }
               


               if (i==0){
                  while (turnOrder[j]>20 | turnOrder[j]<=0){
                     j++;
                  }                                       
               }else if (i == 1){
                  while (turnOrder[j]>30 | 20>turnOrder[j]){
                     j++;
                  }
               }else if (i == 2){
                  while (turnOrder[j]>40 | 30>turnOrder[j]){
                     j++;
                  }
               }else if (i == 3){
                  while (turnOrder[j]>50 | 40>turnOrder[j]){
                     j++;
                  }
               }
               
               
               characterHand = dealCharacterHand(handSize,rand);
               characterStats = createCharacterStats(i+1);
               
               characterTeamTemp[i]=characterTeamTemp[i]-1;
               characterPositions[position-1][0] = turnOrder[j];
               characterPositions[position-1][1] = conversion[0];
               characterPositions[position-1][2] = conversion[1];
               characterPositions[position-1][3] = characterHand[0];
               characterPositions[position-1][4] = characterHand[1];
               characterPositions[position-1][5] = characterStats[1];
               characterPositions[position-1][6] = characterStats[2];
               characterPositions[position-1][7] = characterStats[3];
               characterPositions[position-1][8] = characterStats[4];
               characterPositions[position-1][9] = characterStats[5];

               
               position = position +1;
               j++;
            }                                
               
         }
      }else if (sumCharacterTeam == 3){
         
         for(int i = 0; i<= sumCharacterTeam; i++){
            j=0;
            while (characterTeamTemp[i]>0 & position<=sumCharacterTeam){
               if (position == 1){
                   conversion = one;
               }else if (position == 2){
                   conversion = three;               
               }else if (position == 3){
                   conversion = two;
               }
               
               if (i == 0){
                  g.drawString("War",conversion[0],conversion[1] );
               }else if (i == 1){
                  g.drawString("Mage",conversion[0],conversion[1] );
               }else if (i == 2){
                  g.drawString("Rogue",conversion[0],conversion[1] );
               }else if (i == 3){
                  g.drawString("Bard",conversion[0],conversion[1] );
               }
               
               if (i==0){
                  while (turnOrder[j]>20 | turnOrder[j]<=0){
                     j++;
                  }                                       
               }else if (i == 1){
                  while (turnOrder[j]>30 | 20>turnOrder[j]){
                     j++;
                  }
               }else if (i == 2){
                  while (turnOrder[j]>40 | 30>turnOrder[j]){
                     j++;
                  }
               }else if (i == 3){
                  while (turnOrder[j]>50 | 40>turnOrder[j]){
                     j++;
                  }
               }
               
               characterHand = dealCharacterHand(handSize,rand);
               characterStats = createCharacterStats(i+1);
               
               characterTeamTemp[i]=characterTeamTemp[i]-1;
               characterPositions[position-1][0] = turnOrder[j];
               characterPositions[position-1][1] = conversion[0];
               characterPositions[position-1][2] = conversion[1];
               characterPositions[position-1][3] = characterHand[0];
               characterPositions[position-1][4] = characterHand[1];
               characterPositions[position-1][5] = characterStats[1];
               characterPositions[position-1][6] = characterStats[2];
               characterPositions[position-1][7] = characterStats[3];
               characterPositions[position-1][8] = characterStats[4];
               characterPositions[position-1][9] = characterStats[5];

               
               position = position +1;
               j++;
            }                                
               
         }
      }else if (sumCharacterTeam == 2 | sumCharacterTeam == 1){
      
         for(int i = 0; i<= 3; i++){
         j=0;
            while (characterTeamTemp[i]>0 & position<=sumCharacterTeam){
               if (position == 1){
                   conversion = two;
               }else if (position == 2){
                   conversion = three;               
               }
               
               if (i == 0){
                  g.drawString("War",conversion[0],conversion[1] );
               }else if (i == 1){
                  g.drawString("Mage",conversion[0],conversion[1] );
               }else if (i == 2){
                  g.drawString("Rogue",conversion[0],conversion[1] );
               }else if (i == 3){
                  g.drawString("Bard",conversion[0],conversion[1] );
               }
               
               if (i==0){
                  while (turnOrder[j]>20 | turnOrder[j]<=0){
                     j++;
                  }                                       
               }else if (i == 1){
                  while (turnOrder[j]>30 | 20>turnOrder[j]){
                     j++;
                  }
               }else if (i == 2){
                  while (turnOrder[j]>40 | 30>turnOrder[j]){
                     j++;
                  }
               }else if (i == 3){
                  while (turnOrder[j]>50 | 40>turnOrder[j]){
                     j++;
                  }
               }
               
               characterHand = dealCharacterHand(handSize,rand);
               characterStats = createCharacterStats(i+1);
               
               characterTeamTemp[i]=characterTeamTemp[i]-1;
               characterPositions[position-1][0] = turnOrder[j];
               characterPositions[position-1][1] = conversion[0];
               characterPositions[position-1][2] = conversion[1];
               characterPositions[position-1][3] = characterHand[0];
               characterPositions[position-1][4] = characterHand[1];
               characterPositions[position-1][5] = characterStats[1];
               characterPositions[position-1][6] = characterStats[2];
               characterPositions[position-1][7] = characterStats[3];
               characterPositions[position-1][8] = characterStats[4];
               characterPositions[position-1][9] = characterStats[5];

               
               position = position +1;
               j++;
            }                                
               
         }
      }                                
      return characterPositions;                            
   }
               
      
   
   public static int sumTeam(int[] team){
      int sum = 0;
      for (int i = 0; i<= team.length-1; i++){
         sum = sum + team[i];
      }
      //System.out.println("total = " + sum);
      
      return sum;
   }
      





  
   public static void makeBoardTiles(int boardHeight, int boardWidth, int tilesHorizontal, int tilesVertical, Graphics g){
      for (int tiles = 1;tiles <= tilesHorizontal;tiles++){
         g.drawLine((boardWidth/tilesHorizontal)*tiles,0,(boardWidth/tilesHorizontal)*tiles,boardHeight);         
         g.drawString(String.valueOf(tiles),(boardWidth/tilesHorizontal)*(tiles-1) + (boardWidth/tilesHorizontal)/2,(boardHeight/tilesVertical)/6);
         
      }
      for (int tiles = 1;tiles <= tilesVertical;tiles++){
         g.drawLine(0,(boardWidth/tilesVertical)*tiles,boardWidth,(boardWidth/tilesVertical)*tiles);
         g.drawString(String.valueOf((tiles)),(boardWidth/tilesHorizontal)/10,(boardHeight/tilesVertical)*(tiles-1) + (boardHeight/tilesVertical)/2);
      }
   }
   
   public static int[] teamSelect(Scanner console){     
      int[] characterQue = new int[4];
      int characterTotal = 0;
      int j =0;
         System.out.println("There are 4 character types you can play with.");
         System.out.println("The warrior, the mage, the rogue, and the bard.");
         System.out.println("You can choose up to 4 total characters of any mix.");
      
      for (j = 0; j <4; j++){
         System.out.println("Up to " +(4-characterTotal)+ " characters left");
         if (j == 0) {
            System.out.print("warrior # (0-" +(4-characterTotal)+"): ");              
         } else if (j == 1) {
            System.out.print("mage # (0-" +(4-characterTotal)+"): ");         
         }else if (j == 2) {
            System.out.print("rogue # (0-" +(4-characterTotal)+"): ");          
         }else if (j == 3) {
            System.out.print("bard # (0-" +(4-characterTotal)+"): ");               
         }
         characterQue[j] = console.nextInt();        
         characterTotal = characterTotal +characterQue[j];
         
         System.out.println("If finished in Team Select, enter 1. If not, enter 0");
      
         int finished = console.nextInt();
         if (finished == 1){
            j = 4;
         System.out.println("A brave fighter. A foolish fighter. Soon to be a dead fighter.");
         }else if (j==3){
            System.out.println("Tough luck. You had your chance to choose more characters, but you didn't.");
         }else if (characterTotal == 4){
            j=4;
         System.out.println("What? You're so afraid that you need all four guys. What a joke.");
         System.out.println();
         }
      }
      System.out.println();  
      return characterQue;
      
   }  
   
   public static int[] enemySelect(Scanner console){     
      int[] enemyQue = new int[4];
      int enemyTotal = 0;
      int j =0;
      System.out.println("There are 4 enemy types you can play against.");
      System.out.println("The grunt, the captain, the assassin, and the boss.");
      System.out.println("You can choose up to 4 total enemies of any mix.");
      
      for (j = 0; j <4; j++){

         System.out.println("Up to " +(4-enemyTotal)+ " enemies left");
         if (j == 0) {
            System.out.print("grunt # (0-" +(4-enemyTotal)+"): ");              
         } else if (j == 1) {
            System.out.print("captain # (0-" +(4-enemyTotal)+"): ");         
         }else if (j == 2) {
            System.out.print("assassin # (0-" +(4-enemyTotal)+"): ");          
         }else if (j == 3) {
            System.out.print("boss # (0-" +(4-enemyTotal)+"): ");               
         }
         enemyQue[j] = console.nextInt();
         enemyTotal = enemyTotal + enemyQue[j];
         
         System.out.println("If finished in Enemy Select, enter 1. If not, enter 0");

         int finished = console.nextInt();
         if (finished == 1){
            System.out.println("Wow, You are too afraid to fight more than that?!?!?! ");
            j = 4;
         }else if (j==3){
            System.out.println("You are a bold fighter. Let's see if you can take on these four first.");
         }else if (enemyTotal == 4){
            System.out.println("You are a bold fighter. Let's see how you fare.");         
            j=4;
         }
      }
      System.out.println();
      return enemyQue;
   }
   
   public static int traitToEnemyTrait(int trait){
      int enemyTrait;
      if (trait == 3){
         enemyTrait = 2;
      } else if (trait == 2){
         enemyTrait = 3;
      }else{
         enemyTrait = 1;
      }
      return enemyTrait;
   }
   
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////   
// Character vs Enemy Interactions in Combat

//  %Loops for ever character
//     %Loops for all enemy types
//         %checks if selected enemies are in que
//            %Runs while character is alive    
//                 %Loops for every enemy in que
//                     %Determines who goes first                    
//                     %Runs combat for a single fighter and enemy until a death occurs
//                         %Character Turn
//                             %Converts Character trait into Enemy Trait
//                             %Enemy values (normally distributed for 2 six-sided die)
//                             %Resets player counter which holds characters' "rolls" 
//                             %applies value for ever "card in hand"  
//                                      %Character (low value, high value) (based on 10 sided die w/ modifiers) 

//                             %Character wins outright
//                             if (player{character,2} > enemy) | (player{character,3} > enemy);  %player wins                          
//                                 enemy_stats_test{enemy_type,4} = enemy_stats_test{enemy_type,4} - 1;
// %                                 round = 'done';
//                                 alert = 'player hit1';
//                                 turn = turn +1;                           
//                             
//                             %Character ties/loses withing vigor threshold
//                             elseif vigor_threshold >= (enemy - player{character,character_card+1}) & (enemy - player{character,character_card+1}) >= 0 & character_stats{character,6} > (enemy-player{character,character_card+1});
//                                 character_stats_test{character,6} = character_stats_test{character,6} - (1 + (enemy - player{character,character_card+1}));                                      
//                                 enemy_stats_test{enemy_type,4} = enemy_stats_test{enemy_type,4} - 1;
// %                                 round = 'done';
//                                 alert = 'player hit2';
//                                 turn = turn +1;

//                             %Character misses and enemy is still alive
//                             elseif enemy_stats_test{enemy_type,4} > 0 & (enemy > player{character,2} | enemy > (player{character,3}))
//                                 turn = turn +1;
//                                 alert = 'player miss';
//                             end                          
//                         
//                         %Enemy Turn    
//                         else
//                             
//                             %Tested trait is character defense/enemy offense
//                             %Converts Character trait into Enemy Trait
//                             %Character (low value, high value) (based on 10 sided die w/ modifiers)
//                             %Enemy values (normally distributed for 2 six-sided die)

//                             %Enemy attacks player succesfully
//                             if enemy > player(character-1)-1;
//                                 character_stats_test{character,4} = character_stats_test{character,4} - 1;
//                                 
//                                 alert = 'enemy hit';
//                                 
//                             %Enemy attacks player within vigor threshold    
//                             elseif vigor_threshold >= (enemy - player(character-1)) & (enemy - player(character-1)) >= 0 & character_stats{character,6} > (enemy - player(character-1));
//                                 character_stats_test{character,6} = character_stats_test{character,6} - (1+(enemy-player(character-1)));
//                                 alert ='enemy miss1';
//                                 
//                             %Enemy is below character's vigor threshold
//                             else                                
//                                 alert = 'enemy miss2';
//                             end
//                             
//                             %Advances Turn to Player                      
//                         end
//                         
//                         %Death Report of a fallen character if dead                        
//                     end
//                 end
//             end                               
//         end            
//     end
//         
//     %Displays winning Characters information if alive
// end  
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

//   Character and Enemy Stats and Hand creation   
   
   //Prints Player Hands
   public static void printCharacterHand(int character,int handSize, int[] playerHand){
      //Prints Character Label
      if (character == 1) {
         System.out.print("warrior: ");              
      } else if (character == 2) {
         System.out.print("mage: ");         
      }else if (character == 3) {
         System.out.print("rogue: ");          
      }else if (character == 4) {
         System.out.print("bard: ");               
      }
      
      //Prints Character card values
      for (int i= 0; i <= handSize-1; i++){     
         System.out.print(playerHand[i] + " ");
      }      
      System.out.println();
   }

   //Deals Inital Character Hands
   public static int[] dealCharacterHand(int handSize,Random rand){
      int [] cardRange = {2,2,3,3,4,4,5,5,6,6,7,7,8,8,9,9,10,10,11,11,12,12};
      int[] cardValue = new int[handSize];
      int[] cardID = new int[handSize];
      int repeat = 1;
      
       //First Card     
      cardID[0] = rand.nextInt(cardRange.length);
           
      cardValue[0] = cardRange[cardID[0]];
      
      //If multiple cards
      if (handSize>1){
      
         //Addes every remaining card
         for (int cardNumber = 1; cardNumber <= handSize-1; cardNumber++){
            repeat = 1;
            
            //continues until card "drawn" is not a duplicate
            while (repeat == 1){
               cardID[cardNumber] = rand.nextInt(cardRange.length);    
               
               //checks card with others in hand
               for (int checkID = 0; checkID <= cardNumber -1; checkID++){
               
                  //if a duplicate, end search and "draw a new card"
                  if (cardID[cardNumber] == cardID[checkID]){
                     checkID = handSize;
                     repeat = 1;
                  
                  //If card is not a duplicate   
                  }else{                  
                  cardValue[cardNumber] = cardRange[cardID[cardNumber]];
                  repeat = 0;
                  } 
               }
            }
         }           
      }
      return cardValue;
   }
   
   //Deals and Prints Enemy Hands  
   public static int dealEnemyHand(int enemy,Random rand){
      int [] cardRange = {2,3,3,4,4,4,5,5,5,5,6,6,6,6,6,7,7,7,7,7,7,8,8,8,8,8,9,9,9,9,10,10,10,11,11,12};
      int cardValue;
      int cardID;    
      
       //Draws Enemy Card       
      cardID = rand.nextInt(cardRange.length);      
      cardValue = cardRange[cardID];
      
//       //Printing Enemy cards
//       System.out.println("");
//       if (enemy == 1) {
//          System.out.print("grunt: ");              
//       } else if (enemy == 2) {
//          System.out.print("captain: ");         
//       }else if (enemy == 3) {
//          System.out.print("assassin: ");          
//       }else if (enemy == 4) {
//          System.out.print("boss: ");               
//       }
//       
//       System.out.print(cardValue);
      
      //Returns value
      return cardValue;
   }
   
   //Creates Character stats
   public static int[] createCharacterStats(int row) {
      int atk = -1;
      int def = -1;
      int health = -1;
      int move = -1;
      int vigor = -1;  
     
     //Setting stats for each character type
      if (row == 1) {   
         atk = 7;
         def = 5;
         health = 4;
         move = 2;
         vigor = 10;
               
      } else if (row == 2) {   
         atk = 6;
         def = 4;
         health = 4;
         move = 2;
         vigor = 10;      
      }else if (row == 3) {    
         atk = 2;
         def = 3;
         health = 4;
         move = 2;
         vigor = 10;      
      }else if (row == 4) {  
         atk = 1;
         def = 7;
         health = 4;
         move = 3;
         vigor = 10;            
      }
  
      //Collects a single character's stats in an int array 
      int[] characterStats = {row,atk,def,health,move,vigor};
      
      //returns character stats
      return characterStats;
   }
   
   //Prints Character Stats
   public static void printCharacterStats(int[] characterStats){
   
      //Prints character label
      if (characterStats[0] == 1) {
         System.out.println("warrior");              
      } else if (characterStats[0] == 2) {
         System.out.println("mage");         
      }else if (characterStats[0] == 3) {
         System.out.println("rogue");          
      }else if (characterStats[0] == 4) {
         System.out.println("bard");               
      }
      
      //Prints stat label and value
      for (int i= 1; i <= 5; i++){
         if (i == 1) {
            System.out.print("atk: ");              
         } else if (i == 2) {
            System.out.print("def: ");         
         }else if (i == 3) {
            System.out.print("health: ");          
         }else if (i == 4) {
            System.out.print("move: ");               
         }else if (i == 5) {
            System.out.print("vigor: ");               
         }       
         System.out.println(characterStats[i]);
      }      
      System.out.println();
   }
      //Creates Enemy stats
      public static int[] createEnemyStats(int row) {
      int atk = -1;
      int def = -1;
      int health = -1;
      int move = -1;  
     
      // Sets stats for all enemy types
      if (row == 1) {   
         atk = 0;
         def = 0;
         health = 1;
         move = 2;
               
      } else if (row == 2) {   
         atk = 2;
         def = 2;
         health = 2;
         move = 3;      
      }else if (row == 3) {    
         atk = 4;
         def = 0;
         health = 2;
         move = 3;      
      }else if (row == 4) {  
         atk = 4;
         def = 4;
         health = 4;
         move = 2;            
      }
   
      // Collects a single enemy's stats in an int array
      int[] enemyStats = {row,atk,def,health,move};
      return enemyStats;
   }
   
   
   //Prints Enemy Stats
   public static void printEnemyStats(int[] enemyStats){
      //Prints Enemy label
      if (enemyStats[0] == 1) {
         System.out.println("grunt");              
      } else if (enemyStats[0] == 2) {
         System.out.println("captain");         
      }else if (enemyStats[0] == 3) {
         System.out.println("assassin");          
      }else if (enemyStats[0] == 4) {
         System.out.println("boss");               
      }
      
      //Prints stat label and value
      for (int i= 1; i <= 4; i++){
         if (i == 1) {
            System.out.print("atk: ");              
         } else if (i == 2) {
            System.out.print("def: ");         
         }else if (i == 3) {
            System.out.print("health: ");          
         }else if (i == 4) {
            System.out.print("move: ");               
         }      
         System.out.println(enemyStats[i]);
      }      
      System.out.println();
   }   
}
