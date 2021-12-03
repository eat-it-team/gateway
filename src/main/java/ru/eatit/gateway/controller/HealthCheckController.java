package ru.eatit.gateway.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.LocatedFileStatus;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.RemoteIterator;
import org.apache.hadoop.hdfs.DistributedFileSystem;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class HealthCheckController {

    @GetMapping("healthcheck")
    public String ok() {
        return "ok";
    }

    @GetMapping("hd")
    public void hd(@RequestParam("path") String path) {
        try {
            Configuration conf = new Configuration();
            conf.set("hadoop.job.ugi", "team14");
            // user = ?
            conf.addResource(new Path("conf/core-site.xml"));
            conf.addResource(new Path("conf/hdfs-site.xml"));
            conf.set("fs.hdfs.impl", DistributedFileSystem.class.getName());
            System.setProperty("HADOOP_USER_NAME", "team14");
            FileSystem fileSystem = FileSystem.get(conf);
            log.info("Подключились к: " + fileSystem.getUri());
            RemoteIterator<LocatedFileStatus> iterator = fileSystem.listFiles(new Path(path), false);
            while (iterator.hasNext()) {
                log.info(iterator.next().getPath().toString());
            }
            fileSystem.close();
        } catch (Exception exc) {
            log.error("Exception Pavil: ", exc);
        }
    }
}
