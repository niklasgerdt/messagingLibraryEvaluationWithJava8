package mom.analysis;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import com.google.gson.Gson;

@Component
@PropertySource("classpath:/basic_simulation.properties")
public class ReportWriter {
    @Value("${reportname}")
    private String fileName;
    private final Gson gson = new Gson();

    public void write(Report report) {
        String asJson = gson.toJson(report);
        File f = new File(fileName + "." + System.currentTimeMillis());
        try {
            f.createNewFile();
            FileOutputStream fos = new FileOutputStream(f);
            fos.write(asJson.getBytes(), 0, asJson.length());
            fos.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
