package edu.pucmm.Practica20111614;

import org.apache.commons.validator.routines.UrlValidator;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by jrdis on 25/5/2017.
 */
public class main {
    public static void main(String[] args) {
        int cont = 0;
        String[] formato = {"http","https"};
        Scanner check_URL = new Scanner(System.in);

        UrlValidator valid = new UrlValidator(formato);
        String URL="";
        while (!valid.isValid(URL)) {
            System.out.print("Introduzca la URL: ");
            URL = check_URL.next();

            if (!valid.isValid(URL)) {
                System.out.println("La URL es inválida...");
            } else {

                System.out.println("La URL es válida...");
            }

        }

        check_URL.close();
        try {


            Document doc = Jsoup.connect(URL).get();

            Elements space = doc.body().getElementsByTag("p");
            Elements forms = doc.select("form");
            Elements formsPost = doc.select("form[method=post]");
            Elements formsGet = doc.select("form[method=get]");

            ArrayList<Elements> inputs = new ArrayList<Elements>();
            ArrayList<Elements> spacess = new ArrayList<Elements>();


            for (Element formT : forms) {
                inputs.add(formT.getElementsByTag("input"));
            }

            for (Element pace : space) {
                if (!pace.getElementsByTag("img").isEmpty())
                    spacess.add(pace.getElementsByTag("img"));
            }

            String[] lines = doc.html().split("\r\n|\r|\n");

            System.out.println("Cantidad de lineas: " + lines.length);
            System.out.println("Cantidad de Parrafos: " + space.size());
            System.out.println("Cantidad de imagenes entre parrafos: " + spacess.size());
            System.out.println("forms (post): " + formsPost.size());
            System.out.println("forms (get): " + formsGet.size());


            System.out.println("Etiquetas Input de forms: ");

            for (Elements items : inputs) {
                System.out.println("Form: ");
                for (Element input : items) {
                    String typeInput = input.attr("type");
                    System.out.println("Type: " + typeInput );
                }

            }

            Connection.Response d = Jsoup.connect(URL ).data("asignatura", "practica1").method(Connection.Method.POST).execute();
            System.out.println(d.body());

        }

        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
