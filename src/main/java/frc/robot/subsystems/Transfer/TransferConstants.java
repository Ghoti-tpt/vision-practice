package frc.robot.subsystems.Transfer;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.controls.VoltageOut;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;

import edu.wpi.first.wpilibj.motorcontrol.Talon;

import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.MotorAlignmentValue;

import frc.robot.RobotConstants;

public class TransferConstants {
    public static final int floorRollerID = 0;
    public static final int floorRollerFollowerID = 0;
    public static final int sideSweeperID = 0;
    public static final int sideSweeperFollowerID = 0;
    public static final int turretTransferID = 0;
    public static final int cornerSweepID = 0;
    
    public static class MotorConfig {
        
        private static final TalonFXConfiguration floorMotorConfig = new TalonFXConfiguration();
        public static final TalonFX floorRoller = new TalonFX(TransferConstants.floorRollerID, RobotConstants.MAIN_SYSTEMS_CANBUS);
        public static final TalonFX floorRollerFollower = new TalonFX(TransferConstants.floorRollerFollowerID, RobotConstants.MAIN_SYSTEMS_CANBUS);
        
        public void FloorMotorConfig() {
            floorMotorConfig.MotorOutput.NeutralMode = NeutralModeValue.Coast;
            floorMotorConfig.MotorOutput.Inverted = InvertedValue.Clockwise_Positive;
            floorMotorConfig.CurrentLimits.StatorCurrentLimitEnable = true;
            floorMotorConfig.CurrentLimits.StatorCurrentLimit = 80;
            floorMotorConfig.CurrentLimits.SupplyCurrentLimitEnable = true;
            floorMotorConfig.CurrentLimits.SupplyCurrentLimit = 50;
            floorMotorConfig.CurrentLimits.SupplyCurrentLowerTime = 1;
            floorMotorConfig.CurrentLimits.SupplyCurrentLowerLimit = 30;

            floorRoller.getConfigurator().apply(floorMotorConfig);
            floorRollerFollower.getConfigurator().apply(floorMotorConfig);
            floorRollerFollower.setControl(new Follower(floorRollerID, MotorAlignmentValue.Opposed));
        }

        private static final TalonFXConfiguration sideSweepMotorConfig = new TalonFXConfiguration();
        public static final TalonFX sideSweep = new TalonFX(TransferConstants.sideSweeperID, RobotConstants.MAIN_SYSTEMS_CANBUS);
        public static final TalonFX sideSweepFollower = new TalonFX(TransferConstants.sideSweeperFollowerID, RobotConstants.MAIN_SYSTEMS_CANBUS);    

        public void SideSweepMotorConfig() {
            sideSweepMotorConfig.MotorOutput.NeutralMode = NeutralModeValue.Coast;
            sideSweepMotorConfig.MotorOutput.Inverted = InvertedValue.Clockwise_Positive;
            sideSweepMotorConfig.CurrentLimits.StatorCurrentLimitEnable = true;
            sideSweepMotorConfig.CurrentLimits.StatorCurrentLimit = 80;
            sideSweepMotorConfig.CurrentLimits.SupplyCurrentLimitEnable = true;
            sideSweepMotorConfig.CurrentLimits.SupplyCurrentLimit = 50;
            sideSweepMotorConfig.CurrentLimits.SupplyCurrentLowerTime = 1;
            sideSweepMotorConfig.CurrentLimits.SupplyCurrentLowerLimit = 30;

            sideSweep.getConfigurator().apply(sideSweepMotorConfig);
            sideSweepFollower.getConfigurator().apply(sideSweepMotorConfig);
            sideSweepFollower.setControl(new Follower(sideSweeperID, MotorAlignmentValue.Opposed));
        }

        private static final TalonFXConfiguration turretTransferConfig = new TalonFXConfiguration();
        public static final TalonFX turretTransfer = new TalonFX(TransferConstants.turretTransferID,RobotConstants.MAIN_SYSTEMS_CANBUS);

        public void turretTransferConfig() {
            turretTransferConfig.MotorOutput.NeutralMode = NeutralModeValue.Coast;
            turretTransferConfig.MotorOutput.Inverted = InvertedValue.Clockwise_Positive;
            turretTransferConfig.CurrentLimits.StatorCurrentLimitEnable = true;
            turretTransferConfig.CurrentLimits.StatorCurrentLimit = 80;
            turretTransferConfig.CurrentLimits.SupplyCurrentLimitEnable = true;
            turretTransferConfig.CurrentLimits.SupplyCurrentLimit = 50;
            turretTransferConfig.CurrentLimits.SupplyCurrentLowerTime = 1;
            turretTransferConfig.CurrentLimits.SupplyCurrentLowerLimit = 30;

            turretTransfer.getConfigurator().apply(turretTransferConfig);
        }

        private static final TalonFXConfiguration cornerSweepMotorConfig = new TalonFXConfiguration();
        public static final TalonFX cornerSweep = new TalonFX(TransferConstants.cornerSweepID, RobotConstants.MAIN_SYSTEMS_CANBUS);

        public void CornerSweepMotorConfig() {
            cornerSweepMotorConfig.MotorOutput.NeutralMode = NeutralModeValue.Coast;
            cornerSweepMotorConfig.MotorOutput.Inverted = InvertedValue.Clockwise_Positive;
            cornerSweepMotorConfig.CurrentLimits.StatorCurrentLimitEnable = true;
            cornerSweepMotorConfig.CurrentLimits.StatorCurrentLimit = 80;
            cornerSweepMotorConfig.CurrentLimits.SupplyCurrentLimitEnable = true;
            cornerSweepMotorConfig.CurrentLimits.SupplyCurrentLimit = 50;
            cornerSweepMotorConfig.CurrentLimits.SupplyCurrentLowerTime = 1;
            cornerSweepMotorConfig.CurrentLimits.SupplyCurrentLowerLimit = 30;

            cornerSweep.getConfigurator().apply(cornerSweepMotorConfig);
        }
    }       
}


