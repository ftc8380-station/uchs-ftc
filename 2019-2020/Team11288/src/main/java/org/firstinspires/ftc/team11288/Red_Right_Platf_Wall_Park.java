package org.firstinspires.ftc.team11288;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.configuration.annotations.DigitalIoDeviceType;
import com.qualcomm.robotcore.util.ElapsedTime;

import static com.qualcomm.robotcore.hardware.DcMotor.RunMode.STOP_AND_RESET_ENCODER;


@Autonomous(name = "Red_Right_Platf_Wall_Park", group = "Linear Opmode")
@Disabled                            // Comment this out to add to the opmode list
public class Red_Right_Platf_Wall_Park extends LinearOpMode {
    //initialize these variables, override them in the constructor

    /* Declare OpMode members. */
    //wheels
    private DcMotor motorFrontRight;
    private DcMotor motorFrontLeft;
    private DcMotor motorBackRight;
    private DcMotor motorBackLeft;
    private DcMotor motorLift;

    private UtilHolonomic teamUtils;


    //    private elbow             = null;
//    private Servo wrist       = null;
    private Servo claw = null;
    //private Servo platform = null;


    private ElapsedTime runtime = new ElapsedTime();


    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        //initialize wheels
        motorFrontRight = hardwareMap.dcMotor.get("motor front right");
        motorFrontLeft = hardwareMap.dcMotor.get("motor front left");
        motorBackLeft = hardwareMap.dcMotor.get("motor back left");
        motorBackRight = hardwareMap.dcMotor.get("motor back right");
        motorLift = hardwareMap.dcMotor.get("motor lift");
        claw = hardwareMap.servo.get("claw servo");
        //platform = hardwareMap.servo.get("platform servo");
        claw.setPosition(0);
        //platform.setPosition(1);
        motorFrontRight.setDirection(DcMotorSimple.Direction.FORWARD);
        motorFrontLeft.setDirection(DcMotorSimple.Direction.FORWARD);
        motorBackRight.setDirection(DcMotorSimple.Direction.FORWARD);
        motorBackLeft.setDirection(DcMotorSimple.Direction.FORWARD);

        motorLift.setDirection(DcMotorSimple.Direction.FORWARD);
        motorLift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorLift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motorLift.setMode(STOP_AND_RESET_ENCODER);

        //utils class initializer
        teamUtils = new UtilHolonomic(motorFrontRight, motorFrontLeft, motorBackRight, motorBackLeft, telemetry);
        //teamUtils.InitExtraSensors(hardwareMap);

        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        //Play started

        boolean stepsCompleted = false;
        teamUtils.InitExtraSensors(hardwareMap);
        teamUtils.InitPlatform(hardwareMap);

        while (opModeIsActive()) {
            if (!stepsCompleted) {
                stepsCompleted = true;
                // run this loop until the end of the match (driver presses stop)
                teamUtils.drivebyDistance(0.85, 0.0, 3);//drive away from wall
                teamUtils.drivebyDistance(0.0, -0.85, 28);//drive to corner
                teamUtils.drivebyDistance(0.85, 0, 27);//drive to base plate
                teamUtils.GrabPlaform(true);
                sleep(800);
                //drive back to corner
                teamUtils.drivebyDistance(-0.85, 0, 23);//drive towards corner
                teamUtils.GrabPlaform(false);
                sleep(800);
                //These steps need adjustment, but seemed like the safest way to push the platform into place
                teamUtils.drivebyDistance(-0.85, 0, 3);//drive away from foundation
                teamUtils.drivebyDistance(0.0, 0.85, 28);//drive towards bridge
                teamUtils.drivebyDistance(0.85, 0, 42);//drive towards center
                teamUtils.drivebyDistance(0.0, -0.85, 29);//drive towards wall
                teamUtils.drivebyDistance(-0.85, 0, 24);//push foundation
                teamUtils.drivebyDistance(0.0, 0.85, 20);//drive up to park
                teamUtils.drivebyDistance(-0.85, 0.0, 36);//drive away from center
                teamUtils.drivebyDistance(0.0, 0.85, 32);//drive up to park
                teamUtils.stopWheelsSpeedMode();
                claw.setPosition(1);

                try {
                    Thread.sleep(3000);
                } catch(Exception e) {}


                requestOpModeStop();
            }
        }
    }
}


