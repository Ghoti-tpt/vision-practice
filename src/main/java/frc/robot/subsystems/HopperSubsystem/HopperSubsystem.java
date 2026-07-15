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
    private final TalonFX mSideSweaperBottomFx = HopperConstants.mSideSweaperBottomFx;

    private VoltageOut voltageRequestHopper = new VoltageOut(0.0).withEnableFOC(Constants.EnableFOC);
    private VoltageOut voltageRequestSideSweaper = new VoltageOut(0.0).withEnableFOC(Constants.EnableFOC);

    public enum HopperState {
        SHOOTING,
        JAMMED, // may not
        IDLING
    }
    
    public HopperState currenHopperState = HopperState.IDLING;

    private HopperSubsystem () {} 

    @Override
    public void periodic() {
        applyState();
    }
    
    private void applyState() {
        switch (currenHopperState) {
            case SHOOTING:
                voltageRequestHopper.Output = 3;
                voltageRequestSideSweaper.Output = 3;
                break;
            case JAMMED:
                break;
            case IDLING:
                voltageRequestHopper.Output = 0;
                voltageRequestSideSweaper.Output = -3;
                break;
        }
        mHopperFx.setControl(voltageRequestHopper);
        mSideSweaperBottomFx.setControl(voltageRequestSideSweaper);
    }


    public static HopperSubsystem getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new HopperSubsystem();
        }
        return INSTANCE;
    }

}   
