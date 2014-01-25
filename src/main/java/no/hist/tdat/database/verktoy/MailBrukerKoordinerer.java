package no.hist.tdat.database.verktoy;

import no.hist.tdat.javabeans.Bruker;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Thomas on 24.01.14.
 */
public class MailBrukerKoordinerer implements RowMapper<Bruker> {
    /**
     * Denne metoden er en mal på hvordan Bruker objekter skal opprettes ved henting fra database.
     *
     * @param resultSet settes av Spring, og er resultatet fra database-spørringen
     * @param rowNum    sier hvor langt i resultsettet vi har kommet
     * @return et Bruker-objekt ut i fra datane i databasen.
     * @throws java.sql.SQLException exception blir tatt hånd om av rammeverket.
     */
    @Override
    public Bruker mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Bruker bruker = new Bruker();
        bruker.setMail(resultSet.getString("mail"));
        return bruker;
    }
}

