package frc.robot.subsystems.HopperSubsystem;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.MotorAlignmentValue;
import com.ctre.phoenix6.signals.NeutralModeValue;

public class HopperConstants {

    public static final TalonFX mHopperFx = new TalonFX(0);
    public static final TalonFX mSideSweaperBottomFx =  new TalonFX(0);

    // not added yet
    public static final TalonFX mSideSweaperMiddleFx = new TalonFX(0);
    public static final TalonFX mSideSweaperTopFx = new TalonFX(0);

    private HopperConstants() {
        configureHopperMotor();
    }

    private void configureHopperMotor() {
        TalonFXConfiguration motorConfig = new TalonFXConfiguration();

        motorConfig.MotorOutput.NeutralMode = NeutralModeValue.Coast;
        // motorConfig.MotorOutput.Inverted = InvertedValue.Clockwise_Positive;

        motorConfig.CurrentLimits.StatorCurrentLimit = 80;
        motorConfig.CurrentLimits.StatorCurrentLimitEnable = true;
        motorConfig.CurrentLimits.SupplyCurrentLimit = 30;
        motorConfig.CurrentLimits.SupplyCurrentLimitEnable = true;
        motorConfig.CurrentLimits.SupplyCurrentLowerLimit = 0;
        
        mHopperFx.getConfigurator().apply(motorConfig);
        mSideSweaperBottomFx.getConfigurator().apply(motorConfig);
        mSideSweaperMiddleFx.setControl(new Follower(mSideSweaperBottomFx.getDeviceID(), MotorAlignmentValue.Aligned ));
        mSideSweaperTopFx.setControl(new Follower(mSideSweaperBottomFx.getDeviceID(), MotorAlignmentValue.Aligned ));
        // mSideSweaperBottomFx.setControl(new Follower(mHopperFx.getDeviceID(), MotorAlignmentValue.Opposed));

    }  
}
