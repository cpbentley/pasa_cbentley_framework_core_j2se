package pasa.cbentley.framework.core.j2se.engine;

import java.io.IOException;
import java.io.InputStream;

import pasa.cbentley.core.src4.interfaces.IHost;
import pasa.cbentley.core.src4.interfaces.ITimeCtrl;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.framework.core.framework.src4.ctx.ObjectCFC;
import pasa.cbentley.framework.core.j2se.ctx.CoreFrameworkJ2seCtx;
import pasa.cbentley.framework.core.ui.j2se.engine.TimeControlJ2se;

public abstract class HostCoreJ2se extends ObjectCFC implements IHost {

   private TimeControlJ2se      timeCtrl;


   public HostCoreJ2se(CoreFrameworkJ2seCtx cfc) {
      super(cfc);

      timeCtrl = new TimeControlJ2se();
   }

   /**
    * 
    */
   public InputStream getResourceAsStream(String file) throws IOException {
      return this.getClass().getResourceAsStream(file);
   }

   public ITimeCtrl getTimeCtrl() {
      return timeCtrl;
   }
   
   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, HostCoreJ2se.class, 40);
      toStringPrivate(dc);
      super.toString(dc.sup());
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, HostCoreJ2se.class, 40);
      toStringPrivate(dc);
      super.toString1Line(dc.sup1Line());
   }

   private void toStringPrivate(Dctx dc) {
      
   }
   //#enddebug
   


}
