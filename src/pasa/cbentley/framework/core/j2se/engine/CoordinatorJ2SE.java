/*
 * (c) 2018-2020 Charles-Philip Bentley
 * This code is licensed under MIT license (see LICENSE.txt for details)
 */
package pasa.cbentley.framework.core.j2se.engine;

import pasa.cbentley.byteobjects.src4.core.ByteObject;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.framework.core.j2se.ctx.CoreFrameworkJ2seCtx;
import pasa.cbentley.framework.core.src4.app.AppCtx;
import pasa.cbentley.framework.core.src4.engine.CoordinatorAbstract;
import pasa.cbentley.framework.core.src4.interfaces.ILauncherHost;

public abstract class CoordinatorJ2SE extends CoordinatorAbstract {


   protected CoordinatorJ2SE(CoreFrameworkJ2seCtx cac, ILauncherHost launcherHost) {
      super(cac, launcherHost);
      ShutDownHook shutDownHook = new ShutDownHook(this);
      Runtime.getRuntime().addShutdownHook(shutDownHook);
   }

   protected void subExit() {
      //get the settings of the app
      if(isLoaded()) {
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
      dc.root(this, CoordinatorJ2SE.class,41);
      super.toString(dc.sup());
   }


   public void toString1Line(Dctx dc) {
      dc.root1Line(this, CoordinatorJ2SE.class);
      super.toString1Line(dc.sup1Line());
   }
   //#enddebug
}
