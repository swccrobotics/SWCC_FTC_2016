package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.Range;

/**
 * This opmode uses RobotCheckoutHW to initialize the required hardware.
 *
 * Motor Controller 1, Channel 1:  Left Launcher Motor:        "Left"
 * Motor Controller 1, Channel 2:  Right Launcher Motor:       "DriveRight"
 *
 */

@TeleOp(name="LauncherCheckout", group="Main")

public class LauncherCheck extends LinearOpMode {

    /* Declare OpMode members. */
    private LauncherCheckHW robot = new LauncherCheckHW();   // Use the hardware assigned in the RobotCheckoutHW class
    private final static double[] LauncherSpeed = {0.2, 0.4, 0.6, 0.8, 1.0}; // Options for speed of ball launcher
    private int LaunchSpeedIdx = 0;
    private boolean LauncherState = false; // false for off, true for on.

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

            // ----------SECTION FOR BALL LAUNCHER----------
            // Use Gamepad 1 Right Bumper and Trigger to increase/decrease the launcher speed.
            if (gamepad1.right_bumper) {
                LaunchSpeedIdx += 1;
            } else if (gamepad1.right_trigger > 0.5) {
                LaunchSpeedIdx -= 1;
            }
            // Keep the LaunchSpeedIdx within the bounds of the array.
            LaunchSpeedIdx = Range.clip(LaunchSpeedIdx, 0, LauncherSpeed.length-1); // Length - 1 because array is zero-indexed.

            // Use Gamepad 1 "Y" button to enable/disable the launcher.
            if (gamepad1.y) {
                LauncherState = !LauncherState;
            }

            if (LauncherState) {
                robot.BallLauncherLeft.setPower(LauncherSpeed[LaunchSpeedIdx]);
                robot.BallLauncherRight.setPower(LauncherSpeed[LaunchSpeedIdx]);
            } else {
                robot.BallLauncherLeft.setPower(0.0);
                robot.BallLauncherRight.setPower(0.0);
            }

            // Send telemetry message to signify robot running;
            telemetry.addData("BallLauncherPower:",  "Ball Launcher Power = %.2f", LauncherSpeed[LaunchSpeedIdx]);
            telemetry.addData("BallLauncherState",  "Ball Launcher State = %b", LauncherState);
            telemetry.update();

            // Pause for metronome tick.  40 mS each cycle = update 25 times a second.
            robot.waitForTick(40);
        }
    }
}
