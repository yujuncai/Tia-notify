package org.kikyou.tia.netty.chatroom.utils.monitor;



import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.unit.DataUnit;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.GlobalMemory;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.hardware.NetworkIF;
import oshi.software.os.FileSystem;
import oshi.software.os.OSFileStore;
import oshi.software.os.OperatingSystem;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * 系统信息获取工具类
 * 1.操作系统信息
 * 2.系统cpu使用信息
 * 3.系统内存信息
 * 4.系统卡流量信息
 * 5.磁盘使用量信息
 */
@Slf4j
public class SystemInfoUtil {

    private static SystemInfo systemInfo;

    private static HardwareAbstractionLayer abstractionLayer;

    private static OperatingSystem operatingSystem;

    private static CentralProcessor centralProcessor;

    private static GlobalMemory globalMemory;

    private static long[] oldTicks;

    private static Map<String, Long[]> networkInfoMap;

    private static List<NetworkIF> networkIFList;

    private static DecimalFormat df = new DecimalFormat("0.00");

    private SystemInfoUtil() {
    }


    static {
        try {
            systemInfo = new SystemInfo();
            abstractionLayer = systemInfo.getHardware();
            operatingSystem = systemInfo.getOperatingSystem();
            centralProcessor = abstractionLayer.getProcessor();
            globalMemory = abstractionLayer.getMemory();
            oldTicks = new long[CentralProcessor.TickType.values().length];
            networkInfoMap = new ConcurrentHashMap<>();
            networkIFList = getNetwork();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    enum NetworkType {
        /**
         * 当前网卡时间戳
         */
        TIME_STAMP(0),
        /**
         * 网卡总发送量
         */
        SEND(1),
        /**
         * 网卡总接收量
         */
        ACCEPT(2);
        private int index;

        NetworkType(int value) {
            this.index = value;
        }

        public int getIndex() {
            return index;
        }
    }

    /**
     * 获取磁盘列表信息
     *
     * @return
     */
    public static List<DisksInfo> getDisksList() {
        FileSystem fileSystem = operatingSystem.getFileSystem();
        List<OSFileStore> fileStores = fileSystem.getFileStores();
        List<DisksInfo> list = new ArrayList<>();
        for (int i = 0; i < fileStores.size(); i++) {
            OSFileStore osFileStore = fileStores.get(i);
            DisksInfo disksInfo = new DisksInfo();
            disksInfo.setDirName(osFileStore.getMount());
            String name = osFileStore.getName();
            disksInfo.setSysTypeName(name);
            disksInfo.setTypeName(osFileStore.getType());
            long total = osFileStore.getTotalSpace();
            long free = osFileStore.getUsableSpace();
            long used = total - free;
            if (used < 0) {
                // 有的挂载盘获取到的 free比total还大 不知是否是方法问题
                continue;
            }
            disksInfo.setTotal(total);
            disksInfo.setFree(free);
            disksInfo.setUsed(used);
            if (total != 0) {
                disksInfo.setUsage(Double.parseDouble(df.format((double) used / total)));
            }
            list.add(disksInfo);
        }
        return list;
    }

    /**
     * 获取网络接口NetworkIF对象列表
     *
     * @return
     */
    private static List<NetworkIF> getNetwork() {
        List<NetworkIF> list = new ArrayList<>();
        List<NetworkIF> networkIFs = abstractionLayer.getNetworkIFs();
        for (int i = 0; i < networkIFs.size(); i++) {
            NetworkIF networkIF = networkIFs.get(i);
            if (!networkIF.isKnownVmMacAddr()) {
                if (networkIF.getMacaddr() != null && networkIF.getIPv4addr().length > 0
                        && networkIF.getIPv6addr().length > 0) {
                    if (!networkInfoMap.containsKey(networkIF.getMacaddr())) {
                        networkIF.updateAttributes();
                        Long[] data = new Long[]{networkIF.getTimeStamp(), networkIF.getBytesSent(), networkIF.getBytesRecv()};
                        networkInfoMap.put(networkIF.getMacaddr(), data);
                    }
                    list.add(networkIF);
                }
            }
        }
        return list;
    }

    /**
     * 网卡实际的传输速率受多方面影响，具体如下：
     * 1、硬盘的读写（I/O）速度达不到。
     * 2、网卡本身性能差。
     * 3、交换机/（HUB）性能差。
     * 4、通讯线路条件质量差。
     * 5、网络性能差。
     * <p>
     * 一般情况下：
     * 网络条件比较好的网络利用率100Mbits的一般为：1/8(此方法默认)
     * 网络及各方面条件差的利用率一般为：1/12
     */
    public static List<NetworkInfo> getNetworkInfo() {
        networkIFList = getNetwork();
        List<NetworkInfo> list = new ArrayList<>();
        for (int i = 0; i < networkIFList.size(); i++) {
            NetworkIF networkIF = networkIFList.get(i);
            if (networkIF.updateAttributes()) {
                if (networkIF.getIPv4addr().length > 0 && networkIF.getIPv6addr().length > 0) {
                    NetworkInfo networkInfo = new NetworkInfo();
                    networkInfo.setIpv4Address(networkIF.getIPv4addr()[0]);
                    networkInfo.setIpv6Address(networkIF.getIPv6addr()[0]);
                    networkInfo.setMacAddress(networkIF.getMacaddr());
                    networkInfo.setNetworkName(networkIF.getName());

                    //计算
                    Long[] oldData = networkInfoMap.get(networkIF.getMacaddr());
                    long time = oldData[NetworkType.TIME_STAMP.getIndex()] - networkIF.getTimeStamp();
                    if (time == 0) {
                        continue;
                    }
                    long send = (oldData[NetworkType.SEND.getIndex()] - networkIF.getBytesSent()) * 8 / time * 1000;
                    long accept = (oldData[NetworkType.ACCEPT.getIndex()] - networkIF.getBytesRecv()) * 8 / time * 1000;
                    Long[] newData = new Long[]{networkIF.getTimeStamp(), networkIF.getBytesSent(), networkIF.getBytesRecv()};
                    networkInfoMap.put(networkInfo.getMacAddress(), newData);

                    //对象赋值
                    networkInfo.setTimeStamp(networkIF.getTimeStamp());
                    networkInfo.setSend(send);
                    networkInfo.setAccept(accept);
                    list.add(networkInfo);
                }
            }

        }
        return list;
    }

    /**
     * 获取系统运行信息
     *
     * @return
     * @throws InterruptedException
     */
    public static OSRuntimeInfo getOSRuntimeInfo() throws InterruptedException {
        OSRuntimeInfo osRuntimeInfo = new OSRuntimeInfo();
        osRuntimeInfo.setTimestamp(DateUtil.now());
        //cpu使用率
        osRuntimeInfo.setCpuUsage(getCpuRate());
        //cpu基准速度（GHz）
        osRuntimeInfo.setCpuMaxFreq(df.format(centralProcessor.getMaxFreq() / 1000000000.0) + " GHz");
        //cpu当前速度（GHz）
        long[] currentFreq = centralProcessor.getCurrentFreq();
        long avg = Arrays.stream(currentFreq).sum() / currentFreq.length;
        osRuntimeInfo.setCpuCurrentFreq(df.format(avg / 1000000000.0) + " GHz");
        //系统内存总量
        osRuntimeInfo.setTotalMemory(globalMemory.getTotal());
        //系统使用量
        osRuntimeInfo.setUsedMemory(globalMemory.getTotal() - globalMemory.getAvailable());
        //可用虚拟总内存
        osRuntimeInfo.setSwapTotalMemory(globalMemory.getVirtualMemory().getSwapTotal());
        //已用虚拟内存
        osRuntimeInfo.setSwapUsedMemory(globalMemory.getVirtualMemory().getSwapUsed());
        //磁盘信息
        osRuntimeInfo.setDisksList(getDisksList());
        //磁盘读取速率
        Map<String, Double> diskIo = getDiskIo();
        double diskReadRate = diskIo.get("diskReadRate");
        osRuntimeInfo.setDiskReadRate(diskReadRate);
        //磁盘写入速率
        double diskWriteRate = diskIo.get("diskWriteRate");
        osRuntimeInfo.setDiskWriteRate(diskWriteRate);
        //网卡信息
        osRuntimeInfo.setNetworkList(getNetworkInfo());
        return osRuntimeInfo;
    }

    public static OSInfo getSystemInfo() {
        Properties props = System.getProperties();
        OSInfo osInfo = new OSInfo();
        //操作系统
        osInfo.setOs(props.getProperty("os.name"));
        //系统架构
        osInfo.setOsArch(props.getProperty("os.arch"));
        //java版本
        osInfo.setJavaVersion(props.getProperty("java.version"));
        //工作目录
        osInfo.setUserDir(props.getProperty("user.dir"));
        //CPU核数
        osInfo.setCpuCount(centralProcessor.getLogicalProcessorCount());
        //主机信息
        try {
            InetAddress address = InetAddress.getLocalHost();
            osInfo.setHost(address.getHostAddress());
            osInfo.setHostName(address.getHostName());
        } catch (UnknownHostException e) {
            log.error("主机信息获取失败");
        }
        //系统启动时间
        osInfo.setBootTime(getBootTime());
        return osInfo;
    }


    public static void main(String[] args) throws Exception {

        //系统信息
        System.out.println("-----------系统信息-----------");
        OSInfo osInfo = getSystemInfo();
        System.out.println("操作系统："+ osInfo.getOs());
        System.out.println("系统架构："+ osInfo.getOsArch());
        System.out.println("Java版本："+ osInfo.getJavaVersion());
        System.out.println("工作目录："+ osInfo.getUserDir());
        System.out.println("cpu核心数："+ osInfo.getCpuCount());
        System.out.println("主机host："+ osInfo.getHost());
        System.out.println("主机名称："+ osInfo.getHostName());
        String bootTime = osInfo.getBootTime();
        String runTime = "";
        if (StrUtil.isNotEmpty(bootTime)) {
            DateTime start = DateUtil.parse(bootTime, DatePattern.NORM_DATETIME_PATTERN);
            runTime = DateUtil.formatBetween(start, DateUtil.date());
        }
        System.out.println("系统正常运行时长："+ runTime);
        //运行时信息
        System.out.println("-----------运行时信息-----------");
        OSRuntimeInfo osRuntimeInfo = getOSRuntimeInfo();
        //1.CPU信息
        System.out.println("------cpu信息------");
        System.out.println("cpu使用率：" + formatRate(osRuntimeInfo.getCpuUsage()));
        System.out.println("cpu基准速度：" + osRuntimeInfo.getCpuMaxFreq());
        System.out.println("cpu速度：" + osRuntimeInfo.getCpuCurrentFreq());
        //2.内存信息
        System.out.println("------内存信息------");
        //系统内存总量
        long total = osRuntimeInfo.getTotalMemory();
        long used = osRuntimeInfo.getUsedMemory();
        double usage = used * 1.0 / total;
        System.out.println("系统内存总量：" + total + " -> " + formatData(total));
        System.out.println("系统内存使用量：" + used + " -> " + formatData(used));
        System.out.println("系统内存使用率：" + formatRate(usage));
        //可用虚拟总内存
        long swapTotal = osRuntimeInfo.getSwapTotalMemory();
        //已用虚拟内存
        long swapUsed = osRuntimeInfo.getSwapUsedMemory();
        System.out.println("可用虚拟总内存(swap)：" + swapTotal + " -> " + formatData(swapTotal));
        System.out.println("虚拟内存使用量(swap)：" + swapUsed + " -> " + formatData(swapUsed));
        //3.磁盘信息
        System.out.println("------磁盘信息------");
        System.out.println("磁盘读取速度：" + osRuntimeInfo.getDiskReadRate() + "Kb/s");
        System.out.println("磁盘写入速度：" + osRuntimeInfo.getDiskWriteRate() + "Kb/s");
        List<DisksInfo> disksList = osRuntimeInfo.getDisksList();
        for (DisksInfo disksInfo : disksList) {
            System.out.println("挂载点：" + disksInfo.getDirName());
            System.out.println("文件系统名称：" + disksInfo.getSysTypeName());
            System.out.println("文件系统类型：" + disksInfo.getTypeName());
            System.out.println("磁盘总量：" + disksInfo.getTotal() + " -> " + formatData(disksInfo.getTotal()));
            System.out.println("磁盘使用量：" + disksInfo.getUsed() + " -> " + formatData(disksInfo.getUsed()));
            System.out.println("磁盘剩余量：" + disksInfo.getFree() + " -> " + formatData(disksInfo.getFree()));
            System.out.println("磁盘使用率：" + formatRate(disksInfo.getUsage()));
        }
        //4.网卡网络信息
        List<NetworkInfo> netList = getNetworkInfo();
        System.out.println("------网卡网络信息------");
        for (NetworkInfo networkInfo : netList) {
            System.out.println("ipv4地址："+networkInfo.getIpv4Address());
            System.out.println("mac地址："+networkInfo.getMacAddress());
            System.out.println("网卡名称："+networkInfo.getNetworkName());
            double send = networkInfo.getSend() / 1024.0;
            double accept = networkInfo.getAccept() / 1024.0;
            System.out.println("上传速度↑："+String.format("%.1f%s", send, "Kbps"));
            System.out.println("下载速度↓："+String.format("%.1f%s", accept, "Kbps"));
        }
    }


    /**
     * @return cpuRate cpu使用率
     * @throws InterruptedException
     */
    public static double getCpuRate() throws InterruptedException {
        CentralProcessor processor = systemInfo.getHardware().getProcessor();
        long[] prevTicks = processor.getSystemCpuLoadTicks();
        // 睡眠1s
        TimeUnit.SECONDS.sleep(1);
        long[] ticks = processor.getSystemCpuLoadTicks();
        long nice = ticks[CentralProcessor.TickType.NICE.getIndex()]
                - prevTicks[CentralProcessor.TickType.NICE.getIndex()];
        long irq = ticks[CentralProcessor.TickType.IRQ.getIndex()]
                - prevTicks[CentralProcessor.TickType.IRQ.getIndex()];
        long softirq = ticks[CentralProcessor.TickType.SOFTIRQ.getIndex()]
                - prevTicks[CentralProcessor.TickType.SOFTIRQ.getIndex()];
        long steal = ticks[CentralProcessor.TickType.STEAL.getIndex()]
                - prevTicks[CentralProcessor.TickType.STEAL.getIndex()];
        long cSys = ticks[CentralProcessor.TickType.SYSTEM.getIndex()]
                - prevTicks[CentralProcessor.TickType.SYSTEM.getIndex()];
        long user = ticks[CentralProcessor.TickType.USER.getIndex()]
                - prevTicks[CentralProcessor.TickType.USER.getIndex()];
        long iowait = ticks[CentralProcessor.TickType.IOWAIT.getIndex()]
                - prevTicks[CentralProcessor.TickType.IOWAIT.getIndex()];
        long idle = ticks[CentralProcessor.TickType.IDLE.getIndex()]
                - prevTicks[CentralProcessor.TickType.IDLE.getIndex()];
        long totalCpu = user + nice + cSys + idle + iowait + irq + softirq + steal;
        double d = 1.0 - (idle * 1.0 / totalCpu);
        return Double.parseDouble(df.format(d));
    }

    /**
     * 获取系统启动时间
     *
     * @return
     */
    public static String getBootTime() {
        String uptime = "";
        String os = System.getProperty("os.name").toLowerCase();
        try {
            if (os.contains("win")) {
                Process uptimeProc = Runtime.getRuntime().exec("net statistics Workstation");
                BufferedReader in = new BufferedReader(new InputStreamReader(uptimeProc.getInputStream(), Charset.forName("GBK")));
                String line;
                while ((line = in.readLine()) != null) {
                    if (line.startsWith("Statistics since")) {
                        SimpleDateFormat format = new SimpleDateFormat("'Statistics since' yyyy/MM/dd HH:mm:ss");
                        Date bootTime = format.parse(line);
                        uptime = DateUtil.format(bootTime, DatePattern.NORM_DATETIME_PATTERN);
                        break;
                    } else if (line.startsWith("统计数据开始于")) {
                        SimpleDateFormat format = new SimpleDateFormat("'统计数据开始于' yyyy/MM/dd HH:mm:ss");
                        Date bootTime = format.parse(line);
                        uptime = DateUtil.format(bootTime, DatePattern.NORM_DATETIME_PATTERN);
                        break;
                    }
                }
            } else if (os.contains("mac") || os.contains("nix") || os.contains("nux") || os.contains("aix")) {
                Process uptimeProc = Runtime.getRuntime().exec("uptime -s");
                BufferedReader in = new BufferedReader(new InputStreamReader(uptimeProc.getInputStream()));
                String line = in.readLine();
                if (line != null) {
                    uptime = line;
                }
            }
        } catch (Exception e) {
            log.error("获取系统启动时间异常: {}", e.getMessage());
        }
        return uptime;
    }

    /**
     * 获取磁盘读写速度（Kb/秒）
     *
     * @return
     */
    public static Map<String, Double> getDiskIo() {
        Map<String, Double> map = new HashMap<>();
        StringBuilder sb = new StringBuilder();
        String os = System.getProperty("os.name").toLowerCase();
        try {
            if (os.contains("mac") || os.contains("nix") || os.contains("nux") || os.contains("aix")) {
                Process pos = Runtime.getRuntime().exec("iostat -d 1 2");
                pos.waitFor();
                InputStreamReader isr = new InputStreamReader(pos.getInputStream());
                LineNumberReader lnr = new LineNumberReader(isr);
                String line;
                while ((line = lnr.readLine()) != null) {
                    sb.append(line).append("\n");
                }
            }
            String info = sb.toString();
            if (StrUtil.isEmpty(info)) {
                map.put("diskReadRate", 0D);
                map.put("diskWriteRate", 0D);
                return map;
            }
            String[] data = info.split("\n");
            for (int i = 7; i < data.length; i++) {
                String[] numdata = data[i].split(" +");
                String devName = numdata[0];
                double diskReadRate = Double.parseDouble(numdata[2]); //磁盘读数据速率
                double diskWriteRate = Double.parseDouble(numdata[3]); //磁盘写数据速率
                //这里简单统计，只需要统计一个就行，直接break结束循环
                map.put("diskReadRate", diskReadRate);
                map.put("diskWriteRate", diskWriteRate);
                break;
            }
        } catch (Exception e) {
            map.put("diskReadRate", 0D);
            map.put("diskWriteRate", 0D);
            log.error("获取磁盘传输速度异常: {}", e.getMessage());
        }
        return map;
    }

    /**
     * 格式化输出百分比
     *
     * @param rate 待格式化数据
     *
     * <pre>
     *     0.1234   -> 12.34%
     *     1.2      -> 120%
     * </pre>
     * @return
     */
    public static String formatRate(double rate) {
        return new DecimalFormat("#.##%").format(rate);
    }

    /**
     * 格式化输出大小 B/KB/MB...
     *
     * @param size 字节大小
     * @return
     */
    public static String formatData(long size) {
        if (size <= 0L) {
            return "0B";
        } else {
            int digitGroups = Math.min(DataUnit.UNIT_NAMES.length - 1, (int) (Math.log10((double) size) / Math.log10(1024.0D)));
            return (new DecimalFormat("#,##0.##")).format((double) size / Math.pow(1024.0D, (double) digitGroups)) + " " + DataUnit.UNIT_NAMES[digitGroups];
        }
    }
}

