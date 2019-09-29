package org.firstinspires.ftc.teamcode.subsystem.output.drivetrain

import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorSimple
import org.firstinspires.ftc.teamcode.math.AngularVelocity
import org.firstinspires.ftc.teamcode.math.TickCount
import org.firstinspires.ftc.teamcode.math.TickCountPerAngle

class OutputMotorGroup(vararg val motors: OutputMotor) : OutputMotor {
    override fun close() {
        for(motor in motors) {
            motor.close()
        }
    }
    
    override fun increaseTargetPosition(change: TickCount) {
        for(motor in motors) {
            motor.increaseTargetPosition(change)
        }
    }
    
    override fun resetDeviceConfigurationForOpMode() {
        for(motor in motors) {
            motor.resetDeviceConfigurationForOpMode()
        }
    }
    
    override fun setDirection(value: DcMotorSimple.Direction) {
        for(motor in motors) {
            motor.setDirection(value)
        }
    }
    
    override fun setGearing(value: Double) {
        for(motor in motors) {
            motor.setGearing(value)
        }
    }
    
    override fun setMaxAchievableSpeedFraction(value: Double) {
        for(motor in motors) {
            motor.setMaxAchievableSpeedFraction(value)
        }
    }
    
    override fun setMode(value: DcMotor.RunMode) {
        for(motor in motors) {
            motor.setMode(value)
        }
    }
    
    override fun setMaxSpeed(value: AngularVelocity) {
        for(motor in motors) {
            motor.setMaxSpeed(value)
        }
    }

    override fun setSpeed(value: AngularVelocity) {
        for(motor in motors) {
            motor.setSpeed(value)
        }
    }

    override fun setTargetPosition(value: TickCount) {
        for(motor in motors) {
            motor.setTargetPosition(value)
        }
    }
    
    override fun setTickCountPerAngle(value: TickCountPerAngle) {
        for(motor in motors) {
            motor.setTickCountPerAngle(value)
        }
    }
    
    override fun setPower(value: Double) {
        for(motor in motors) {
            motor.setPower(value)
        }
    }
    
    override fun setZeroPowerBehavior(value: DcMotor.ZeroPowerBehavior) {
        for(motor in motors) {
            motor.setZeroPowerBehavior(value)
        }
    }
}
