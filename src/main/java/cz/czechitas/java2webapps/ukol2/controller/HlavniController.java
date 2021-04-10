package cz.czechitas.java2webapps.ukol2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Controller
public class HlavniController {

    private final List<String> obrazky = Arrays.asList(
            "https://source.unsplash.com/7E0d3DZwS5c/1600x900",
            "https://source.unsplash.com/y3yv_NG1kYU/1600x900",
            "https://source.unsplash.com/k-QD1vR_QpU/1600x900",
            "https://source.unsplash.com/Q78W18T-dss/1600x900",
            "https://source.unsplash.com/1MHWTwXL62w/1600x900",
            "https://source.unsplash.com/1LQo33nz3TM/1600x900",
            "https://source.unsplash.com/ZKbve9f7Mp4/1600x900",
            "https://source.unsplash.com/bKhETeDV1WM/1600x900",
            "https://source.unsplash.com/KMrYZp6ismc/1600x900",
            "https://source.unsplash.com/Ete0zMKPWys/1600x900"
    );


    @GetMapping("/")
    public ModelAndView zobrazIndex() throws IOException {
        ModelAndView vysledek = new ModelAndView("index");
        Random generatorNahodnehoCisla = new Random();
        List<String> listCitatu = readAllLines("citaty.txt");
        int cisloObrazku = generatorNahodnehoCisla.nextInt(obrazky.size());
        int cisloTextu = generatorNahodnehoCisla.nextInt(listCitatu.size());
        String obrazek = obrazky.get(cisloObrazku);
        String text = listCitatu.get(cisloTextu);
        vysledek.addObject("obrazek", obrazek);
        vysledek.addObject("text", text);
        return vysledek;
    }

    private static List<String> readAllLines(String resource) throws IOException {
        //Soubory z resources se získávají pomocí classloaderu. Nejprve musíme získat aktuální classloader.
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

        //Pomocí metody getResourceAsStream() získáme z classloaderu InpuStream, který čte z příslušného souboru.
        //Následně InputStream převedeme na BufferedRead, který čte text v kódování UTF-8
        try (InputStream inputStream = classLoader.getResourceAsStream(resource);
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {

            //Metoda lines() vrací stream řádků ze souboru. Pomocí kolektoru převedeme Stream<String> na List<String>.
            return reader
                    .lines()
                    .collect(Collectors.toList());
        }
    }
}
