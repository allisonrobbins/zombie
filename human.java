package cs2113.zombies;
import cs2113.util.Helper;
public class human
{
     int xpos;
     int ypos;
     int direction;
     public human(int xpos, int ypos)
     {
          this.xpos = xpos;
          this.ypos = ypos;
          direction = Helper.nextInt(4);
     }
     public void humanTurn()
     {
          double a = Helper.nextDouble();
          if(a>0.1)
          {
               return;
          }
          else if(a<=0.025)
          {
               direction = 0;
          }
          else if(a<=0.05 && a>0.025)
          {
               direction = 1;
          }
          else if(a<=0.075 && a>0.05)
          {
               direction = 2;
          }
          else if(a<=0.1 && a>0.075)
          {
               direction = 3;
          }
     }
     public void update(City city)
     {
          humanTurn();
          isThereAZombie(city);
          tooCloseToWall(city);
          tooCloseToEdge();
          humanMove(city);
     }
     public void tooCloseToEdge()
     {
          if(xpos<=1)
          {
               direction = 3;//turn away from edge of screen
               return;
          }
          else if(xpos>=198)//turn away from edge of screen
          {
               direction = 1;
               return;
          }
          else if(ypos<=1)//turn away from edge of screen
          {
               direction = 2;
               return;
          }
          else if(ypos>=198)// turn  away from edge of screen
          {
               direction = 0;
          }
     }
     public void tooCloseToWall(City city)
     {
          if((direction == 0) && !city.isOpenSpace(xpos,ypos-1))
          {
               direction = 2;
          }
          else if((direction == 1) && !city.isOpenSpace(xpos-1,ypos))
          {
               direction =3;
          }
          else if((direction == 2) && !city.isOpenSpace(xpos,ypos+1))
          {
               direction = 0;
          }
          else if((direction == 3) && !city.isOpenSpace(xpos+1,ypos))
          {
               direction = 1;
          }
     }
     public void humanMove(City city)
     {
          try{
               if (direction == 0)//facing north
               {
                    city.stuff[xpos][ypos] = 0;
                    ypos--;
                    city.stuff[xpos][ypos] = 2;
               } else if (direction == 1)//facing west
               {
                    city.stuff[xpos][ypos] = 0;
                    xpos--;
                    city.stuff[xpos][ypos] = 2;
               } else if (direction == 2)//facing south
               {
                    city.stuff[xpos][ypos] = 0;
                    ypos++;
                    city.stuff[xpos][ypos] = 2;
               } else if (direction == 3)//facing east
               {
                    city.stuff[xpos][ypos] = 0;
                    xpos++;
                    city.stuff[xpos][ypos] = 2;

               }
          }catch(ArrayIndexOutOfBoundsException e)//I kept getting a weird error around the edges of the screen so i put a try catch block in
          {
               return;
          }
     }





     public boolean amIZombieNow(City city)
     {
          if(city.stuff[xpos][ypos] == 3)
          {
               return true;
          }
          if(ypos+1<200 && city.stuff[xpos][ypos++]==3)
          {
               return true;
          }
          if(ypos-1>0 && city.stuff[xpos][ypos--]==3)
          {
               return true;
          }
          if(xpos+1<200 && city.stuff[xpos++][ypos]==3)
          {
               return true;
          }
          if(xpos-1>0 && city.stuff[xpos--][ypos]==3)
          {
               return true;
          }
          return false;

     }
     public boolean isThereAZombie(City city)
     {
          for(int i=1; i<=10; i++) {
               switch (direction) {
                    case 0:// facing north
                         if (ypos - i <= 0) {
                              return false;
                         } else if (city.stuff[xpos][ypos - i] == 2) {
                              direction=2;
                              humanMove(city);
                              return true;
                         } else if (city.stuff[xpos][ypos - i] == 1) {
                              return false;
                         }
                         break;
                    case 1:// facing west
                         if (xpos-i <= 0) {
                              return false;
                         } else if (city.stuff[xpos - i][ypos] == 2) {
                              direction = 3;
                              humanMove(city);
                              return true;
                         } else if (city.stuff[xpos - i][ypos] == 1) {
                              return false;
                         }
                         break;
                    case 2:// facing south
                         if (ypos + i >= 200) {
                              return false;
                         } else if (city.stuff[xpos][ypos + i] == 2) {
                              direction = 0;
                              humanMove(city);
                              return true;
                         } else if (city.stuff[xpos][ypos + i] == 1) {
                              return false;
                         }
                         break;
                    case 3:// facing east
                         if (xpos + i >= 200) {
                              return false;
                         } else if (city.stuff[xpos + i][ypos] == 2) {
                              direction = 1;
                              humanMove(city);
                              return true;
                         } else if (city.stuff[xpos + i][ypos] == 1) {
                              return false;
                         }
                         break;
               }
          }
          return false;
     }
}