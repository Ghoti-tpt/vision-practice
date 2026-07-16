package frc.robot.subsystems.IntakeSubsystem;

import org.littletonrobotics.junction.Logger;
import org.littletonrobotics.junction.networktables.LoggedNetworkNumber;

import com.ctre.phoenix6.controls.DynamicMotionMagicVoltage;
import com.ctre.phoenix6.controls.VoltageOut;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.units.measure.Voltage;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.subsystems.HopperSubsystem.HopperSubsystem;

import frc.robot.Constants;

public class IntakeSubsystem extends SubsystemBase {
    private static IntakeSubsystem INSTANCE;

    public enum IntakeState {
        INTAKING,
        STORING
    }

    private DynamicMotionMagicVoltage IntakeArmMMRequest =  IntakeConstants.IntakeArmMMRequest;
    private VoltageOut IntakeWheelMMRequest = IntakeConstants.IntakeWheelMMRequest;



    private TalonFX mArmFx = IntakeConstants.mArmFx;
    private TalonFX mIntakeWheelFx = IntakeConstants.mIntakeWheelFx;

    public IntakeState currentIntakeState = IntakeState.STORING;
  
    private LoggedNetworkNumber logIntakeRevolutionTargetStore = new LoggedNetworkNumber("Rebuilt/Intake/Tuning/Revolution/StoreTarget", 0);
    private LoggedNetworkNumber logIntakeRevolutionTargetIntaking = new LoggedNetworkNumber("Rebuilt/Intake/Tuning/Revolution/IntakeTarget", 0);
    

    private double intakeRevolutionTarget = 0;

    private IntakeSubsystem() {
    }

    @Override
    public void periodic() {
        applyState();
        publishLog();
    }


    private void applyState() {
        switch (currentIntakeState) {
            case INTAKING:
                intakeRevolutionTarget = logIntakeRevolutionTargetIntaking.get();
                mArmFx.setControl(IntakeArmMMRequest.withPosition(intakeRevolutionTarget));
                IntakeWheelMMRequest.Output = 3;
    
                break;
            case STORING:
                intakeRevolutionTarget = logIntakeRevolutionTargetStore.get();
                mArmFx.setControl(IntakeArmMMRequest.withPosition(intakeRevolutionTarget));
                IntakeWheelMMRequest.Output = 0;
                break;
        }

        mIntakeWheelFx.setControl(IntakeWheelMMRequest);
    }

    private void publishLog(){
        Logger.recordOutput("Rebuilt/Intake/currentState", currentIntakeState);


    }

    public static IntakeSubsystem getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new IntakeSubsystem();
        }
        return INSTANCE;
    }


}
