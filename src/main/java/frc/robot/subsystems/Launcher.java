package frc.robot.subsystems;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Launcher extends SubsystemBase {
    private final Solenoid m_release = new Solenoid(PneumaticsModuleType.CTREPCM, 0);
    private final Solenoid m_chamber = new Solenoid(PneumaticsModuleType.CTREPCM, 1);
    private final DigitalOutput m_ledIndicator = new DigitalOutput(0);

    private final AnalogInput m_pressureInpt = new AnalogInput(0);

    private double pressure = 0.0;

    public Launcher() {
        m_release.set(false);
        m_chamber.set(false);
    }

  @Override
  public void periodic() {
    pressure = ((m_pressureInpt.getVoltage() - 0.5) / 4.0) * 200.0;
  }

  public Command openChamberValve() {
      return runEnd(
          () -> m_chamber.set(true),
          () -> m_chamber.set(false)
      );
  }

  public Command openShotValve() {
    return runEnd(
        () -> m_release.set(true),
        () -> m_release.set(false)
    );
  }

  public Command Shoot() {
      // load pressure into the shot chamber
    return run(m_release::close)
        .withTimeout(1.0)
        .andThen(run(() -> m_chamber.set(true))
            .withTimeout(3.0))
//          .until(() -> pressure > 180.0)
        // close SCBA tank prep valve
        .andThen(run(() -> m_chamber.set(false))
            .withTimeout(0.5))
        // actually trigger shot
        .andThen(run(() -> m_release.set(true))
            .withTimeout(0.5))
        // close all valves for safety
        .finallyDo(() -> {
          m_chamber.set(false);
          m_release.set(false);
        });
  }
}
