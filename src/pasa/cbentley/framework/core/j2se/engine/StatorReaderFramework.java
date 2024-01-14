/*
 * (c) 2018-2020 Charles-Philip Bentley
 * This code is licensed under MIT license (see LICENSE.txt for details)
 */
package pasa.cbentley.framework.core.j2se.engine;

import pasa.cbentley.byteobjects.src4.core.ByteObject;
import pasa.cbentley.byteobjects.src4.ctx.BOCtx;
import pasa.cbentley.byteobjects.src4.stator.StatorReaderBO;

public class StatorReaderFramework extends StatorReaderBO {

   public StatorReaderFramework(BOCtx boc) {
      super(boc);
   }

   /**
    * 
    * Model state is orthognal/independant to View State
    * 
    * Returns this if the key is not linked.
    * 
    * @param key
    * @return
    */
   public StatorReaderFramework getKeyedStatorView(ByteObject key) {
      // TODO Auto-generated method stub
      return null;
   }

}
