package cz.nix3r.commands;

import com.sun.management.OperatingSystemMXBean;
import cz.nix3r.enums.LogType;
import cz.nix3r.utils.CommonUtils;
import cz.nix3r.utils.LogSystem;
import org.javacord.api.entity.message.MessageBuilder;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.server.Server;
import org.javacord.api.interaction.SlashCommandInteraction;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.lang.management.ManagementFactory;
import java.util.concurrent.TimeUnit;

public class StatusCommand {

    private static int totalUsagePoint = 0;
    private static final String[] COLOR_PALLETE = new String[]{ "#00FF00", "#33FF00", "#66FF00", "#99FF00", "#CCFF00",
            "#FFFF00", "#FFCC00", "#FF9900", "#FF6600", "#FF3300" };

    public static void run(SlashCommandInteraction interaction) {

        long start = System.currentTimeMillis();
        String[] ram = getRamUsage();
        String[] cpuProcess = getCpuUsage();
        BufferedImage screenshot = takeScreenshot();

        LogSystem.log(LogType.INFO, "Status command catch by '" + interaction.getUser().getName() + "'");
        interaction.createImmediateResponder().setContent("Generating status ..").respond().join();

        EmbedBuilder builder = new EmbedBuilder()
                .setColor(Color.decode(COLOR_PALLETE[totalUsagePoint - 1]))
                .setTitle("NixBot status")
                .addField("Total Memory (" + ram[2] + "/" + ram[0] + ")", ram[3])
                .addField("Bot CPU usage (" + cpuProcess[0] + "% / 10.0%)", cpuProcess[1])
                .addField("Total usage (1-10 | low-high)", totalUsagePoint + " points")
                .addField("Time since start", formatTime(System.currentTimeMillis() - CommonUtils.time_since_start), true)
                .addField("Since start", LogSystem.error_counter + " errors", true)
                .addField("Generating timestamp", System.currentTimeMillis() - start + " ms", false)
                .setImage(screenshot)
                .setThumbnail(((Server)CommonUtils.bot.getServers().toArray()[0]).getIcon().get())
                .setFooter("Version: " + CommonUtils.version);

        MessageBuilder message = new MessageBuilder()
                .setEmbed(builder)
                .addAttachment(new File("./nixbot.log"), "NixBot log file");

        message.send(interaction.getChannel().get()).join();
        LogSystem.log(LogType.INFO, "End of command status by '" + interaction.getUser().getName() + "'");

    }

    private static String[] getCpuUsage(){
        OperatingSystemMXBean osBean = ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class);

        // Get the CPU usage as a percentage
        double cpuUsage = osBean.getProcessCpuLoad() * 100;
        int used = (int) cpuUsage;
        if(used > 10) used = 10;
        totalUsagePoint = (int)((totalUsagePoint + used) / 2);
        String progressBar = "";
        for(int i = 0; i < used; i++) progressBar += used > 4 ? ":red_square: " : ":orange_square: ";
        for(int i = 0; i < (10 - used); i++) progressBar += ":green_square: ";
        return new String[]{
                String.format("%.2f", cpuUsage),
                progressBar
        };
    }

    private static String[] getRamUsage(){

        OperatingSystemMXBean osBean = ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class);

        // Get the total amount of physical memory in bytes
        long totalPhysicalMemory = osBean.getTotalPhysicalMemorySize();

        // Get the amount of free physical memory in bytes
        long freePhysicalMemory = osBean.getFreePhysicalMemorySize();

        // Calculate the used physical memory in bytes
        long usedPhysicalMemory = totalPhysicalMemory - freePhysicalMemory;

        String progressBar = "";
        int used = (int)(usedPhysicalMemory / (totalPhysicalMemory / 10));
        totalUsagePoint = used;

        for(int i = 0; i < used; i++) progressBar += used > 4 ? ":red_square: " : ":orange_square: ";
        for(int i = 0; i < (10 - used); i++) progressBar += ":green_square: ";

        // Convert the memory values to human-readable format (e.g., KB, MB, GB)
        String totalMemory = formatMemory(totalPhysicalMemory);
        String freeMemory = formatMemory(freePhysicalMemory);
        String usedMemory = formatMemory(usedPhysicalMemory);

        String[] response = {
                totalMemory,
                freeMemory,
                usedMemory,
                progressBar
        };

        LogSystem.log(LogType.INFO, "Generated RAM usage. Total memory: " + totalMemory + ". Free memory: " + freeMemory + ". Used memory: " + usedMemory);

        return response;

    }

    private static String formatTime(long time){
        long hours = TimeUnit.MILLISECONDS.toHours(time);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(time) % 60;
        long seconds = TimeUnit.MILLISECONDS.toSeconds(time) % 60;
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    private static String formatMemory(long bytes) {
        long kilo = 1024;
        long mega = kilo * 1024;
        long giga = mega * 1024;

        if (bytes >= giga) {
            return String.format("%.2f GB", (double) bytes / giga);
        } else if (bytes >= mega) {
            return String.format("%.2f MB", (double) bytes / mega);
        } else if (bytes >= kilo) {
            return String.format("%.2f KB", (double) bytes / kilo);
        } else {
            return bytes + " B";
        }
    }

    private static BufferedImage takeScreenshot(){
        Robot robot = null;
        try {
            robot = new Robot();
            Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
            BufferedImage screenshot = robot.createScreenCapture(screenRect);
            LogSystem.log(LogType.INFO, "Took a screenshot successfully");
            return screenshot;
        } catch (AWTException e) {
            LogSystem.log(LogType.ERROR, e.getMessage());
            return null;
        }
    }

}
