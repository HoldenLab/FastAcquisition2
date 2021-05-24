/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import ij.IJ;
import ij.ImagePlus;
import ij.gui.NewImage;
import mmcorej.CMMCore;
import org.micromanager.internal.MMStudio;

/**
 *
 * @author Holden
 * Released under the BSD license, see LICENSE.txt
 */
public class LiveViewThread  implements Runnable{
   //make a new image
   //every 50ms see if we should update the image
   private static final int WAIT_TIME = 50;
   private static final String IM_TITLE="FastAcq Live View" ;
   private double imSatPc_ = 2;
   
   private CMMCore core_;
   private MMStudio gui_;
   private boolean stopping_;
   private AcquisitionThread acqThread_;


   public LiveViewThread(MMStudio gui, AcquisitionThread acqThread){
      gui_ = gui;
      core_ = gui_.getCMMCore();
      acqThread_ = acqThread;
   }
   
   @Override
   public void run() {
      try{
         // assumes 16 bit images
         int w = (int) core_.getImageWidth();
         int h = (int) core_.getImageHeight();
         long depth = core_.getBytesPerPixel();  
         ImagePlus liveIm = null;
         if (depth ==1){
            liveIm = NewImage.createByteImage("Fast Acquisition", w, h, 1,NewImage.FILL_BLACK);
         } else if (depth==2){
            liveIm = NewImage.createShortImage("Fast Acquisition", w, h, 1,NewImage.FILL_BLACK);
         } else {
            gui_.logs().logError("Error: Unsupported camera BitDepth");
            gui_.logs().showError("Error: Unsupported camera BitDepth");
         }

         liveIm.show();

         stopping_ = false;
         while (!stopping_){
            if (core_.isSequenceRunning() ){
               //show the next image
               liveIm.getProcessor().setPixels(acqThread_.acqStack.getPixels(acqThread_.curFrame_));
               IJ.run(liveIm, "Enhance Contrast", "saturated="+imSatPc_);
               liveIm.setTitle(IM_TITLE+" "+(acqThread_.getCurFrame_()+1)+"/"+acqThread_.getnFrame_());
               liveIm.updateAndDraw();
               liveIm.show();
            }
            Thread.sleep(WAIT_TIME);
         }
      } catch (Exception ex) {
         gui_.logs().logError(ex);
      }
      
   }

   void stopLiveView() {
      stopping_ = true;
   }

   /**
    * @return the imSatPc_
    */
   public double getImSatPc_() {
      return imSatPc_;
   }

   /**
    * @param imSatPc_ the imSatPc_ to set
    */
   public void setImSatPc_(double imSatPc_) {
      this.imSatPc_ = imSatPc_;
   }
   
}
