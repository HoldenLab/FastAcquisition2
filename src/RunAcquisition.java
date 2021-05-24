/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import mmcorej.CMMCore;
import org.micromanager.internal.MMStudio;

/**
 *
 * @author Seamus Holden, EPFL, Switzerland
 * Released under the BSD license, see LICENSE.txt
 */
public class RunAcquisition {

   private String rootDirName_ = null;
   private String imNamePrefix_ = null;
   private String absoluteImPath_ = null;
   private int nFrame_;
   private double imSatPc_;
   private AcquisitionThread acqThread_ = null;
   Thread at1_ = null;


   CMMCore core_;
   MMStudio gui_;
   //Thread liveView_ = null;
   
   public  RunAcquisition(MMStudio gui){
      gui_ = gui;
      core_ = gui_.getCMMCore();

   }
   
   /**
    * @return the rootDirName_
    */
   public String getRootDirName_() {
      return rootDirName_;
   }

   /**
    * @param rootDirName_ the rootDirName_ to set
    */
   public void setRootDirName_(String rootDirName_) {
      this.rootDirName_ = rootDirName_;
   }

   /**
    * @return the imNamePrefix_
    */
   public String getImNamePrefix_() {
      return imNamePrefix_;
   }

   /**
    * @param imNamePrefix_ the imNamePrefix_ to set
    */
   public void setImNamePrefix_(String imNamePrefix_) {
      this.imNamePrefix_ = imNamePrefix_;
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

   void startAcquisition(String rootDirName, String imNamePrefix, int nFrames, boolean showLiveView, double imSatPc) {
      rootDirName_ = rootDirName;
      imNamePrefix_ = imNamePrefix;
      nFrame_ = nFrames;

      makeAbsolutePath();

      if (absoluteImPath_ !=null && nFrame_>0 && isEnoughRam()){
         if (acqThread_== null){
            acqThread_= new AcquisitionThread(gui_,absoluteImPath_,nFrame_,showLiveView,imSatPc);
            at1_ = new Thread(acqThread_);
            at1_.start();
            gui_.logs().logMessage("Started new AcquisitionThread.");            
         } else if (at1_ == null || !at1_.isAlive()){
            acqThread_.setAbsoluteImPath_(absoluteImPath_);
            acqThread_.setnFrame_(nFrame_);
            acqThread_.setShowLiveView_(showLiveView);
            acqThread_.setImSatPc_(imSatPc);
            at1_ = new Thread(acqThread_);
            at1_.start();
            gui_.logs().logMessage("Reused existing AcquisitionThread.");
         } else {
            gui_.logs().showMessage("Warning: Cannot start new acqusition while a previous acquistion is still running");
         } 
      } else if (!isEnoughRam()) {
            gui_.logs().showMessage("Warning: Cannot start new acqusition  - not enough RAM to hold whole movie in memory");
      } else {
         gui_.logs().logError(new Exception("Error: absoluteImPath_ or nFrame_ not initialized"));
      }
   }

   void stopAcquisition() {
      acqThread_.abortAcquisition();
   }

   private boolean isEnoughRam() {
      //TODO: check if the acquisition is possible with the ram available
      return true;
   }

   private void makeAbsolutePath() {
      // see if rootDirName_/(imNamePrefix_)_1 etc already exists
      // if not, create the necessary directories, 
      // if so, create directory with the name _2, etc
      //
      // files created using this plugin are named .tif instead of .ome.tif
      // because they are not OME - no metadata!)
      
      String acqName = rootDirName_ + "/" + imNamePrefix_ + "_1";
      String acqFolder = FolderMaker.createAcqFolder(acqName);
      absoluteImPath_ = acqFolder + "/" + imNamePrefix_ + ".tif";
        
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
      acqThread_.setImSatPc_(imSatPc_);
      
   }

   /**
    * @return the acqThread_
    */
   public AcquisitionThread getAcqThread_() {
      return acqThread_;
   }

 
   
}
