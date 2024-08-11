package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Launcher extends SubsystemBase {
    private final Solenoid m_solenoid = new Solenoid(PneumaticsModuleType.CTREPCM, 0);
    public Launcher(){}
    public Command Shoot(boolean canShoot) {
        return run(() -> m_solenoid.set(canShoot));
    }
}
