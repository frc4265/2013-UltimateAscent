package edu.orhs.wildbots.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import java.util.Vector;

public class TeleopGroup extends CommandGroup {
    
    public  TeleopGroup() {
        addParallel(new ShooterControl());
        addParallel(new PneumaticsControl());
        addParallel(new Drive());
    }
}
