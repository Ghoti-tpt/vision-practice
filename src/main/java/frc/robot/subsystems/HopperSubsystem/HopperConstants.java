package frc.robot.subsystems.HopperSubsystem;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.MotorAlignmentValue;
import com.ctre.phoenix6.signals.NeutralModeValue;

public class HopperConstants {

    public static final TalonFX mHopperFx = new TalonFX(0);
    public static final TalonFX mSideSweaperFx =  new TalonFX(0);;

    private HopperConstants() {
        configureMotor();
    }

    private void configureMotor() {
        TalonFXConfiguration motorConfig = new TalonFXConfiguration();

        motorConfig.MotorOutput.NeutralMode = NeutralModeValue.Coast;
        // motorConfig.MotorOutput.Inverted = InvertedValue.Clockwise_Positive;

        // stator -> what your giving the motor
        // supply -> what your 

        motorConfig.CurrentLimits.StatorCurrentLimit = 80;
        motorConfig.CurrentLimits.StatorCurrentLimitEnable = true;
        motorConfig.CurrentLimits.SupplyCurrentLimit = 30;
        motorConfig.CurrentLimits.SupplyCurrentLimitEnable = true;
        motorConfig.CurrentLimits.SupplyCurrentLowerLimit = 0;
        
        mHopperFx.getConfigurator().apply(motorConfig);
        mHopperFx.setControl(new Follower(mSideSweaperFx.getDeviceID(), MotorAlignmentValue.Opposed));

    }  
}
