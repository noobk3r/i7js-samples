package com.itextpdf.samples.book.part1.chapter02;

import com.itextpdf.core.testutils.annotations.type.SampleTest;
import com.itextpdf.samples.GenericTest;
import com.lowagie.database.DatabaseConnection;
import com.lowagie.database.HsqldbConnection;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Ignore;
import org.junit.experimental.categories.Category;

@Ignore
@Category(SampleTest.class)
public class Listing_02_01_DatabaseTest extends GenericTest {
    public static final String DEST = "./target/test/resources/book/part1/chapter02/Listing_02_01_DatabaseTest.txt";

    public static void main(String args[]) throws IOException, SQLException {
        new Listing_02_01_DatabaseTest().manipulatePdf(DEST);
    }

    public void manipulatePdf(String dest) throws FileNotFoundException, SQLException, UnsupportedEncodingException {
        // no PDF, just a text file
        PrintStream out = new PrintStream(new FileOutputStream(DEST));
        // Make the connection to the database
        DatabaseConnection connection = new HsqldbConnection("filmfestival");
        // create the statement
        Statement stm = connection.createStatement();
        // execute the query
        ResultSet rs = stm.executeQuery("SELECT country FROM film_country ORDER BY country");
        // loop over the results
        while (rs.next()) {
            // write a country to the text file
            out.println(rs.getString("country"));
        }
        // close the statement
        stm.close();
        // close the database connection
        connection.close();
        // flush and close the print stream
        out.flush();
        out.close();
    }
}
