package org.firstinspires.ftc.teamcode;/* Copyright (c) 2017 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

import android.view.View;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.BasicMovements;
import org.firstinspires.ftc.teamcode.GirlsRobot;


/**
 * This file contains an minimal example of a Linear "OpMode". An OpMode is a 'program' that runs in either
 * the autonomous or the teleop period of an FTC match. The names of OpModes appear on the menu
 * of the FTC Driver Station. When an selection is made from the menu, the corresponding OpMode
 * class is instantiated on the Robot Controller and executed.
 *
 * This particular OpMode just executes a basic Tank Drive Teleop for a two wheeled robot
 * It includes all the skeletal structure that all linear OpModes contain.
 *
 * Use Android Studios to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list
 */

@Autonomous(name="Red No Park", group="Linear Opmode")
@Disabled
public class GirlsAutoRedLeftNoPark extends LinearOpMode {

    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor leftDrive = null;
    private DcMotor rightDrive = null;
    private DcMotor arm=null;
    private GirlsRobot robot = null;
    private Servo arm_servo = null;
    private ColorSensor color_sensor = null;

    private double time = 1.0;

    public void wait(double time) {
        double startTime = System.currentTimeMillis();
        while (((System.currentTimeMillis() - startTime) * Math.pow(10, -3)) < time) { /*Waiting for bot to finish*/ }
    }
    public boolean isBlue() {
        int red = color_sensor.red();
        int green = color_sensor.green();
        int blue = color_sensor.blue();
        telemetry.addData("" + blue, " RGB");
        double percent_blue = ((double) (red + green + blue)) / ((double) (blue));
        if (blue != 0 && blue != 255 && percent_blue > 0.75)
            return true;
        return false;
    }

    public boolean isRed() {
        int red = color_sensor.red();
        int green = color_sensor.green();
        int blue = color_sensor.blue();
        telemetry.addData(""+red," RGB");
        double percent_red = ((double) (red + green + blue)) / ((double) (red));
        if(red != 0 && red != 255 && percent_red > 0.75)
            return true;
        return false;
    }

    public void moving() {

    }

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        leftDrive = hardwareMap.get(DcMotor.class, "ld");
        rightDrive = hardwareMap.get(DcMotor.class, "rd");
        arm_servo = hardwareMap.get(Servo.class,"as");

        arm = hardwareMap.get(DcMotor.class, "arm");
        leftDrive.setDirection(DcMotor.Direction.FORWARD);
        rightDrive.setDirection(DcMotor.Direction.REVERSE);
        color_sensor = hardwareMap.get(ColorSensor.class, "cs");
        color_sensor.enableLed(true); //Active Mode
        robot = new GirlsRobot(0.45,0.69,rightDrive,leftDrive,arm,arm_servo,color_sensor);


        arm_servo.setPosition(0.45);
        wait(1.0);
        robot.extendArm();
        wait(1.0);
        int red = color_sensor.red();
        telemetry.addData(""+red," RED RGB VALUE");
        telemetry.addData(""+color_sensor.blue()," BLUE RGB VALUE");
        telemetry.addData(""+color_sensor.green()," GREEN RGB VALUE");
        telemetry.update();
        wait(5.0);

        if (isBlue()) {
            telemetry.addData("Color: ", "Blue!");
            telemetry.update();
            arm_servo.setPosition(0.0);
            wait(2.0);
        }
        else if (isRed()) {
            telemetry.addData("Color: ", "Red!");
            telemetry.update();
            arm_servo.setPosition(1.0);
            wait(2.0);
        }

        arm_servo.setPosition(0.45);
        robot.retractArm();
        arm_servo.setPosition(1.0);

    }
}
