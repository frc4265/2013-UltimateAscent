package edu.orhs.wildbots;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.orhs.wildbots.commands.*;
import edu.orhs.wildbots.subsystems.*;
import edu.wpi.first.wpilibj.communication.FRCCommonControlData;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import java.util.Vector;

public class Robot extends IterativeRobot {

    static Command autoGroup;
    static Command teleopGroup;
    
    public static OI oi;
   
    public static Chassis chassis;
    public static Pneumatics pneumatics;
    public static Shooter shooter;
    
    public static FRCCommonControlData analogReader;
    
    //Runs on cRIO boot up
    public void robotInit() {
	RobotMap.init();

        chassis = new Chassis();
        pneumatics = new Pneumatics();
        shooter = new Shooter();
        
        analogReader  = new FRCCommonControlData();
        
        
        oi = new OI();
        
	//initialize command groups here***
        autoGroup = new AutoGroup();
        teleopGroup = new TeleopGroup();
        //***
    }

    public void autonomousInit() {
        //schedules autonomous to run
        Scheduler.getInstance().removeAll();
        if (autoGroup != null) autoGroup.start();
    }

    //Autonomous control loop
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
    }

    public void teleopInit() {
	
        
        Scheduler.getInstance().removeAll();
        if (teleopGroup != null) teleopGroup.start();
        
    }

    //teleop control loop
    public void teleopPeriodic() {
        Scheduler.getInstance().run();
    }

    //dont worry about this
    public void testPeriodic() {
        LiveWindow.run();
    }
}
