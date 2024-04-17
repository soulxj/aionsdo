package com.eleanor.processors.movement;

import com.aionemu.gameserver.model.gameobjects.Creature;
import com.eleanor.processors.AGameProcessor;
import com.eleanor.processors.movement.motor.AMovementMotor;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Processor that handle all npc's movements
 *
 * @author MetaWind
 */
public class MovementProcessor extends AGameProcessor {

    /* Creature, that registered in this processor */
    private final ConcurrentHashMap<Creature, AMovementMotor> _registeredCreatures = new ConcurrentHashMap<Creature, AMovementMotor>();

    /**
     * Default constructor
     */
    public MovementProcessor() {
        super(12);
    }

    /**
     * Applies motor to creature and cancel previous one if exsist
     *
     * @param creature Source creature
     * @param newMotor Motor to apply
     * @return
     */
    private boolean applyMotor(Creature creature, AMovementMotor newMotor) {

        AMovementMotor oldMotor = _registeredCreatures.put(creature, newMotor);

        if (oldMotor == newMotor)
            throw new Error("Attempt to replace same movement motors");

        if (oldMotor != null)
            oldMotor.stop();

        newMotor.start();

        return true;
    }
}
