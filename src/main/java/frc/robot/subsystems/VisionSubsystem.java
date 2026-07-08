package frc.robot.subsystems;

import frc.robot.util.LimelightHelpers.PoseEstimate;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.util.LimelightHelpers;
import frc.robot.util.LimelightHelpers.*;
import org.littletonrobotics.junction.Logger;
import frc.robot.subsystems.*;

public class VisionSubsystem extends SubsystemBase {
    private String limelightName = "limelight-fl";
    private String limelightName2 = "limelight-bl";
    private static VisionSubsystem instance;
    private static CommandSwerveDrivetrain drive;

    public VisionSubsystem(CommandSwerveDrivetrain drive) {
        if (this.drive == null) {
            this.drive = drive;
        }
    }
    public VisionSubsystem() {
        
    }

    public static synchronized VisionSubsystem getInstance() {
        if (instance == null) {
            instance = new VisionSubsystem();
        }
        return instance;
    }
    @Override
    public void periodic() {
        PoseEstimate estimatedPoseFL = LimelightHelpers.getBotPoseEstimate_wpiBlue(limelightName);
        PoseEstimate estimatedPoseBL = LimelightHelpers.getBotPoseEstimate_wpiBlue(limelightName2);
        Logger.recordOutput("Rebuilt/Vision/EstimatedPoseFL", estimatedPoseFL.pose);
        Logger.recordOutput("Rebuilt/Vision/EstimatedPoseBL", estimatedPoseBL.pose);
        //drive.addVisionMeasurement(estimatedPoseFL.pose, estimatedPoseFL.timestampSeconds);
        drive.addVisionMeasurement(estimatedPoseBL.pose, estimatedPoseBL.timestampSeconds);
    }

    public void publishLogs() {
        
    }
}
