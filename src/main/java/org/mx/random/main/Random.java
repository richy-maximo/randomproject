/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mx.random.main;

import static java.lang.Math.abs;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import org.apache.commons.lang.math.RandomUtils;
import org.mx.random.model.Juzgado;

/**
 *
 * @author Ricardo.Alvarez
 */
public class Random {

    private static List<Juzgado> juzgadoList;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        initLista();
        int listaLength = juzgadoList.size();
        int random = 0;
        
        int opt = 0;
        for (int i = 0; i <= 100; i++) {
        opt = (int) (minutesToday() % 3);
        switch(opt){
            case 0:
                random = (int) (randomize1()%listaLength);
                break;
            case 1:
                random = (int) (randomize2()%listaLength);
                break;
            case 2:
                random = (int) (randomize3()%listaLength);
                break;
        }
        System.out.println("Y EL GANADOR ES -------------------> " + juzgadoList.get(random).getNombre());
        }
        /*
        for (int i = 0; i <= 1000; i++) {
            System.out.println("final " + randomize3());
        }       
        */
        /*
        for (Locale locale : Locale.getAvailableLocales()) {
            System.out.println(locale.getCountry() + "  " 
                    + locale.getDisplayCountry() + "  " + locale.getLanguage() 
                    + "  " + locale.getDisplayLanguage() 
                    + "           " + locale.getDisplayName());
        }   
         */
    }

    private static void initLista() {
        juzgadoList = new ArrayList<>();
        juzgadoList.add(new Juzgado(1, "divorcio", "Juzgado supremo"));
        juzgadoList.add(new Juzgado(2, "divorcio", "Juzgado local"));
        juzgadoList.add(new Juzgado(3, "divorcio", "Juzgado fulanito"));
        juzgadoList.add(new Juzgado(4, "divorcio", "Juzgado perengano"));
        juzgadoList.add(new Juzgado(5, "divorcio", "Juzgado mangano"));
        juzgadoList.add(new Juzgado(6, "divorcio", "Juzgado severiano"));
        juzgadoList.add(new Juzgado(7, "divorcio", "Juzgado enriquiano"));

    }

    private static Long randomize1() {
        System.out.println("r1");
        Long random = 0L;
        Locale locale = DataRandom.random(Locale.getAvailableLocales());

        int opt = 0;

        opt = (int) (milisToday() % 3);

        switch (opt) {
            case 0:
                random = ASCIIValue(locale.getDisplayCountry());
                break;

            case 1:
                random = HASHvalue(locale.getDisplayCountry());
                break;

            case 2:
                random = RandomValue(locale.getDisplayCountry());
                break;
        }

        return abs(random);

    }

    private static Long randomize2() {
        System.out.println("r2");
        Long random = 0L;
        String language = DataRandom.random(Locale.getISOLanguages());

        int opt = 0;

        opt = (int) (milisToday() % 3);

        switch (opt) {
            case 0:
                random = ASCIIValue(language);
                break;

            case 1:
                random = HASHvalue(language);
                break;

            case 2:
                random = RandomValue(language);
                break;
        }

        return abs(random);
    }

    private static Long randomize3() {
        System.out.println("r3");
        Long random = 0L;
        String str = UUID.randomUUID().toString();

        int opt = 0;

        opt = (int) (milisToday() % 3);

        switch (opt) {
            case 0:
                random = ASCIIValue(str);
                break;

            case 1:
                random = HASHvalue(str);
                break;

            case 2:
                random = RandomValue(str);
                break;
        }

        return abs(random);
    }

    private static Long ASCIIValue(String s) {
        System.out.println("en ascii " + s);
        int sum_char = 0;

        // loop to sum the ascii value of chars 
        for (int i = 0; i < s.length(); i++) {
            sum_char += (int) s.charAt(i);
            //System.out.println("suma va en " + sum_char);
        }

        // Returning average of chars 
        return sum_char * (secondsToday());
    }

    private static Long HASHvalue(String s) {
        System.out.println("en hash " + s);
        return s.hashCode() * (secondsToday());
    }

    private static Long RandomValue(String s) {
        System.out.println("en long " + s);
        return RandomUtils.nextLong() + s.length();
    }

    private static Long secondsToday() {
        ZonedDateTime now = ZonedDateTime.now();
        //ZonedDateTime midnight = now.atStartOfDay();
        //Duration duration = Duration.between(midnight, now);
        long secondsPassed = now.getSecond();

        return secondsPassed;
    }

    private static Long milisToday() {
        ZonedDateTime now = ZonedDateTime.now();
        //ZonedDateTime midnight = now.atStartOfDay();
        //Duration duration = Duration.between(midnight, now);
        long milisPassed = now.getNano();
        
        return milisPassed;
    }
    
    private static Long minutesToday() {
        ZonedDateTime now = ZonedDateTime.now();
        //ZonedDateTime midnight = now.atStartOfDay();
        //Duration duration = Duration.between(midnight, now);
        long minutesPassed = now.getMinute();

        return minutesPassed;
    }
}
