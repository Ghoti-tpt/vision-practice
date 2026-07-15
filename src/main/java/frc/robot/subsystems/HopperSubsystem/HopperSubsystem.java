package frc.robot.subsystems.HopperSubsystem;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.controls.StrictFollower;
import com.ctre.phoenix6.controls.VoltageOut;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.MotorAlignmentValue;
import com.ctre.phoenix6.signals.NeutralModeValue;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.Constants;

public class HopperSubsystem extends SubsystemBase{
    private static HopperSubsystem INSTANCE;
    
    private final TalonFX mHopperFx = HopperConstants.mHopperFx;

    private VoltageOut voltageRequest = new VoltageOut(0.0).withEnableFOC(Constants.EnableFOC);

    public enum HopperState {
        RUNNING,
        STOP
    }
    
    public HopperState currenHopperState = HopperState.STOP;

    private HopperSubsystem () {} 

    @Override
    public void periodic() {
        applyState();
    }
    
    private void applyState() {
        switch (currenHopperState) {
            case RUNNING:
                voltageRequest.Output = 3;
                break;
            case STOP:
                voltageRequest.Output = 0;
                break;
        }
        mHopperFx.setControl(voltageRequest);
    }

    public static HopperSubsystem getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new HopperSubsystem();
        }
        return INSTANCE;
    }

}   
