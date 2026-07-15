package frc.robot.subsystems.IntakeSubsystem;

import org.littletonrobotics.junction.networktables.LoggedNetworkNumber;

import com.ctre.phoenix6.controls.DynamicMotionMagicVoltage;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.subsystems.HopperSubsystem.HopperSubsystem;

import frc.robot.Constants;

public class IntakeSubsystem extends SubsystemBase {
    private static IntakeSubsystem INSTANCE;

    public enum IntakeState {
        INTAKING,
        STORING
    }

    private DynamicMotionMagicVoltage IntakeMMRequest;

    private TalonFX mArmFx = IntakeConstants.mArmFx;

    public IntakeState currentIntakeState = IntakeState.STORING;
  
    private LoggedNetworkNumber logIntakeArmTarget = new LoggedNetworkNumber("Rebuilt/Intake/Tuning/Target", 0);
    
    private IntakeSubsystem() {
            IntakeMMRequest = new DynamicMotionMagicVoltage(0, IntakeConstants.logIntakeMMVeloc.getAsDouble(), IntakeConstants.logIntakeMMAccel.getAsDouble()).withEnableFOC(Constants.ENABLEFOC);
    }

    @Override
    public void periodic() {
        applyState();
        publishLog();
    }


    private void applyState() {
        switch (currentIntakeState) {
            case INTAKING:
                mArmFx.setControl(IntakeMMRequest.withPosition(logIntakeArmTarget.get()));
                break;
            case STORING:
                mArmFx.setControl(IntakeMMRequest.withPosition(logIntakeArmTarget.get()));
                break;
        }
    }

    private void publishLog(){
        
    }

    public static IntakeSubsystem getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new IntakeSubsystem();
        }
        return INSTANCE;
    }


}
