package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.TalonSRXControlMode;
import com.ctre.phoenix.motorcontrol.TalonSRXFeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.math.controller.ArmFeedforward;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Pivot extends SubsystemBase {
  // TODO find actual values for these
  private final double ENCODER_COUNTS_PER_REVOLUTION = 7.0;
  private final double PIVOT_GEAR_RATIO = (15752961.0 / 83521.0) * (22.0 / 54.0);

  private final WPI_TalonSRX m_pivot = new WPI_TalonSRX(5);
  private final double minPivotAngle = -200.0;
  private final double maxPivotAngle = 2870.0;
  private final ArmFeedforward feedforward = new ArmFeedforward(0.0,0.5,0.0);

  public Pivot() {
    m_pivot.setInverted(true);
    m_pivot.configSelectedFeedbackSensor(TalonSRXFeedbackDevice.QuadEncoder, 0, 10);
  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("Pivot Position", -m_pivot.getSelectedSensorPosition() / ENCODER_COUNTS_PER_REVOLUTION / PIVOT_GEAR_RATIO);
  }

  public void runMotor(double speed) {
    double angle = -m_pivot.getSelectedSensorPosition() / ENCODER_COUNTS_PER_REVOLUTION / PIVOT_GEAR_RATIO;

    if (angle < minPivotAngle && speed < 0) {
      speed = 0.;
    } else if (angle > maxPivotAngle && speed > 0) {
      speed = 0;
    }

    moveMotor(speed);
  }

  public void resetEncoder() {
    m_pivot.setSelectedSensorPosition(0.0);
  }
  public void moveMotor(double speed) {
    double output = feedforward.calculate(m_pivot.getSelectedSensorPosition()  / ENCODER_COUNTS_PER_REVOLUTION / PIVOT_GEAR_RATIO * Math.PI * 2.0, 0.0);
    m_pivot.setVoltage(output + speed * 12.0);
  }

  public Command runMotorFactory(double speed) {
    return runEnd(() -> runMotor(speed), () -> runMotor(0.0));
  }

  public Command resetEncoderFactory() {
    return runOnce(this::resetEncoder);
  }
}
