package gestionVehicules.initializer;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class DatabaseInitializer {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PostConstruct
    public void init() {
        // Votre script SQL ici
        String sql = "CREATE OR REPLACE FUNCTION getsequence(p_length INT, p_prefix VARCHAR, sequenceName varchar, OUT result VARCHAR)\n" +
                "AS $$\n" +
                "DECLARE\n" +
                "    seq_val INT;\n" +
                "BEGIN\n" +
                "    -- Obtenir la prochaine valeur de la séquence\n" +
                "    SELECT NEXTVAL(sequenceName) INTO seq_val;\n" +
                "\n" +
                "    -- Générer la séquence avec le préfixe et la longueur spécifiés\n" +
                "    result := p_prefix || LPAD(seq_val::TEXT, p_length, '0');\n" +
                "END;\n" +
                "$$ LANGUAGE plpgsql;";

        jdbcTemplate.execute(sql);
    }
}