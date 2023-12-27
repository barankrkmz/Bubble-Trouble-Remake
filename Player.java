
//Baran Korkmaz
//Student no :2021400090
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
public class Player {
    public int PERIOD_OF_PLAYER = 6000;
    public double PLAYER_HEIGHT_WIDTH_RATE = 37.0/27.0;
    public double PLAYER_HEIGHT_SCALEY_RATE = 1.0/8.0;
    public double SCALED_HEIGHT = (double)(10*PLAYER_HEIGHT_SCALEY_RATE);
    public double SCALED_WIDTH = (double)(0.8/(PLAYER_HEIGHT_WIDTH_RATE))*(SCALED_HEIGHT);

    public double x_position_player = 8.0;

    public boolean lostByTime = false;
    public boolean lostByTouch = false;
    public boolean win = false;

    Environment environment = new Environment();
    Arrow arrow = new Arrow();
    Bar bar = new Bar();


    public double change_in_x_player = 2*environment.PAUSE_DURATION* environment.canvasWidth/(double)(PERIOD_OF_PLAYER*16);
    // the method where everything happens
    public void  playerMovement(){
        // arraylist with balls in it
        ArrayList<Ball> balls = new ArrayList<Ball>();
        Ball ballA = new Ball(4,0.5,0,123);
        balls.add(ballA);
        Ball ballB = new Ball((double)16/3,0.5,1,-123);
        balls.add(ballB);
        Ball ballC = new Ball(4,0.5,2,123);
        balls.add(ballC);
        //Setting canvas
        int canvasWidth = 800;
        int canvasHeight = 500;
        StdDraw.setCanvasSize(canvasWidth, canvasHeight);
        StdDraw.setXscale(0.0, 16.0);
        StdDraw.setYscale(-1.0, 9.0);
        environment.getBackground();
        StdDraw.picture(8.0, 0.0, "player_back.png");
        StdDraw.show();
        StdDraw.enableDoubleBuffering();

        boolean check = true;
        boolean isArrowUp = false;
        double starting_time = System.currentTimeMillis();
        // the while loop for animation
        while(check){
            StdDraw.clear();
            environment.getBackground();
            // taking keyboard input for player movement
            if (StdDraw.isKeyPressed(KeyEvent.VK_LEFT)) {
                if(x_position_player>0.5) {
                    x_position_player -= change_in_x_player;
                }
            }if (StdDraw.isKeyPressed(KeyEvent.VK_RIGHT)) {
                if(x_position_player<15.5){
                    x_position_player += change_in_x_player;
                }
            }if(StdDraw.isKeyPressed(KeyEvent.VK_SPACE)){
                if(!isArrowUp){
                    arrow.x_position_arrow = x_position_player;
                    isArrowUp = true;
                }else{
                    continue;
                }
            }
            //checking if there is an arrow already in play, if so there won't be any more arrows
            if(isArrowUp) {
                if (arrow.y_position_arrow > 4) {
                    isArrowUp = false;
                    arrow.y_position_arrow = -5.0;
                } else {
                    arrow.y_position_arrow += arrow.change_y_position_arrowhead;
                    StdDraw.picture(arrow.x_position_arrow, arrow.y_position_arrow, "arrow.png");
                }
            }
            double current_time = System.currentTimeMillis();
            bar.passed_time_bar = (current_time-starting_time);

            //the movement for balls which includes projectile motion and elastic motion
            for(int i=0 ; i<balls.size(); i++){
                if(balls.get(i).x_ball + balls.get(i).levelRadius>=16){
                    balls.get(i).change_in_x *= -1;
                }else if(balls.get(i).x_ball - balls.get(i).levelRadius<=0){
                    balls.get(i).change_in_x *= -1;
                }
                StdDraw.picture(balls.get(i).x_ball,balls.get(i).y_ball,
                        "ball.png",balls.get(i).levelRadius*2,balls.get(i).levelRadius*2);
                if(balls.get(i).direction>0){
                    balls.get(i).x_ball += balls.get(i).change_in_x;
                }else{
                    balls.get(i).x_ball -= balls.get(i).change_in_x;
                }
                if(balls.get(i).y_ball-balls.get(i).levelRadius <=0.2){
                    balls.get(i).start_y_velocity = Math.sqrt(2* balls.get(i).gravity* balls.get(i).max_y_height);
                }else{
                    balls.get(i).start_y_velocity -= balls.get(i).gravity;
                }
                balls.get(i).y_ball += balls.get(i).start_y_velocity;
            }

            // checking the arrow and ball contact, if so checking the ball level too in case for creating lower level balls
            if(isArrowUp){
                for(int i = 0; i<balls.size(); i++){
                    if(arrow.x_position_arrow>= balls.get(i).x_ball-balls.get(i).levelRadius &&
                            arrow.x_position_arrow<= balls.get(i).x_ball+balls.get(i).levelRadius &&
                    arrow.y_position_arrow + 4.5 >= balls.get(i).y_ball-balls.get(i).levelRadius){
                        isArrowUp = false;
                        arrow.y_position_arrow = -5.0;
                        if(balls.get(i).level_ball == 2){
                            Ball ballx = new Ball(balls.get(i).x_ball,balls.get(i).y_ball, 1,123);
                            Ball bally = new Ball(balls.get(i).x_ball,balls.get(i).y_ball, 1,-123);
                            balls.add(ballx);balls.add(bally);
                        }else if(balls.get(i).level_ball == 1){
                            Ball ballx = new Ball(balls.get(i).x_ball,balls.get(i).y_ball, 0,123);
                            Ball bally = new Ball(balls.get(i).x_ball,balls.get(i).y_ball, 0,-123);
                            balls.add(ballx);balls.add(bally);
                        }
                        balls.remove(balls.get(i));
                    }
                }
            }

            //checking for ball player contact
            for(int i = 0 ; i< balls.size(); i++){
                if((balls.get(i).x_ball< x_position_player - SCALED_WIDTH/2 && balls.get(i).y_ball<= SCALED_HEIGHT) && (Math.abs(x_position_player - SCALED_WIDTH/2 -balls.get(i).x_ball) <= balls.get(i).levelRadius)){
                    lostByTouch = true;
                }if((balls.get(i).x_ball> x_position_player +SCALED_WIDTH/2 && balls.get(i).y_ball <= SCALED_HEIGHT) && (Math.abs(balls.get(i).x_ball-x_position_player-SCALED_WIDTH/2)<= balls.get(i).levelRadius)){
                    lostByTouch = true;
                }if((balls.get(i).x_ball> x_position_player -SCALED_WIDTH/2 && balls.get(i).x_ball< x_position_player + SCALED_WIDTH/2) && balls.get(i).y_ball>= SCALED_HEIGHT && (Math.abs(balls.get(i).y_ball-SCALED_HEIGHT) <= balls.get(i).levelRadius)){
                    lostByTouch = true;
                }if(Math.sqrt(Math.pow(balls.get(i).x_ball-x_position_player+SCALED_WIDTH/2, 2) + Math.pow(balls.get(i).y_ball-SCALED_HEIGHT,2))<= balls.get(i).levelRadius){
                    lostByTouch = true;
                }if(Math.sqrt(Math.pow(balls.get(i).x_ball-x_position_player-SCALED_WIDTH/2, 2) + Math.pow(balls.get(i).y_ball-SCALED_HEIGHT,2)) <= balls.get(i).levelRadius){
                    lostByTouch = true;
                }
            }


            //checking if the game was lost because of the ball player contact and if so writing game over
            if(lostByTouch) {
                StdDraw.picture(8,10/2.18, "game_screen.png", 16/3.18, (double)10/4);
                StdDraw.setPenColor(StdDraw.BLACK);
                StdDraw.setFont( new Font("Helvetica", Font.BOLD, 30) );
                StdDraw.text(8, 5, "GAME OVER!");
                StdDraw.setFont( new Font("Helvetica", Font.ITALIC, 15) );
                StdDraw.text(8, 10/2.3, "To Replay Click \"Y\"");
                StdDraw.text(8, 10/2.6, "To Quit Click \"N\"");
                check = false;
            }

            //checking if the game was won by the player and if so writing you won
            if(balls.size()==0){
                win = true;
                StdDraw.picture(8,10/2.18, "game_screen.png", 16/3.18, (double)10/4);
                StdDraw.setPenColor(StdDraw.BLACK);
                StdDraw.setFont( new Font("Helvetica", Font.BOLD, 30) );
                StdDraw.text(8, 5, "YOU WON!");
                StdDraw.setFont( new Font("Helvetica", Font.ITALIC, 15) );
                StdDraw.text(8, 10/2.3, "To Replay Click \"Y\"");
                StdDraw.text(8, 10/2.6, "To Quit Click \"N\"");
                check = false;
            }

            //checking if the game was lost because of the time situation and if so writing game over
            if(balls.size()>0 && 8*(1-(double)(2*bar.passed_time_bar/environment.TOTAL_GAME_DURATION))<=-8){
                lostByTime = true;
                StdDraw.picture(8,10/2.18, "game_screen.png", 16/3.18, (double)10/4);
                StdDraw.setPenColor(StdDraw.BLACK);
                StdDraw.setFont( new Font("Helvetica", Font.BOLD, 30) );
                StdDraw.text(8, 5, "GAME OVER!");
                StdDraw.setFont( new Font("Helvetica", Font.ITALIC, 15) );
                StdDraw.text(8, 10/2.3, "To Replay Click \"Y\"");
                StdDraw.text(8, 10/2.6, "To Quit Click \"N\"");
                check = false;
            }



            StdDraw.picture(8,-0.5,"bar.png",16,1);
            StdDraw.setPenColor(225,(int)(225-(225*(bar.passed_time_bar/environment.TOTAL_GAME_DURATION))),0);
            StdDraw.filledRectangle(8*(1-(double)(2*bar.passed_time_bar/environment.TOTAL_GAME_DURATION)),-0.5,8,0.25);
            StdDraw.picture(x_position_player, SCALED_HEIGHT/2, "player_back.png",SCALED_WIDTH,SCALED_HEIGHT);
            StdDraw.show();
            StdDraw.enableDoubleBuffering();
            StdDraw.pause(environment.PAUSE_DURATION);

        }
        //checking if the player wants to play a new game, if not exiting the code
        boolean check2 = true;
        while(check2){
            if (StdDraw.isKeyPressed(KeyEvent.VK_Y)){
                check2 = false;
            }else if(StdDraw.isKeyPressed(KeyEvent.VK_N)){
                System.exit(0);
                check2 = false;
            }
        }
        //if player wants to continue resetting the variables
        lostByTime = false;
        lostByTouch = false;
        win = false;
        playerMovement();




    }


}
