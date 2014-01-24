package no.hist.tdat.database.verktoy;

import no.hist.tdat.javabeans.Oving;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Klassen BrukerKoordinerer tar i mot data fra databaser, slik at spørringer retunerer
 * objekter av typen Bruker.
 *
 * @author vimCnett
 */
public class KoeOvingKoordinerer implements RowMapper<Oving>{
    /**
     * Denne metoden er en mal på hvordan Bruker objekter skal opprettes ved henting fra database.
     * @param resultSet settes av Spring, og er resultatet fra database-spørringen
     * @param rowNum sier hvor langt i resultsettet vi har kommet
     * @return et Bruker-objekt ut i fra datane i databasen.
     * @throws java.sql.SQLException exception blir tatt hånd om av rammeverket.
     */
    @Override
    public Oving mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Oving oving = new Oving();
        oving.setOvingnr(resultSet.getInt("oving_nr"));
        oving.setOvingid(resultSet.getInt("oving_id"));
        return oving;
    }
}
