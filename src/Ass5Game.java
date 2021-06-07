/**
 * @author Or Avraham Yehezkel 314915869
 * Class start a arkanoid demo game.
 */
public class Ass5Game {
    /**
     * main method, initialize and start anew game.
     * @param args cmd arguments.
     */
    public static void main(String[] args) {
        // create a new game
        Game game = new Game();
        // initialize it
        game.initialize();
        // runs it
        game.run();
        // once it finishes - close window
        game.getEnvironment().getGui().close();
    }
}
