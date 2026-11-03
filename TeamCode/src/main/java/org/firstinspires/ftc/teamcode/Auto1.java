package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.pedropathing.follower.Follower;
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

@Autonomous
public class Auto1 extends OpMode {

    private Follower follower;
    private final PoseFactory p = PoseFactory.degrees();
    private final Pose startPose = p.of(24, 24, 0);
    private final Pose pose2 = p.of(48, 48, 90);
    private final Pose controlPose = p.of(80,70 , 45);
    private final Pose pose3 = p.of(86, 86, 0);
    private Path path1() {
        return line(startPose, pose2).linear(startPose, pose2); //linear path from startPose to pose2 means οτι γυρναει οο παει
    }
    private Path path2() {
        return curve(pose2, controlPose, pose3).linear(pose2, pose3);
    }
    private Command autoRoutine() {
        return sequential(
                follow(follower, path1()),
                // Mechanisms εδω μπενουν
                follow(follower, path2())
        );
    }



    @Override
    public void init() {
        Scheduler.reset();
        follower = Constants.create(hardwareMap);
        follower.setPose(startPose);
        follower = Constants.create(hardwareMap);
        follower.setPose(startPose);
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