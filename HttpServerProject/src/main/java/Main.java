import services.impl.HttpServiceImpl;

public class Main {
    public static void main(String[] args) {
        HttpServiceImpl service = new HttpServiceImpl();
        service.connect(9090);
    }
}
