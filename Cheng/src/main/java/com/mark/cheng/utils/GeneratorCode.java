package com.mark.cheng.utils;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * use mybatis-plus generator code
 *
 * @author cheng
 * @since 2022/5/20 18:54
 **/
public class GeneratorCode {

    public static void main(String[] args) {
        generate();
    }

    private static void generate() {
        /* =====資料庫連線 資訊===== */
        // 設定要連線的DB URL
        String url = "jdbc:mysql://localhost:3306/cheng?serverTimezone=GMT%2b8";
        // DB的帳號
        String username = "root";
        // DB的密碼
        String password = "e23s5";
        // 作者名稱
        String author = "Cheng®";
        /* =====資料庫連線 資訊===== */

        /* =====Package 資訊===== */
        // This class packageName
        String packName = GeneratorCode.class.getPackage().getName();
        // 最上層的package名稱
        String parentPackageName = packName.substring(0, packName.lastIndexOf("."));
        /* =====Package 資訊===== */

        /* =====專案路徑 資訊===== */
        // 自訂的資料夾(若無可不加)
        String myDir = File.separator + "Cheng";
        // 專案路徑
        String projectPath = System.getProperty("user.dir") + myDir;
        /* =====專案路徑 資訊===== */

        /* =====輸出程式碼路徑 資訊===== */
        // 要輸出code的路徑
        String outputDir = projectPath + "/src/main/java";
        // 輸出mapper檔案的路徑
        String outputFile = projectPath + "/src/main/java/" + parentPackageName.replace(".", File.separator) + "/mapper";
        /* =====輸出程式碼路徑 資訊===== */

        /* =====資料庫表格 資訊===== */
        // 設定要產生的表格名稱
        List<String> tableNames = Arrays.asList("sys_role_menu");
        // 設定要過濾的表格名稱的前綴字
        List<String> tablePrefixNames = Arrays.asList("");
        /* =====資料庫表格 資訊===== */


        FastAutoGenerator.create(url, username, password)
                .globalConfig(builder ->
                        builder.author(author)
                        .enableSwagger()
                        .outputDir(outputDir))
                .packageConfig(builder ->
                        builder.parent(parentPackageName)
                        // 會在Controller上面的@RequestMaping第一個路徑名稱
                        .moduleName("")
                        .pathInfo(Collections.singletonMap(OutputFile.mapper, outputFile)))
                .strategyConfig(builder -> {
                    // 使用Lombok
                    builder.entityBuilder().enableLombok();
                    // 使用鴕峰轉連字串
                    builder.controllerBuilder().enableHyphenStyle()
                            // 使用@RestController
                            .enableRestStyle();
                    // 使用@Mapper，如果有在Config中設定 @MapperScan("com.mark.cheng.mapper")就可以不用再加上@Mapper
//                    builder.mapperBuilder().enableMapperAnnotation();
                    builder.addInclude(tableNames)
                            .addTablePrefix(tablePrefixNames);
                })
//                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，預設的是Velocity引擎模板
                .execute();
    }
}
