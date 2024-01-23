/*
 * (c) 2018-2020 Charles-Philip Bentley
 * This code is licensed under MIT license (see LICENSE.txt for details)
 */
package pasa.cbentley.framework.core.j2se.engine;

import pasa.cbentley.core.src4.logging.IDLogConfig;
import pasa.cbentley.core.src4.logging.ILogConfigurator;
import pasa.cbentley.core.src4.logging.ITechLvl;

public class LogConfiguratorJ2SE2FlowInitBridge implements ILogConfigurator {

   public LogConfiguratorJ2SE2FlowInitBridge() {
   }

   public void apply(IDLogConfig log) {
      log.setLevelGlobal(ITechLvl.LVL_03_FINEST);
      
      
      log.setFlagTag(FLAG_09_PRINT_FLOW, true);
      log.setFlagTag(FLAG_20_PRINT_INIT, true);
      log.setFlagTag(FLAG_19_PRINT_BRIDGE, true);
      
      
   }

}
