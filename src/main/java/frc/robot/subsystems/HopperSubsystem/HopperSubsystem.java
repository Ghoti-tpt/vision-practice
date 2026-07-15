package frc.robot.subsystems.HopperSubsystem;

import com.ctre.phoenix6.controls.VoltageOut;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import org.littletonrobotics.junction.Logger;
import org.littletonrobotics.junction.networktables.LoggedNetworkBoolean;
import org.littletonrobotics.junction.networktables.LoggedNetworkNumber;

import frc.robot.Constants;

public class HopperSubsystem extends SubsystemBase{
    private static HopperSubsystem INSTANCE;
    
    private final TalonFX mBedFx = HopperConstants.mBedFx;
    private final TalonFX mSideSweaperBottomFx = HopperConstants.mSideSweaperBottomFx;

    private VoltageOut voltageRequestBed = new VoltageOut(0.0).withEnableFOC(Constants.EnableFOC);
    private VoltageOut voltageRequestSideSweaper = new VoltageOut(0.0).withEnableFOC(Constants.EnableFOC);

    // I love commiting :)

    public enum SweaperState {
        SHOOTING,
        JAMMED, // may not use
        IDLING
    }

    public enum BedState {
        IDLING,
        INTAKING
    }
    
    public SweaperState currentSweaperState = SweaperState.IDLING;
    public BedState currentBedState = BedState.IDLING;

    private HopperSubsystem () {} 

    @Override
    public void periodic() {
        applyState();
        publicLog();
    }
    
    private void applyState() {
        switch (currentSweaperState) {
            case SHOOTING:
                voltageRequestSideSweaper.Output = 3;
                break;
            case JAMMED:
                break;
            case IDLING:
                voltageRequestSideSweaper.Output = 0;
                break;
        }

        switch (currentBedState) {
            case INTAKING:
                voltageRequestBed.Output = 3;
                break;
            case IDLING:
                voltageRequestBed.Output = 0;
                break;
        }

        mBedFx.setControl(voltageRequestBed);
        mSideSweaperBottomFx.setControl(voltageRequestSideSweaper);
    }

    private void publicLog() {
        Logger.recordOutput("Rebuilt/Hopper/Bed/currentBedState", currentBedState);
        Logger.recordOutput("Rebuilt/Hopper/Sweaper/currentSweaperState", currentSweaperState);
    }


    public static HopperSubsystem getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new HopperSubsystem();
        }
        return INSTANCE;
    }

}   
