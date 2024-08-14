/*
 * (c) 2018-2020 Charles-Philip Bentley
 * This code is licensed under MIT license (see LICENSE.txt for details)
 */
package pasa.cbentley.framework.core.j2se.engine;

import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.framework.core.framework.src4.engine.CoordinatorAbstract;
import pasa.cbentley.framework.core.framework.src4.interfaces.ILauncherHost;
import pasa.cbentley.framework.core.j2se.ctx.CoreFrameworkJ2seCtx;

public abstract class CoordinatorJ2se extends CoordinatorAbstract {

   protected ShutDownHook shutDownHook;

   protected CoordinatorJ2se(CoreFrameworkJ2seCtx cac, ILauncherHost launcherHost) {
      super(cac, launcherHost);

      shutDownHook = new ShutDownHook(this);
      Runtime.getRuntime().addShutdownHook(shutDownHook);
   }
   
   public ShutDownHook getShutDownHook() {
      return shutDownHook;
   }

   protected void subExit() {
      //get the settings of the app
      if (isLoaded()) {
      }
      subExitJ2SE();
   }

   protected void subResume() {
      subResumeJ2SE();
   }

   protected void subPause() {
      subPauseJ2SE();
   }

   protected abstract void subExitJ2SE();

   protected abstract void subPauseJ2SE();

   protected abstract void subResumeJ2SE();

   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, CoordinatorJ2se.class, 41);
      super.toString(dc.sup());
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, CoordinatorJ2se.class);
      super.toString1Line(dc.sup1Line());
   }
   //#enddebug
}
