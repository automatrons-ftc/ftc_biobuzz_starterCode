package org.firstinspires.ftc.teamcode.Tools.Pedro;

import com.pedropathing.algorithm.Foresight;
import com.pedropathing.revhub.drivetrains.Mecanum;
import com.pedropathing.revhub.localizers.PinpointLocalizer;
import com.pedropathing.tuning.autotune.Procedure;
import com.pedropathing.tuning.autotune.Tuner;

import org.firstinspires.ftc.teamcode.Tools.Pedro.Procedure.ForesightTuner;

public class Tuning {
    // Tuners go here
    @Tuner
    public static Procedure mecanumTuner() {
        return new org.firstinspires.ftc.teamcode.pedro.procedures.MecanumTuner();
    }
//    @Tuner
//    public static Procedure tests() {
//        return new org.firstinspires.ftc.teamcode.pedro.procedures.Tests(hardwareMap -> new Mecanum(hardwareMap, Constants.drivetrainConfig), null, null);
//    }
    @Tuner
    public static Procedure pinpointTuner() {
        return new org.firstinspires.ftc.teamcode.pedro.procedures.PinpointTuner();
    }
//    @Tuner
//    public static Procedure tests() {
//        return new org.firstinspires.ftc.teamcode.pedro.procedures.Tests(hardwareMap -> new Mecanum(hardwareMap, Constants.drivetrainConfig), (hardwareMap -> new PinpointLocalizer(hardwareMap, Constants.localizerConfig)), null);
//    }
    @Tuner
    public static Procedure foresightTuner() {
        return new ForesightTuner((hardwareMap) -> new PinpointLocalizer(hardwareMap, Constants.localizerConfig), (hardwareMap) -> new Mecanum(hardwareMap, Constants.drivetrainConfig));
    }
    @Tuner
    public static Procedure tests() {
        return new org.firstinspires.ftc.teamcode.pedro.procedures.Tests(hardwareMap -> new Mecanum(hardwareMap, Constants.drivetrainConfig), (hardwareMap -> new PinpointLocalizer(hardwareMap, Constants.localizerConfig)), () -> new Foresight(Constants.foresightConfig));
    }

}