package org.kikyou.tia.netty.chatroom.utils.monitor;


import lombok.Data;

/**
 * 磁盘信息封装类
 *
 */
@Data
public class DisksInfo {

    /**
     * 文件系统的挂载点
     */
    private String dirName;

    /**
     * 文件系统名称
     */
    private String sysTypeName;

    /**
     * 文件系统的类型(FAT，NTFS，etx2，ext4等)
     */
    private String typeName;

    /**
     * 总大小
     */
    private long total;

    /**
     * 剩余大小
     */
    private long free;

    /**
     * 已经使用量
     */
    private long used;

    /**
     * 资源的使用率
     */
    private double usage = 100;
}

