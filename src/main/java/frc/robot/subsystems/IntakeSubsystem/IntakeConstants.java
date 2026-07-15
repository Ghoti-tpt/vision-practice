package frc.robot.subsystems.IntakeSubsystem;

import org.littletonrobotics.junction.networktables.LoggedNetworkNumber;

import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.MotorAlignmentValue;
import com.ctre.phoenix6.signals.NeutralModeValue;

public class IntakeConstants {
    
    public static final TalonFX mArmFx = new TalonFX(0);;

    private LoggedNetworkNumber logIntakeMMKS = new LoggedNetworkNumber("/Tuning/Intake/Angle/kS", 0);
    private LoggedNetworkNumber logIntakeMMKV = new LoggedNetworkNumber("/Tuning/Intake/Angle/kV", 0);
    private LoggedNetworkNumber logIntakeMMKA = new LoggedNetworkNumber("/Tuning/Intake/Angle/kA", 0);
    private LoggedNetworkNumber logIntakeMMKP = new LoggedNetworkNumber("/Tuning/Intake/Angle/kP", 0);
    private LoggedNetworkNumber logIntakeMMKI = new LoggedNetworkNumber("/Tuning/Intake/Angle/kI", 0);
    private LoggedNetworkNumber logIntakeMMKD = new LoggedNetworkNumber("/Tuning/Intake/Angle/kD", 0);
    private LoggedNetworkNumber logIntakeMMKG = new LoggedNetworkNumber("/Tuning/Intake/Angle/kG", 0.3);

    public static LoggedNetworkNumber logIntakeMMVeloc = new LoggedNetworkNumber("Rebuilt/Intake/Tuning/Velocity", 0);
    public static LoggedNetworkNumber logIntakeMMAccel = new LoggedNetworkNumber("Rebuilt/Intake/Tuning/Acceleration", 0);
  

    private IntakeConstants () {
        configureMotor();
    }
    private void configureMotor() {
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
