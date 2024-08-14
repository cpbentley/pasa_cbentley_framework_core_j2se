/*
 * (c) 2018-2020 Charles-Philip Bentley
 * This code is licensed under MIT license (see LICENSE.txt for details)
 */
package pasa.cbentley.framework.core.j2se.ctx;

import pasa.cbentley.byteobjects.src4.core.ByteObject;
import pasa.cbentley.byteobjects.src4.ctx.IConfigBO;
import pasa.cbentley.core.src4.interfaces.ITimeCtrl;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IStringable;
import pasa.cbentley.core.src5.ctx.C5Ctx;
import pasa.cbentley.framework.core.data.src5.ctx.CoreData5Ctx;
import pasa.cbentley.framework.core.framework.src4.ctx.CoreFrameworkCtx;
import pasa.cbentley.framework.core.framework.src4.interfaces.ILauncherHost;
import pasa.cbentley.framework.core.io.src5.ctx.CoreIO5Ctx;
import pasa.cbentley.framework.core.ui.j2se.ctx.CoreUiJ2seCtx;
import pasa.cbentley.framework.core.ui.j2se.engine.TimeControlJ2se;

public abstract class CoreFrameworkJ2seCtx extends CoreFrameworkCtx implements IStringable {

   protected final CoreUiJ2seCtx cucj;

   protected final CoreData5Ctx  dac;

   protected final CoreIO5Ctx    ioc5;

   public CoreFrameworkJ2seCtx(IConfigCoreFrameworkJ2SE config, CoreUiJ2seCtx cuc, CoreData5Ctx dac, CoreIO5Ctx ioc, ILauncherHost launcher) {
      super(config, cuc, dac, ioc, launcher);
      this.dac = dac;
      this.ioc5 = ioc;
      this.cucj = cuc;
   }

   public CoreData5Ctx getCoreData5Ctx() {
      return dac;
   }


   public C5Ctx getC5() {
      return ioc5.getC5();
   }
   
   public CoreIO5Ctx getCoreIO5Ctx() {
      return ioc5;
   }

   public CoreUiJ2seCtx getCoreUiJ2seCtx() {
      return cucj;
   }
   

   public String[] getStackTrace(Throwable e) {
      if (e == null) {
         e = new Throwable();
      }
      StackTraceElement[] st = e.getStackTrace();
      String[] str = new String[st.length];
      for (int i = 0; i < st.length; i++) {
         str[i] = st[i].toString();
      }
      return str;
   }


   protected void matchConfig(IConfigBO config, ByteObject settings) {
      super.matchConfig(config, settings);
   }

   
   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, CoreFrameworkJ2seCtx.class,78);
      toStringPrivate(dc);
      super.toString(dc.sup());
   }

   private void toStringPrivate(Dctx dc) {
      
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, CoreFrameworkJ2seCtx.class);
      toStringPrivate(dc);
      super.toString1Line(dc.sup1Line());
   }

   //#enddebug
   

}
