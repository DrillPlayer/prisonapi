package main;

import mines.MineCommands;
import mines.MineHandler;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import sql.SQL;

import java.sql.SQLException;

public class Main extends JavaPlugin implements Listener {
        public static Main instance;
        public sql.SQL SQL;

        @Override
        public void onEnable() {
                instance = this;
                getServer().getPluginManager().registerEvents(this, this);
                getServer().getPluginManager().registerEvents(new MineHandler(), this);
                this.getCommand("test").setExecutor(new MineCommands());
                getLogger().info("Prison started");
                this.SQL = new SQL();

                try {
                        SQL.connect();
                } catch (ClassNotFoundException e) {
                        Bukkit.getLogger().info("Datenbank ist nicht connected");
                } catch (SQLException throwables) {
                        Bukkit.getLogger().info("Datenbank ist nicht connected");
                }

                if (SQL.isConnected()) {
                        Bukkit.getLogger().info("Datenbank ist connected");
                        //SQLGetter.createPageTable();
                }
        }

        @Override
        public void onDisable() {
            // Plugin shutdown logic
        }

        public static Main getInstance() {
                return instance;
        }

}
