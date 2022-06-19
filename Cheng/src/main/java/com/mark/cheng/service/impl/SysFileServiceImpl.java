package com.mark.cheng.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.mark.cheng.entity.SysFile;
import com.mark.cheng.mapper.SysFileMapper;
import com.mark.cheng.service.SysFileService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 服務實現類別
 *
 * @author Cheng®
 * @since 2022-06-05
 */
@Service
public class SysFileServiceImpl extends ServiceImpl<SysFileMapper, SysFile> implements SysFileService {

    @Resource
    private SysFileMapper sysFileMapper;

    public SysFile getFileByMd5(String md5) {
        LambdaQueryWrapper<SysFile> wrapper = new LambdaQueryWrapper<SysFile>()
                .eq(SysFile::getMd5, md5);
        return sysFileMapper.selectOne(wrapper);
    }
}
