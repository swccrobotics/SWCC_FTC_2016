package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * This is NOT an opmode. This class is used to define all the specific hardware for a single robot.
 *
 * Motor Controller 1, Channel 1:  Left Drive Motor:        "DriveLeft"
 * Motor Controller 1, Channel 2:  Right Drive Motor:       "DriveRight"
 * Motor Controller 2, Channel 1:  Ball Conveyor:            "Conveyor"
 */
public class Nov12SkirmishHW
{
    /* Public OpMode members. */
    public DcMotor DriveLeft = null;
    public DcMotor DriveRight = null;
    public DcMotor Conveyor = null;


    /* local OpMode members. */
    HardwareMap hwMap           =  null;
    private ElapsedTime period  = new ElapsedTime();

    /* Constructor */
    public Nov12SkirmishHW(){

    }

    /* Initialize standard Hardware interfaces */
    public void init(HardwareMap ahwMap) {
        // Save reference to Hardware map
        hwMap = ahwMap;

        // Define and Initialize Motors
        DriveLeft = hwMap.dcMotor.get("DriveLeft");
        DriveLeft.setDirection(DcMotor.Direction.FORWARD);
        DriveRight  = hwMap.dcMotor.get("DriveRight");
        DriveRight.setDirection(DcMotor.Direction.REVERSE);
        Conveyor = hwMap.dcMotor.get("Conveyor");
        Conveyor.setDirection(DcMotor.Direction.FORWARD);

        // Set all motors to zero power
        DriveLeft.setPower(0);
        DriveRight.setPower(0);
        Conveyor.setPower(0);
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

