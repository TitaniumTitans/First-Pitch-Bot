// Copyright (c) FIRST and other WPILib contributors.

// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Launcher;
import frc.robot.subsystems.Pivot;
import frc.robot.subsystems.Turret;


public class RobotContainer {
    CommandXboxController m_controller = new CommandXboxController(0);
    Drivetrain m_drivetrain = new Drivetrain();
    Launcher m_launcher = new Launcher();

    Pivot m_pivot = new Pivot();
    Turret m_turret = new Turret();
    public RobotContainer()
    {
        configureBindings();
    }
    
    
    private void configureBindings() {
        m_drivetrain.setDefaultCommand(
                m_drivetrain.Drive(
                        () -> MathUtil.applyDeadband(m_controller.getLeftY(), 0.1),
                        () -> MathUtil.applyDeadband(m_controller.getRightX(), 0.1)
                )
        );

//        m_controller.a().whileTrue(m_launcher.Shoot());

        m_controller.a().whileTrue(m_launcher.openChamberValve());
        m_controller.b().whileTrue(m_launcher.openShotValve());

        m_controller.leftTrigger().whileTrue(m_turret.runMotorFactory(0.4));
        m_controller.rightTrigger().whileTrue(m_turret.runMotorFactory(-0.4));

        m_controller.leftBumper().whileTrue(m_pivot.runMotorFactory(0.5));
        m_controller.rightBumper().whileTrue(m_pivot.runMotorFactory(-0.2));

        m_controller.start().onTrue(m_pivot.resetEncoderFactory()
                .alongWith(m_turret.resetEncoderFactory())
                .ignoringDisable(true));
    }
    
    
    public Command getAutonomousCommand()
    {
        return Commands.print("No autonomous command configured");
    }
}
