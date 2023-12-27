//Baran Korkmaz
//Student no :2021400090
import java.lang.Math;
public class Ball {
        Environment environment = new Environment();
        Player player = new Player();
        public int PERIOD_OF_BALL = 15000;

        public double gravity = 0.00230 * 10;
        public double radius_multiplier = 2.0;
        public double height_multiplier = 1.75;
        public double levelRadius ;
        public double levelHeight;

        public double x_ball;
        public double y_ball;
        public int level_ball;


        public int direction;
        public double change_in_x =  2*environment.PAUSE_DURATION* environment.canvasWidth/(double)(PERIOD_OF_BALL*16);
        public double max_y_height ;
        public double start_y_velocity =Math.sqrt(2*gravity*0.5);

        // the ball constructor
        public Ball(double x_ball, double y_ball, int level_ball, int direction ){
                this.x_ball = x_ball;
                this.y_ball = y_ball;
                this.level_ball = level_ball;
                this.direction = direction;
                if(level_ball==0){
                        levelRadius=10*0.0175;
                        max_y_height = player.SCALED_HEIGHT*1.4;

                } else if (level_ball==1){
                        levelRadius=10*0.0175*radius_multiplier;
                        max_y_height = player.SCALED_HEIGHT*1.4*height_multiplier;
                }else if(level_ball==2){
                        levelRadius=10*0.0175*radius_multiplier*radius_multiplier;
                        max_y_height = player.SCALED_HEIGHT*1.4*height_multiplier*height_multiplier;
                }

        }



}
