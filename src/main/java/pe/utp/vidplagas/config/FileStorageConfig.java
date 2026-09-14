package pe.utp.vidplagas.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Expone la carpeta {app.upload-dir} (fuera del classpath, para que
 * sobreviva a un empaquetado en .jar) como recurso estatico bajo /uploads/**,
 * de modo que las fotos de evidencia subidas en los reportes se puedan
 * visualizar directamente, ej: http://localhost:8080/uploads/archivo.jpg
 */
@Configuration
public class FileStorageConfig implements WebMvcConfigurer {

    @Value("${app.upload-dir}")
    private String uploadDir;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String ubicacion = "file:" + uploadDir + "/";
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations(ubicacion);
    }
}
