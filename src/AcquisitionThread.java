/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import ij.ImagePlus;
import ij.ImageStack;
import ij.gui.NewImage;
import ij.io.FileSaver;
import mmcorej.CMMCore;
import org.micromanager.internal.MMStudio;
/**
 *
 * @author Holden
 * Released under the BSD license, see LICENSE.txt
 */
public class AcquisitionThread implements Runnable{

   private CMMCore core_;
   private MMStudio gui_;

   private String absoluteImPath_;
   private int nFrame_;
   private double imSatPc_;
   private boolean stopping_ = false;
   private boolean showLiveView_ = true;

   private LiveViewThread liveViewThread_;
   private Thread lt1;
   public int curFrame_ = 0;
   ImageStack acqStack = null;
   
   AcquisitionThread(MMStudio gui, String absoluteImPath, int nFrame, boolean showLiveView,double imSatPc){
      gui_ = gui;
      core_ = gui_.getCMMCore();

      absoluteImPath_ = absoluteImPath;
      nFrame_ = nFrame;
      showLiveView_ = showLiveView;
      imSatPc_ = imSatPc;
   }

   @Override
   public void run() {
      try {
         //----------------------------------------
         //acquire and save the image without using the mda
         // assumes 16 bit images
         int w = (int) core_.getImageWidth();
         int h = (int) core_.getImageHeight();
         long depth = core_.getBytesPerPixel();  
  
         ImagePlus acqIm = null;
         if (depth ==1){
            acqIm = NewImage.createByteImage("Fast Acquisition", w, h, nFrame_,NewImage.FILL_BLACK);
         } else if (depth==2){
            acqIm = NewImage.createShortImage("Fast Acquisition", w, h, nFrame_,NewImage.FILL_BLACK);
         } else {
            gui_.logs().logError("Error: Unsupported camera BitDepth");
            gui_.logs().showError("Error: Unsupported camera BitDepth");
         }

         acqStack = acqIm.getStack();
         
         core_.stopSequenceAcquisition();//stop previous acqs or live mode.
         if (showLiveView_){
            startLiveView();
         }
         curFrame_ = 0;
         core_.initializeCircularBuffer();
         core_.startSequenceAcquisition( nFrame_, 0.0, false) ;
         stopping_ = false;
         while (!stopping_ && (core_.isSequenceRunning() || core_.getRemainingImageCount() > 0) ){
           if (core_.getRemainingImageCount() > 0) {
             acqStack.setPixels(core_.popNextImage(),curFrame_+1);//stupid imagej zero indexing 
             curFrame_++;
           }
         }
         
         // If curFrame_ < nFrame_ (ie if acquisition is aborted), delete the extra frames
         for (int ii= nFrame_-1; ii >  curFrame_; ii--){
            acqStack.deleteSlice(ii+1);//stupid imagej zero indexing 
         }

         liveViewThread_.stopLiveView();

         acqIm.setStack(acqStack);
         FileSaver fs = new FileSaver(acqIm);
         fs.saveAsTiffStack(absoluteImPath_);

         //deallocoate the images (just in case imagej would otherwise retain them)
         acqStack = null;
         acqIm = null;

      } catch (Exception ex) {
         gui_.logs().logError(ex);
      }

   }

   public void abortAcquisition(){
      stopping_ = true;
      if (showLiveView_){
         liveViewThread_.stopLiveView();
      }
      try {
         core_.stopSequenceAcquisition();
      } catch (Exception ex) {
         gui_.logs().logError(ex);
      }
      gui_.logs().logMessage("Acquisition aborted.");
   }

   /**
    * @return the absoluteImPath_
    */
   public String getAbsoluteImPath_() {
      return absoluteImPath_;
   }

   /**
    * @param absoluteImPath_ the absoluteImPath_ to set
    */
   public void setAbsoluteImPath_(String absoluteImPath_) {
      this.absoluteImPath_ = absoluteImPath_;
   }

   /**
    * @return the nFrame_
    */
   public int getnFrame_() {
      return nFrame_;
   }

   /**
    * @param nFrame_ the nFrame_ to set
    */
   public void setnFrame_(int nFrame_) {
      this.nFrame_ = nFrame_;
   }

   /**
    * @return the showLiveView_
    */
   public boolean isShowLiveView_() {
      return showLiveView_;
   }

   /**
    * @param showLiveView_ the showLiveView_ to set
    */
   public void setShowLiveView_(boolean showLiveView_) {
      this.showLiveView_ = showLiveView_;
   }

   private void startLiveView() {
     if (liveViewThread_== null){
         liveViewThread_= new LiveViewThread(gui_, this);
         liveViewThread_.setImSatPc_(imSatPc_);
         lt1 = new Thread(liveViewThread_);
         lt1.start();
         gui_.logs().logMessage("Started new LiveViewThread.");
      } else if (lt1 == null || !lt1.isAlive()){
         liveViewThread_.setImSatPc_(imSatPc_);
         lt1 = new Thread(liveViewThread_);
         lt1.start();
         gui_.logs().logMessage("Reused existing LiveViewThread.");
      } else {
         gui_.logs().showMessage("Warning: Cannot start new live view while a previous acquistion is still running");
      } 
   }

   /**
    * @return the curFrame_
    */
   public int getCurFrame_() {
      return curFrame_;
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
