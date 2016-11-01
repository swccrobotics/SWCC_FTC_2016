package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * This is NOT an opmode. This class is used to define all the specific hardware for a single robot.
 *
 * Motor channel:  Left drive motor:        "LeftWheel"
 * Motor channel:  Right drive motor:       "RightWheel"
 */
public class OneArmyHW
{
    /* Public OpMode members. */
    public DcMotor leftWheelMotor  = null;
    public DcMotor rightWheelMotor = null;

    /* local OpMode members. */
    HardwareMap hwMap           =  null;
    private ElapsedTime period  = new ElapsedTime();

    /* Constructor */
    public OneArmyHW(){

    }

    /* Initialize standard Hardware interfaces */
    public void init(HardwareMap ahwMap) {
        // Save reference to Hardware map
        hwMap = ahwMap;

        // Define and Initialize Motors
        leftWheelMotor  = hwMap.dcMotor.get("LeftWheel");
        leftWheelMotor.setDirection(DcMotor.Direction.FORWARD);
        rightWheelMotor = hwMap.dcMotor.get("RightWheel");
        rightWheelMotor.setDirection(DcMotor.Direction.REVERSE);

        // Set all motors to zero power
        leftWheelMotor.setPower(0);
        rightWheelMotor.setPower(0);

        // Set all motors to run without encoders.
        // May want to use RUN_USING_ENCODERS if encoders are installed.
        leftWheelMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightWheelMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
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

