
package edu.orhs.wildbots.subsystems;

import edu.orhs.wildbots.RobotMap;
import edu.orhs.wildbots.Averager;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Shooter extends Subsystem implements PIDOutput, PIDSource {
    
    Encoder encoder;
    SpeedController victor = RobotMap.shooterVictor;
    public static PIDController PID;
    
    //PIDConstants
    /*
     * P: 10
     * I: 3
     * D: 0.5
     * F: 0.01
     */
    private static double s_P = 10.000;
    private static double s_I = 3.000;
    private static double s_D = 0.5;
    private static double s_F = 0.01;
    private double setPoint = 0;
    
    //Constant setPoints
    private static final double pSet = 36.5;
    private static final double fcSet = 48.3;
    private static final double hSet = 51.000;
    private Mode mode;
    
    
    
    Averager averager = new Averager(20);
    
    private final double PIDScaler = 70;
    
    public Shooter(){
        encoder = RobotMap.shooterEncoder;
        PID = new PIDController(s_P, s_I, s_D, s_F, this, this, 0.005);
        PID.setOutputRange(-100, 100);
        PID.setAbsoluteTolerance(0.05);
        PID.enable();
        SmartDashboard.putBoolean("Manual", false);
        SmartDashboard.putBoolean("Pyramid", true);
        SmartDashboard.putBoolean("Full Court", false);
        SmartDashboard.putBoolean("Hanging", false);
        SmartDashboard.putBoolean("shooterOn", true);
        
    }
    
    public void pidWrite(double output){
        victor.set(((setPoint+6.007)/83.915)+output/PIDScaler);
        averager.addSample(encoder.getRate());
        if(victor.get() < 0){
            victor.set(0);
        }
    }
    public double pidGet(){
        return Math.abs(encoder.getRate());
    }
   
    public void initDefaultCommand() {
        
    }
    public void setSpeed(double speed){
        averager.addSample(encoder.getRate());
        victor.set(speed);
    }
    
    public void setPID(double s){
        //preset stuff0
        if(SmartDashboard.getBoolean("shooterOn")){
        if(mode == Mode.MANUAL){
            setPoint = s*PIDScaler;
            SmartDashboard.putBoolean("Manual", true);
            SmartDashboard.putBoolean("Pyramid", false);
            SmartDashboard.putBoolean("Full Court", false);
            SmartDashboard.putBoolean("Hanging", false);
        }
        if(mode == Mode.PYRAMID){
            setPoint = pSet;
            SmartDashboard.putBoolean("Manual", false);
            SmartDashboard.putBoolean("Pyramid", true);
            SmartDashboard.putBoolean("Full Court", false);
            SmartDashboard.putBoolean("Hanging", false);
        }
        if(mode == Mode.FULL_COURT){
            setPoint = fcSet;
            SmartDashboard.putBoolean("Manual", false);
            SmartDashboard.putBoolean("Pyramid", false);
            SmartDashboard.putBoolean("Full Court", true);
            SmartDashboard.putBoolean("Hanging", false);
        }
        if(mode == Mode.HANGING){
            setPoint = hSet;
            SmartDashboard.putBoolean("Manual", false);
            SmartDashboard.putBoolean("Pyramid", false);
            SmartDashboard.putBoolean("Full Court", false);
            SmartDashboard.putBoolean("Hanging", true);
        }
        }else{
            setPoint = 0;
            if(mode == Mode.MANUAL){
                SmartDashboard.putBoolean("Manual", true);
                SmartDashboard.putBoolean("Pyramid", false);
                SmartDashboard.putBoolean("Full Court", false);
                SmartDashboard.putBoolean("Hanging", false);
            }
            if(mode == Mode.PYRAMID){
                SmartDashboard.putBoolean("Manual", false);
                SmartDashboard.putBoolean("Pyramid", true);
                SmartDashboard.putBoolean("Full Court", false);
                SmartDashboard.putBoolean("Hanging", false);
            }
            if(mode == Mode.FULL_COURT){
                SmartDashboard.putBoolean("Manual", false);
                SmartDashboard.putBoolean("Pyramid", false);
                SmartDashboard.putBoolean("Full Court", true);
                SmartDashboard.putBoolean("Hanging", false);
            }
            if(mode == Mode.HANGING){
                SmartDashboard.putBoolean("Manual", false);
                SmartDashboard.putBoolean("Pyramid", false);
                SmartDashboard.putBoolean("Full Court", false);
                SmartDashboard.putBoolean("Hanging", true);
            }
        }
        PID.setSetpoint(setPoint);
        
    }
    public double getRate(){
        SmartDashboard.putData("shooter PIDController", PID);
        SmartDashboard.putNumber("Duty Cycle", victor.get());
        return averager.getAverage();
    }
    public void enablePID(){
        PID.enable();
    }
    public void disablePID(){
        PID.disable();
    }
    public void setAbsoluteTolerance(double t){
        PID.setAbsoluteTolerance(t);
    }
    public PIDController getPID(){
        return PID;
    }
    public void setMode(Mode m){
        mode = m;
    }
    public static class Mode {
        private int mode;
        public Mode(int m){
            mode = m;
        }
        private static final int iMANUAL = 0;
        private static final int iPYRAMID = 1;
        private static final int iFULL_COURT = 2;
        private static final int iHANGING = 3;
        public static final Mode MANUAL = new Mode(iMANUAL);
        public static final Mode PYRAMID = new Mode(iPYRAMID);
        public static final Mode FULL_COURT = new Mode(iFULL_COURT);
        public static final Mode HANGING = new Mode(iHANGING);
        
    }
}

