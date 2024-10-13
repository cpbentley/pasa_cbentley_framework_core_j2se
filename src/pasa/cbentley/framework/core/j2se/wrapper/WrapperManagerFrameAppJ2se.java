package pasa.cbentley.framework.core.j2se.wrapper;

import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.framework.core.j2se.ctx.CoreFrameworkJ2seCtx;
import pasa.cbentley.framework.core.ui.src4.interfaces.IWrapperManager;
import pasa.cbentley.framework.core.ui.src4.wrapper.WrapperManagerAbstract;

public abstract class WrapperManagerFrameAppJ2se extends WrapperManagerAbstract implements IWrapperManager {

   public WrapperManagerFrameAppJ2se(CoreFrameworkJ2seCtx cfc) {
      super(cfc.getCUC());
   }

   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, WrapperManagerFrameAppJ2se.class, 28);
      toStringPrivate(dc);
      super.toString(dc.sup());
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, WrapperManagerFrameAppJ2se.class, 28);
      toStringPrivate(dc);
      super.toString1Line(dc.sup1Line());
   }

   private void toStringPrivate(Dctx dc) {

   }
   //#enddebug

}
