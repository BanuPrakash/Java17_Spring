package client;

import com.adobe.api.LogService;

import java.util.ServiceLoader;

public class Main {
    public static void main(String[] args) {
        ServiceLoader<LogService> loader = ServiceLoader.load(LogService.class);
        for(LogService service: loader) {
            service.log("Log written by " + service.getClass());
        }
    }
}
