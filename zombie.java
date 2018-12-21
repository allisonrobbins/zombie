package cs2113.zombies;
import cs2113.util.Helper;

public class zombie
{
    int xpos;
    int ypos;
    int direction;
    public zombie(int xpos, int ypos)
    {
        this.xpos = xpos;
        this.ypos = ypos;
        direction = Helper.nextInt(4);
    }
    public void update(City city)
    {
        zombieTurn();
        //isThereAHuman(city);
        tooCloseToEdge();
        tooCloseToWall(city);
        zombieMove(city);
    }
    public void zombieTurn()
    {
        double a = Helper.nextDouble();
        if(a>0.2)
        {
            return;
        }
        else if(a<=0.05)
        {
            direction = 0;
        }
        else if(a<=0.1 && a>0.05)
        {
            direction = 1;
        }
        else if(a<=0.1 && a>0.15)
        {
            direction = 2;
        }
        else if(a<=0.2 && a>0.15)
        {
            direction = 3;
        }
    }
    public void tooCloseToEdge()
    {
        if(xpos<=1)
        {
            direction = 3;//turn away from edge of screen
        }
        else if(xpos>=198)//turn away from edge of screen
        {
            direction = 1;
        }
        else if(ypos<=1)//turn away from edge of screen
        {
            direction = 2;
        }
        else if(ypos>=198)// turn away from edge of screen
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
    public void zombieMove(City city)
    {
        try
        {
            switch(direction)
            {
                case 0:// face north
                    city.stuff[xpos][ypos] = 0;
                    ypos--;
                    city.stuff[xpos][ypos] = 3;
                    break;
                case 1:// face west
                    city.stuff[xpos][ypos] = 0;
                    xpos--;
                    city.stuff[xpos][ypos] = 3;
                    break;
                case 2:// face south
                    city.stuff[xpos][ypos] = 0;
                    ypos++;
                    city.stuff[xpos][ypos] = 3;
                    break;
                case 3:// face east
                    city.stuff[xpos][ypos] = 0;
                    xpos++;
                    city.stuff[xpos][ypos] = 3;
                    break;
            }
        }catch(ArrayIndexOutOfBoundsException e)
        {
            return;
        }
    }
}