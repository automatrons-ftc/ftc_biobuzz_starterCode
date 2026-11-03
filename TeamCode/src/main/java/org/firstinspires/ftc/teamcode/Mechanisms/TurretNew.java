package org.firstinspires.ftc.teamcode.Mechanisms;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import com.pedropathing.follower.Follower;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;
import org.firstinspires.ftc.teamcode.Tools.PIDF;
import org.firstinspires.ftc.teamcode.Tools.Pedro.Constants;

public class TurretNew {
    private DcMotorEx turretMotor;
    private Telemetry telemetry;

    private double TICKS_PER_DEGREE = 6.5;
    private final PIDF  pidf;

    public TurretNew(HardwareMap hwMap, Telemetry telemetry) {

        this.telemetry = telemetry;

        turretMotor = hwMap.get(DcMotorEx.class, "rotary");

        pidf = new PIDF();
    }
    private Follower follower;


    public void init() {
        follower = Constants.create(hardwareMap);
    }
    public void loop() {
        follower.update();
    }
}
