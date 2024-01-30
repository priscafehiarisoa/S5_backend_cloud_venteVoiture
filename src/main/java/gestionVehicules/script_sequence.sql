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


--  get solde client
select * from commissions



CREATE SEQUENCE user_seq START 1;

CREATE SEQUENCE categorie_seq START 1;
CREATE SEQUENCE annonce_seq START 1;
CREATE SEQUENCE boite_seq START 1;
CREATE SEQUENCE carburant_seq START 1;
CREATE SEQUENCE commission_annonce_seq START 1;
CREATE SEQUENCE commission_seq START 1;
CREATE SEQUENCE couleur_seq START 1;
CREATE SEQUENCE favori_seq START 1;
CREATE SEQUENCE marque_seq START 1;
CREATE SEQUENCE modele_seq START 1;
CREATE SEQUENCE moteur_seq START 1;
CREATE SEQUENCE pays_seq START 1;
CREATE SEQUENCE transactions_seq START 1;
CREATE SEQUENCE vehicule_seq START 1;
CREATE SEQUENCE image_seq START 1;
