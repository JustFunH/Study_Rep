package com.meguru.facade;

import com.meguru.facade.plugin.MyPlugin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.net.URL;
import java.net.URLClassLoader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@RestController
public class TimeController {

    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private MyPlugin myPlugin;

    @GetMapping("/time")
    public String getTime() {
        if (myPlugin != null) {
            myPlugin.beforeGetTime();
        }
        return LocalDate.now().format(dateFormatter);
    }

    // 实现了插件的jar包必须存在meguru.plugin文件, 内涵实现MyPlugin的全类名
    @GetMapping("/loadPlugin/{path}")
    public String loadPlugin(@PathVariable("path") String path) {
        File file = new File(path);
        try (URLClassLoader urlClassLoader = new URLClassLoader(new URL[]{file.toPath().toUri().toURL()});
             InputStream fileStream = urlClassLoader.getResourceAsStream("meguru.plugin")) {
            String myPluginFullClassName = new String(fileStream.readAllBytes());
            Class<?> aClass = urlClassLoader.loadClass(myPluginFullClassName);
            Constructor<?> constructor = aClass.getConstructor();
            this.myPlugin = (MyPlugin) constructor.newInstance();
            return "加载成功";
        } catch (Exception ex) {
            return "加载失败";
        }

    }
}
