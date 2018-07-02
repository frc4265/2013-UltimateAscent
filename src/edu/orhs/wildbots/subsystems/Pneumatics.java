package edu.orhs.wildbots.subsystems;

import edu.orhs.wildbots.RobotMap;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import java.util.Timer;
import java.util.TimerTask;

public class Pneumatics extends Subsystem {
    
    public static class Gear {
        private int value;
        private static final int HIGHi = 1;
        private static final int LOWi = 0;
        public Gear(int i){
            value = i;
        }
        public int getRawValue(){
            return value;
        }
        public static final Gear HIGH = new Gear(HIGHi);
        public static final Gear LOW = new Gear(LOWi);
    }
    private DoubleSolenoid.Value currentGear;
    public static class Value {
        public static final int SHOOTING = 0;
        public static final int LOADING = 1;
    }
    Compressor compressor = RobotMap.pneumaticsCompressor;
    DoubleSolenoid shooterKicker = RobotMap.pneumaticsShooterKicker;
    DoubleSolenoid shooterLatch = RobotMap.pneumaticsShooterLatch;
    DoubleSolenoid shifters = RobotMap.pneumaticsShifters;
    DoubleSolenoid angle = RobotMap.pneumaticsAngle;
    DoubleSolenoid climber = RobotMap.pneumaticsClimber;

    private boolean isShooting = false;
    
    public void initDefaultCommand() {

    }
    public void startCompressor(){
        compressor.start();
    }
    public void stopCompressor(){
        compressor.stop();
            
    }
    public void setCurrentGear(DoubleSolenoid.Value val){
        currentGear = val;
    }
    public boolean getSwitchValue(){
        return compressor.getPressureSwitchValue();
    }
    public void shoot(){
        if(!isShooting){
            isShooting = true;              
            
            //shooter sequence timings
            double time_delay_1 = 0.1;      //safety drop time
            double time_delay_2 = 0.150;    //kicker out time
            double time_delay_3 = 0.125;    //kicker in time
            
            Timer firstTask = new Timer();
            Timer secondTask = new Timer();
            Timer thirdTask = new Timer();
            shooterLatch.set(DoubleSolenoid.Value.kForward );
            firstTask.schedule(new TimerTask(){
                public void run(){
                   shooterKicker.set(DoubleSolenoid.Value.kForward);
                }
            }, (int)(time_delay_1*1000));
            secondTask.schedule(new TimerTask(){
                public void run(){
                    shooterKicker.set(DoubleSolenoid.Value.kReverse);
                }
            }, (int)((time_delay_1+time_delay_2)*1000));
            thirdTask.schedule(new TimerTask(){
                public void run(){
                    shooterLatch.set(DoubleSolenoid.Value.kReverse);
                    isShooting = false;
                }
            }, (int)((time_delay_1+time_delay_2+time_delay_3)*1000));
        }
    }
    public void changeShooter(int position){
        switch(position){
            case 0: angle.set(DoubleSolenoid.Value.kReverse); SmartDashboard.putBoolean("shooterstate", false); break;
            case 1: angle.set(DoubleSolenoid.Value.kForward); SmartDashboard.putBoolean("shooterstate", true); /*retractClimber();*/  break;
            default: System.out.println("ERROR: Invalid position"); break;
        }
    }
    public void shift(Gear gear){
        switch(gear.getRawValue()){
            case 0: shifters.set(DoubleSolenoid.Value.kReverse); currentGear = DoubleSolenoid.Value.kReverse; SmartDashboard.putBoolean("gear", false); break;
            case 1: shifters.set(DoubleSolenoid.Value.kForward); currentGear = DoubleSolenoid.Value.kForward; SmartDashboard.putBoolean("gear", true); break;
            default: System.out.println("ERROR: Invalid Gear"); break;
        }
        
    }
    public void rawShift(DoubleSolenoid.Value val){
        currentGear = val;
        shifters.set(val);
    }
    public void fineAdjustDown(){
        System.out.println(currentGear);
        shifters.set(DoubleSolenoid.Value.kReverse);
    }
    public DoubleSolenoid.Value getCurrentGear(){
        return currentGear;
    }
    public void extendClimber(){
        climber.set(DoubleSolenoid.Value.kForward);
        //angle.set(DoubleSolenoid.Value.kReverse);
        SmartDashboard.putBoolean("climber", true);
    }
    public void retractClimber(){
        climber.set(DoubleSolenoid.Value.kReverse);
        
        SmartDashboard.putBoolean("climber", false);
    }
}

