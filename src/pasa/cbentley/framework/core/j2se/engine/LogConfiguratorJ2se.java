/*
 * (c) 2018-2020 Charles-Philip Bentley
 * This code is licensed under MIT license (see LICENSE.txt for details)
 */
package pasa.cbentley.framework.core.j2se.engine;

import pasa.cbentley.core.src4.logging.IDLogConfig;
import pasa.cbentley.core.src4.logging.ILogConfigurator;
import pasa.cbentley.core.src4.logging.ITechDLogConfig;
import pasa.cbentley.core.src4.logging.ITechLvl;

public class LogConfiguratorJ2se implements ILogConfigurator {

   public LogConfiguratorJ2se() {
   }

   public void apply(IDLogConfig log) {
      log.setLevelGlobal(ITechLvl.LVL_03_FINEST);
      log.setFlagMaster(ITechDLogConfig.MASTER_FLAG_02_OPEN_ALL_PRINT, true);
      
      //log.setFlagTag(FLAG_09_PRINT_FLOW, true);
   }

}
