package edu.orhs.wildbots;

import edu.orhs.wildbots.commands.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.buttons.JoystickButton;


/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
    
    public static Joystick leftStick;
    public static Joystick rightStick;

    public OI() {

        rightStick = new Joystick(1);
        
        leftStick = new Joystick(2);

    }
    
    public Joystick getLeftStick() {
        return leftStick;
    }

    public Joystick getRightStick() {
        return rightStick;
    }

    public class Buttons {
        public static final int SHOOT = 1;
        public static final int SHOOTING_POSITION = 4;
        public static final int LOADING_POSITION = 6;
        public static final int CLIMBER_UP = 3;
        public static final int CLIMBER_DOWN = 5;
        public static final int HIGH_GEAR = 6;
        public static final int LOW_GEAR = 4;
        public static final int SHOOTER_BUTTON = 2;
        public static final int PHOTON_CANNON_BUTTON = 2;
        public static final int PID_ENABLE = 12;
        public static final int MANUAL_PRESET = 10;
        public static final int PYRAMID_PRESET = 11;
        public static final int FULL_COURT_PRESET = 9;
        public static final int HANGING_PRESET = 7;
        public static final int BACKWARDS_BUTTON = 1;
        public static final int REAR_LOADER_OUT = 3;
        public static final int REAR_LOADER_IN = 5;
    }
    public class Axis {
        public static final int PYRAMID_SPEED = 1;
        public static final int X_FINE = 5;
        public static final int Y_FINE = 6;
    }
}

