package pasa.cbentley.framework.core.j2se.engine;

import pasa.cbentley.core.src4.interfaces.IHostFeature;
import pasa.cbentley.framework.core.framework.src4.ctx.ObjectCFC;
import pasa.cbentley.framework.core.j2se.ctx.CoreFrameworkJ2seCtx;
import pasa.cbentley.framework.core.ui.j2se.engine.HostFeatureUiJ2se;
import pasa.cbentley.framework.core.ui.src4.tech.ITechHostFeatureDrawUI;

/**
 * @author Charles Bentley
 *
 */
public class HostFeatureCoreJ2se extends ObjectCFC implements IHostFeature, ITechHostFeatureDrawUI {

   private HostFeatureUiJ2se hostFeatureUiJ2se;

   public HostFeatureCoreJ2se(CoreFrameworkJ2seCtx cfc) {
      super(cfc);

      hostFeatureUiJ2se = cfc.getCoreUiJ2seCtx().getHostFeatureUiJ2se();
   }

   public boolean setHostFeatureEnabled(int featureID, boolean b) {
      switch (featureID) {
         default:
            return hostFeatureUiJ2se.setHostFeatureEnabled(featureID, b);
      }
   }

   public boolean setHostFeatureOn(int featureID) {
      return this.setHostFeatureEnabled(featureID, true);
   }

   public boolean setHostFeatureOff(int featureID) {
      return this.setHostFeatureEnabled(featureID, false);
   }

   public boolean setHostFeatureEnabledFactory(int featureID, boolean b) {
      switch (featureID) {
         default:
            return hostFeatureUiJ2se.setHostFeatureEnabledFactory(featureID, b);
      }
   }

   public boolean setHostFeatureFactoryOn(int featureID) {
      return setHostFeatureEnabledFactory(featureID, true);
   }

   public boolean setHostFeatureFactoryOff(int featureID) {
      return setHostFeatureEnabledFactory(featureID, false);
   }

   public boolean isHostFeatureSupported(int featureID) {
      switch (featureID) {
         default:
            return hostFeatureUiJ2se.isHostFeatureSupported(featureID);
      }
   }

   public boolean isHostFeatureEnabled(int featureID) {
      switch (featureID) {
         default:
            return hostFeatureUiJ2se.isHostFeatureEnabled(featureID);
      }
   }

   public boolean isHostFeatureFactoryEnabled(int featureID) {
      switch (featureID) {
         default:
            return hostFeatureUiJ2se.isHostFeatureFactoryEnabled(featureID);
      }
   }

}
