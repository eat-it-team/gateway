package ru.eatit.gateway.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class HealthCheckController {

    @GetMapping("healthcheck")
    public String ok() {
        return "ok";
    }

    @GetMapping("hd")
    public void hd() {
        try {
            Configuration conf = new Configuration();
            conf.set("hadoop.job.ugi", "team14");
            conf.addResource(new Path("conf/core-site.xml"));
            conf.addResource(new Path("conf/hdfs-site.xml"));
            FileSystem fileSystem = FileSystem.get(conf);
            log.info("Подключились к: " + fileSystem.getUri());
            Path path = new Path("/usr/src/app/Pavel");
            log.info("LS: " + fileSystem.listFiles(new Path(""), false));
            boolean rez = fileSystem.mkdirs(path);
            log.info("Rez + " + rez);
            fileSystem.close();
        } catch (Exception exc) {
            log.error("Exception Pavil: ", exc);
        }
    }
}
