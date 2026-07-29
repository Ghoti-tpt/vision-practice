package frc.robot.subsystems.Intake;

import org.littletonrobotics.junction.networktables.LoggedNetworkNumber;

import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.DynamicMotionMagicVoltage;
import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.controls.VoltageOut;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.MotorAlignmentValue;
import com.ctre.phoenix6.signals.NeutralModeValue;

import frc.robot.RobotConstants;

public class IntakeConstants {
    // Device IDs (from device ids.txt)
    public static final int ARM_ID = 20;          // intake rack
    public static final int WHEEL_ID = 21;        // intake right
    public static final int WHEEL_FOLLOWER_ID = 22; // intake left

    public static final TalonFX mArmFx = new TalonFX(ARM_ID, RobotConstants.MAIN_SYSTEMS_CANBUS);
    public static final TalonFX mIntakeWheelFx = new TalonFX(WHEEL_ID, RobotConstants.MAIN_SYSTEMS_CANBUS);
    public static final TalonFX mIntakeWheelFollowerFx = new TalonFX(WHEEL_FOLLOWER_ID, RobotConstants.MAIN_SYSTEMS_CANBUS);

    public static LoggedNetworkNumber logIntakeMMKS = new LoggedNetworkNumber("/Tuning/Intake/Angle/kS", 0);
    public static LoggedNetworkNumber logIntakeMMKV = new LoggedNetworkNumber("/Tuning/Intake/Angle/kV", 0);
    public static LoggedNetworkNumber logIntakeMMKA = new LoggedNetworkNumber("/Tuning/Intake/Angle/kA", 0);
    public static LoggedNetworkNumber logIntakeMMKP = new LoggedNetworkNumber("/Tuning/Intake/Angle/kP", 0);
    public static LoggedNetworkNumber logIntakeMMKI = new LoggedNetworkNumber("/Tuning/Intake/Angle/kI", 0);
    public static LoggedNetworkNumber logIntakeMMKD = new LoggedNetworkNumber("/Tuning/Intake/Angle/kD", 0);
    public static LoggedNetworkNumber logIntakeMMKG = new LoggedNetworkNumber("/Tuning/Intake/Angle/kG", 0);

    public static LoggedNetworkNumber logIntakeMMVeloc = new LoggedNetworkNumber("Rebuilt/Intake/Tuning/Velocity", 0);
    public static LoggedNetworkNumber logIntakeMMAccel = new LoggedNetworkNumber("Rebuilt/Intake/Tuning/Acceleration", 0);

    public static DynamicMotionMagicVoltage IntakeArmMMRequest = new DynamicMotionMagicVoltage(0, logIntakeMMVeloc.getAsDouble(), logIntakeMMAccel.getAsDouble()).withEnableFOC(RobotConstants.ENABLE_FOC);
    public static VoltageOut IntakeWheelMMRequest = new VoltageOut(0).withEnableFOC(RobotConstants.ENABLE_FOC);

    // Holds the most recently applied arm configuration so the subsystem can
    // detect live tuning changes without dereferencing a null config.
    public static TalonFXConfiguration motorConfig = new TalonFXConfiguration();

    static {
        configureWheelMotor();
        configureArmMotor();
    }

    private static void configureWheelMotor() {
        TalonFXConfiguration wheelConfig = new TalonFXConfiguration();

        wheelConfig.MotorOutput.NeutralMode = NeutralModeValue.Coast;

        wheelConfig.CurrentLimits.StatorCurrentLimit = 80;
        wheelConfig.CurrentLimits.StatorCurrentLimitEnable = true;
        wheelConfig.CurrentLimits.SupplyCurrentLimit = 30;
        wheelConfig.CurrentLimits.SupplyCurrentLimitEnable = true;
        wheelConfig.CurrentLimits.SupplyCurrentLowerLimit = 0;

        mIntakeWheelFx.getConfigurator().apply(wheelConfig);
        mIntakeWheelFollowerFx.getConfigurator().apply(wheelConfig);
        mIntakeWheelFollowerFx.setControl(new Follower(mIntakeWheelFx.getDeviceID(), MotorAlignmentValue.Opposed));
    }

    public static void configureArmMotor() {
        TalonFXConfiguration armConfig = new TalonFXConfiguration();

        armConfig.MotorOutput.NeutralMode = NeutralModeValue.Coast;
        // armConfig.MotorOutput.Inverted = InvertedValue.Clockwise_Positive;

        armConfig.MotionMagic.MotionMagicAcceleration = logIntakeMMAccel.get();
        armConfig.MotionMagic.MotionMagicCruiseVelocity = logIntakeMMVeloc.get();

        Slot0Configs slot0Config = new Slot0Configs().withKS(logIntakeMMKS.get())
                                                    .withKV(logIntakeMMKV.get())
                                                    .withKA(logIntakeMMKA.get())
                                                    .withKP(logIntakeMMKP.get())
                                                    .withKI(logIntakeMMKI.get())
                                                    .withKD(logIntakeMMKD.get())
                                                    .withKG(logIntakeMMKG.get());

        armConfig.Slot0 = slot0Config;

        armConfig.CurrentLimits.StatorCurrentLimit = 80;
        armConfig.CurrentLimits.StatorCurrentLimitEnable = true;
        armConfig.CurrentLimits.SupplyCurrentLimit = 30;
        armConfig.CurrentLimits.SupplyCurrentLimitEnable = true;
        armConfig.CurrentLimits.SupplyCurrentLowerLimit = 0;

        mArmFx.getConfigurator().apply(armConfig);
        motorConfig = armConfig;
    }

    private IntakeConstants() {}
}
