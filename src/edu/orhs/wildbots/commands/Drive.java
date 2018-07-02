package edu.orhs.wildbots.commands;

import edu.orhs.wildbots.OI;
import edu.orhs.wildbots.Robot;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class  Drive extends Command {
    
    private Joystick driveStick;
    private Joystick shooterStick;
    
    
    private final double joystickThreshold = 0.05;
    private boolean pCannonToggle = true;
    private boolean pcb_prevState = false;
    private boolean pcb_currState = true;
    
    public Drive() {
	requires(Robot.chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        SmartDashboard.putNumber("Red", 0.0);
        SmartDashboard.putNumber("Green", 0.0);
        SmartDashboard.putNumber("Blue", 0.0);
        SmartDashboard.putBoolean("Photon Cannon", pCannonToggle);
        if(DriverStation.getInstance().getAlliance().equals(Alliance.kBlue)){
            SmartDashboard.putNumber("Red", 0.0);
            SmartDashboard.putNumber("Green", 0.0);
            SmartDashboard.putNumber("Blue", 1.0);
        }else if(DriverStation.getInstance().getAlliance().equals(Alliance.kRed)){
            SmartDashboard.putNumber("Red", 1.0);
            SmartDashboard.putNumber("Green", 0.0);
            SmartDashboard.putNumber("Blue", 0.0);
        }
        driveStick = Robot.oi.getRightStick();
        shooterStick = Robot.oi.getLeftStick();
    }

    protected void execute(){
        pcb_prevState = pcb_currState;
        pcb_currState = driveStick.getRawButton(OI.Buttons.PHOTON_CANNON_BUTTON);
        double power = 0.0;
        double turn = 0.0;
        double mult;
        
        double red = SmartDashboard.getNumber("Red");
        double green = SmartDashboard.getNumber("Green");
        double blue = SmartDashboard.getNumber("Blue");
        
        Robot.chassis.setLights(red,green,blue);
        
        if (shooterStick.getRawButton(OI.Buttons.REAR_LOADER_OUT)){
            Robot.chassis.setRearLoader(10);
        }
        if (shooterStick.getRawButton(OI.Buttons.REAR_LOADER_IN)){
            Robot.chassis.setRearLoader(160);
        }
        
        
        if (Math.abs(driveStick.getY()) >= joystickThreshold){
            power = driveStick.getY();
        }
        if (Math.abs(driveStick.getZ()) >= joystickThreshold){
            turn = driveStick.getZ();
        }
        if(driveStick.getRawButton(OI.Buttons.BACKWARDS_BUTTON)){
            power*=-1;
        }
        if(!pcb_prevState && pcb_currState){
            pCannonToggle = !pCannonToggle;
            SmartDashboard.putBoolean("Photon Cannon", pCannonToggle);
        }
        //Fine Tuning
        if(driveStick.getRawAxis(OI.Axis.X_FINE) < 0){
            Robot.chassis.fineTune(1, -1);
            return;
        }
        
        if(driveStick.getRawAxis(OI.Axis.X_FINE) > 0){
            Robot.chassis.fineTune(-1, 1);
            return;
        }
        
        if(driveStick.getRawAxis(OI.Axis.Y_FINE) < 0){
            Robot.chassis.fineTune(-1, -1);
            return;
        }
        
        if(driveStick.getRawAxis(OI.Axis.Y_FINE) > 0){
            Robot.chassis.fineTune(1, 1);
            return;
        }
        Robot.chassis.setPhotonCannon(SmartDashboard.getBoolean("Photon Cannon"));
        mult = ((-driveStick.getRawAxis(4)+1)/2);
        Robot.chassis.wildDrive(power, turn, mult, true);
        putData();
    }
    private void putData(){
        SmartDashboard.putNumber("Battery", DriverStation.getInstance().getBatteryVoltage());
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
