package pasa.cbentley.framework.core.j2se.engine;

import pasa.cbentley.core.src4.logging.IDLogConfig;
import pasa.cbentley.core.src4.logging.ILogConfigurator;
import pasa.cbentley.core.src4.logging.ITechLvl;

public class LogConfiguratorJ2seBridgeCreateStator  implements ILogConfigurator {

   public LogConfiguratorJ2seBridgeCreateStator() {
   }

   public void apply(IDLogConfig log) {
      
      
      log.setLevelGlobal(ITechLvl.LVL_03_FINEST);

      log.setFlagMaster(MASTER_FLAG_11_IGNORES_BIGS, true);

      log.setFlagTag(FLAG_01_PRINT_ALWAYS, true);
      log.setFlagTag(FLAG_02_PRINT_NULL, true);
      
      log.setFlagTag(FLAG_25_PRINT_CREATE, true);
      log.setFlagTag(FLAG_19_PRINT_BRIDGE, true);
      log.setFlagTag(FLAG_04_PRINT_STATOR, true);

   }

}
