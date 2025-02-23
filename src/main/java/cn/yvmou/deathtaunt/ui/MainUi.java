package cn.yvmou.deathtaunt.ui;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class MainUi implements Listener {

    public void openUi(Player player) {
        Inventory inv = Bukkit.createInventory(player, 6*9, "URARA!");
        ItemStack item_bk = new ItemStack(Material.DIAMOND);


        inv.setItem(0,item_bk);
        inv.setItem(1,item_bk);
        inv.setItem(2,item_bk);
        inv.setItem(3,item_bk);
        inv.setItem(4,item_bk);
        inv.setItem(5,item_bk);
        inv.setItem(6,item_bk);
        inv.setItem(7,item_bk);
        inv.setItem(8,item_bk);
        inv.setItem(9,item_bk);
        inv.setItem(17,item_bk);
        inv.setItem(18,item_bk);
        inv.setItem(26,item_bk);
        inv.setItem(27,item_bk);
        inv.setItem(35,item_bk);
        inv.setItem(36,item_bk);
        inv.setItem(44,item_bk);
        inv.setItem(45,item_bk);
        inv.setItem(46,item_bk);
        inv.setItem(47,item_bk);
        inv.setItem(48,item_bk);
        inv.setItem(49,item_bk);
        inv.setItem(50,item_bk);
        inv.setItem(51,item_bk);
        inv.setItem(52,item_bk);
        inv.setItem(53,item_bk);

        ItemStack item_button1 = new ItemStack(Material.GOLD_INGOT);
        ItemStack item_button2 = new ItemStack(Material.ANVIL);
        inv.setItem(22,item_button1);
        inv.setItem(31,item_button2);


        player.openInventory(inv);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event){
        //从这里可以看出来, 标题不是随意设置的, 我们经常用标题作为区分GUI的标志
        if(event.getWhoClicked().getOpenInventory().getTitle().equals("URARA!")){
            event.setCancelled(true); //这样玩家就没办法拿出来物品了

            //getRawSlot获得玩家点击的格子编号
            //但是玩家点击GUI之外不是格子的地方也会触发InventoryClickEvent, 需要做处理!
            if(event.getRawSlot()<0 || event.getRawSlot()>event.getInventory().getSize() || event.getInventory()==null)
                return;

            //自从Mojang把HIM删掉以后, 能触发InventoryClickEvent的只有Player了
            //目前来说可以直接把它强转成Player
            Player p = (Player)event.getWhoClicked();

            if(event.getRawSlot()==22){
                p.sendMessage("你点击了金锭!");
                p.closeInventory();
            } else {
                p.sendMessage("你没有点击金锭!");
                p.closeInventory();
            }
        }
    }
}


