package frc.robot.subsystems.Transfer;

import com.ctre.phoenix6.controls.VoltageOut;
import com.ctre.phoenix6.hardware.TalonFX;
import org.littletonrobotics.junction.Logger;
import org.littletonrobotics.junction.networktables.LoggedNetworkNumber;

import edu.wpi.first.wpilibj.motorcontrol.Talon;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.subsystems.Transfer.TransferConstants;
import frc.robot.subsystems.Transfer.TransferConstants.MotorConfig;
import frc.robot.RobotConstants;

public class Transfer extends SubsystemBase {
    
    public enum FloorRollerStates {
        IDLE,
        RUN_IN
    }

    public enum TransferStates {
        IDLE,
        RUN_IN
    }

    private FloorRollerStates floorRollerState = FloorRollerStates.IDLE;
    private TransferStates transferState = TransferStates.IDLE;

    private VoltageOut floorRollerVoltageRequest = new VoltageOut(0.0).withEnableFOC(RobotConstants.ENABLE_FOC);
    private TalonFX floorRoller = MotorConfig.floorRoller;

    LoggedNetworkNumber floorRollerIdleVoltage = new LoggedNetworkNumber("Rebuilt/Transfer/FloorRoller/IdleVoltage",10);
    LoggedNetworkNumber floorRollerRunVoltage = new LoggedNetworkNumber("Rebuilt/Transfer/FloorRoller/RunVoltage",50);

    private VoltageOut sideSweepVoltageRequest = new VoltageOut(0.0).withEnableFOC(RobotConstants.ENABLE_FOC);
    private TalonFX sideSweep = MotorConfig.sideSweep;
    private VoltageOut cornerSweepVoltageRequest = new VoltageOut(0.0).withEnableFOC(RobotConstants.ENABLE_FOC);
    private TalonFX cornerSweep = MotorConfig.cornerSweep;
    private VoltageOut turretTransferVoltageRequest = new VoltageOut(0.0).withEnableFOC(RobotConstants.ENABLE_FOC);
    private TalonFX turretTransfer = MotorConfig.turretTransfer;
    
    LoggedNetworkNumber sideSweepIdleVoltage = new LoggedNetworkNumber("Rebuilt/Transfer/SideSweep/IdleVoltage",10);
    LoggedNetworkNumber sideSweepRunVoltage = new LoggedNetworkNumber("Rebuilt/Transfer/SideSweep/RunVoltage",50);
    LoggedNetworkNumber cornerSweepIdleVoltage = new LoggedNetworkNumber("Rebuilt/Transfer/CornerSweep/IdleVoltage",10);
    LoggedNetworkNumber cornerSweepRunVoltage = new LoggedNetworkNumber("Rebuilt/Transfer/CornerSweep/RunVoltage",50);
    LoggedNetworkNumber turretTransferIdleVoltage = new LoggedNetworkNumber("Rebuilt/Transfer/TurretTransfer/IdleVoltage",10);
    LoggedNetworkNumber turretTransferRunVoltage = new LoggedNetworkNumber("Rebuilt/Transfer/TurretTransfer/RunVoltage",50);
    
    @Override
    public void periodic() {
        ApplyState();
        LogValues();
    }

    private void ApplyState() {
        switch (floorRollerState) {
            case IDLE:
                floorRollerVoltageRequest.Output = floorRollerIdleVoltage.get();
                break;
            case RUN_IN:
                floorRollerVoltageRequest.Output = floorRollerRunVoltage.get();
                break;
        }
        floorRoller.setControl(floorRollerVoltageRequest);

        switch (transferState) {
            case IDLE:
                sideSweepVoltageRequest.Output = sideSweepIdleVoltage.get();
                cornerSweepVoltageRequest.Output = cornerSweepIdleVoltage.get();
                turretTransferVoltageRequest.Output = turretTransferIdleVoltage.get();
                break;
            case RUN_IN:
                sideSweepVoltageRequest.Output = sideSweepRunVoltage.get();
                cornerSweepVoltageRequest.Output = cornerSweepRunVoltage.get();
                turretTransferVoltageRequest.Output = turretTransferRunVoltage.get();
                break;
        }
        sideSweep.setControl(floorRollerVoltageRequest);
        cornerSweep.setControl(cornerSweepVoltageRequest);
        turretTransfer.setControl(turretTransferVoltageRequest);

    }

    private void LogValues() {
        Logger.recordOutput("Rebuilt/Transfer/FloorRollerState", floorRollerState);
        Logger.recordOutput("Rebuilt/Transfer/TurretTransferState", transferState);

        Logger.recordOutput("Rebuilt/Transfer/FloorRoller/Stator", floorRoller.getStatorCurrent().getValueAsDouble());
        Logger.recordOutput("Rebuilt/Transfer/FloorRoller/Supply", floorRoller.getSupplyCurrent().getValueAsDouble());
        Logger.recordOutput("Rebuilt/Transfer/FloorRoller/RPM", floorRoller.getVelocity().getValueAsDouble());
        Logger.recordOutput("Rebuilt/Transfer/FloorRoller/Request", floorRollerVoltageRequest.Output);


        Logger.recordOutput("Rebuilt/Transfer/SideSweep/Stator", sideSweep.getStatorCurrent().getValueAsDouble());
        Logger.recordOutput("Rebuilt/Transfer/SideSweep/Supply", sideSweep.getSupplyCurrent().getValueAsDouble());
        Logger.recordOutput("Rebuilt/Transfer/SideSweep/RPM", sideSweep.getVelocity().getValueAsDouble());
        Logger.recordOutput("Rebuilt/Transfer/SideSweep/Request", sideSweepVoltageRequest.Output);

        Logger.recordOutput("Rebuilt/Transfer/CornerSweep/Stator", cornerSweep.getStatorCurrent().getValueAsDouble());
        Logger.recordOutput("Rebuilt/Transfer/CornerSweep/Supply", cornerSweep.getSupplyCurrent().getValueAsDouble());
        Logger.recordOutput("Rebuilt/Transfer/CornerSweep/RPM", cornerSweep.getVelocity().getValueAsDouble());
        Logger.recordOutput("Rebuilt/Transfer/CornerSweep/Request", cornerSweepVoltageRequest.Output);
        
        Logger.recordOutput("Rebuilt/Transfer/TurretTransfer/Stator", turretTransfer.getStatorCurrent().getValueAsDouble());
        Logger.recordOutput("Rebuilt/Transfer/TurretTransfer/Supply", turretTransfer.getSupplyCurrent().getValueAsDouble());
        Logger.recordOutput("Rebuilt/Transfer/TurretTransfer/RPM", turretTransfer.getVelocity().getValueAsDouble());
        Logger.recordOutput("Rebuilt/Transfer/TurretTransfer/Request", turretTransferVoltageRequest.Output);
    }
}
