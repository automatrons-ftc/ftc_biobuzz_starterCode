package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.pedropathing.follower.Follower;

import org.firstinspires.ftc.teamcode.Mechanisms.AutoShooter;
import org.firstinspires.ftc.teamcode.Mechanisms.Intake;
import org.firstinspires.ftc.teamcode.Tools.Pedro.Constants;
import com.pedropathing.api.PoseFactory;
import com.pedropathing.math.Pose;
import static com.pedropathing.api.Paths.*;
import com.pedropathing.paths.Path;
import com.pedropathing.ivy.Scheduler;
import static com.pedropathing.ivy.Scheduler.schedule;
import static com.pedropathing.ivy.pedro.PedroCommands.follow;
import com.pedropathing.ivy.Command;
import com.pedropathing.ivy.Scheduler;

import static com.pedropathing.ivy.Scheduler.schedule;
import static com.pedropathing.ivy.groups.Groups.sequential;
import static com.pedropathing.ivy.pedro.PedroCommands.follow;
import static com.pedropathing.ivy.commands.Commands.waitMs;

@Autonomous
public class AutoBB1 extends OpMode {
    private Intake intake;
    private AutoShooter autoShooter;
    private Follower follower;
    private final PoseFactory poseFactory = PoseFactory.degrees();

    private final Pose start = poseFactory.of(58.9891, 11.4206, 90);
    private final Pose path1 = poseFactory.of(11.0028, 11.4438, 270);
    private final Pose path1Control1 = poseFactory.of(35.5367, 29.7888, 0);
    private final Pose point2Start = poseFactory.of(11.0028, 11.4438, 270);
    private final Pose point2 = poseFactory.of(57.7244, 35.7081, 270);
    private final Pose point2Control1 = poseFactory.of(45.0762, 14.3154, 0);
    private final Pose point3Start = poseFactory.of(57.7244, 35.7081, 270);
    private final Pose point3 = poseFactory.of(58.7589, 107.3411, 270);
    private final Pose point4Start = poseFactory.of(58.7589, 107.3411, 270);
    private final Pose point4 = poseFactory.of(47.5726, 119.2219, 90);
    private final Pose point5Start = poseFactory.of(47.5726, 119.2219, 90);
    private final Pose point5 = poseFactory.of(58.3044, 108.5128, 270);
    private final Pose point6Start = poseFactory.of(58.1255, 118.3516, 270);
    private final Pose point6 = poseFactory.of(22.9024, 71.5598, 270);
    private final Pose point6Control1 = poseFactory.of(22.0138, 108.7662, 0);
    private final Pose point7Start = poseFactory.of(22.9024, 71.5598, 270);
    private final Pose point7 = poseFactory.of(13.7806, 46.7185, 180);
    private final Pose point7Control1 = poseFactory.of(24.2893, 49.7583, 0);
    private final Pose point8Start = poseFactory.of(13.7806, 46.7185, 180);
    private final Pose point8 = poseFactory.of(57.5405, 35.6996, 90);
    private final Pose point8Control1 = poseFactory.of(30.7735, 29.3302, 0);
    private final Pose point9Start = poseFactory.of(57.5405, 35.6996, 90);
    private final Pose point9 = poseFactory.of(12.5, 106.5561, 90);
    private final Pose point9Control1 = poseFactory.of(13.0522, 27.6406, 0);
    public Path path1() {
        return curve(start, path1Control1, path1).linear(start, path1);
    }

    public Path path2() {
        return curve(point2Start, point2Control1, point2).linear(point2Start, point2);
    }

    public Path path3() {
        return line(point3Start, point3).linear(point3Start, point3);
    }

    public Path path4() {
        return line(point4Start, point4).linear(point4Start, point4);
    }

    public Path path5() {
        return line(point5Start, point5).linear(point5Start, point5);
    }

    public Path path6() {
        return curve(point6Start, point6Control1, point6).linear(point6Start, point6);
    }

    public Path path7() {
        return curve(point7Start, point7Control1, point7).linear(point7Start, point7);
    }

    public Path path8() {
        return curve(point8Start, point8Control1, point8).linear(point8Start, point8);
    }

    public Path path9() {
        return curve(point9Start, point9Control1, point9).linear(point9Start, point9);
    }
    private Command autoRoutine() {
        return sequential(
                follow(follower, path1()),
                waitMs(500),
                // Mechanisms εδω μπενουν
                follow(follower, path2()),
                waitMs(500),
                follow(follower, path3()),
                waitMs(2000),
                follow(follower, path4()),
                waitMs(500),
                follow(follower, path5()),
                waitMs(2000),
                follow(follower, path6()),
                waitMs(500),
                follow(follower, path7()),
                waitMs(500),
                follow(follower, path8()),
                waitMs(2000),
                follow(follower, path9())
        );
    }



    @Override
    public void init() {
        Scheduler.reset();
        follower = Constants.create(hardwareMap);
        follower.setPose(start);
        follower.update();
    }

    @Override
    public void start() {
        //schedule(follow(follower, path1())); αυτο δεν χριαζετε γτ ειναι μεσα στ οροθτινε
        schedule(autoRoutine());

    }

    @Override
    public void loop() {
        follower.update();
        Scheduler.execute();

        telemetry.addData("X", follower.pose().x());
        telemetry.addData("Y", follower.pose().y());
        telemetry.addData("Heading", Math.toDegrees(follower.pose().heading()));
        telemetry.addData("Follower Mode", follower.mode());
        telemetry.update();
    }

}