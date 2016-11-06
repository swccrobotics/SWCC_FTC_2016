package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * This is NOT an opmode. This class is used to define all the specific hardware for a single robot.
 *
 * Motor Controller 1, Channel 1:  Left Launcher Motor:        "DriveLeft"
 * Motor Controller 1, Channel 2:  Right Launcher Motor:       "DriveRight"

 *
 * To be implemented later.....
 * Phone Camera
 * IR Seeker
 * Distance Sensor
 *
 */
public class LauncherCheckHW
{
    /* Public OpMode members. */
    public DcMotor BallLauncherLeft = null;
    public DcMotor BallLauncherRight = null;

    /* local OpMode members. */
    HardwareMap hwMap           =  null;
    private ElapsedTime period  = new ElapsedTime();

    /* Constructor */
    public LauncherCheckHW(){

    }

    /* Initialize standard Hardware interfaces */
    public void init(HardwareMap ahwMap) {
        // Save reference to Hardware map
        hwMap = ahwMap;

        // Define and Initialize Motors
        BallLauncherLeft  = hwMap.dcMotor.get("BallLauncher");
        BallLauncherLeft.setDirection(DcMotor.Direction.FORWARD);
        BallLauncherRight  = hwMap.dcMotor.get("BallLauncher");
        BallLauncherRight.setDirection(DcMotor.Direction.REVERSE);

        // Set all motors to zero power
        BallLauncherLeft.setPower(0);
        BallLauncherRight.setPower(0);
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

