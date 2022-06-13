package com.mark.cheng.service;

import com.mark.cheng.entity.SysFile;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 服務類別
 *
 * @author Cheng®
 * @since 2022-06-05
 */
public interface SysFileService extends IService<SysFile> {

    SysFile getFileByMd5(String md5);
}
