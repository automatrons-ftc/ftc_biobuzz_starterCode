package org.firstinspires.ftc.teamcode.Mechanisms;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Shooter {

    private DcMotor motor1;

    private double shooterPower = 0.5;

    private boolean motorOn;

    private boolean wasPressedRT;
    private boolean wasPressedUp;
    private boolean wasPressedRight;
    private boolean wasPressedDown;

    public void init(HardwareMap hwMap) {
        motor1 = hwMap.get(DcMotor.class, "shooter");

        motor1.setPower(0.0);
        motor1.setDirection(DcMotorSimple.Direction.FORWARD);
        motor1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motor1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
    }

    public void update(Gamepad gamepad) {

        if (gamepad.dpad_up && !wasPressedUp) {
            shooterPower = 0.5;
        }
        else if (gamepad.dpad_right && !wasPressedRight) {
            shooterPower = 1.0;
        }
        else if (gamepad.dpad_down && !wasPressedDown) {
            shooterPower = 0.0;
        }
        if (gamepad.right_bumper && !wasPressedRT) {
            motorOn = !motorOn;
        }

        motor1.setPower(motorOn ? shooterPower : 0.0);
        wasPressedRT = gamepad.right_bumper;
        wasPressedUp = gamepad.dpad_up;
        wasPressedRight = gamepad.dpad_right;
        wasPressedDown = gamepad.dpad_down;
    }
    public boolean isMotorOn() {
        return motorOn;
    }
    public double getShooterPower() {
        return shooterPower;
    }
}