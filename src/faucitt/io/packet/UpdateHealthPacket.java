package faucitt.io.packet;

import java.io.IOException;

import faucitt.io.MCSocket;

public class UpdateHealthPacket extends Packet {

	public UpdateHealthPacket() {
		super((byte) PacketID.UpdateHealth.getId());
	}
	
	public static float Health;
	public static short Food;
	public static float FoodSaturation;

	@Override
	public void write(MCSocket socket) throws IOException {
		socket.writeFloat(Health);
		socket.writeShort(Food);
		socket.writeFloat(FoodSaturation);
	}
	
	public static UpdateHealthPacket read(MCSocket socket) throws IOException {
		UpdateHealthPacket packet = new UpdateHealthPacket();
		setHealth(socket.readFloat());
		setFood(socket.readShort());
		setFoodSaturation(socket.readFloat());
		return packet;
	}

	public float getHealth() {
		return Health;
	}

	public static void setHealth(float health) {
		Health = health;
	}

	public short getFood() {
		return Food;
	}

	public static void setFood(short food) {
		Food = food;
	}

	public float getFoodSaturation() {
		return FoodSaturation;
	}

	public static void setFoodSaturation(float foodSaturation) {
		FoodSaturation = foodSaturation;
	}
}
