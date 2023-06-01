package org.kikyou.tia.netty.notify.web.service;


import jakarta.annotation.Resource;
import org.kikyou.tia.netty.notify.web.dto.UsageDto;
import org.springframework.stereotype.Service;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.GlobalMemory;
import oshi.software.os.FileSystem;
import oshi.software.os.OSFileStore;
import oshi.util.Util;

import java.util.Arrays;


@Service
public class UsageService
{
    @Resource
    private SystemInfo systemInfo;


    private int getProcessor()
    {
        CentralProcessor centralProcessor = systemInfo.getHardware().getProcessor();

        long[] prevTicksArray = centralProcessor.getSystemCpuLoadTicks();
        long prevTotalTicks = Arrays.stream(prevTicksArray).sum();
        long prevIdleTicks = prevTicksArray[CentralProcessor.TickType.IDLE.getIndex()];

        Util.sleep(1000);

        long[] currTicksArray = centralProcessor.getSystemCpuLoadTicks();
        long currTotalTicks = Arrays.stream(currTicksArray).sum();
        long currIdleTicks = currTicksArray[CentralProcessor.TickType.IDLE.getIndex()];

        return (int) Math.round((1 - ((double) (currIdleTicks - prevIdleTicks)) / ((double) (currTotalTicks - prevTotalTicks))) * 100);
    }

    /**
     * Gets ram usage
     *
     * @return int that display ram usage
     */
    private int getRam()
    {
        GlobalMemory globalMemory = systemInfo.getHardware().getMemory();

        long totalMemory = globalMemory.getTotal();
        long availableMemory = globalMemory.getAvailable();

        return (int) Math.round(100 - (((double) availableMemory / totalMemory) * 100));
    }

    /**
     * Gets storage usage
     *
     * @return int that display storage usage
     */
    private int getStorage()
    {
        FileSystem fileSystem = systemInfo.getOperatingSystem().getFileSystem();

        long totalStorage = fileSystem.getFileStores().stream().mapToLong(OSFileStore::getTotalSpace).sum();
        long freeStorage = fileSystem.getFileStores().stream().mapToLong(OSFileStore::getFreeSpace).sum();

        return (int) Math.round(((double) (totalStorage - freeStorage) / totalStorage) * 100);
    }

    /**
     * Used to deliver dto to corresponding controller
     *
     * @return ResponseEntityWrapperAsset filled with usageDto
     */
    public UsageDto getUsage()
    {

            UsageDto usageDto = new UsageDto();

            usageDto.setProcessor(getProcessor());
            usageDto.setRam(getRam());
            usageDto.setStorage(getStorage());

            return usageDto;


    }
}