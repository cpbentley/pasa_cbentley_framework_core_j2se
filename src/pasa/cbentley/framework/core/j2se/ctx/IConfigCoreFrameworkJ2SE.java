/*
 * (c) 2018-2020 Charles-Philip Bentley
 * This code is licensed under MIT license (see LICENSE.txt for details)
 */
package pasa.cbentley.framework.core.j2se.ctx;

import pasa.cbentley.framework.core.src4.ctx.IConfigCoreFramework;

/**
 * 
 * @author Charles Bentley
 *
 */
public interface IConfigCoreFrameworkJ2SE extends IConfigCoreFramework {

   
   /**
    * When true, save frames config state
    * @return
    */
   public boolean isUsingScreenConfig();
}
