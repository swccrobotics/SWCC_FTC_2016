package org.firstinspires.ftc.teamcode.opmodes;

import com.google.blocks.ftcrobotcontroller.BlocksActivity;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.vuforia.HINT;
import com.vuforia.Vuforia;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;
import org.firstinspires.ftc.teamcode.R;

/**
 * Created by GE on 10/25/2016.


public class VuforiaOP extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        VuforiaLocalizer.Parameters params = new VuforiaLocalizer.Parameters(R.id.cameraMonitorViewId);
        params.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
        params.vuforiaLicenseKey = "AQ9QNIT/////AAAAGYNVcnOF30S7kPi5VeSUNphD3tDzx49XI2Oyu7s4CfF5SqD/H4Pn6Wi02T0lOLD5btsyv/vhSBBzSsqkDccv+lLW+Qg28HXJ2981HO4fFTGeewsyRMAkqUdGOyK2t6qKNVznbZMfRVWpsE6qZcu2NiLAWU9uj5ESAKCLuKn+2rsLYK3WDFx54LNxoOYm7H9FdPAMbG/WTRprFh5fT67K3j9zMr9iB99E0d95I70v9q2T5Eo6Wx+cJFrIZNvg8042WL5ElLYuXSOPbZn8S9ScGTGqO2Z8AAcd1jiNRP3YXMKzMQ1pRU3ZQhQheeww4aSCiWOiVs6lYHDt7KXVN8kYnRoiY4+mZ0NxNb8tl+pXANkW";
        params.cameraMonitorFeedback = VuforiaLocalizer.Parameters.CameraMonitorFeedback.AXES;

        VuforiaLocalizer vuforia = ClassFactory.createVuforiaLocalizer(params);
        Vuforia.setHint(HINT.HINT_MAX_SIMULTANEOUS_IMAGE_TARGETS, 4);

        VuforiaTrackables beacons = vuforia.loadTrackablesFromAsset("FTC_2016-17");
        beacons.get(0).setName('Wheels');
        beacons.get(1).setName('Tools');
        beacons.get(2).setName('Lego');
        beacons.get(3).setName('Gears');


        waitForStart();


        beacons.activate();

        while (opModeIsActive()){
            for(VuforiaTrackables beac : beacons){
        }
        for(lat 1-)
    }
}
**/