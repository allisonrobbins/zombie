package cs2113.zombies;

import cs2113.util.Helper;

import java.awt.*;
import java.util.ArrayList;


public class City{
    /*
    stuff is an array that holds all the walls, zombies, and humans. for each space on the dot panel...
    if space = 0, nothing is there
    if space = 1, wall is there
    if space = 2, human is there
    if space = 3, zombie is there
    */
    public int stuff[][];
    private int width, height;
    ArrayList<human> allHumans;//this arraylist keeps track of all the humans
    ArrayList<zombie> allZombies;//this arraylist keeps track of all zombies
    ArrayList<human> humansToKill;//this list is to store dead humans while iterating through allHumans
    /*
     * Create a new City and fill it with buildings and people.
     * @param w width of city
     * @param h height of city
     * @param numB number of buildings
     * @param numP number of people
     */
    public City(int w, int h, int numB, int numP) {
        width = w;
        height = h;
        stuff = new int[w][h];
        allHumans = new ArrayList<>();
        allZombies = new ArrayList<>();
        humansToKill = new ArrayList<>();
        randomBuildings(numB);
        populate(numP);
    }
    /*
     * Generates numPeople random people distributed throughout the city.
     * People must not be placed inside walls!
     * @param numP the number of people to generate
     * also generates the single zombie that starts in the simulation
     */
    private void populate(int numP)
    {
        while(numP>=0)
        {
            int x = Helper.nextInt(width);
            int y = Helper.nextInt(height);

            if(stuff[x][y] == 0)//if the space is open (no walls, other humans or zombies)
            {
                human a = new human(x,y);
                allHumans.add(a);
                stuff[x][y] = 2;// 2 means there is a human there
                numP--;
            }
        }
        while(true)
        {
            int x = Helper.nextInt(width);
            int y = Helper.nextInt(height);
            if(stuff[x][y] == 0)//if the space is open (no walls, humans or zombies)
            {
                zombie bill = new zombie(x,y);//gave him a nice lil name
                allZombies.add(bill);//add him to the list to keep track of him
                stuff[x][y] = 3;// 3 stands for a zombie
                return;//done after 1 zombie has been created
            }
        }
    }
    /**
     * Generates a random set of numB buildings.
     *
     * @param numB the number of buildings to generate
     */
    private void randomBuildings(int numB) {
        /* Create buildings of a reasonable size for this map */
        int bldgMaxSize = width/6;
        int bldgMinSize = width/50;

        /* Produce a bunch of random rectangles and fill in the walls array */
        for(int i=0; i < numB; i++) {
            int tx, ty, tw, th;
            tx = Helper.nextInt(width);
            ty = Helper.nextInt(height);
            tw = Helper.nextInt(bldgMaxSize) + bldgMinSize;
            th = Helper.nextInt(bldgMaxSize) + bldgMinSize;

            for(int r = ty; r < ty + th; r++) {
                if(r >= height)
                    continue;
                for(int c = tx; c < tx + tw; c++) {
                    if(c >= width)
                        break;
                    stuff[c][r] = 1;// 1 means that there is a wall there
                }
            }
        }
    }
    /*
     * Updates the state of the city for a time step. This method also transforms humans into zombies
     */
    public void update()
    {
        for(int i=0; i<allHumans.size(); i++)//goes through entire list of humans
        {
            allHumans.get(i).update(this);//human class is where they update
            if(allHumans.get(i).amIZombieNow(this)==true)//if human needs to become zombie...
            {
                humansToKill.add(allHumans.get(i));//add the human object to humansToKill
            }//continue iterating to find other dead humans
        }
        for(int i=0; i<humansToKill.size(); i++)//for all the humans that need to become zombies...
        {
            allHumans.remove(humansToKill.get(i));//remove human object from allHumans
            zombie larry = new zombie(humansToKill.get(i).xpos,humansToKill.get(i).ypos);
            allZombies.add(larry);
            stuff[larry.xpos][larry.ypos] = 3;
        }
        humansToKill.clear();
        for(int i=0; i<allZombies.size(); i++)
        {
            allZombies.get(i).update(this);
        }
    }
    public boolean isOpenSpace(int x, int y)
    {
        if(x>=200 || y>=200)
        {
            return false;
        }
        if(x<0 || y<0)
        {
            return false;
        }
        if(stuff[x][y] == 0)
        {
            return true;
        }
        return false;
    }
    /**
     * Draw the buildings and all humans.
     */
    public void draw()
    {
        /* Clear the screen */
        ZombieSim.dp.clear(Color.black);
        for(int r = 0; r < height; r++)
        {
            for(int c = 0; c < width; c++)
            {
                if(stuff[c][r] == 1)
                {
                    ZombieSim.dp.setPenColor(Color.DARK_GRAY);
                    ZombieSim.dp.drawDot(c, r);
                }
                else if(stuff[c][r] == 2)
                {
                    ZombieSim.dp.setPenColor(Color.WHITE);
                    ZombieSim.dp.drawDot(c, r);
                }
                else if(stuff[c][r] == 3)
                {
                    ZombieSim.dp.setPenColor(Color.RED);
                    ZombieSim.dp.drawDot(c, r);
                }
            }
        }
    }
    /**
     * Draw the buildings.
     * First set the color for drawing, then draw a dot at each space
     * where there is a wall.
     */
    public void addZombie(int x, int y)
    {
        if(stuff[x][y]==1)
        {
            System.out.println("Wall @ ("+x+","+y+"): can't add zombie");
            return;
        }
        zombie steve = new zombie(x,y);
        allZombies.add(steve);
        System.out.println("adding zombie @ ("+x+","+y+")");
    }
}