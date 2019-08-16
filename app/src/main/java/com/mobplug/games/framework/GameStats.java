/*
 * Copyright 2019 André Cipriani Bandarra
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.mobplug.games.framework;

/**
 * Collects game stats
 * @author andreban
 */
public class GameStats {
    private int frameCount;
    private long statsInterval = 0L;
    private static final long MAX_INTERVAL_TIME = 1000L;//1 second
    private long totalTime = 0L;
    private static int NUM_FPS = 10;
    private double[] fpsStore = new double[NUM_FPS];
    private double[] upsStore = new double[NUM_FPS];
    private double avgFps = 0;
    private double avgUps = 0;
    private int statsCount = 0;
    private long lastStatsTime;
    private int period;
    private int totalGameUpdatesWithoutRender;

    public GameStats(int period) {
        this.period = period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public void init() {
        lastStatsTime = Util.getMillis();
    }
    
    public double getAvgFps() { return avgFps; }
    public double getAvgUps() { return avgUps; }
    public void update(int gameUpdatesWithoutRender) {
        totalGameUpdatesWithoutRender += gameUpdatesWithoutRender;
        frameCount++;

        //the time that should have passed between renders
        statsInterval += period;

        if (statsInterval >= MAX_INTERVAL_TIME) {
            long currTime = Util.getMillis();

            //the real elapsed time
            long realElapsed = currTime - lastStatsTime;

            totalTime += realElapsed;


//            double timingError =
//                ((double)(realElapsed - statsInterval)) / statsInterval * 100.0;
            double currFps = (double)frameCount / totalTime * 1000;

            double currUps = (double)(totalGameUpdatesWithoutRender + frameCount) / totalTime * 1000;

            fpsStore[statsCount % NUM_FPS] = currFps;
            upsStore[statsCount % NUM_FPS] = currUps;

            double totalFps = 0;
            double totalUps = 0;
            for (int i = 0; i < NUM_FPS; i++) {
                totalFps += fpsStore[i];
                totalUps += upsStore[i];
            }

            avgFps = 0;
            avgUps = 0;

            statsCount++;
            if (statsCount < NUM_FPS) {
                avgFps = totalFps / statsCount;
                avgUps = totalUps / statsCount;
            } else {
                avgFps = totalFps / NUM_FPS;
                avgUps = totalUps / NUM_FPS;
            }

/*            System.err.println("stats Interval: " + statsInterval + "ms");
            System.err.println("Real Elapsed: " + realElapsed + "ms");
            System.err.println("Timing Error:" + timingError + "% ");
            System.err.println("currFps: " + currFps + "fps");
            System.err.println("currUps: " + currUps + "ups");
            System.err.println("avgfps: " + avgFps);
            System.err.println("avgups: " + avgUps);*/

            lastStatsTime = Util.getMillis();
            statsInterval = 0;
        }
    }
}
