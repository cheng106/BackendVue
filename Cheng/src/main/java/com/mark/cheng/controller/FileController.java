package com.mark.cheng.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mark.cheng.entity.SysFile;
import com.mark.cheng.model.R;
import com.mark.cheng.service.SysFileService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import static com.mark.cheng.ChengApplication.contextFullPath;

/**
 * @author cheng
 * @since 2022/6/4 15:25
 **/
@Slf4j
@RestController
@RequestMapping("file")
public class FileController {

    @Value("${files.upload.path}")
    private String fileUploadPath;

    @Autowired
    private SysFileService fileService;

    /**
     * 上傳檔案
     *
     * @param file 上傳的檔案
     * @return java.lang.String 檔案URL 連結
     **/
    @PostMapping("upload")
    public String upload(@RequestParam MultipartFile file) {
        String originalFileName = file.getOriginalFilename();
        try {
            // 判斷是否為指定的圖檔
            String type = FileUtil.extName(originalFileName);
            List<String> types = Arrays.asList("png", "jpg", "jpeg");
            if (!types.contains(type)) {
                return "only png or jpg";
            }

            // 取得md5做校驗
            String md5 = DigestUtils.md5Hex(file.getBytes());
            System.out.println("md5 = " + md5);

            // 檢查DB中是否有相同檔案
            SysFile fileByMd5 = fileService.getFileByMd5(md5);
            if (fileByMd5 != null) {
                // 若找到相同檔案則直接返回之前存的記錄
                return fileByMd5.getUrl();
            } else {
                long size = file.getSize();
                log.info("size:{}, type:{}", size, type);
                // 建立資料夾
                Files.createDirectories(Paths.get(fileUploadPath));

                // 新命名一個檔案
                String uuid = IdUtil.simpleUUID();
                String fileName = uuid + StrUtil.DOT + type;
                File uploadFile = new File(fileUploadPath + fileName);

                // 把取到的檔案存到指定目錄
                file.transferTo(uploadFile);
                // 將檔案資訊存到DB
                String url = String.format("%s%s%s", contextFullPath, "/file/", fileName);
                SysFile saveFile = new SysFile();
                saveFile.setName(originalFileName);
                saveFile.setType(type);
                saveFile.setSize(size / FileUtils.ONE_KB);
                saveFile.setUrl(url);
                saveFile.setMd5(md5);
                fileService.save(saveFile);
                return url;
            }

        } catch (IOException e) {
            String msg = e.getMessage();
            log.error("ERR:{}", msg, e);
            return msg;
        }
    }

    @DeleteMapping("{id}")
    @ApiOperation("刪除使用者")
    public R delete(@PathVariable Integer id) {
        SysFile file = fileService.getById(id);
        file.setIsDelete(true);
        return R.success(fileService.updateById(file));
    }

    @PostMapping("del/batch")
    public R deleteBatch(@RequestBody List<Integer> ids) {
        LambdaQueryWrapper<SysFile> wrapper = new LambdaQueryWrapper<SysFile>()
                .in(SysFile::getId, ids);
        fileService.list(wrapper).forEach(f -> {
            f.setIsDelete(true);
            fileService.updateById(f);
        });
        return R.success();
    }

    @PostMapping("update")
    public R update(@RequestBody SysFile file) {
        log.info("-=--------------:{}",file);
        return R.success(fileService.updateById(file));
    }

    @GetMapping("{uuid}")
    public void download(@PathVariable String uuid, HttpServletResponse response) {
        log.info("uuid:{}", uuid);
        File uploadFile = new File(fileUploadPath + uuid);

        try (ServletOutputStream os = response.getOutputStream()) {
            response.addHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" +
                    URLEncoder.encode(uuid, StandardCharsets.UTF_8.name()));
            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);

            // 取得檔案的bytes並寫出
            os.write(FileUtil.readBytes(uploadFile));
            os.flush();

        } catch (IOException e) {
            log.error("ERR:{}", e.getMessage(), e);
        }
    }

    @GetMapping("page")
    @ApiOperation("檔案管理-分頁查詢")
    public IPage<SysFile> findPage(@RequestParam Integer pageNum,
                                   @RequestParam Integer pageSize,
                                   @RequestParam(defaultValue = "") String name) {
        IPage<SysFile> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<SysFile> wrapper = new LambdaQueryWrapper<SysFile>()
                .eq(SysFile::getIsDelete, false)
                .like(SysFile::getName, name);
        return fileService.page(page, wrapper);
    }
}
