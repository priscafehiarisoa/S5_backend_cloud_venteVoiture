CREATE OR REPLACE FUNCTION getsequence(p_length INT, p_prefix VARCHAR, sequenceName varchar, OUT result VARCHAR)
AS $$
DECLARE
    seq_val INT;
BEGIN
    -- Obtenir la prochaine valeur de la séquence
    SELECT NEXTVAL(sequenceName) INTO seq_val;

    -- Générer la séquence avec le préfixe et la longueur spécifiés
    result := p_prefix || LPAD(seq_val::TEXT, p_length, '0');
END;
$$ LANGUAGE plpgsql;

select * from getsequence(3,'PRA','moteur_seq')