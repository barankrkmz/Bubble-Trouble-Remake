//Baran Korkmaz
//Student no :2021400090
public class Environment {

    public int TOTAL_GAME_DURATION = 40000;
    public int PAUSE_DURATION = 15;
    public int canvasWidth = 800;
    public int canvasHeight = 500;


    //The method to get background
    public void getBackground(){
        /*StdDraw.setCanvasSize(canvasWidth, canvasHeight);
        StdDraw.setXscale(0.0, 16.0);
        StdDraw.setYscale(-1.0, 9.0);*/
        StdDraw.picture(8.0, 4.0, "background.png",16,10);
        StdDraw.enableDoubleBuffering();
    }
}
