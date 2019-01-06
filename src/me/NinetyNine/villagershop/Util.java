package me.NinetyNine.villagershop;

import lombok.Getter;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.BlockIterator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Util {

    @Getter
    private static final Util instance = new Util();

    public void freezeEntity(Entity en) {
        net.minecraft.server.v1_8_R3.Entity nmsEn = ((CraftEntity)en).getHandle();
        NBTTagCompound compound = new NBTTagCompound();
        nmsEn.c(compound);
        compound.setByte("NoAI", (byte)1);
        nmsEn.f(compound);
    }

    public void unfreezeEntity(Entity entity) {
        net.minecraft.server.v1_8_R3.Entity nmsEn = ((CraftEntity)entity).getHandle();
        NBTTagCompound compound = new NBTTagCompound();
        nmsEn.c(compound);
        compound.setByte("NoAI", (byte)0);
        nmsEn.f(compound);
    }

    public LivingEntity getTarget(Player player) {
        List<Entity> nearbyEntities = player.getNearbyEntities(25.0D, 25.0D, 25.0D);
        List<LivingEntity> ents = new ArrayList();
        Iterator var4 = nearbyEntities.iterator();

        while (var4.hasNext()) {
            Entity e = (Entity) var4.next();
            if (e instanceof LivingEntity) {
                ents.add((LivingEntity) e);
            }
        }

        LivingEntity target = null;
        BlockIterator iterator = new BlockIterator(player, 25);

        while (true) {
            while (iterator.hasNext()) {
                Block block = iterator.next();
                int bx = block.getX();
                int by = block.getY();
                int bz = block.getZ();
                Iterator var17 = ents.iterator();

                while (var17.hasNext()) {
                    LivingEntity e = (LivingEntity) var17.next();
                    Location loc = e.getLocation();
                    double ex = loc.getX();
                    double ey = loc.getY();
                    double ez = loc.getZ();
                    if ((double) bx - 0.75D <= ex && ex <= (double) bx + 1.75D && (double) bz - 0.75D <= ez && ez <= (double) bz + 1.75D && (double) (by - 1) <= ey && ey <= (double) by + 2.5D) {
                        target = e;
                        break;
                    }
                }
            }

            return target;
        }
    }
}
