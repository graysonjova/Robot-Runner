package demoreee;

import java.util.Random;

/**
 * The LevelData class implements the level generation mechanism.
 */
public class LevelData {
	
	String[] currentLvl;
	public static int currentLevelWidth;
	
	public LevelData() {
		
	}

	/**
     * A function that clears the spawn.
     * @param temp - The temp variable.
     * @param i - Counter.
     * @param j - Counter.
     * @return - It will either return 1 if it at the bottom level to guarantee the you can walk a spawn area or a 0 if its at level 2 or 3 so one can walk through it.
     */
    private static int clearSpawn(int temp,int i, int j) {// this is just to clear spawn as the name suggest
         if((8>=i&&6<=i)&& (0<=j&&j<4)){
            temp = rand.nextInt(2);
        }
        //for blocks that are 2-3 blocks from the bottom and 4 blocks from the start, its a guaranteed empty space
        if((i==10||i==9)&& 0<=j&&j<4){
            temp = 0;
        }
        //for blocks that are 1 blocks from the bottom and 4 blocks from the start, its a guaranteed floor.
        else if(i==11&&j<5){
            temp = 1;
        }
        return temp;
    }
    
	/**
     * A function that generates floors wtihout a specific design.
     * @return - It will either return 1 if it at the bottom level to guarantee the you can walk a spawn area or a 0 if its at level 2 or 3 so one can walk through it.
     */
    private static int gen(){
        int temp = rand.nextInt(40);
        //32 in 40 chance a block to be empty
        if(temp>8){
            temp = 0;
        }
        
        return temp;
    }
    
    /**
     * A function that generates specifically the bottom floor.
     * @return - It will either return 1 if it at the bottom level to guarantee the you can walk a spawn area or a 0 if its at level 2 or 3 so one can walk through it.
     */
    private static int bottomGen(){//this is the special generator for the buttom floor
        int temp = rand.nextInt(9);
        //4 out of 9 chances of it being empty
        if(temp>4){
            temp = 0;
        }
        
        return temp;
    }

    /**
     * A function that generates the top floor.
     * @return - The level array containing the top floor generation.
     */
    private static char[][] topGen(char[][] arrLvl, int lvlWidth, int lvlHeight){//special generator for the top floor
         for(int j = 0; j<=lvlWidth;j++){//top floor is always a wall
             arrLvl[0][j]='1';
            }
         
        return arrLvl;
    }
 
    /**
     * A function that generates spiders and wires, while making sure no spiders are dropping in the spawn area.
     * @param arrLvl - The level array.
     * @param lvlWidth - The level width.
     * @return - The level array containing the spiders, air and wires generation.
     */
    private static char[][] spiderGen(char[][] arrLvl, int lvlWidth){
	 int random;
     for(int j = 0; j<=lvlWidth;j++){
    	 
         if(j<5){
        	 //Guarantees that a spider can't drop onto the Player's spawn.
        	 arrLvl[1][j]='1';
         }
         else {
        	 random = rand.nextInt(10);
             if(random == 0){
            	 //One in ten chance of spawning a spider.
            	 arrLvl[1][j]='4';
             }
             else if(random == 1 || random == 2){
            	 //Three in ten chance of spawning some wires.
            	 arrLvl[1][j]='8';
             }
             else if(random == 3){
            	//Three in ten chance of spawning some wires.
            	 arrLvl[1][j]='9';
             }
             else if(random > 4){
            	 //Seven in ten chance of spawning air.
            	 arrLvl[1][j]='0';
             }       
         }   	 
     }
     
    return arrLvl;
    }

    
    public char[][] arrLvl;
    
    /**
     * A function that creates the length of the level.
     * @return - The length of the level.
     */
    private static int lvlLenGen(){
        int temp = rand.nextInt(50);
        //create the size of the level
            temp = 80+temp;
        
        return temp;
    }
    
    /**
     * A function that makes sure that there aren't too many walls spawned in the same X coordinate.
     * @param arrLvl - The level array.
     * @param  lvlWidth - The level width.
     * @param lvlHeight - The level height.
     * @return - The level Array.
     */
    private static char[][] deadEndKill(char[][] arrLvl,int lvlWidth,int lvlHeight){
        //as long as its not at the bottom or top floor, check for complete diagonals
        for(int i =2; i<lvlHeight ;i++){
            for (int j =1; j<lvlWidth ;j++){
                if(arrLvl[i][j]=='1'){
                    //then remove the diagonal
                    arrLvl = diagonalBlockKill(arrLvl,i,j);
                }
     
            }
        }
        //as long as its in the level, check for extremly tall walls
        for (int k =1; k<lvlWidth ;k++){
            //then break the tower
            arrLvl = towerBlockKill(arrLvl,k,lvlHeight);
            
        }
        return arrLvl;
    }
    
    /**
     * A function that prevents a start shaped wall from spawning. It doesn't look pleasing and block the player unnecesarily
     * @param arrLvl - The level array.
     * @param xcoor - The x coordinate.
     * @param ycoor - The y coordinate.
     * @return - The level array that avoids the star shaped wall from spawning.
     */
    private static char[][] diagonalBlockKill(char[][] arrLvl,int xcoor,int ycoor){
        int temp = rand.nextInt(4);
        //check if the diagonals of a block is filled with blocks
        if(arrLvl[xcoor-1][ycoor-1]=='1'&&arrLvl[xcoor+1][ycoor-1]=='1'
          &&arrLvl[xcoor-1][ycoor+1]=='1'&&arrLvl[xcoor-1][ycoor+1]=='1'){
            //if true, remove one of them.
            switch(temp) {
                case 0:
                    arrLvl[xcoor-1][ycoor-1] = 0;
                    break;
                case 1:
                     arrLvl[xcoor+1][ycoor-1] = 0;
                    break;
                case 2:
                    arrLvl[xcoor-1][ycoor+1] = 0;
                    break;
                case 3:
                    arrLvl[xcoor+1][ycoor+1] = 0;
                    break;
                default:
                    arrLvl[xcoor-1][ycoor-1] = 0;
        }
                    
                   
        }
        return arrLvl;
    }
    
    /**
     * A function that prevents walls that are too big from spawning.
     * @param arrLvl -The level array.
     * @param xcoor - The x coordinate.
     * @param lvlHeight - The level Height.
     * @return - The level array that avoid walls that are too big from spawning.
     */
    private static char[][] towerBlockKill(char[][] arrLvl,int xcoor,int lvlHeight){
        //this function is to prevent walls that are way too big
        int num = 0;
        int counter=0;
        int[] blockarr = new int[lvlHeight];
        for(int y =0; y<lvlHeight;y++){
            //count how many blocks are present in the same yAxis
            if(arrLvl[lvlHeight][xcoor]=='1'){
                num = num+1;
                blockarr[counter]=y;
                counter=counter+1;
            }
        }
        //if the number of blocks on the yAxis is more than 80% of the level height, remove some of them
        if(num>((lvlHeight*4)/5)){
            for(int i=1;i<(num-(lvlHeight*9)/10);i++){
                int temp = rand.nextInt(num-1)+1;
                arrLvl[temp][xcoor] = 0;
                
            }
                
        }
        return arrLvl;
    }

    /**
     * A function that detects if a wall block is the edge of said wall.
     * @param arrLvl - The level array.
     * @param lvlWidth - The level width.
     * @param lvlHeight - The level height.
     * @return - The level array that detects edges.
     */
    private static char[][] edgeDetector(char[][] arrLvl,int lvlWidth, int lvlHeight){//This function is to detect if a wall block is the edge of said wall
        for(int j =2; j<lvlWidth-2 ;j++){
            for (int i =1; i<lvlHeight-2 ;i++){
                char ind ='1';
                if(arrLvl[i][j]=='1'){
                    //preeliminary function, to help with complexity, so we dont have to check every edge block
                    //check if it is possible to be an edge by checking the block to its left/right and to its left/right diagonal
                    if((arrLvl[i][j-1]=='1'||arrLvl[i][j-1]=='l')&&(arrLvl[i][j+1]=='0')){
                        //this block could be a right edge, go to check right
                       ind = checkRight(arrLvl,i,j);
                    }
                    else if((arrLvl[i][j+1]=='1'||arrLvl[i][j+1]=='r')&&(arrLvl[i][j-1]=='0')){
                       //this block could be a left edge, go to check left
                       ind = checkLeft(arrLvl,i,j);
                    }
                    arrLvl[i][j]=ind;
                }
            }

       }
        
               return arrLvl;

    }
    /**
     * A function that checks if a block is at the right edge of a wall/floor.
     * @param arrLvl - The level array.
     * @param i - Counter.
     * @param j- Counter.
     * @return - The new block design.
     */
    private static char checkRight(char[][] arrLvl,int i, int j){

    	int count =0;
    	char ret = '1';
    	//it is an right edge,then it will only have one block around it, namely the block to its direct left
    	for(int a =0; a<=2;a++){
    		for(int b =1; b<=2;b++){
     	       if(arrLvl[i+a-1][j+b-1]=='1'||arrLvl[i+a-1][j+b-1]=='l'){
     	    	   //check how many block is around the block we are checking
     	    	   count = count+1;
     	       }
    		}
    	}
    	//if its one, then its at the right edge of a wall/floor
    	if(count == 1){
    		//change block designation to 'r'
    		ret = 'r';
    	}
    	return ret;
    }
 
    /**
     * A function that checks if a block is at the left edge of a wall/floor.
     * @param arrLvl - The level array.
     * @param i - Counter.
     * @param j- Counter.
     * @return - The new block design.
     */
    private static char checkLeft(char[][] arrLvl,int i, int j){

    	int count =0;
    	char ret = '1';
    	//it is an right edge,then it will only have one block around it, namely the block to its direct right
    		for(int a =0; a<=2;a++){
    			for(int b =0; b<=1;b++){
    				if(arrLvl[i+a-1][j+b-1]=='1'||arrLvl[i+a-1][j+b-1]=='r'){
    					//check how many block is around the block we are checking
    					count = count+1;
    				}
    			}
    		}
    		//if its one, then its at the right edge of a wall/floor
    		if(count == 1){
    			//change block designation to 'L'
    			ret = 'l';
    		}
    		return ret;
    	}
  
    /**
     * A function that checks if a block is at the left edge of a wall/floor.
     * @param num - The level array.
     * @return - The block.
     */
    private static int blockString(int num){//this is where we assign the blocks to a value.
    	int spider = 00400;
    	int wires = 8989;
    	int line4 = 1171;
        int line3 = 111;
        int line2 = 11;
        int square = 1;
        int hazardTop = 2;
        int pickup = 3;
        int block;
        switch(num) {
            //i think this function is self explaintatory
            case 1:
                block = square;
                break;
            case 2:
                block = line2;
                break;
            case 3:
                block = line3;
                break;
            case 4:
                block = line4;
                break;
            case 5:
                block = hazardTop;

            case 6:
                block = pickup;
                break;
            case 7:
                block = wires;
                break;
            case 8:
                block = spider;
                break;
            default:
                block = 0;
        }
        return block;
    }
    
    /**
     * A function that checks if the path below something is empty.
     * @param arrLvl - The level array.
     * @param x - The x coordinate.
     * @param y - The y coordinate.
     * @param pathCheck - Checks that path.
     * @return - The checked path.
     */
    private static int pathCheck(char[][] arrLvl,int x,int y,int pathCheck){
        //this is just to check if the path below something is empty.
        if(arrLvl[y+1][x]=='0'){
            pathCheck = pathCheck+1;
        }
        return pathCheck;
    } 
    
    /**
     * A function that prevents scenarios which make the jumping action impossible.
     * @param arrLvl - The level array.
     * @param yCoor - The y coordinate.
     * @param xCoor - The x coordinate.
     * @return - The array level avoiding unjumpable scenarios.
     */
    private static char[][] preventUnJumpable(char[][] arrLvl,int yCoor, int xCoor){
        //only check if its not in the edge of the map;
        if(yCoor<=7&&yCoor>=3&&xCoor!=0){
        int checkAround =0;
        int checkAroundBig =0;
         for(int a =0; a<=2;a++){
            for(int b=0; b<=2; b++){
                if(arrLvl[yCoor-1+a][xCoor-1+b]=='0'){
                //check if this block is surrounded by empty spaces
                 checkAround = checkAround+1;
             }
            }
         }
        //this is divided to three different check for effeciency reason, this way it dont have to check 20 block for each block
        if(checkAround == 9){
            //check the block within 3 down, 1 up , 3 left and 2 right of the block
            for(int a =0; a<=4;a++){
                for(int b=0; b<=5; b++){
                    if(arrLvl[yCoor+3-a][xCoor-3+b]=='0'){
                        //if its empty, then add one to the check
                        checkAroundBig = checkAroundBig+1;
                        }
                    }
                }
             }
        //if the block and everything around it is empty
        if(checkAroundBig ==20){
            //then add a block in the middle of it
            arrLvl[yCoor+1][xCoor]='1';
            }
        }
        return arrLvl;
    }

    /**
     * A function that creates the pathfinding/pathcreator algorithm.
     * @param arrLvl - The level array.
     * @param lvlWidth - The level width.
     * @param lvlHeight - The level height.
     * @return - The level array containing the pathcreator mechanism.
     */
    private static char[][] createLine(char[][] arrLvl,int lvlWidth, int lvlHeight){
        int x = 0;//first int from finish line is the width coordinate
        int y = 10;
        int isFinishLinePresent = 0;
        int temp = 0;
        int pathNoBot = 0;
        int xMovement = 0;
        int yMovement = 0;
        int ind =0;
        //as long as its not near the end of the level, do this
        while(x<lvlWidth-5){

            arrLvl[y][x]='p';
            ind = ind+1;
            arrLvl = preventUnJumpable(arrLvl, y, x);
            //random call is the movement of the path, 1 signify forward and 2 signify up/down
            temp = randomCal(xMovement,yMovement);
            //if we previously go forward, the system will record that.
            if (temp == 1){
                xMovement = xMovement+1;
                x=x+1;
                yMovement = 0;
                //now do path check
                pathNoBot = pathCheck(arrLvl,x,y,pathNoBot);
                //path check function : if the path has been going straight for 5 turns and
                //all 5 turns they dont have a floor to step on, then create a  for them floor.
                if(pathNoBot>= 5){
                    arrLvl[y+1][x]='1';
                    pathNoBot = 0;
                }
            }
            //if we previously go upwards/downwards, the system will record that
            if (temp == 2){
                yMovement = yMovement+1;
                //if at bottom floor, then the path cant go down, and vice versa
                arrLvl = checkBottom(arrLvl,y,x);
                if(y == 11){
                    y = y-1;
                }//make sure its not at the bottom
                else if(y ==1){
                    y = y+1;//make sure its not at the top
                }
                else{
                    int temp3 = rand.nextInt(10)+1;
                    if(temp3-y<=0){//the higher it go, the bigger the chance it will decide to go down on the next path.
                        y = y-1;
                    }
                    else{
                        y = y+1;//then do the same thing as last time, but change the ycoor instead of Xcoor
                    }
                    
                    
                }             
                xMovement = 0;
            }

            }
        
        while (x <= lvlWidth-2){//If it is within 5 block form the edge, do this.
            temp = randomCal(xMovement,yMovement);
            int temp4 = rand.nextInt(3);
            if (temp == 1){//move forward if temp equals to 1
                xMovement = xMovement+1;
                x = x+1;
                yMovement = 0;
            }
            if (temp == 2){//move in y coor(depends on the Ycoordinate location now), do same thing the function above this
                yMovement = yMovement+1;
                arrLvl = checkBottom(arrLvl,y,x);
                if(y == 11){
                    y = y-1;
                }
                else if(y ==1){
                    y = y+1;
                }
                else{
                    int temp3 = rand.nextInt(10)+1;
                    if(temp3-y<=0){
                        y = y-1;
                    }
                    else{
                        y = y+1;
                    }
                    
                    
                }             
                xMovement =0;
            }
                
            if (temp4 == 1){//1 in a 3 chance of finish being generated
                arrLvl[y][x]='f';
                arrLvl[y][x-1] = 'p';
                arrLvl[y+1][x] = '1';
                isFinishLinePresent =1;
            }
            break;
            }
            if(isFinishLinePresent ==0){//at the last two block, if finish line is not present, then make one.
                arrLvl[y][x]='f';
                arrLvl[y+1][x] = '1';
                arrLvl[y-1][x] = '0';
                arrLvl[y][x-1] = 'p';
                arrLvl[y][x+1] = '0';
            }
            
            return arrLvl;
    }
    
    /**
     * A function that creates the pathgenerator for the bot. Determines the next move eiether up down or forward.
     * @param xmov - The x coordinate movement.
     * @param ymov - The y coordinate movement.
     * @return - The specific movement up or down or forward.
     */
   private static int randomCal(int xmov, int ymov){
       int movement=0;
       //check if it has moved less than 3 times inr row to the right
       if((xmov >=0&&xmov<=3)&&ymov ==0){
        int temp = rand.nextInt(6);
        switch(temp) {
            //1 in 6 chance of it moving on y axis
            case 1:
                movement = 2;
            break;
            //5 in 6 chance of it moving on x axis
            default:
                movement = 1;
       }
     }
       //check if its has moved between 4 to 7 times in a row to the right
        else if((xmov >=4&&xmov<=7)&&ymov ==0){
            int temp = rand.nextInt(4);
            //1 in 4 chance of it moving in y axis
            switch(temp) {
                case 1:
                movement = 2;
            break;
            //3 in 4 chance of it moving in x axis
            default:
                movement = 1;
       }
     }
        //check if it has moved more than 7 times in arow to the right
        else if(xmov >=7 &&ymov ==0){
            int temp = rand.nextInt(3);
            switch(temp) {
             //1 in 3 chance of moving in y axis
                case 1:
                movement = 2;
                break;
            //2 in 3 chance of moving in x axis
            default:
                movement = 1;
       }
     }
     else{
     switch(ymov) {//if it has been moving up, go here
        //if it moved up/down on the previous move
        case 1:
            int temp = rand.nextInt(2);
            switch(temp){
                // 1 in 2 chance of it moving in y axis
                case 1:
                movement = 2;
                break;
                default:
                movement = 1;
               }
        break;
        // check if it has moved twice in a row in the y axis
        case 2:
            temp = rand.nextInt(3);
            switch(temp){
                // 1 in 3 chance of it moving in y axis
                case 1:
                movement = 2;
                break;
                default:
                    movement = 1;
               }
        break;
        //check if it has moved 3 times ina row in the y axis
        case 3:
          //guaranteed move right;
          movement = 1;
        break;
        default:
            movement =1;
       }
     }
       return movement;
       
   }

   /**
    * A function that checks where the path is and if it has anything below it.
    * @param arrLvl - The level array.
    * @param yCoor - The y coordinate.
    * @param xCoor - The x coordinate.
    * @return - The level array checked accordingly.
    */
       private static char[][] checkBottom(char[][] arrLvl,int yCoor, int xCoor){

        int ind=0;
        if(yCoor<=7){
        for(int i =0 ; i<=4;i++)
        {
         if (arrLvl[yCoor+i][xCoor] ==0){
             ind = ind+1;
         }

         if(ind ==4)
             arrLvl[yCoor+4][xCoor]=1;

        }
        ;}
        return arrLvl;

    }

    
   static Random rand = new Random();
   static String[] levelGen() {
        String floor = "";
        String[] lvlKey = new String[12];
        int lvlHeight = 11;
        int noBlock = 0;
        int[] finishLine = new int[1];
        int lvlWidth = lvlLenGen();
        currentLevelWidth = (lvlWidth) * 60;
        char[][] arrLvl = new char[lvlHeight+1][lvlWidth+10];
            for(int i = 0; i<= lvlHeight;i++){
                //here we generate the level in a string.
                noBlock = 0;
                for(int j = 0; j <= lvlWidth;j++){
                    //generate the normal blocks
                    int temp = gen();
                    if(i==11){
                        //then we generate the bottom level
                        temp = bottomGen();
                    }
                    //clear the spawn
                    temp = clearSpawn(temp,i,j);
                    int block = blockString(temp);
                    int length = String.valueOf(block).length();
                    int length2= length;
                    String tempStr = Integer.toString(block);
                    //this part we translate the generated string into a multidimensional array for easier coding
                    for(int k = 0; k < length; k++){
                        if(length2==1){
                        arrLvl[i][j] =tempStr.charAt(k);
                        }
                        else{
                            arrLvl[i][j] =tempStr.charAt(k);
                            j=j+1;
                            length2 =length2-1;
                        }
                    }
                 }
              }

           arrLvl = spiderGen(arrLvl,lvlWidth);//then generate spiders
           arrLvl = topGen(arrLvl,lvlWidth,lvlHeight);//then generate the top
           arrLvl = deadEndKill(arrLvl,lvlWidth,lvlHeight);//then stop all deadends
           arrLvl = createLine(arrLvl,lvlWidth,lvlHeight);//then create a direct line to finish
           arrLvl = edgeDetector(arrLvl,lvlWidth,lvlHeight);//finally detect edges
           //then convert is back to array of strings, since its what the initcontent wants
            for(int i = 0; i<= lvlHeight;i++){
                for(int j = 0; j<=lvlWidth; j++){
                    String abcd = String.valueOf(arrLvl[i][j]);
                    floor = floor+abcd;
                    }
            lvlKey[i] = floor;
            floor ="";
            }
        return lvlKey;
        }

   /**
    * A function that generates the level.
    */
   public void genLevel() {
	   currentLvl =  levelGen();
   }
   
   /**
    * A function that gets the level data.
    * @return - Returns the current level.
    */
   public String[] getLevel() {
	   return currentLvl;
   }
}
