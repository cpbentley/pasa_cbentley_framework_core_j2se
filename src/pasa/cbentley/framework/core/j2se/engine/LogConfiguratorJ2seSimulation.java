/*
 * (c) 2018-2020 Charles-Philip Bentley
 * This code is licensed under MIT license (see LICENSE.txt for details)
 */
package pasa.cbentley.framework.core.j2se.engine;

import pasa.cbentley.core.src4.logging.IDLogConfig;
import pasa.cbentley.core.src4.logging.ILogConfigurator;
import pasa.cbentley.core.src4.logging.ITechLvl;

public class LogConfiguratorJ2seSimulation implements ILogConfigurator {

   public LogConfiguratorJ2seSimulation() {
   }

   public void apply(IDLogConfig log) {
      log.setLevelGlobal(ITechLvl.LVL_03_FINEST);
      
      
      log.setFlagMaster(MASTER_FLAG_07_THREAD_DATA, true);
      log.setFlagMaster(MASTER_FLAG_10_OWNER_NAME_UC, true);
      
      log.setFlagMaster(MASTER_FLAG_12_LEVEL, true);
      
      log.setFlagTag(FLAG_01_PRINT_ALWAYS, true);
      log.setFlagTag(FLAG_02_PRINT_NULL, true);
      
      log.setFlagTag(FLAG_27_PRINT_SIMULATION, true);
   }

}
