package agh.cs.project1b;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.Scanner;

import static org.junit.Assert.*;

public class KonstParserTest {
    private KonstParser parser;

    @Before
    public void SetUp() throws Exception {
        Scanner scanner = new Scanner(new File("C:\\Users\\Ola\\IdeaProjects\\projekt1b\\src\\main\\agh\\cs\\project1b\\uokik.txt"));
        parser = new KonstParser(scanner);
    }
    @Test
    public void isSimpleTextTest() throws Exception {
        assertTrue(parser.isSimpleText("W celu uzyskania informacji mogących stanowić dowód"));
        assertFalse(parser.isSimpleText("Art. 105ia. 1. W celu uzyskania informacji mogących stanowić dowód"));
        assertFalse(parser.isSimpleText("ŹRÓDŁA PRAWA"));
    }

    @Test
    public void findLevelTest() throws Exception {
        assertEquals(Level.DZIAL,parser.findLevel("DZIAŁ I"));
        assertEquals(Level.ROZDZIAL,parser.findLevel("Rozdział III"));
        assertEquals(Level.PODTYTUL,parser.findLevel("ŹRÓDŁA PRAWA"));
        assertEquals(Level.ART,parser.findLevel("Art. 105ia. 1. W celu uzyskania informacji mogących stanowić dowód"));
        assertEquals(Level.UST,parser.findLevel("1. W celu uzyskania informacji mogących stanowić dowód"));
        assertEquals(Level.PKT,parser.findLevel("1) pokoju, sojuszy, układów politycznych lub układów wojskowych,"));
        assertEquals(Level.LIT,parser.findLevel("a) udzielenie w toku kontroli lub przeszukania nieprawdziwych lub"));
    }

    @Test
    public void extractIdNumTest() throws Exception {
        assertEquals("I",parser.extractIdNum("DZIAŁ I",Level.DZIAL));
        assertEquals("III",parser.extractIdNum("Rozdział III",Level.ROZDZIAL));
        assertEquals("ŹRÓDŁA PRAWA",parser.extractIdNum("ŹRÓDŁA PRAWA",Level.PODTYTUL));
        assertEquals("105ia",parser.extractIdNum("Art. 105ia. 1. W celu uzyskania informacji mogących stanowić dowód",Level.ART));
        assertEquals("1",parser.extractIdNum("1. W celu uzyskania informacji mogących stanowić dowód",Level.UST));
        assertEquals("1",parser.extractIdNum("1) pokoju, sojuszy, układów politycznych lub układów wojskowych,",Level.PKT));
        assertEquals("a",parser.extractIdNum("a) udzielenie w toku kontroli lub przeszukania nieprawdziwych lub",Level.LIT));
    }

    @Test
    public void removeIdTest() throws Exception {
        assertEquals("",parser.removeId("DZIAŁ I",Level.DZIAL));
        assertEquals("",parser.removeId("Rozdział III",Level.ROZDZIAL));
        assertEquals("",parser.removeId("ŹRÓDŁA PRAWA",Level.PODTYTUL));
        assertEquals("1. W celu uzyskania informacji mogących stanowić dowód",parser.removeId("Art. 105ia. 1. W celu uzyskania informacji mogących stanowić dowód",Level.ART));
        assertEquals("W celu uzyskania informacji mogących stanowić dowód",parser.removeId("1. W celu uzyskania informacji mogących stanowić dowód",Level.UST));
        assertEquals("pokoju, sojuszy, układów politycznych lub układów wojskowych,",parser.removeId("1) pokoju, sojuszy, układów politycznych lub układów wojskowych,",Level.PKT));
        assertEquals("udzielenie w toku kontroli lub przeszukania nieprawdziwych lub",parser.removeId("a) udzielenie w toku kontroli lub przeszukania nieprawdziwych lub",Level.LIT));
    }

}