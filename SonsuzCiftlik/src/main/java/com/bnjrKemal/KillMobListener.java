package com.bnjrKemal;

import com.bgsoftware.superiorskyblock.api.SuperiorSkyblockAPI;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;
import java.util.UUID;

public class KillMobListener implements Listener {

    @EventHandler
    public void onSpawnMob(EntitySpawnEvent e){
        if (SuperiorSkyblockAPI.getIslandAt(e.getEntity().getLocation()) == null) return;

        UUID uuid = SuperiorSkyblockAPI.getIslandAt(e.getEntity().getLocation()).getUniqueId();

        if(!SonsuzCiftlik.buttonKillMobListener.getOrDefault(uuid, false)) return;

        if(e.getEntity() instanceof LivingEntity)
            ((LivingEntity) e.getEntity()).setHealth(0.0);
    }

    @EventHandler
    public void onKillMob(EntityDeathEvent e){
        if (SuperiorSkyblockAPI.getIslandAt(e.getEntity().getLocation()) == null) return;

        UUID uuid = SuperiorSkyblockAPI.getIslandAt(e.getEntity().getLocation()).getUniqueId();

        if(!SonsuzCiftlik.buttonKillMobListener.getOrDefault(uuid, false)) return;

        int amount = 0;

        for(ItemStack itemStack : e.getDrops()){
            if(SonsuzCiftlik.getItemYaml().get(uuid + "." + itemStack.getType()) != null)
                amount = SonsuzCiftlik.getItemYaml().getInt(uuid + "." + itemStack.getType());
            SonsuzCiftlik.getItemYaml().set(uuid + "." + itemStack.getType(), amount + itemStack.getAmount());
            itemStack.setType(Material.AIR);
        }
        try {
            SonsuzCiftlik.getItemYaml().save(SonsuzCiftlik.getItemFile());
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

}
