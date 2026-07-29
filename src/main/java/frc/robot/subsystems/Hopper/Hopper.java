package frc.robot.subsystems.Hopper;

import com.ctre.phoenix6.controls.VoltageOut;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import org.littletonrobotics.junction.Logger;

import frc.robot.RobotConstants;

/**
 * Ball-handling subsystem consolidating the "Hopper" (Astin-Hopper branch)
 * and "Transfer" (Nathan-branch) subsystems, which drove the same physical
 * mechanism: the floor bed, the side sweepers, and the turret transfer feed.
 */
public class Hopper extends SubsystemBase {
    private static Hopper INSTANCE;

    private final TalonFX bedBottom = HopperConstants.bedBottom;
    private final TalonFX bedTopFollower = HopperConstants.bedTopFollower;
    private final TalonFX sideSweepBottom = HopperConstants.sideSweepBottom;
    private final TalonFX sideSweepTopFollower = HopperConstants.sideSweepTopFollower;
    private final TalonFX turretTransfer = HopperConstants.turretTransfer;

    private final VoltageOut bedVoltageRequest = new VoltageOut(0.0).withEnableFOC(RobotConstants.ENABLE_FOC);
    private final VoltageOut sideSweepVoltageRequest = new VoltageOut(0.0).withEnableFOC(RobotConstants.ENABLE_FOC);
    private final VoltageOut turretTransferVoltageRequest = new VoltageOut(0.0).withEnableFOC(RobotConstants.ENABLE_FOC);

    public enum SweeperState {
        SHOOTING,
        JAMMED, // may not use
        IDLING
    }

    public enum BedState {
        IDLING,
        INTAKING
    }

    public SweeperState currentSweeperState = SweeperState.IDLING;
    public BedState currentBedState = BedState.IDLING;

    private Hopper() {}

    public static synchronized Hopper getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Hopper();
        }
        return INSTANCE;
    }

    @Override
    public void periodic() {
        applyState();
        publishLog();
    }

    private void applyState() {
        switch (currentSweeperState) {
            case SHOOTING:
                sideSweepVoltageRequest.Output = HopperConstants.SIDE_SWEEP_SHOOT_VOLTAGE;
                turretTransferVoltageRequest.Output = HopperConstants.TURRET_TRANSFER_SHOOT_VOLTAGE;
                break;
            case JAMMED:
                break;
            case IDLING:
                sideSweepVoltageRequest.Output = 0;
                turretTransferVoltageRequest.Output = 0;
                break;
        }

        switch (currentBedState) {
            case INTAKING:
                bedVoltageRequest.Output = HopperConstants.BED_INTAKE_VOLTAGE;
                break;
            case IDLING:
                bedVoltageRequest.Output = 0;
                break;
        }

        bedBottom.setControl(bedVoltageRequest);
        sideSweepBottom.setControl(sideSweepVoltageRequest);
        turretTransfer.setControl(turretTransferVoltageRequest);
    }

    private void publishLog() {
        Logger.recordOutput("Rebuilt/Hopper/Bed/currentBedState", currentBedState);
        Logger.recordOutput("Rebuilt/Hopper/Sweeper/currentSweeperState", currentSweeperState);

        Logger.recordOutput("Rebuilt/Hopper/Bed/voltageRequest", bedVoltageRequest.Output);
        Logger.recordOutput("Rebuilt/Hopper/Sweeper/voltageRequest", sideSweepVoltageRequest.Output);
        Logger.recordOutput("Rebuilt/Hopper/TurretTransfer/voltageRequest", turretTransferVoltageRequest.Output);

        logMotor("Bed/Bottom", bedBottom);
        logMotor("Bed/TopFollower", bedTopFollower);
        logMotor("Sweeper/Bottom", sideSweepBottom);
        logMotor("Sweeper/TopFollower", sideSweepTopFollower);
        logMotor("TurretTransfer", turretTransfer);
    }

    private void logMotor(String name, TalonFX motor) {
        Logger.recordOutput("Rebuilt/Hopper/Motors/" + name + "/Velocity", motor.getVelocity().getValueAsDouble());
        Logger.recordOutput("Rebuilt/Hopper/Motors/" + name + "/Stator", motor.getStatorCurrent().getValueAsDouble());
        Logger.recordOutput("Rebuilt/Hopper/Motors/" + name + "/Supply", motor.getSupplyCurrent().getValueAsDouble());
    }
}
