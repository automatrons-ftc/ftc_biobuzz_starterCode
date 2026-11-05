package org.firstinspires.ftc.teamcode.Mechanisms;

import com.pedropathing.math.Pose;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;
import org.firstinspires.ftc.teamcode.Tools.PIDF;

public class TurretNew {
    private DcMotorEx turretMotor;
    private Telemetry telemetry;
    private final PIDF pidf;

    private final double TICKS_PER_DEGREE = 6.5;
    private final double CENTER_OFFSET = 156;

    private final double MAX_ANGLE = 60;
    private final double MIN_ANGLE = -60;

    public enum ZeroingState {
        ZEROING,
        IDLE
    }

    private ZeroingState state = ZeroingState.ZEROING;

    private final double STALL_POWER = 2.0; //se amber
    private final long STALL_TIME = 200;// se milisec nomizo
    private long timeStart = 0; //arxiko time gia to zeroing
    private boolean timerLimit = false; //to orio tou zero

    //ta apo kato einai ta dio buttons to zero kai to on off dld
    private boolean resetPrevButton = false;
    private boolean aimEnabled = true;
    private boolean wasPressedAim = false;


    public TurretNew(HardwareMap hm, Telemetry telemetry) {
        this.telemetry = telemetry;

        turretMotor = hm.get(DcMotorEx.class, "rotary");

        turretMotor.setMode(
                DcMotor.RunMode.STOP_AND_RESET_ENCODER
        );
        turretMotor.setMode(
                DcMotor.RunMode.RUN_WITHOUT_ENCODER
        );
        turretMotor.setZeroPowerBehavior(
                DcMotor.ZeroPowerBehavior.BRAKE
        );

        pidf = new PIDF();
        pidf.reset();
    }

    public void update(Pose robotPose,
                       double goalX,
                       double goalY,
                       boolean resetButton,
                       boolean aimButton) {

        //το ρεσετ για να βρει το 0 με mechanical stop σορρυ όντος δεν θημάμαι αν έχουμε στο περσινο my bad
        if (resetButton && !resetPrevButton) {
            state = ZeroingState.ZEROING;

            turretMotor.setMode(
                    DcMotor.RunMode.RUN_WITHOUT_ENCODER
            );

            pidf.reset();
            timerLimit = false;
        }

        resetPrevButton = resetButton;

        //αυτοματο αιμ ον οφφ
        if (aimButton && !wasPressedAim) {
            aimEnabled = !aimEnabled;
        }
        wasPressedAim = aimButton;

        //gia to zero
        if (state == ZeroingState.ZEROING) {
            zeroing();
            return;
        }
        //gia to on off
        if (!aimEnabled) {
            turretMotor.setPower(0);
            return;
        }

        //edo paizei na to exo kanei olo lathos to pull on positions aplos me ta kainouria to brhka etsi sto  pedro
        double robotX = robotPose.x();
        double robotY = robotPose.y();

        double robotHeading = Math.toDegrees(
                robotPose.heading()
        );


        //apostash
        double dx = goalX - robotX;
        double dy = goalY - robotY;

        double goalAngle = Math.toDegrees(
                Math.atan2(dy, dx)
        );


        double turretAngle = goalAngle - robotHeading;

        // nomizo to exoume etsi to phra apo persi to exo valei 150 - (-150) gia na mhn snaparei sto kalodio alla den eimai sigours pou paei thelei testing
        turretAngle = ((turretAngle + 180) % 360 + 360)
                % 360 - 180;

        double limitedAngle = Math.max(
                MIN_ANGLE,
                Math.min(MAX_ANGLE, turretAngle)
        );

        boolean canAimAtGoal =
                Math.abs(turretAngle - limitedAngle) < 0.001;

        int desiredPosition = (int) Math.round(
                (limitedAngle - CENTER_OFFSET)
                        * TICKS_PER_DEGREE
        );

        runPID(desiredPosition);

        telemetry.addData("Robot X", robotX);
        telemetry.addData("Robot Y", robotY);

        telemetry.addData("Robot Heading", robotHeading);

        telemetry.addData("Goal Angle", goalAngle);

        telemetry.addData("Required Turret Angle", turretAngle);

        telemetry.addData("Limited Turret Angle", limitedAngle);

        telemetry.addData("Can Aim At Goal", canAimAtGoal);

        telemetry.addData("Target Position", desiredPosition);

        telemetry.addData(
                "Current Position",
                turretMotor.getCurrentPosition()
        );
    }

    private void zeroing() {

        turretMotor.setPower(0.3);

        if (turretMotor.getCurrent(CurrentUnit.AMPS)
                >= STALL_POWER) {

            if (!timerLimit) {

                timerLimit = true;
                timeStart = System.currentTimeMillis();

            } else if (System.currentTimeMillis() - timeStart
                    >= STALL_TIME) {

                turretMotor.setPower(0);

                turretMotor.setMode(
                        DcMotor.RunMode.STOP_AND_RESET_ENCODER
                );

                turretMotor.setMode(
                        DcMotor.RunMode.RUN_WITHOUT_ENCODER
                );

                state = ZeroingState.IDLE;

                timerLimit = false;
                pidf.reset();
            }

        } else {

            timerLimit = false;
        }

        telemetry.addData("Turret State", state);

        telemetry.addData(
                "Motor Current",
                turretMotor.getCurrent(CurrentUnit.AMPS)
        );
    }

    private void runPID(int desiredPosition) {

        int currentPosition =
                turretMotor.getCurrentPosition();

        double power = pidf.calculate(
                desiredPosition,
                currentPosition
        );

        turretMotor.setPower(power);

        double error = desiredPosition - currentPosition;

        telemetry.addData(
                "Turret Error (degrees)",
                error / TICKS_PER_DEGREE
        );

        telemetry.addData("Turret Power", power);
    }
}
