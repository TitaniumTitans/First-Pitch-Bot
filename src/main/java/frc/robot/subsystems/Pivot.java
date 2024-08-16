package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.TalonSRXControlMode;
import com.ctre.phoenix.motorcontrol.TalonSRXFeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Pivot extends SubsystemBase {
  // TODO find actual values for these
  private final double ENCODER_COUNTS_PER_REVOLUTION = 1.0;
  private final double PIVOT_GEAR_RATIO = 1.0;

  private final TalonSRX m_pivot = new TalonSRX(5);
  private final double minPivotAngle = -200.0;
  private final double maxPivotAngle = 2870.0;

  public Pivot() {
    m_pivot.setInverted(true);
    m_pivot.configSelectedFeedbackSensor(TalonSRXFeedbackDevice.QuadEncoder, 0, 10);
  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("Pivot Position", -m_pivot.getSelectedSensorPosition());
  }

  public void runMotor(double speed) {
    double angle = -m_pivot.getSelectedSensorPosition() * ENCODER_COUNTS_PER_REVOLUTION * PIVOT_GEAR_RATIO;

    if (angle < minPivotAngle && speed < 0) {
      speed = 0.;
    } else if (angle > maxPivotAngle && speed > 0) {
      speed = 0;
    }

    m_pivot.set(TalonSRXControlMode.PercentOutput, speed);
  }

  public void resetEncoder() {
    m_pivot.setSelectedSensorPosition(0.0);
  }

  public Command runMotorFactory(double speed) {
    return runEnd(() -> runMotor(speed), () -> runMotor(0.0));
  }

  public Command resetEncoderFactory() {
    return runOnce(this::resetEncoder);
  }
}
