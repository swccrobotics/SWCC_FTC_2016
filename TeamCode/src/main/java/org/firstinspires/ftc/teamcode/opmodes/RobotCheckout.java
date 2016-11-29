package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

/**
 * This opmode uses RobotCheckoutHW to initialize the required hardware.
 *
 * Motor Controller 1, Channel 1:  Left Drive Motor:        "DriveLeft"
 * Motor Controller 1, Channel 2:  Right Drive Motor:       "DriveRight"
 * Motor Controller 2, Channel 1:  Conveyer Belt Motor:     "Conveyor"
 * Motor Controller 2, Channel 2:  Ball Launcher Motor:     "BallLauncher"
 *
 * Servo Controller, Channel 1:    Beacon Arm:              "BeaconArm"
 *
 *
 */

@TeleOp(name="RobotCheckout", group="Main")

public class RobotCheckout extends LinearOpMode {

    /* Declare OpMode members. */
    private RobotCheckoutHW robot           = new RobotCheckoutHW();   // Use the hardware assigned in the RobotCheckoutHW class
    private final static double CONVEYOR_POWER = 1.0; // Ball conveyor operating power (0 to 1).
    private final static double BEACON_EXTEND = 0.35; // This is the value that the beacon-pushing arm goes to to press the button.
  //  private final static double BEACON_RETRACT = 0.95; // This is the retracted value for the beacon arm.
    private final static double BEACON_RETRACT = 0.05; // This is the retracted value for the beacon arm.
    private final static int BUTTON_DEBOUNCE = 5; // Number of cycles to ignore a button press to avoid repeating actions.
    private final static int ENCODER_TICKS = 4150; // Number of encoder ticks per motor revolution.

    private boolean BeaconState = false; // false for retracted, true for extended.
    private boolean ConveyorState = false; // false for off, true for on.
    private int ConveyorDirection = 1; // 1 for bring ball in, -1 for spit ball out.
    private int LauncherMotorPosition = 0; // This will reflect the encoder ticks of the Launcher.

    private double ServoPos = 0; //TEST DELETE THIS

    private double GP2ADebounce = 0; // Gamepad2.a debounce. If this value is not zero, the program ignores the button press.
    private double  GP2YDebounce = 0; // Gamepad2.y debounce.

    @Override
    public void runOpMode() throws InterruptedException {

        /* Initialize the hardware variables.
         * The init() method of the hardware class does all the work here
         */
        robot.init(hardwareMap);

        robot.BeaconArm.setPosition(BEACON_RETRACT);

        // Send telemetry message to signify robot waiting;
        telemetry.addData("Say", "Hello Driver");    //
        telemetry.update();

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {

            // --------GAMEPAD DEBOUNCE SECTION-----------
            if (GP2ADebounce > 0) {
                GP2ADebounce -= 1;
            } else {
                GP2ADebounce = 0;
            }

            if (GP2YDebounce > 0) {
                GP2YDebounce -= 1;
            } else {
                GP2YDebounce = 0;
            }

            // ----------SECTION FOR DRIVE MOTORS----------
            // Left and right sticks control left and right drive motors.
            robot.DriveLeft.setPower(gamepad1.left_stick_y);
            robot.DriveRight.setPower(gamepad1.right_stick_y);

            // ----------SECTION FOR BALL LAUNCHER----------
            LauncherMotorPosition = -robot.BallLauncher.getCurrentPosition(); // Motor runs backwards so make the position reference positive.

            robot.BallLauncher.setPower(-gamepad2.right_trigger);

            // ----------SECTION FOR BEACON ARM---------------
            if (gamepad1.x) {
                robot.BeaconArm.setPosition(BEACON_RETRACT);
                BeaconState = false;
            }
            if (gamepad1.b) {
                robot.BeaconArm.setPosition(BEACON_EXTEND);
                BeaconState = true;
            }

            // ---------SECTION FOR CONVEYOR-----------------
            if (gamepad2.a) {
                if (GP2ADebounce == 0) {
                    ConveyorState = !ConveyorState;
                    GP2ADebounce = BUTTON_DEBOUNCE;
                }
            }

            if (gamepad2.y) {
                if (GP2YDebounce == 0) {
                    ConveyorDirection = -ConveyorDirection;
                    GP2YDebounce = BUTTON_DEBOUNCE;
                }
            }

            if (ConveyorState) {
                robot.Conveyor.setPower(ConveyorDirection*CONVEYOR_POWER);
            } else  {
                robot.Conveyor.setPower(0);
            }

            //telemetry.addData("ArmRightState",  "Right Arm State = %b", ArmRightState);
            telemetry.addData("BeaconState = ", BeaconState);
            telemetry.addData("ConveyorState = ", ConveyorState);
            telemetry.addData("ConveyorDirection = ", ConveyorDirection);
            telemetry.addData("Ball Launcher Encoder = ", LauncherMotorPosition);
            telemetry.addData("ServoPos = ", ServoPos);
            telemetry.update();

            // Pause for metronome tick.  40 mS each cycle = update 25 times a second.
            robot.waitForTick(40);
        }
    }
}
