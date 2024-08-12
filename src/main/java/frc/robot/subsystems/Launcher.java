package frc.robot.subsystems;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Launcher extends SubsystemBase {
    private final Solenoid m_solenoid = new Solenoid(PneumaticsModuleType.CTREPCM, 0);
    private final DigitalOutput m_digitalOutput = new DigitalOutput(0);
    public Launcher() {}
    public Command Shoot(boolean canShoot) {
        return run(() -> m_digitalOutput.set(true))
                .withTimeout(1.0)
                .andThen(
                        run(() ->
                            m_digitalOutput.set(false))
                                .withTimeout(0.5)
                                .andThen(
                                        run(() -> m_solenoid.set(canShoot))
                                )
                        );
    }
}
