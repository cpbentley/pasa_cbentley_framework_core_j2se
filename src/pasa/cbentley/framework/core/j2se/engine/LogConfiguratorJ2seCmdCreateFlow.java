package pasa.cbentley.framework.core.j2se.engine;

import pasa.cbentley.core.src4.logging.IDLogConfig;
import pasa.cbentley.core.src4.logging.ILogConfigurator;
import pasa.cbentley.core.src4.logging.ITechLvl;

public class LogConfiguratorJ2seCmdCreateFlow  implements ILogConfigurator {

   public LogConfiguratorJ2seCmdCreateFlow() {
   }

   public void apply(IDLogConfig log) {
      
      
      log.setLevelGlobal(ITechLvl.LVL_03_FINEST);

      log.setFlagMaster(MASTER_FLAG_11_IGNORES_BIGS, true);
      log.setFlagMaster(MASTER_FLAG_12_LEVEL, true);

      log.setFlagTag(FLAG_01_PRINT_ALWAYS, true);
      log.setFlagTag(FLAG_02_PRINT_NULL, true);
      
      log.setFlagTag(FLAG_25_PRINT_CREATE, true);
      log.setFlagTag(FLAG_11_PRINT_COMMANDS, true);
      log.setFlagTag(FLAG_09_PRINT_FLOW, true);

   }

}
