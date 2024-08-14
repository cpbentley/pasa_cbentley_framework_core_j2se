package pasa.cbentley.framework.core.j2se.engine;

import pasa.cbentley.core.src4.interfaces.IHostData;
import pasa.cbentley.framework.core.draw.j2se.engine.HostDataDrawJ2se;
import pasa.cbentley.framework.core.framework.src4.ctx.CoreFrameworkCtx;
import pasa.cbentley.framework.core.framework.src4.ctx.ObjectCFC;
import pasa.cbentley.framework.core.j2se.ctx.CoreFrameworkJ2seCtx;
import pasa.cbentley.framework.core.ui.j2se.engine.HostDataUiJ2se;

public class HostDataCoreJ2se extends ObjectCFC implements IHostData {

   private HostDataUiJ2se hostDataUI;

   public HostDataCoreJ2se(CoreFrameworkJ2seCtx cfc) {
      super(cfc);

      hostDataUI = cfc.getCoreUiJ2seCtx().getHostDataUIJ2se();
   }

   public float getHostDataFloat(int dataID) {
      switch (dataID) {

         default:
            break;
      }
      return hostDataUI.getHostDataFloat(dataID);
   }

   public int getHostDataInt(int dataID) {
      switch (dataID) {

         default:
            break;
      }
      return hostDataUI.getHostDataInt(dataID);
   }

   public Object getHostDataObject(int dataID) {
      switch (dataID) {

         default:
            break;
      }
      return hostDataUI.getHostDataObject(dataID);
   }

   public String getHostDataString(int dataID) {
      switch (dataID) {
         default:
            break;
      }
      return hostDataUI.getHostDataString(dataID);
   }
}
