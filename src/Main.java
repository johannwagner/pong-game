import de.vogler.pong.Game;

import javax.swing.*;

public class Main {


    public static void main(String[] args) {

        JFrame frame = new JFrame("Pong ~ Sebastian Vogler");

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        Game game = new Game(frame);
        game.start();


    }
}
