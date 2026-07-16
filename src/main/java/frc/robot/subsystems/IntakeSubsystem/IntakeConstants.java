package frc.robot.subsystems.IntakeSubsystem;

import org.littletonrobotics.junction.networktables.LoggedNetworkNumber;

import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.DynamicMotionMagicVoltage;
import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.controls.VoltageOut;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.MotorAlignmentValue;
import com.ctre.phoenix6.signals.NeutralModeValue;

import frc.robot.Constants;

public class IntakeConstants {
    
    public static final TalonFX mArmFx = new TalonFX(0);
    public static final TalonFX mIntakeWheelFx = new TalonFX(0);
    public static final TalonFX mIntakeWheelFollowerFx = new TalonFX(0);

    public static LoggedNetworkNumber logIntakeMMKS = new LoggedNetworkNumber("/Tuning/Intake/Angle/kS", 0);
    public static LoggedNetworkNumber logIntakeMMKV = new LoggedNetworkNumber("/Tuning/Intake/Angle/kV", 0);
    public static LoggedNetworkNumber logIntakeMMKA = new LoggedNetworkNumber("/Tuning/Intake/Angle/kA", 0);
    public static LoggedNetworkNumber logIntakeMMKP = new LoggedNetworkNumber("/Tuning/Intake/Angle/kP", 0);
    public static LoggedNetworkNumber logIntakeMMKI = new LoggedNetworkNumber("/Tuning/Intake/Angle/kI", 0);
    public static LoggedNetworkNumber logIntakeMMKD = new LoggedNetworkNumber("/Tuning/Intake/Angle/kD", 0);
    public static LoggedNetworkNumber logIntakeMMKG = new LoggedNetworkNumber("/Tuning/Intake/Angle/kG", 0);

    public static LoggedNetworkNumber logIntakeMMVeloc = new LoggedNetworkNumber("Rebuilt/Intake/Tuning/Velocity", 0);
    public static LoggedNetworkNumber logIntakeMMAccel = new LoggedNetworkNumber("Rebuilt/Intake/Tuning/Acceleration", 0);
  
    public static DynamicMotionMagicVoltage  IntakeArmMMRequest=  new DynamicMotionMagicVoltage(0, logIntakeMMVeloc.getAsDouble(), logIntakeMMAccel.getAsDouble()).withEnableFOC(Constants.ENABLEFOC);;
    public static VoltageOut IntakeWheelMMRequest = new VoltageOut(0).withEnableFOC(Constants.ENABLEFOC);

    public static TalonFXConfiguration motorConfig;

    private IntakeConstants () {
        configureArmMotor();
        configureWheelMotor();
    }

    private void configureWheelMotor() {
        TalonFXConfiguration motorConfig = new TalonFXConfiguration();

        motorConfig.MotorOutput.NeutralMode = NeutralModeValue.Coast;

        motorConfig.CurrentLimits.StatorCurrentLimit = 80;
        motorConfig.CurrentLimits.StatorCurrentLimitEnable = true;
        motorConfig.CurrentLimits.SupplyCurrentLimit = 30;
        motorConfig.CurrentLimits.SupplyCurrentLimitEnable = true;
        motorConfig.CurrentLimits.SupplyCurrentLowerLimit = 0;
        
        mIntakeWheelFx.getConfigurator().apply(motorConfig);
        mIntakeWheelFollowerFx.setControl(new Follower(mIntakeWheelFx.getDeviceID(), MotorAlignmentValue.Opposed));

    }

    public static void configureArmMotor() {
        TalonFXConfiguration motorConfig = new TalonFXConfiguration();

        motorConfig.MotorOutput.NeutralMode = NeutralModeValue.Coast;
        // motorConfig.MotorOutput.Inverted = InvertedValue.Clockwise_Positive;

        motorConfig.MotionMagic.MotionMagicAcceleration = logIntakeMMAccel.get();
        motorConfig.MotionMagic.MotionMagicCruiseVelocity = logIntakeMMVeloc.get();

        Slot0Configs slot0Config = new Slot0Configs().withKS(logIntakeMMKS.get())
                                                    .withKV(logIntakeMMKV.get())
                                                    .withKA(logIntakeMMKA.get())
                                                    .withKP(logIntakeMMKP.get())
                                                    .withKI(logIntakeMMKI.get())
                                                    .withKD(logIntakeMMKD.get())
                                                    .withKG(logIntakeMMKG.get());

        motorConfig.Slot0 = slot0Config;

        motorConfig.CurrentLimits.StatorCurrentLimit = 80;
        motorConfig.CurrentLimits.StatorCurrentLimitEnable = true;
        motorConfig.CurrentLimits.SupplyCurrentLimit = 30;
        motorConfig.CurrentLimits.SupplyCurrentLimitEnable = true;
        motorConfig.CurrentLimits.SupplyCurrentLowerLimit = 0;
        
        mArmFx.getConfigurator().apply(motorConfig);
    }  
}
