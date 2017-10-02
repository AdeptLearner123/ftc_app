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

@TeleOp(name="Dmitry Mecanum Teleop", group="Pushbot")
//@Disabled
public class DmitryMecanumTeleop extends OpMode{

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

    private void move(double lf, double lb, double rf, double rb) {
        telemetry.addData("Moving: ", lf + " " + lb + " " + rf + " " + rb);
        robot.leftFrontMotor.setPower(lf);
        robot.leftBackMotor.setPower(lb);
        robot.rightFrontMotor.setPower(rf);
        robot.rightBackMotor.setPower(rb);
    }
    private double avg(double x, double y) {
        return (x + y) / 2.0;
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
        double x = gamepad1.left_stick_x;
        double y = -gamepad1.left_stick_y;
        double turn = gamepad1.right_stick_x;

        if (Math.abs(x) <= 0.2) {
            x = 0;
        }
        if (Math.abs(y) <= 0.2) {
            y = 0;
        }
        if (Math.abs(turn) <= 0.1) {
            turn = 0;
        }

        if(gamepad1.right_trigger > 0.5){
            x /= 5;
            y /= 5;
            turn /= 5;
        }
        telemetry.addData("Controller: ", "b: " + gamepad1.b + " right trigger: " + gamepad1.right_trigger + " left trigger: " + gamepad1.left_trigger + " x: " + x + " y: " + y);

        if (turn == 0) {
            // lf, lb, rf, rb
            if (y == 0) {
                //strafe
                if (x != 0) {
                    move(-x, x, x, -x);
                } else { //don't move
                    move(0, 0, 0, 0);
                }
            } else if (x == 0) {
                //forward/backward
                move(-y, -y, -y, -y);
            } else { // if x != 0 and y != 0
                //forward right
                if (x > 0 && y > 0) {
                    //move (1, 0, 0, 1);
                    move(-0.5, 0, 0, -0.5);
                    //move(avg(x, y), x - y, x - y, avg(x, y));
                } else if (x > 0 && y < 0) {
                    //move (0, -1, -1, 0);
                    move (0, 0.5, 0.5, 0);
                    //move(-x + y, -avg(x, y), -avg(x, y), -x + y);
                } else if (x < 0 && y < 0) {
                    //move(-1, 0, 0, -1);
                    move(0.5, 0, 0, 0.5);
                    //move(-avg(x, y), -x + y, -x + y, -avg(x, y));
                } else if (x < 0 && y > 0) {
                    //move(0, 1, 1, 0);
                    move(0, -0.5, -0.5, 0);
                    //move(x - y, avg(x, y), avg(x, y), x - y);
                }
            }
        } else {
            // lf, lb, rf, rb
            move(turn, turn, -turn, -turn);
        }
        if (gamepad1.x) {
            move(1, 0, 0, 0);
        }
        if (gamepad1.y) {
            move(0, 1, 0, 0);
        }
        if (gamepad1.a) {
            move(0, 0, 1, 0);
        }
        if (gamepad1.b) {
            move(0, 0, 0, 1);
        }

        System.out.println("test'");

        updateTelemetry(telemetry);
    }

    /*
     * Code to run ONCE after the driver hits STOP
     */
    @Override
    public void stop() {
    }
}