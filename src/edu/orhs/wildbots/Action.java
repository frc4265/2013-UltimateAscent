package edu.orhs.wildbots;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;


public abstract class Action extends Command{
    
    boolean oneTime = true;
    
    public Action(Subsystem subsystem){
        requires(subsystem);
    }
    public Action(Subsystem subsystem, boolean oneTime){
        requires(subsystem);
        this.oneTime = oneTime;
    }
    protected void initialize() {
    
    }
    
    protected void execute() {
        
    }

    protected boolean isFinished() {
        return oneTime;
    }

    protected void end() {
    }

    protected void interrupted() {
    }
    
}
