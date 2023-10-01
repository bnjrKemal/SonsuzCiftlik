package com.bnjrKemal.listener;

import com.bgsoftware.superiorskyblock.api.SuperiorSkyblockAPI;
import com.bnjrKemal.SonsuzCiftlik;
import org.bukkit.block.data.Ageable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockGrowEvent;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class GrowListener implements Listener {

    @EventHandler
    public void onGrowth(BlockGrowEvent e) {

        if (SuperiorSkyblockAPI.getIslandAt(e.getNewState().getLocation()) == null) return;

        UUID uuid = SuperiorSkyblockAPI.getIslandAt(e.getNewState().getLocation()).getUniqueId();

        if(!SonsuzCiftlik.buttonGrowListener.getOrDefault(uuid, false)) return;

        List<ItemStack> itemStacks = new ArrayList<>();

        switch(e.getNewState().getType()){
            case WHEAT:
                Ageable age = (Ageable) e.getNewState().getBlockData();
                if (age.getAge() == age.getMaximumAge()) {
                    e.getNewState().getDrops().forEach(item -> itemStacks.add(item));
                    e.setCancelled(true);
                }
                break;
            case PUMPKIN, MELON, SUGAR_CANE, CACTUS:
                e.getNewState().getDrops().forEach(item -> itemStacks.add(item));
                e.setCancelled(true);
                break;
        }

        int amount = 0;

        for(ItemStack itemStack : itemStacks){
            if(SonsuzCiftlik.getItemYaml().get(uuid + "." + itemStack.getType()) != null)
                amount = SonsuzCiftlik.getItemYaml().getInt(uuid + "." + itemStack.getType());
            SonsuzCiftlik.getItemYaml().set(uuid + "." + itemStack.getType(), amount + itemStack.getAmount());
        }
        try {
            SonsuzCiftlik.getItemYaml().save(SonsuzCiftlik.getItemFile());
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}