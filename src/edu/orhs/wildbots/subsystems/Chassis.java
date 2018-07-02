package edu.orhs.wildbots.subsystems;

import edu.orhs.wildbots.RobotMap;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Chassis extends Subsystem {
    
    SpeedController l1 = RobotMap.chassisL1;
    SpeedController l2 = RobotMap.chassisL2;
    SpeedController r1 = RobotMap.chassisR1;
    SpeedController r2 = RobotMap.chassisR2;
    SpeedController rLEDs = RobotMap.chassisRedLEDs;
    SpeedController gLEDs = RobotMap.chassisGreenLEDs;
    SpeedController bLEDs = RobotMap.chassisBlueLEDs;
    RobotDrive driveTrain = RobotMap.chassisDriveTrain;
    Gyro gyro = RobotMap.chassisGyro;
    Encoder left = RobotMap.chassisLeft;
    Encoder right = RobotMap.chassisRight;
    Servo rearLoader = RobotMap.rearLoader;
    Relay photonCannon = RobotMap.chassisPhotonCannon;

    private final double fineSpeedMult = 0.5;
    
    public void initDefaultCommand() {
        
    }
    
    public void wildDrive(double power, double turn, double mult, boolean squaredInputs){
        driveTrain.arcadeDrive(power*mult, turn*mult, squaredInputs);
    }
    public void fineTune(double left, double right){
        driveTrain.tankDrive(left*fineSpeedMult, right*fineSpeedMult);
        System.out.println("Fine Tuning");
    }
    //set RGB values for chassis LEDs
    public void setLights(double r, double g, double b){
        rLEDs.set(r);
        gLEDs.set(g);
        bLEDs.set(b);
    }
    //set the Photon Cannon either on or off
    public void setPhotonCannon(boolean on){
        if(on){
            photonCannon.set(Relay.Value.kForward);
        }else{
            photonCannon.set(Relay.Value.kOff);
        }
    }
    public void setRearLoader(double angle){
        rearLoader.setAngle(angle);
    }
}

