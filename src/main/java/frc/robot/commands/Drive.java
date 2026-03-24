// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import static frc.robot.Constants.OperatorConstants.*;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.subsystems.CANDriveSubsystem;

/* You should consider using the more terse Command factories API instead https://docs.wpilib.org/en/stable/docs/software/commandbased/organizing-command-based.html#defining-commands */
public class Drive extends Command {
  /** Creates a new Drive. */
  CANDriveSubsystem driveSubsystem;
  CommandXboxController controller;

  public Drive(CANDriveSubsystem driveSystem, CommandXboxController driverController) {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(driveSystem);
    driveSubsystem = driveSystem;
    controller = driverController;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  // The Y axis of the controller is inverted so that pushing the
  // stick away from you (a negative value) drives the robot forwards (a positive
  // value). The X axis is scaled down so the rotation is more easily
  // controllable.
  @Override
  public void execute() {

    // scales the drive speed during a game based on driver inputs
    // left is slower, right is faster
    double activeDriveScaling = 1;
    boolean pressingLeftBump = controller.leftBumper().getAsBoolean();
    boolean pressingRightBump = controller.rightBumper().getAsBoolean();
    if (pressingLeftBump) {activeDriveScaling += ACTIVE_DECREASE_DRIVE_SCALE_MODIFIER;}
    if (pressingRightBump) {activeDriveScaling += ACTIVE_INCREASE_DRIVE_SCALE_MODIFIER;}
  
    //if controller.button is pushed
    // set scale to what is desired
    driveSubsystem.driveArcade(controller.getLeftY() * DRIVE_SCALING * activeDriveScaling, controller.getRightX() * ROTATION_SCALING * activeDriveScaling);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    driveSubsystem.driveArcade(0, 0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
