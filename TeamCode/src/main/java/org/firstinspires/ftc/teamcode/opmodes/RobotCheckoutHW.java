package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ServoController;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * This is NOT an opmode. This class is used to define all the specific hardware for a single robot.
 *
 * Motor Controller 1, Channel 1:  Left Drive Motor:        "DriveLeft"
 * Motor Controller 1, Channel 2:  Right Drive Motor:       "DriveRight"
 * Motor Controller 2, Channel 1:  Conveyer Belt Motor:     "Conveyer"
 * Motor Controller 2, Channel 2:  Ball Launcher Motor:     "BallLauncher"
 *
 * Servo Controller, Channel 1:    Beacon Arm:              "BeaconArm"
 * Servo Controller, Channel 2:    Catapult Trigger:        "LaunchTrigger"
 *
 *
 */
public class RobotCheckoutHW
{
    /* Public OpMode members. */
    public DcMotor DriveLeft  = null;
    public DcMotor DriveRight = null;
    public DcMotor Conveyor = null;
    public DcMotor BallLauncher = null;
    public Servo BeaconArm = null;
    public Servo LaunchTrigger = null;

    /* local OpMode members. */
    HardwareMap hwMap           =  null;
    private ElapsedTime period  = new ElapsedTime();

    /* Constructor */
    public RobotCheckoutHW(){

    }

    /* Initialize standard Hardware interfaces */
    public void init(HardwareMap ahwMap) {
        // Save reference to Hardware map
        hwMap = ahwMap;

        // Define and Initialize Motors
        DriveLeft  = hwMap.dcMotor.get("DriveLeft");
        DriveLeft.setDirection(DcMotor.Direction.FORWARD);
        DriveRight = hwMap.dcMotor.get("DriveRight");
        DriveRight.setDirection(DcMotor.Direction.REVERSE);
        Conveyor  = hwMap.dcMotor.get("Conveyor");
        Conveyor.setDirection(DcMotor.Direction.REVERSE);
        BallLauncher  = hwMap.dcMotor.get("BallLauncher");
        BallLauncher.setDirection(DcMotor.Direction.REVERSE);

        // Set all motors to zero power
        DriveLeft.setPower(0);
        DriveRight.setPower(0);
        Conveyor.setPower(0);
        BallLauncher.setPower(0);

        // Set drive motors to run with encoders and rest to not.
        DriveLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        DriveRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        Conveyor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        BallLauncher.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BallLauncher.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        // Define and Initialize Servos
        BeaconArm = hwMap.servo.get("BeaconArm");
    }

    /***
     *
     * waitForTick implements a periodic delay. However, this acts like a metronome with a regular
     * periodic tick.  This is used to compensate for varying processing times for each cycle.
     * The function looks at the elapsed cycle time, and sleeps for the remaining time interval.
     *
     * @param periodMs  Length of wait cycle in mSec.
     * @throws InterruptedException
     */
    public void waitForTick(long periodMs) throws InterruptedException {

        long  remaining = periodMs - (long)period.milliseconds();

        // sleep for the remaining portion of the regular cycle period.
        if (remaining > 0)
            Thread.sleep(remaining);

        // Reset the cycle clock for the next pass.
        period.reset();
    }
}

