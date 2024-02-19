package client;

//import com.adobe.repo.EmployeeRepo;
import com.adobe.service.AppService;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        AppService service = new AppService();
//        EmployeeRepo repo;
        service.insert();
    }
}