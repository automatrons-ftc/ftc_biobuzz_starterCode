package org.firstinspires.ftc.teamcode.Mechanisms;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
public class Shooter {


    private DcMotor motor1;
    private boolean motorOn;
    private boolean motor2On;
    private boolean wasPressedRT;

    public void init(HardwareMap hwMap) {
        motor1 = hwMap.get(DcMotor.class, "shooter");
        motor1.setPower(0.0);
        motor1.setDirection(DcMotorSimple.Direction.FORWARD);
        motor1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motor1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT); // Change για φλοατ και βρεακ οταν σταματαει αναλωγως, στο ιντακε κανωνικα μας βολευει μπρεακ
    }

    public void inhale(double power){
        motor1.setPower(power);
    }

    public void update(Gamepad gamepad) {
        if ((gamepad.dpad_up) && !wasPressedRT){
            motorOn = !motorOn;
        }
        if ((gamepad.dpad_right )&& !wasPressedRT) {
            motor2On = !motor2On;
        }
        else if ((gamepad.dpad_down) && !wasPressedRT) {
            motorOn = false;
        }
        wasPressedRT = gamepad.right_bumper;
        inhale(motorOn ? 0.5 : 0.0);
        wasPressedRT = gamepad.right_bumper;
        inhale(motor2On ? 1.0 : 0.0);

    }

    public boolean isMotorOn() {
        return motorOn;
    }
}
