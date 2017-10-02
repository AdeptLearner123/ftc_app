/*
Copyright (c) 2016 Robert Atkinson

All rights reserved.

Redistribution and use in source and binary forms, with or without modification,
are permitted (subject to the limitations in the disclaimer below) provided that
the following conditions are met:

Redistributions of source code must retain the above copyright notice, this list
of conditions and the following disclaimer.

Redistributions in binary form must reproduce the above copyright notice, this
list of conditions and the following disclaimer in the documentation and/or
other materials provided with the distribution.

Neither the name of Robert Atkinson nor the names of his contributors may be used to
endorse or promote products derived from this software without specific prior
written permission.

NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
"AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESSFOR A PARTICULAR PURPOSE
ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR
TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name="Mecanum Teleop", group="Pushbot")
//@Disabled
public class MecanumTeleop extends OpMode{

    /* Declare OpMode members. */
    PushbotHardware robot       = new PushbotHardware();
    /*
     * Code to run ONCE when the driver hits INIT
     */

    @Override
    public void init() {
        /* Initialize the hardware variables.
         * The init() method of the hardware class does all the work here
         */
        robot.init(hardwareMap);

        // Send telemetry message to signify robot waiting;
        telemetry.addData("Say", "Robot is waiting");    //
        // Send telemetry message to signify robot running;
        // *******************************************
        telemetry.addData("Welcome to the Mecanum robot", "");
        // *******************************************
        updateTelemetry(telemetry);
    }

    /*
     * Code to run REPEATEDLY after the driver hits INIT, but before they hit PLAY
     */

    /*
     * Code to run ONCE when the driver hits PLAY
     */
    @Override
    public void start() {
    }

    private void move(double left, double right, double turning) {
        robot.leftFrontMotor.setPower(left - turning);
        robot.leftBackMotor.setPower(left - turning);
        robot.rightFrontMotor.setPower(right + turning);
        robot.rightBackMotor.setPower(right + turning);

        //telemetry.addData("driver", left+ "    " + right);
    }

    private void move4(double lf, double rf, double lb, double rb) {
        robot.leftFrontMotor.setPower(lf);
        robot.leftBackMotor.setPower(lb);
        robot.rightFrontMotor.setPower(rf);
        robot.rightBackMotor.setPower(rb);
    }

    /*
    private void vertical(boolean dir) {

    }
    private void horizontal (boolean dir) {

    }
    /*
     * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
     */
    @Override
    public void loop() {
        double y = 0 - gamepad1.left_stick_y;
        double x = 0 - gamepad1.left_stick_x;
        double turn = 0 - gamepad1.right_stick_x;
        if(gamepad1.right_trigger > 0.5){
            x /= 10;
            y /= 10;
        }

        telemetry.addData("Controller: ", "b: " + gamepad1.b + " right trigger: " + gamepad1.right_trigger + " left trigger: " + gamepad1.left_trigger + " x: " + x + " y: " + y);
        if (x != 0) {
            if (y > 0) {
                if (gamepad1.b) {
                    move((y - x)/3, (y + x)/3, turn/3);
                    telemetry.addData("Driver", "turning moving forward slowly");
                } else {
                    move(y - x, y + x, turn);
                    telemetry.addData("Driver", "turning moving forward");
                }
            } else if (y < 0) {
                if (gamepad1.b)
                {
                    move((y + x)/3, (y - x)/3, turn/3);
                    telemetry.addData("Driver", "turning moving backward slowly");
                } else {
                    move(y + x, y - x, turn);
                    telemetry.addData("Driver", "turning moving backward");
                }
            } else {
                if (gamepad1.b) {
                    telemetry.addData("Driver","just turning slowly");
                    move(-x/3, x/3, turn/3);
                } else {
                    telemetry.addData("Driver", "just turning");
                    move(-x, x, turn);
                }
            }
        } else {
            if (gamepad1.b) {
                telemetry.addData("Driver","just moving slowly");
                move(y/3, y/3, turn/3);
            } else {
                telemetry.addData("Driver", "just moving");
                move(y, y, turn);
            }
        }

        updateTelemetry(telemetry);
    }

    /*
     * Code to run ONCE after the driver hits STOP
     */
    @Override
    public void stop() {
    }
}