package frc.robot.subsystems.Hopper;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.StrictFollower;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;

import frc.robot.RobotConstants;

public class HopperConstants {
    // Device IDs (from device ids.txt)
    public static final int BED_BOTTOM_ID = 24;
    public static final int BED_TOP_ID = 25;
    public static final int SIDE_SWEEP_BOTTOM_ID = 26;
    public static final int SIDE_SWEEP_TOP_ID = 27;
    public static final int TURRET_TRANSFER_ID = 30;

    // Placeholder voltage setpoints (tune on the robot)
    public static final double BED_INTAKE_VOLTAGE = 3.0;
    public static final double SIDE_SWEEP_SHOOT_VOLTAGE = 3.0;
    public static final double TURRET_TRANSFER_SHOOT_VOLTAGE = 3.0;

    public static final TalonFX bedBottom = new TalonFX(BED_BOTTOM_ID, RobotConstants.MAIN_SYSTEMS_CANBUS);
    public static final TalonFX bedTopFollower = new TalonFX(BED_TOP_ID, RobotConstants.MAIN_SYSTEMS_CANBUS);
    public static final TalonFX sideSweepBottom = new TalonFX(SIDE_SWEEP_BOTTOM_ID, RobotConstants.MAIN_SYSTEMS_CANBUS);
    public static final TalonFX sideSweepTopFollower = new TalonFX(SIDE_SWEEP_TOP_ID, RobotConstants.MAIN_SYSTEMS_CANBUS);
    public static final TalonFX turretTransfer = new TalonFX(TURRET_TRANSFER_ID, RobotConstants.MAIN_SYSTEMS_CANBUS);

    static {
        configureBed();
        configureSideSweep();
        configureTurretTransfer();
    }

    private static TalonFXConfiguration baseConfig() {
        TalonFXConfiguration motorConfig = new TalonFXConfiguration();
        motorConfig.MotorOutput.NeutralMode = NeutralModeValue.Coast;
        motorConfig.MotorOutput.Inverted = InvertedValue.Clockwise_Positive;
        motorConfig.CurrentLimits.StatorCurrentLimitEnable = true;
        motorConfig.CurrentLimits.StatorCurrentLimit = 80;
        motorConfig.CurrentLimits.SupplyCurrentLimitEnable = true;
        motorConfig.CurrentLimits.SupplyCurrentLimit = 50;
        motorConfig.CurrentLimits.SupplyCurrentLowerTime = 1;
        motorConfig.CurrentLimits.SupplyCurrentLowerLimit = 30;
        return motorConfig;
    }

    private static void configureBed() {
        TalonFXConfiguration config = baseConfig();
        bedBottom.getConfigurator().apply(config);
        bedTopFollower.getConfigurator().apply(config);
        bedTopFollower.setControl(new StrictFollower(BED_BOTTOM_ID));
    }

    private static void configureSideSweep() {
        TalonFXConfiguration config = baseConfig();
        sideSweepBottom.getConfigurator().apply(config);
        sideSweepTopFollower.getConfigurator().apply(config);
        sideSweepTopFollower.setControl(new StrictFollower(SIDE_SWEEP_BOTTOM_ID));
    }

    private static void configureTurretTransfer() {
        TalonFXConfiguration config = baseConfig();
        turretTransfer.getConfigurator().apply(config);
    }

    private HopperConstants() {}
}
