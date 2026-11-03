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



    @Override
    public void init() {
        Scheduler.reset();
        follower = Constants.create(hardwareMap);
        follower.setPose(startPose);


    }

    @Override
    public void start() {
        schedule(follow(follower, path1()));

    }

    @Override
    public void loop() {

    }
}