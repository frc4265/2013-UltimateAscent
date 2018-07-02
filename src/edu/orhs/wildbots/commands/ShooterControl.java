package edu.orhs.wildbots.commands;

import edu.orhs.wildbots.OI;
import edu.wpi.first.wpilibj.command.Command;
import edu.orhs.wildbots.Robot;
import edu.orhs.wildbots.subsystems.Shooter;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class  ShooterControl extends Command {

    Joystick shooterStick;
    Joystick panel;
    private boolean shooterToggle = true;
    private boolean PIDToggle = true;
    private boolean sb_prev = false;
    private boolean sb_curr = false;
    private boolean pidb_prev = false;
    private boolean pidb_curr = false;
    
    public ShooterControl() {
	requires(Robot.shooter);
    }

    protected void initialize() {
        shooterStick = Robot.oi.getLeftStick();
        SmartDashboard.putNumber("PID Tolerance", 0.005);
        Robot.shooter.setMode(Shooter.Mode.PYRAMID);
    }
    protected void execute() {
        //scaling algorithm
        double s = (-shooterStick.getRawAxis(4)+1)/2;
        
        //Button state updates
        sb_prev = sb_curr;
        sb_curr = shooterStick.getRawButton(OI.Buttons.SHOOTER_BUTTON);
        pidb_prev = pidb_curr;
        pidb_curr = shooterStick.getRawButton(OI.Buttons.PID_ENABLE);
        
        //Shooter toggle
        if(!sb_prev && sb_curr){
            shooterToggle = !shooterToggle;
        }
        if(shooterToggle){
            if(Robot.shooter.getPID().isEnable())
                Robot.shooter.setPID(s);
            else
                Robot.shooter.setSpeed(s);
        }else{
            Robot.shooter.setPID(0);
            Robot.shooter.setSpeed(0);
        }
        SmartDashboard.putBoolean("shooterOn", shooterToggle);
        
        //Mode change
        if(shooterStick.getRawButton(OI.Buttons.MANUAL_PRESET)){
            Robot.shooter.setMode(Shooter.Mode.MANUAL);
        }
        if(shooterStick.getRawButton(OI.Buttons.PYRAMID_PRESET)){
            Robot.shooter.setMode(Shooter.Mode.PYRAMID);
        }
        if(shooterStick.getRawButton(OI.Buttons.FULL_COURT_PRESET)){
            Robot.shooter.setMode(Shooter.Mode.FULL_COURT);
        }
        if(shooterStick.getRawButton(OI.Buttons.HANGING_PRESET)){
            Robot.shooter.setMode(Shooter.Mode.HANGING);
        }
        //PID toggle
        if(!pidb_prev && pidb_curr){
            if(Robot.shooter.getPID().isEnable()){
                Robot.shooter.getPID().disable();
            }else{
                Robot.shooter.enablePID();
            }
        }
        
        putData();
        Robot.shooter.setAbsoluteTolerance(SmartDashboard.getNumber("PID Tolerance"));
    }
    
    private void putData(){
        SmartDashboard.putNumber("SHOOTER_SPEED", Robot.shooter.getRate());
    }
    
    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    protected void interrupted() {
    }
}
