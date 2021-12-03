package ru.eatit.gateway.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.LocatedFileStatus;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.RemoteIterator;
import org.apache.hadoop.hdfs.DistributedFileSystem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.eatit.gateway.controller.entity.response.TaskIdResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
public class HadoopService {

    private final ResponseCacheService responseCacheService;

    @Autowired
    public HadoopService(ResponseCacheService responseCacheService) {
        this.responseCacheService = responseCacheService;
    }

    public FileSystem getFileSystem() {
        Configuration conf = new Configuration();
        conf.set("hadoop.job.ugi", "team14");
        // user = ?
        conf.addResource(new Path("conf/core-site.xml"));
        conf.addResource(new Path("conf/hdfs-site.xml"));
        conf.set("fs.hdfs.impl", DistributedFileSystem.class.getName());
        System.setProperty("HADOOP_USER_NAME", "team14");
        try {
            return FileSystem.get(conf);
        } catch (IOException e) {
            return null;
        }
    }


    /**
     * Положит к кэш респонсов найденные файлы, содержащие Имя или Фамилию пользователя
     *
     * @param taskId id response
     * @param set    набор ключевых слов
     * @return Найденные имена файлов из hdfs
     */
    public void analyseFiles(String taskId, Set<String> set) {

        List<String> result = new ArrayList<>();
        FileSystem fileSystem = getFileSystem();
        try {
            log.info("Подключились к: " + fileSystem.getUri());
            RemoteIterator<LocatedFileStatus> iterator = null;
            iterator = fileSystem.listFiles(new Path("/user/team14/input"), false);
            while (iterator.hasNext()) {
                Path file = iterator.next().getPath();
                if (file.getName().toLowerCase().endsWith("html")) {
                    for (String word : set) {
                        if (file.getName().contains(word)) {
                            result.add(file.getName());
                        }
                        break;
                    }
                }
                log.info(file.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fileSystem != null) {
                    fileSystem.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        responseCacheService.put(taskId, new TaskIdResponse(taskId, 5000, null, result));
    }

}
