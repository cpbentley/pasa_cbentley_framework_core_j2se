package pasa.cbentley.framework.core.j2se.engine;

import pasa.cbentley.core.src4.interfaces.IHostService;
import pasa.cbentley.framework.core.framework.src4.ctx.ObjectCFC;
import pasa.cbentley.framework.core.j2se.ctx.CoreFrameworkJ2seCtx;
import pasa.cbentley.framework.core.ui.j2se.engine.HostServiceUiJ2se;
import pasa.cbentley.framework.core.ui.src4.tech.ITechHostServiceDrawUI;

/**
 * 
 *
 */
public class HostServiceCoreJ2se extends ObjectCFC implements IHostService, ITechHostServiceDrawUI {

   private HostServiceUiJ2se hostServiceUiJ2se;

   public HostServiceCoreJ2se(CoreFrameworkJ2seCtx cfc) {
      super(cfc);

      hostServiceUiJ2se = cfc.getCoreUiJ2seCtx().getHostServiceUiJ2se();
   }

   public boolean isHostServiceActive(int serviceID) {
      switch (serviceID) {
         default:
            return hostServiceUiJ2se.isHostServiceActive(serviceID);
      }
   }

   public boolean isHostServiceSupported(int serviceID) {
      switch (serviceID) {
         default:
            return hostServiceUiJ2se.isHostServiceSupported(serviceID);
      }
   }

   public boolean setHostServiceActive(int serviceID, boolean isActive) {
      switch (serviceID) {
         default:
            return hostServiceUiJ2se.setHostServiceActive(serviceID, isActive);
      }
   }

   public boolean setHostServiceOff(int serviceID) {
      return setHostServiceActive(serviceID, false);
   }

   public boolean setHostServiceOn(int serviceID) {
      return setHostServiceActive(serviceID, true);
   }

}
