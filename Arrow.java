//Baran Korkmaz
//Student no :2021400090
public class Arrow {
        public int PERIOD_OF_ARROW = 1500;
        public double y_position_arrow = -5.5;
        public double x_position_arrow = 8.0;
        Environment environment = new Environment();
        public double change_y_position_arrowhead = environment.PAUSE_DURATION* environment.canvasHeight/(double)(PERIOD_OF_ARROW*10);


}
