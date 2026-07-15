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
    private final TalonFX mSideSweeperBottomFx = HopperConstants.mSideSweeperBottomFx;

    private final TalonFX mBedFollowerFx = HopperConstants.mBedFollowerFx;
    private final TalonFX mTurretTransferFx = HopperConstants.mTurretTransferFx;
    private final TalonFX mCornerSweeperFx = HopperConstants.mCornerSweeperFx;
    private final TalonFX mSideSweeperTopFx = HopperConstants.mSideSweeperTopFx;

    private VoltageOut voltageRequestBed = new VoltageOut(0.0).withEnableFOC(Constants.EnableFOC);
    private VoltageOut voltageRequestSideSweeper = new VoltageOut(0.0).withEnableFOC(Constants.EnableFOC);

    // I love commiting :)

    public enum SweeperState {
        SHOOTING,
        JAMMED, // may not use
        IDLING
    }

    public enum BedState {
        IDLING,
        INTAKING
    }
    
    public SweeperState currentSweeperState = SweeperState.IDLING;
    public BedState currentBedState = BedState.IDLING;

    private HopperSubsystem () {} 

    @Override
    public void periodic() {
        applyState();
        publicLog();
    }
    
    private void applyState() {
        switch (currentSweeperState) {
            case SHOOTING:
                voltageRequestSideSweeper.Output = 3;
                break;
            case JAMMED:
                break;
            case IDLING:
                voltageRequestSideSweeper.Output = 0;
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
        mSideSweeperBottomFx.setControl(voltageRequestSideSweeper);
    }

    private void publicLog() {
        Logger.recordOutput("Rebuilt/Hopper/Bed/currentBedState", currentBedState);
        Logger.recordOutput("Rebuilt/Hopper/Sweeper/currentSweeperState", currentSweeperState);

        Logger.recordOutput("Rebuilt/Hopper/Bed/voltageRequest", voltageRequestBed.Output);
        Logger.recordOutput("Rebuilt/Hopper/Sweeper/voltageRequest", voltageRequestSideSweeper.Output);

        Logger.recordOutput("Rebuilt/Hopper/Motors/Bed/Velocity/mBedFx", mBedFx.getVelocity().getValueAsDouble());
        
        Logger.recordOutput("Rebuilt/Hopper/Motors/Bed/Velocity/mBedFxFollower", mBedFollowerFx.getVelocity().getValueAsDouble());

        Logger.recordOutput("Rebuilt/Hopper/Motors/Sweeper/Velocity/mSideSweeperBottomFx", mSideSweeperBottomFx.getVelocity().getValueAsDouble());

        Logger.recordOutput("Rebuilt/Hopper/Motors/Sweeper/Velocity/mTurretTransferFx", mTurretTransferFx.getVelocity().getValueAsDouble());

        Logger.recordOutput("Rebuilt/Hopper/Motors/Sweeper/Velocity/mCornerSweeperFx", mCornerSweeperFx.getVelocity().getValueAsDouble());

        Logger.recordOutput("Rebuilt/Hopper/Motors/Sweeper/Velocity/mSideSweeperTopFx", mSideSweeperTopFx.getVelocity().getValueAsDouble());
        
    }


    public static HopperSubsystem getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new HopperSubsystem();
        }
        return INSTANCE;
    }

}   
