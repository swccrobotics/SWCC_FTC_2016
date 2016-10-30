package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.Range;

/**
 * This opmode uses RobotCheckoutHW to initialize the required hardware.
 *
 * Motor Controller 1, Channel 1:  Left Drive Motor:        "DriveLeft"
 * Motor Controller 1, Channel 2:  Right Drive Motor:       "DriveRight"
 * Motor Controller 2, Channel 1:  Conveyer Belt Motor:     "Conveyer"
 * Motor Controller 2, Channel 2:  Ball Launcher Motor:     "BallLauncher"
 *
 * Servo Controller, Channel 1:    Ball Platform Servo:     "Platform"
 * Servo Controller, Channel 2:    Left Beacon Arm:         "ArmLeft"
 * Servo Controller, Channel 3:    Right Beacon Arm:        "ArmRight"
 *
 *
 */

@TeleOp(name="RobotCheckout", group="Main")

public class RobotCheckout extends LinearOpMode {

    /* Declare OpMode members. */
    private RobotCheckoutHW robot           = new RobotCheckoutHW();   // Use the hardware assigned in the RobotCheckoutHW class
    private final static double PlatformRetract = 0;
    private final static double PlatformExtend = 1.0;
    private final static double ArmLeftRetract = 0;
    private final static double ArmLeftExtend = 1.0;
    private final static double ArmRightRetract = 0;
    private final static double ArmRightExtend = 1.0;
    private final static double[] LauncherSpeed = {0.2, 0.4, 0.6, 0.8, 1.0}; // Options for speed of ball launcher
    private final static double ConveyorSpeed = 1.0; // speed of conveyor belt
    private int LaunchSpeedIdx = 0;
    private boolean LauncherState = false; // false for off, true for on.
    private boolean ArmLeftState = false; // false for retracted, true for extended.
    private boolean ArmRightState = false; // false for retracted, true for extended.
    private boolean ConveyorState = false; // false for retracted, true for extended.
    private boolean PlatformState = false; // false for retracted, true for extended.

    @Override
    public void runOpMode() throws InterruptedException {

        /* Initialize the hardware variables.
         * The init() method of the hardware class does all the work here
         */
        robot.init(hardwareMap);

        // Send telemetry message to signify robot waiting;
        telemetry.addData("Say", "Hello Driver");    //
        telemetry.update();

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {

            // ----------SECTION FOR DRIVE MOTORS----------
            // Left and right sticks control left and right drive motors.
            robot.DriveLeft.setPower(gamepad1.left_stick_y);
            robot.DriveRight.setPower(gamepad1.right_stick_y);

            // ----------SECTION FOR BALL LAUNCHER----------
            // Use Gamepad 1 Right Bumper and Trigger to increase/decrease the launcher speed.
            if (gamepad1.right_bumper)
                LaunchSpeedIdx += 1;
            else if (gamepad1.right_trigger > 0.5)
                LaunchSpeedIdx -= 1;
            // Keep the LaunchSpeedIdx within the bounds of the array.
            LaunchSpeedIdx = Range.clip(LaunchSpeedIdx, 0, LauncherSpeed.length-1); // Length - 1 because array is zero-indexed.

            // Use Gamepad 1 "Y" button to enable/disable the launcher.
            if (gamepad1.y)
                LauncherState = !LauncherState;

            if (LauncherState)
                robot.BallLauncher.setPower(LauncherSpeed[LaunchSpeedIdx]);
            else
                robot.BallLauncher.setPower(0.0);

            // ----------SECTION FOR PLATFORM----------
            // Use Gamepad 1 "A" button to raise/lower platform.
            if (gamepad1.a)
                PlatformState = !PlatformState;

            if (PlatformState)
                robot.Platform.setPosition(PlatformExtend);
            else
                robot.Platform.setPosition(PlatformRetract);

            // ----------SECTION FOR CONVEYOR----------
            // Use Gamepad 1 left bumper to turn on/off conveyor belt.
            if (gamepad1.left_bumper)
                ConveyorState = !ConveyorState;

            if (ConveyorState)
                robot.Conveyor.setPower(ConveyorSpeed);
            else
                robot.Conveyor.setPower(0.0);

            // ----------SECTION FOR ARMS----------
            // Use Gamepad 1 "x" button to extend/retract left arm.
            if (gamepad1.x)
                ArmLeftState = !ArmLeftState;
            // Use Gamepad 1 "b" button to extend/retract left arm.
            if (gamepad1.b)
                ArmRightState = !ArmRightState;

            if (ArmLeftState)
                robot.ArmLeft.setPosition(ArmLeftExtend);
            else
                robot.ArmLeft.setPosition(ArmLeftRetract);

            if (ArmRightState)
                robot.ArmRight.setPosition(ArmRightExtend);
            else
                robot.ArmRight.setPosition(ArmRightRetract);

            // Send telemetry message to signify robot running;
            telemetry.addData("BallLauncherPower:",  "Ball Launcher Power = %.2f", LauncherSpeed[LaunchSpeedIdx]);
            telemetry.addData("BallLauncherState",  "Ball Launcher State = %b", LauncherState);
            telemetry.addData("PlatformState",  "Platform State = %b", PlatformState);
            telemetry.addData("ArmLeftState",  "Left Arm State = %b", ArmLeftState);
            telemetry.addData("ArmRightState",  "Right Arm State = %b", ArmRightState);
            telemetry.update();

            // Pause for metronome tick.  40 mS each cycle = update 25 times a second.
            robot.waitForTick(40);
        }
    }
}
