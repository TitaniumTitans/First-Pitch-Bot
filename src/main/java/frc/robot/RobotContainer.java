// Copyright (c) FIRST and other WPILib contributors.

// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Launcher;


public class RobotContainer {
    CommandXboxController m_controller = new CommandXboxController(0);
    Drivetrain m_drivetrain = new Drivetrain();
    Launcher m_launcher = new Launcher();
    public RobotContainer()
    {
        configureBindings();
    }
    
    
    private void configureBindings() {
        m_drivetrain.setDefaultCommand(
                m_drivetrain.Drive(m_controller)
        );
        m_controller.a().onTrue(m_launcher.Shoot(true));
    }
    
    
    public Command getAutonomousCommand()
    {
        return Commands.print("No autonomous command configured");
    }
}
