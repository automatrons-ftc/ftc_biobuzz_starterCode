
package org.firstinspires.ftc.teamcode.Tools;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.util.Range;

@Config
public class PIDF {

    public static double k_p = 0.008;
    public static double k_i = 0.003;
    public static double k_d = 0.0007;
    public static double k_f = 0.0;

    public static double MAX_INTEGRAL = 0.3;

    private double p = 0;
    private double i = 0;
    private double d = 0;
    private double f = 0;

    private double previous_error = 0;
    private double previous_time = 0;

    public double calculate(double desired_position,
                            double current_position) {

        double current_time = System.nanoTime() / 1e9;
        double dt = current_time - previous_time;

        double current_error = desired_position - current_position;

        p = k_p * current_error;

        if (dt > 0 && dt < 0.1) {
            i += k_i * current_error * dt;
        }

        i = Range.clip(i, -MAX_INTEGRAL, MAX_INTEGRAL);

        if (dt > 0) {
            d = k_d * (current_error - previous_error) / dt;
        } else {
            d = 0;
        }

        f = k_f * Math.signum(current_error);

        double power = p + i + d + f;
        power = Range.clip(power, -1.0, 1.0);

        previous_error = current_error;
        previous_time = current_time;

        return power;
    }

    public void reset() {
        p = 0;
        i = 0;
        d = 0;
        f = 0;

        previous_error = 0;
        previous_time = System.nanoTime() / 1e9;
    }
}
