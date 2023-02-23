package com.mycompany.myapp.service.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

public class DateUtils {

    public static final DateTimeFormatter brDateTimeFormatterWithZone = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm (z)");

    public static final DateTimeFormatter brDateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public static String getFormattedCurrentDateBrt() {
        ZonedDateTime nowBrt = ZonedDateTime.now(ZoneId.of("Brazil/East"));
        return brDateTimeFormatterWithZone.format(nowBrt);
    }

    public static LocalDateTime getLocalDateTimeBrt() {
        ZonedDateTime nowBrt = ZonedDateTime.now(ZoneId.of("Brazil/East"));
        return nowBrt.toLocalDateTime();
    }

    public static boolean verificaFeriados(LocalDate date) {
        Set<LocalDate> feriados = new HashSet<>();
        feriados.addAll(getFeriadosFixos(date.getYear()));
        feriados.addAll(getFeriadosMoveis(date.getYear()));
        return feriados.contains(date);
    }

    /**
     * feriados que acontecem tod.o ano na mesma data, gerar lista para o ano específico
     */
    public static Set<LocalDate> getFeriadosFixos(int year) {
        Set<LocalDate> dates = new HashSet<>();

        // Ano novo
        dates.add(LocalDate.of(year, 1, 1));
        // Tiradentes
        dates.add(LocalDate.of(year, 4, 21));
        // Dia mundial do trabalho
        dates.add(LocalDate.of(year, 5, 1));
        // Independência do Brasil
        dates.add(LocalDate.of(year, 9, 7));
        // Nossa Senhora Aparecida
        dates.add(LocalDate.of(year, 10, 12));
        // Finados
        dates.add(LocalDate.of(year, 11, 2));
        // Proclamação da República
        dates.add(LocalDate.of(year, 11, 15));
        // Natal
        dates.add(LocalDate.of(year, 12, 25));

        return dates;
    }

    /**
     * Calcula páscoa, carnaval, corpus christi e sexta-feira santa
     */
    public static Set<LocalDate> getFeriadosMoveis(int year) {
        Set<LocalDate> dates = new HashSet<>();

        int a = year % 19;
        int b = year / 100;
        int c = year % 100;
        int d = b / 4;
        int e = b % 4;
        int f = (b + 8) / 25;
        int g = (b - f + 1) / 3;
        int h = (19 * a + b - d - g + 15) % 30;
        int i = c / 4;
        int k = c % 4;
        int l = (32 + 2 * e + 2 * i - h - k) % 7;
        int m = (a + 11 * h + 22 * l) / 451;
        int month = (h + l - 7 * m + 114) / 31;
        int day = ((h + l - 7 * m + 114) % 31) + 1;

        LocalDate pascoa = LocalDate.of(year, month, day);

        // Carnaval 47 dias antes da pascoa (sempre cai na terça)
        LocalDate carnaval = pascoa.minusDays(47);

        // CorpusChristi 60 dias apos a pascoa
        // LocalDate corpusChristi = pascoa.plusDays(60);

        LocalDate sextaFeiraSanta = pascoa.minusDays(2);

        // páscoa cai sempre no domingo, entao não precisaria adicionar como feriado
        // dates.add(pascoa);

        // carnaval: adicionar um dia antes e depois (emenda de segunda e quarta-feira de cinzas)
        dates.add(carnaval);
        //dates.add(carnaval.minusDays(1)); // emenda a segunda-feira, ponto facultativo => não precisa adicionar
        //dates.add(carnaval.plusDays(1)); // quarta-feira de cinzas, ponto facultativo => não precisa adicionar

        // corpus christi, não é fériado nacional mas ponto facultativo => não precisa adicionar
        // dates.add(corpusChristi);

        dates.add(sextaFeiraSanta);

        return dates;
    }

    public static LocalDateTime parseLocalDateTime(String date) {
        String[] dates = date.split("/");
        String[] yearTime = dates[2].split(" ");
        if (yearTime.length == 1) {
            return LocalDateTime.of(Integer.parseInt(dates[2]), Integer.parseInt(dates[1]), Integer.parseInt(dates[0]), 0, 0);
        } else {
            String[] times = yearTime[1].split(":");
            return LocalDateTime.of(
                Integer.parseInt(dates[2]),
                Integer.parseInt(dates[1]),
                Integer.parseInt(yearTime[0]),
                Integer.parseInt(times[0]),
                Integer.parseInt(times[1])
            );
        }
    }
}
