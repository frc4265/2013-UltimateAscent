package edu.orhs.wildbots.commands;

import edu.orhs.wildbots.Action;
import edu.orhs.wildbots.Robot;
import edu.orhs.wildbots.subsystems.Pneumatics;
import edu.orhs.wildbots.subsystems.Shooter;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStationLCD;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AutoGroup extends CommandGroup {
    
    public  AutoGroup() {
        addParallel(new Action(Robot.shooter, false){
           public void execute(){
                Robot.shooter.setMode(Shooter.Mode.PYRAMID);
                SmartDashboard.putBoolean("shooterOn", true);
                Robot.shooter.setPID(37);
                Robot.shooter.enablePID();
                DriverStationLCD.getInstance().println(DriverStationLCD.Line.kUser1, 1, "Encoder: "+Robot.shooter.getRate());
                DriverStationLCD.getInstance().updateLCD();
           } 
        });
        addParallel(new Action(Robot.pneumatics, false){
            public void execute(){
                double delay = 3;
                Robot.pneumatics.shift(Pneumatics.Gear.HIGH);
                Robot.pneumatics.changeShooter(Pneumatics.Value.LOADING);
                if(Robot.pneumatics.getSwitchValue()){
                    Robot.pneumatics.stopCompressor();
                }else{ 
                    Robot.pneumatics.startCompressor();
                }
                if(DriverStation.getInstance().getMatchTime()>delay && DriverStation.getInstance().getMatchTime()<delay+4){
                    Robot.pneumatics.shoot();
                }
            }
        });
    }
}
