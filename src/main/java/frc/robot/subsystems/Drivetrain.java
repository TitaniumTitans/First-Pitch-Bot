package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

public class Drivetrain extends SubsystemBase {
    private final WPI_TalonSRX m_flTalon = new WPI_TalonSRX(0);
    private final WPI_TalonSRX m_frTalon = new WPI_TalonSRX(1);
    private final WPI_TalonSRX m_blTalon = new WPI_TalonSRX(2);
    private final WPI_TalonSRX m_brTalon = new WPI_TalonSRX(3);
    private final DifferentialDrive m_diffDrive;

    public Drivetrain() {
        m_brTalon.follow(m_frTalon);
        m_blTalon.follow(m_flTalon);

        m_flTalon.setInverted(true);

        m_diffDrive = new DifferentialDrive(m_flTalon,m_frTalon);
    }

    public void arcadeDrive(double xAxisSpeed, double zAxisRotate) {
        m_diffDrive.arcadeDrive(xAxisSpeed, zAxisRotate);
    }
    public Command Drive(CommandXboxController controller) {
        return runEnd(() -> arcadeDrive(controller.getLeftY(), controller.getRightX()),
                () -> arcadeDrive(0.0,0.0));
    }
}
