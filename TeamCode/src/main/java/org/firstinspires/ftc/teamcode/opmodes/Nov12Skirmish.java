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
 *
 *
 */

@TeleOp(name="Nov12Skirmish", group="Main")

public class Nov12Skirmish extends LinearOpMode {

    /* Declare OpMode members. */
    private Nov12SkirmishHW robot           = new Nov12SkirmishHW();   // Use the hardware assigned in the RobotCheckoutHW class
    private final static double ConveyorSpeed = 1.0; // speed of conveyor belt
    private boolean ConveyorState = false; // false for retracted, true for extended.
    private int ConveyorDebounce = 0;
    private int ConveyorDirection = 1;

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


            // ----------SECTION FOR CONVEYOR----------
            // Use Gamepad 1 left bumper to turn on/off conveyor belt.
            if (gamepad1.right_bumper)
                ConveyorState = true;
            if (gamepad1.right_trigger > 0.5)
                ConveyorState = false;

            if (ConveyorState)
                robot.Conveyor.setPower(ConveyorDirection*ConveyorSpeed);
            else
                robot.Conveyor.setPower(0.0);

            if (gamepad1.b)
                    ConveyorDirection = 1;
            if (gamepad1.x)
                    ConveyorDirection = -1;




            telemetry.addData("ConveyorState", ConveyorState);
            telemetry.addData("ConveyorDirection", ConveyorDirection);
            telemetry.addData("ConveyorDebounce", ConveyorDebounce);

            telemetry.update();
            // Pause for metronome tick.  40 mS each cycle = update 25 times a second.
            robot.waitForTick(40);
        }
    }
}
