package org.firstinspires.ftc.teamcode.Tools.Pedro;

import com.pedropathing.algorithm.Foresight;
import com.pedropathing.algorithm.ForesightConfig;
import com.pedropathing.controllers.Controller;
import com.pedropathing.follower.Follower;
import com.pedropathing.math.Matrix;
import com.pedropathing.math.Vector2D;
import com.pedropathing.revhub.drivetrains.Mecanum;
import com.pedropathing.revhub.drivetrains.MecanumConfig;
import com.pedropathing.revhub.localizers.PinpointConfig;
import com.pedropathing.revhub.localizers.PinpointLocalizer;
import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class Constants {
    public static Follower create(HardwareMap h) {
        // return new Follower(Drivetrain, Localizer, Foresight);
        return new Follower(
                new PinpointLocalizer(h, localizerConfig),
                new Mecanum(h, drivetrainConfig),
                new Foresight(foresightConfig)
        );
    }
    public static MecanumConfig drivetrainConfig = new MecanumConfig(c -> {
        c.frontLeftName.set("motorFL");
        c.frontRightName.set("motorFR");
        c.backLeftName.set("motorBL");
        c.backRightName.set("motorBR");
        c.frontLeftDirection.set(DcMotorSimple.Direction.REVERSE);
        c.frontRightDirection.set(DcMotorSimple.Direction.FORWARD);
        c.backLeftDirection.set(DcMotorSimple.Direction.REVERSE);
        c.backRightDirection.set(DcMotorSimple.Direction.FORWARD);
    });
    public static PinpointConfig localizerConfig = new PinpointConfig(c -> {
        c.name.set("pinpoint");
        c.podType.set(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD);
        c.xPodOffset.set(-2.767385647991511);
        c.yPodOffset.set(-2.855383805402621);
        c.xPodDirection.set(GoBildaPinpointDriver.EncoderDirection.REVERSED);
        c.yPodDirection.set(GoBildaPinpointDriver.EncoderDirection.REVERSED);
        c.globalDistanceUnit.set(DistanceUnit.INCH);
        c.offsetUnits.set(DistanceUnit.INCH);
    });
    public static ForesightConfig foresightConfig = new ForesightConfig(
            c -> {
                Controller primaryTranslationalForward = Controller.proportional(0.3703748892371922);
                Controller secondaryTranslationalForward = Controller.proportional(0.13684362497383382);
                Controller primaryTranslationalLateral = Controller.proportional(0.6462213220451902);
                Controller secondaryTranslationalLateral = Controller.proportional(0.23876151114395536);

                c.forwardTranslational.set(Controller.piecewise(secondaryTranslationalForward).put(2.5, primaryTranslationalForward));
                c.strafeTranslational.set(Controller.piecewise(secondaryTranslationalLateral).put(2.5, primaryTranslationalLateral));

                c.coast.set(Controller.proportionalFeedforward(0.013695847784555435));
                c.brake.set(Controller.proportionalFeedforward(0.01164147061687212));

                c.headingFeedback.set(Controller.proportional(6.735387435829609));
                c.headingBrakeCoefficients.set(Vector2D.cartesian(0.043944388008773747, 0.007163748910303682));

                c.linearBrakeCoefficients.set(Matrix.diag(0.08941257810450676, 0.033232227932521216));
                c.quadraticBrakeCoefficients.set(Matrix.diag(0.0021670332447660764, 0.002615123179501483));

                c.maxAchievableForwardVelocity.set(74.09388245719988);
                c.maxAchievableStrafeVelocity.set(56.46747920877099);
                c.naturalForwardDeceleration.set(26.434646302193052);
                c.naturalStrafeDeceleration.set(66.93073711240984);
            }
    );

}