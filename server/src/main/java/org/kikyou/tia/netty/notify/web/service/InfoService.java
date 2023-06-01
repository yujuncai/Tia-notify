package org.kikyou.tia.netty.notify.web.service;


import jakarta.annotation.Resource;
import org.kikyou.tia.netty.notify.web.dto.InfoDto;
import org.kikyou.tia.netty.notify.web.dto.MachineDto;
import org.kikyou.tia.netty.notify.web.dto.ProcessorDto;
import org.kikyou.tia.netty.notify.web.dto.StorageDto;
import org.springframework.stereotype.Service;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.GlobalMemory;
import oshi.hardware.HWDiskStore;
import oshi.hardware.PhysicalMemory;
import oshi.software.os.OperatingSystem;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Service
public class InfoService
{

    @Resource
    private SystemInfo systemInfo;


    private String getConvertedFrequency(final long[] hertzArray)
    {
        long totalFrequency = Arrays.stream(hertzArray).sum();
        long hertz = totalFrequency / hertzArray.length;

        if ((hertz / 1E+6) > 999)
        {
            return (Math.round((hertz / 1E+9) * 10.0) / 10.0) + " GHz";
        }
        else
        {
            return Math.round(hertz / 1E+6) + " MHz";
        }
    }


    private String getConvertedCapacity(final long bits)
    {
        if ((bits / 1.049E+6) > 999)
        {
            if ((bits / 1.074E+9) > 999)
            {
                return (Math.round((bits / 1.1E+12) * 10.0) / 10.0) + " TiB";
            }
            else
            {
                return Math.round(bits / 1.074E+9) + " GiB";
            }
        }
        else
        {
            return Math.round(bits / 1.049E+6) + " MiB";
        }
    }


    private ProcessorDto getProcessor()
    {
        ProcessorDto processorDto = new ProcessorDto();

        CentralProcessor centralProcessor = systemInfo.getHardware().getProcessor();

        String name = centralProcessor.getProcessorIdentifier().getName();
        if (name.contains("@"))
        {
            name = name.substring(0, name.indexOf('@') - 1);
        }
        processorDto.setName(name.trim());

        int coreCount = centralProcessor.getLogicalProcessorCount();
        processorDto.setCoreCount(coreCount + ((coreCount > 1) ? " Cores" : " Core"));
        processorDto.setClockSpeed(getConvertedFrequency(centralProcessor.getCurrentFreq()));

        String bitDepthPrefix = centralProcessor.getProcessorIdentifier().isCpu64bit() ? "64" : "32";
        processorDto.setBitDepth(bitDepthPrefix + "-bit");

        return processorDto;
    }


    private MachineDto getMachine()
    {
        MachineDto machineDto = new MachineDto();

        OperatingSystem operatingSystem = systemInfo.getOperatingSystem();
        OperatingSystem.OSVersionInfo osVersionInfo = systemInfo.getOperatingSystem().getVersionInfo();
        GlobalMemory globalMemory = systemInfo.getHardware().getMemory();

        machineDto.setOperatingSystem(operatingSystem.getFamily() + " " + osVersionInfo.getVersion() + ", " + osVersionInfo.getCodeName());
        machineDto.setTotalRam(getConvertedCapacity(globalMemory.getTotal()) + " Ram");

        Optional<PhysicalMemory> physicalMemoryOptional = globalMemory.getPhysicalMemory().stream().findFirst();
        if (physicalMemoryOptional.isPresent())
        {
            machineDto.setRamTypeOrOSBitDepth(physicalMemoryOptional.get().getMemoryType());
        }
        else
        {
            machineDto.setRamTypeOrOSBitDepth(operatingSystem.getBitness() + "-bit");
        }

        int processCount = operatingSystem.getProcessCount();
        machineDto.setProcCount(processCount + ((processCount > 1) ? " Procs" : " Proc"));

        return machineDto;
    }


    private StorageDto getStorage()
    {
        StorageDto storageDto = new StorageDto();

        List<HWDiskStore> hwDiskStores = systemInfo.getHardware().getDiskStores();
        GlobalMemory globalMemory = systemInfo.getHardware().getMemory();

        Optional<HWDiskStore> hwDiskStoreOptional = hwDiskStores.stream().findFirst();
        if (hwDiskStoreOptional.isPresent())
        {
            String mainStorage = hwDiskStoreOptional.get().getModel();
            Matcher matcher = Pattern.compile("\\(.{1,15} .{1,15} .{1,15}\\)").matcher(mainStorage);

            if (matcher.find())
            {
                mainStorage = mainStorage.substring(0, matcher.start() - 1);
            }

            storageDto.setMainStorage(mainStorage.trim());
        }
        else
        {
            storageDto.setMainStorage("Undefined");
        }

        long total = hwDiskStores.stream().mapToLong(HWDiskStore::getSize).sum();
        storageDto.setTotal(getConvertedCapacity(total) + " Total");

        int diskCount = hwDiskStores.size();
        storageDto.setDiskCount(diskCount + ((diskCount > 1) ? " Disks" : " Disk"));

        storageDto.setSwapAmount(getConvertedCapacity(globalMemory.getVirtualMemory().getSwapTotal()) + " Swap");

        return storageDto;
    }


    public InfoDto getInfo()
    {

            InfoDto infoDto = new InfoDto();

            infoDto.setProcessor(getProcessor());
            infoDto.setMachine(getMachine());
            infoDto.setStorage(getStorage());

            return infoDto;

    }
}