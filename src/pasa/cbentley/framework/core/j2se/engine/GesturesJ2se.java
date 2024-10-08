/*
 * (c) 2018-2020 Charles-Philip Bentley
 * This code is licensed under MIT license (see LICENSE.txt for details)
 */
package pasa.cbentley.framework.core.j2se.engine;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IDLog;
import pasa.cbentley.framework.core.j2se.ctx.CoreFrameworkJ2seCtx;
import pasa.cbentley.framework.core.ui.src4.ctx.ToStringStaticCoreUi;
import pasa.cbentley.framework.core.ui.src4.interfaces.IHostGestures;
import pasa.cbentley.framework.core.ui.src4.interfaces.ITechSenses;
import pasa.cbentley.framework.core.ui.src4.tech.ITechGestures;

public class GesturesJ2se implements IHostGestures, ITechGestures {

   protected final CoreFrameworkJ2seCtx d;

   public GesturesJ2se(CoreFrameworkJ2seCtx d) {
      this.d = d;
   }

   @Override
   public void enableGesture(int flag) {
      //#debug
      d.toDLog().pEvent("" + ToStringStaticCoreUi.toStringGestureType(flag), null, GesturesJ2se.class, "enableGesture");
      if (flag == ITechSenses.GESTURE_TYPE_05_SHAKE) {

      } else if (flag == ITechSenses.GESTURE_TYPE_07_MOVE) {

      }
   }

   public boolean isGestureSupported(int flag) {
      // TODO Auto-generated method stub
      return false;
   }

   @Override
   public void disableGesture(int flag) {
      //#debug
      d.toDLog().pEvent("" + ToStringStaticCoreUi.toStringGestureType(flag), null, GesturesJ2se.class, "disableGesture");

   }

   //#mdebug
   public IDLog toDLog() {
      return toStringGetUCtx().toDLog();
   }

   public String toString() {
      return Dctx.toString(this);
   }

   public void toString(Dctx dc) {
      dc.root(this, "J2SEGestures");
      toStringPrivate(dc);
   }

   public String toString1Line() {
      return Dctx.toString1Line(this);
   }

   private void toStringPrivate(Dctx dc) {

   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "J2SEGestures");
      toStringPrivate(dc);
   }

   public UCtx toStringGetUCtx() {
      return d.getUC();
   }

   //#enddebug

}
