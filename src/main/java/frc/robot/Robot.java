// Copyright (c) FIRST and other WPILib contributors.

// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.cscore.CvSink;
import edu.wpi.first.cscore.CvSource;
import edu.wpi.first.cscore.UsbCamera;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

public class Robot extends TimedRobot
{
    private Command autonomousCommand;
    private Thread visionThread;
    
    private RobotContainer robotContainer;
    
    
    @Override
    public void robotInit() {
        visionThread = new Thread(
            () -> {
                // get usb camera
                UsbCamera camera = CameraServer.startAutomaticCapture();
                // set resolution
                camera.setResolution(800, 600);

                // sinks capture input
                CvSink sink = CameraServer.getVideo();
                // sources take video output
                CvSource outputStream = CameraServer.putVideo("Aim", 800, 600);

                // mats are opencv frames. they're expensive so we'll reuse this one
                Mat mat = new Mat();

                // this can't be true
                while(!Thread.interrupted()) {
                    // grab the image from the camera
                    if (sink.grabFrame(mat) == 0) {
                        outputStream.notifyError(sink.getError());
                        continue;
                    }

                    Imgproc.getRotationMatrix2D(
                            new Point(400, 300),
                            180,
                            1.0
                    );

                    Imgproc.circle(mat,
                            new Point(400.0, 300.0),
                            20,
                            new Scalar(255, 0, 0),
                            5);

                    outputStream.putFrame(mat);
                }

            });

        visionThread.setDaemon(true);
        visionThread.start();
    }
    
    
    @Override
    public void robotPeriodic()
    {
        CommandScheduler.getInstance().run();
    }
    
    
    @Override
    public void disabledInit() {}
    
    
    @Override
    public void disabledPeriodic() {}
    
    
    @Override
    public void disabledExit() {}
    
    
    @Override
    public void autonomousInit()
    {
        autonomousCommand = robotContainer.getAutonomousCommand();
        
        if (autonomousCommand != null)
        {
            autonomousCommand.schedule();
        }
    }
    
    
    @Override
    public void autonomousPeriodic() {}
    
    
    @Override
    public void autonomousExit() {}
    
    
    @Override
    public void teleopInit()
    {
        if (autonomousCommand != null)
        {
            autonomousCommand.cancel();
        }
    }
    
    
    @Override
    public void teleopPeriodic() {}
    
    
    @Override
    public void teleopExit() {}
    
    
    @Override
    public void testInit()
    {
        CommandScheduler.getInstance().cancelAll();
    }
    
    
    @Override
    public void testPeriodic() {}
    
    
    @Override
    public void testExit() {}
}
