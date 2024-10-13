/*
 * (c) 2018-2020 Charles-Philip Bentley
 * This code is licensed under MIT license (see LICENSE.txt for details)
 */
package pasa.cbentley.framework.core.j2se.ctx;

import pasa.cbentley.byteobjects.src4.ctx.ConfigAbstractBO;
import pasa.cbentley.core.j2se.ctx.J2seCoreCtx;
import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.framework.core.data.src4.ctx.IConfigCoreData;
import pasa.cbentley.framework.core.data.src5.ctx.ConfigCoreData5Default;
import pasa.cbentley.framework.core.data.src5.ctx.IConfigCoreData5;
import pasa.cbentley.framework.core.io.src4.ctx.IConfigCoreIO;
import pasa.cbentley.framework.core.io.src5.ctx.ConfigCoreIO5Def;

public abstract class ConfigCoreFrameworkJ2SE extends ConfigAbstractBO implements IConfigCoreFrameworkJ2se {

   private ConfigCoreIO5Def           configIO;

   private ConfigCoreData5Default configData;

   public ConfigCoreFrameworkJ2SE(UCtx uc) {
      super(uc);
      configIO = new ConfigCoreIO5Def(uc);
      configData = new ConfigCoreData5Default(uc);
   }


   public boolean isUsingScreenConfig() {
      return true;
   }

   public IConfigCoreData getConfigData() {
      return configData;
   }

   public IConfigCoreIO getConfigIO() {
      return configIO;
   }

   public IConfigCoreData5 getConfigData5() {
      return configData;
   }

   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, ConfigCoreFrameworkJ2SE.class, "@line5");
      toStringPrivate(dc);
      super.toString(dc.sup());
   }

   private void toStringPrivate(Dctx dc) {
      
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, ConfigCoreFrameworkJ2SE.class);
      toStringPrivate(dc);
      super.toString1Line(dc.sup1Line());
   }

   //#enddebug
   

}
