package edu.orhs.wildbots;
    
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.CounterBase.EncodingType; 
import edu.wpi.first.wpilibj.Encoder.PIDSourceParameter;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import java.util.Vector;

public class RobotMap {

    public static SpeedController chassisL1;
    public static SpeedController chassisL2;
    public static SpeedController chassisR1;
    public static SpeedController chassisR2;
    public static SpeedController chassisRedLEDs;
    public static SpeedController chassisGreenLEDs;
    public static SpeedController chassisBlueLEDs;
    public static RobotDrive chassisDriveTrain;
    public static Servo rearLoader;
    public static Gyro chassisGyro;
    public static Encoder chassisLeft;
    public static Encoder chassisRight;
    public static Compressor pneumaticsCompressor;
    public static DoubleSolenoid pneumaticsShooterKicker;
    public static DoubleSolenoid pneumaticsShooterLatch;
    public static DoubleSolenoid pneumaticsShifters;
    public static DoubleSolenoid pneumaticsClimber;
    public static DoubleSolenoid pneumaticsAngle;
    public static Encoder shooterEncoder;
    public static SpeedController shooterVictor;
    public static Servo cameraTilt;
    public static Relay chassisPhotonCannon;   
    
    public static void init() {
        chassisL1 = new Victor(1, 1);
	LiveWindow.addActuator("Chassis", "L1", (Victor) chassisL1);
        
        chassisL2 = new Victor(1, 5);
	LiveWindow.addActuator("Chassis", "L2", (Victor) chassisL2);
        
        chassisR1 = new Victor(1, 3);
	LiveWindow.addActuator("Chassis", "R1", (Victor) chassisR1);
        
        chassisR2 = new Victor(1, 4);
	LiveWindow.addActuator("Chassis", "R2", (Victor) chassisR2);
        
        chassisRedLEDs = new Talon(1, 8);
        LiveWindow.addActuator("Chassis", "RLEDs", (Talon) chassisRedLEDs);
        
        chassisGreenLEDs = new Talon(1, 9);
        LiveWindow.addActuator("Chassis", "GLEDs", (Talon) chassisGreenLEDs);
        
        chassisBlueLEDs = new Talon(1, 10);
        LiveWindow.addActuator("Chassis", "BLEDs", (Talon) chassisBlueLEDs);
        
        rearLoader = new Servo(1, 6);
        LiveWindow.addActuator("Chassis", "Rear Loader", (Servo) rearLoader);
        chassisDriveTrain = new RobotDrive(chassisL1, chassisL2, chassisR1, chassisR2);
	
        chassisDriveTrain.setSafetyEnabled(true);
        chassisDriveTrain.setInvertedMotor(RobotDrive.MotorType.kFrontLeft, true);
        chassisDriveTrain.setInvertedMotor(RobotDrive.MotorType.kFrontRight, true);
        chassisDriveTrain.setInvertedMotor(RobotDrive.MotorType.kRearLeft, true);
        chassisDriveTrain.setInvertedMotor(RobotDrive.MotorType.kRearRight, true);


        chassisDriveTrain.setExpiration(0.1);
        chassisDriveTrain.setSensitivity(0.5);
        chassisDriveTrain.setMaxOutput(1.0);

        chassisGyro = new Gyro(1, 1);
	LiveWindow.addSensor("Chassis", "Gyro", chassisGyro);
        chassisGyro.setSensitivity(0.007);
        
        chassisLeft = new Encoder(1, 9, 1, 10, false, EncodingType.k4X);
	LiveWindow.addSensor("Chassis", "Left", chassisLeft);
        chassisLeft.setDistancePerPulse(1.0);
        chassisLeft.setPIDSourceParameter(PIDSourceParameter.kRate);
        chassisLeft.start();
        
        chassisRight = new Encoder(1, 13, 1, 14, true, EncodingType.k4X);
	LiveWindow.addSensor("Chassis", "Right", chassisRight);
        chassisRight.setDistancePerPulse(1.0);
        chassisRight.setPIDSourceParameter(PIDSourceParameter.kRate);
        chassisRight.start();
        
        chassisPhotonCannon = new Relay(1, 2);
        
        pneumaticsCompressor = new Compressor(1,     1, 1, 1);
	
        
        pneumaticsShooterKicker = new DoubleSolenoid(1,2,1);//(1, 2, 1);      
	
        
        pneumaticsShooterLatch = new DoubleSolenoid(1,3,4);//(1, 3, 4);      
	
        
        pneumaticsShifters = new DoubleSolenoid(1,5,6);//(1, 5, 6);      
	
        
        pneumaticsClimber = new DoubleSolenoid(1, 7, 8);      
	
        pneumaticsAngle = new DoubleSolenoid(2, 1, 2);

        shooterEncoder = new Encoder(1, 11, 1, 12, true, EncodingType.k1X);
	LiveWindow.addSensor("Shooter", "Encoder", shooterEncoder);
        shooterEncoder.setDistancePerPulse(1.0/250.0);
        shooterEncoder.setPIDSourceParameter(PIDSourceParameter.kRate);
        shooterEncoder.start();
        
        shooterVictor = new Victor(1, 7);
	LiveWindow.addActuator("Shooter", "Victor", (Victor) shooterVictor);
    }
    /*ROBOT PORT MAPS
     * DIGITAL SIDECAR:
     * PWM:
     * Left 1:      1
     * Left 2:      5
     * Right 1:     3
     * Right 2:     4
     * Shooter CIM: 7
     * Red LEDs:    8
     * Green LEDs:  9
     * Blue LEDs:   10
     * 
     * DIO:
     * Pressure Switch: 1
     * Shooter encoder: 11, 12
     * 
     * RELAY:
     * Compressor:      1
     * Photon Cannon:   2
     * 
     * SOLENOID MODULES:
     * MODULE       1    |  2
     * Shifters A:  5, 6 |
     * Shifters B:       |  5, 6
     * Climber:     7, 8 |
     * Deck:             |  1, 2
     * Kicker A:    2, 1 |
     * Kicker B:    3, 4 |
     * Safety A:    3, 4 |
     * Safety B:         |  4, 3
     * 
     * A: Alpha bot
     * B: Beta bot
     */
}
