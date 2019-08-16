package com.mobplug.games.framework;


import com.mobplug.games.framework.interfaces.Game;
import com.mobplug.games.framework.interfaces.GameRenderer;
import com.mobplug.games.framework.interfaces.GameRunnable;

/**
 * This is the base class for all types of games. Must be compatible
 * with 2D games, Side scrollers and 3D games. It handles the Basic Game Loop
 *
 * @author andreban
 */
public class BaseGameRunnable<G extends Game> implements GameRunnable{
    /**
     * Maximum gameloops that can happen without a sleep or yield
     */
    public static final int MAX_CONSECUTIVE_LOOPS = 17;

    /**
     * Maximium number of gameUpdates that can occur without gameRenders
     */
    private static final int MAX_SKIP_FRAMES = 5;
    
    private int period;
    
    //Volatile variables. Shared between the events thread and game thead
    private volatile boolean running;

    //The game main thread
    private Thread animator;

    private GameStats gameStats;
    
    private Game game;
    
    private GameRenderer<G> renderer;

    /**
     * Creates a new BaseGameRunnable
     * 
     * @param renderer the renderer that knows how to render that game
     * @param game the game
     */
    public BaseGameRunnable(GameRenderer<G> renderer, G game) {
    	this.game = game;
    	this.renderer = renderer;
    	this.period = game.getPeriod();
    	gameStats = new GameStats(period);
    }

    /**
     * Starts the Game Thread
     */
    public void start() {
        if (animator == null || !running) {
            animator = new Thread(this);
            gameStats = new GameStats(period);
            animator.start();
        }
    }

    /**
     * Stops the Game Thread
     */
    public void stop() {
        running = false;
    }

    /**
     * The base game loop
     */
    public void run() {
        running = true;
        long beforeTime, afterTime, elapsed, timeToSleep;
        long overSleepTime = 0;
        long excess = 0;
        int noDelays = 0;

        beforeTime = Util.getMillis();
        //lastStatsTime = beforeTime;
        while (running) {
            if (!game.isPaused() && !game.isGameOver()) {
//            	gameTime += period;
            	game.update();
            }
            renderer.render(); //
            afterTime = Util.getMillis();
            elapsed = afterTime - beforeTime; //how long did we take to hander?
            timeToSleep = (period - elapsed) - overSleepTime; //sleep excess time if we were too fase
            if (timeToSleep > 0) {
                try {
                    Thread.sleep(timeToSleep);
                } catch(InterruptedException ex) {
                }
                //did we sleep too much? deduct from next loop
                overSleepTime = (Util.getMillis() - afterTime) - timeToSleep;
            } else {
                excess -= timeToSleep;//how much did we miss the target?
                //rendering took more than it should! run next loop right away
                overSleepTime = 0;
                if (++noDelays >= MAX_CONSECUTIVE_LOOPS) { //consective loops. Better give other thrads a chance to run!
                    Thread.yield();
                    noDelays = 0;
                }

            }
            beforeTime = Util.getMillis();

            //rendering took to much time! Skip some rendering frames to achieve desired FPU
            int gameUpdatesWithoutRender = 0;
            while (excess > period && gameUpdatesWithoutRender < MAX_SKIP_FRAMES) {
            	if (!game.isPaused() && !game.isGameOver()) {
//                	gameTime+= period;            		
            		game.update();
            	}
                excess -= period;
                gameUpdatesWithoutRender++;
            }
            gameStats.update(gameUpdatesWithoutRender);
        }
    }
}
