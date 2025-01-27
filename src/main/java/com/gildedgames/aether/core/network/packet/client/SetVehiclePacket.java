package com.gildedgames.aether.core.network.packet.client;

import com.gildedgames.aether.core.network.IAetherPacket.AetherPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;

public class SetVehiclePacket extends AetherPacket
{
    private final int passengerID;
    private final int vehicleID;

    public SetVehiclePacket(int passenger, int vehicle) {
        this.passengerID = passenger;
        this.vehicleID = vehicle;
    }

    @Override
    public void encode(PacketBuffer buf) {
        buf.writeInt(this.passengerID);
        buf.writeInt(this.vehicleID);
    }

    public static SetVehiclePacket decode(PacketBuffer buf) {
        int passenger = buf.readInt();
        int vehicle = buf.readInt();
        return new SetVehiclePacket(passenger, vehicle);
    }

    @Override
    public void execute(PlayerEntity player) {
        if (Minecraft.getInstance().player != null && Minecraft.getInstance().player.level != null) {
            Entity passenger = Minecraft.getInstance().player.level.getEntity(this.passengerID);
            Entity vehicle = Minecraft.getInstance().player.level.getEntity(this.vehicleID);
            if (passenger != null && vehicle != null) {
                passenger.startRiding(vehicle);
            }
        }
    }
}
