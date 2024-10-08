/*
 * (c) 2018-2020 Charles-Philip Bentley
 * This code is licensed under MIT license (see LICENSE.txt for details)
 */
package pasa.cbentley.framework.core.j2se.engine;

import pasa.cbentley.core.src4.utils.DateUtils;

/**
 * Usefull when JUnit testing the framework.
 * 
 * Be careful as this Hook will run its own thread, potentially creating deadlocks
 * 
 * So it cannot access GUI or AWT locked 
 * @author Charles Bentley
 *
 */
public class ShutDownHook extends Thread implements Runnable {

   protected final CoordinatorJ2se coord;

   private Runnable                runExtra;

   public ShutDownHook(CoordinatorJ2se coord) {
      this.coord = coord;
   }

   public void addRunAfterAppExit(Runnable run) {
      this.runExtra = run;
   }

   public void run() {
      //#debug
      coord.toDLog().pFlow("", null, ShutDownHook.class, "run@23");

      if (runExtra != null) {
         runExtra.run();
      }

      long milliTime = coord.getCFC().getTimeCtrl().getNowClock();

      //#debug
      coord.toDLog().pFlow("Final Message Time " + DateUtils.getDslashMslashYslashHourslashMin(milliTime), null, ShutDownHook.class, "run@23");

   }

}
