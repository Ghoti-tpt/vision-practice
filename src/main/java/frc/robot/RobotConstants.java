package frc.robot;

import com.ctre.phoenix6.CANBus;
import com.ctre.phoenix6.signals.NeutralModeValue;

public final class RobotConstants {
    public static final int DriverControllerPort = 0;
    public static final String DRIVEBASE_CANBUS_NAME = "CantDrive";
    public static final String MAIN_SYSTEMS_CANBUS_NAME = "CantShoot";
    public static final String RIO_BUS_NAME = "Rio";
    public static final CANBus DRIVEBASE_CANBUS = new CANBus(DRIVEBASE_CANBUS_NAME);
    public static final CANBus MAIN_SYSTEMS_CANBUS = new CANBus(MAIN_SYSTEMS_CANBUS_NAME);
    public static final CANBus RIO_BUS = new CANBus(RIO_BUS_NAME);
    public static final boolean ENABLE_FOC = true;
    public static final NeutralModeValue DEFAULT_NEUTRAL_MODE_VALUE = NeutralModeValue.Brake;
}