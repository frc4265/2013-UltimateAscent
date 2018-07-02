package edu.orhs.wildbots.commands;

import edu.orhs.wildbots.OI;
import edu.wpi.first.wpilibj.command.Command;
import edu.orhs.wildbots.Robot;
import edu.orhs.wildbots.subsystems.Pneumatics;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class  PneumaticsControl extends Command {

    private Joystick driveStick;
    private Joystick shooterStick;
    public double fineThreshold = 0.5;
    
    public PneumaticsControl() {
	requires(Robot.pneumatics);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        driveStick = Robot.oi.getRightStick();
        shooterStick = Robot.oi.getLeftStick();
        Robot.pneumatics.retractClimber();
        Robot.pneumatics.changeShooter(Pneumatics.Value.LOADING);
        Robot.pneumatics.shift(Pneumatics.Gear.HIGH);
        Robot.pneumatics.stopCompressor();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        
        //Compressor Control
        
        if(Robot.pneumatics.getSwitchValue()){
            SmartDashboard.putBoolean("Compressor on", false);
            Robot.pneumatics.stopCompressor(); 
        }else{ 
            Robot.pneumatics.startCompressor();
            SmartDashboard.putBoolean("Compressor on", true);
        }
        //Fine Control
        if(Math.abs(driveStick.getRawAxis(OI.Axis.X_FINE)) > fineThreshold ||
                Math.abs(driveStick.getRawAxis(OI.Axis.Y_FINE)) > fineThreshold){
            Robot.pneumatics.setCurrentGear(Robot.pneumatics.getCurrentGear());
            Robot.pneumatics.fineAdjustDown();
        }else{
            Robot.pneumatics.rawShift(Robot.pneumatics.getCurrentGear());
        }
        //Shooting
        if (shooterStick.getRawButton(OI.Buttons.SHOOT) && SmartDashboard.getBoolean("shooterOn")){
            Robot.pneumatics.shoot();
        }
        
        //change the angle of the shooter for either loading or shooting
        if (shooterStick.getRawButton(OI.Buttons.SHOOTING_POSITION)){
            Robot.pneumatics.changeShooter(Pneumatics.Value.SHOOTING);
        }
        if (shooterStick.getRawButton(OI.Buttons.LOADING_POSITION)){
            Robot.pneumatics.changeShooter(Pneumatics.Value.LOADING);
        }       
        //Shifting
        if (driveStick.getRawButton(OI.Buttons.LOW_GEAR)){
            Robot.pneumatics.shift(Pneumatics.Gear.LOW);
        }
        if (driveStick.getRawButton(OI.Buttons.HIGH_GEAR)){
            Robot.pneumatics.shift(Pneumatics.Gear.HIGH);
        }
        //Climber Control
        if (driveStick.getRawButton(OI.Buttons.CLIMBER_UP)){
            Robot.pneumatics.extendClimber();
        }
        if (driveStick.getRawButton(OI.Buttons.CLIMBER_DOWN)){ 
            Robot.pneumatics.retractClimber();
        }
        
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
