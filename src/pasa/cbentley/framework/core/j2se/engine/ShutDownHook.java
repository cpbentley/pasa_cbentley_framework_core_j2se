/*
 * (c) 2018-2020 Charles-Philip Bentley
 * This code is licensed under MIT license (see LICENSE.txt for details)
 */
package pasa.cbentley.framework.core.j2se.engine;

public class ShutDownHook extends Thread implements Runnable {

   protected final CoordinatorJ2SE coord;

   public ShutDownHook(CoordinatorJ2SE coord) {
      this.coord = coord;
   }

   public void run() {
      //#debug
      coord.toDLog().pFlow("", null, ShutDownHook.class, "run");
   }
   
   
}
