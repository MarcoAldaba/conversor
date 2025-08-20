package com.alura.conversor.principal;

import com.alura.conversor.excepcion.ErrorEnConversionException;
import com.alura.conversor.modelos.RespuestaAPI;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ConversorApp {
    public static void main(String[] args) throws IOException, InterruptedException {
        Scanner lectura = new Scanner(System.in);
        List<String> historialConversiones = new ArrayList<>();
        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .setPrettyPrinting()
                .create();

        String apiKey = "3d5328dbfc2753abf6367a57"; // Reemplaza con tu API key

        System.out.println("*".repeat(60));
        System.out.println("Sea bienvenido/a al Conversor de Moneda =]");
        System.out.println("*".repeat(60));

        while (true) {
            System.out.println("\n1) Dólar =>> Peso argentino");
            System.out.println("2) Peso argentino =>> Dólar");
            System.out.println("3) Dólar =>> Real brasileño");
            System.out.println("4) Real brasileño =>> Dólar");
            System.out.println("5) Dólar =>> Peso colombiano");
            System.out.println("6) Peso colombiano =>> Dólar");
            System.out.println("7) Salir");
            System.out.println("Elija una opción válida:");
            System.out.println("*".repeat(60));

            try {
                var opcion = lectura.nextInt();

                if (opcion == 7) {
                    break;
                }

                String monedaOrigen = "";
                String monedaDestino = "";

                switch (opcion) {
                    case 1:
                        monedaOrigen = "USD";
                        monedaDestino = "ARS";
                        break;
                    case 2:
                        monedaOrigen = "ARS";
                        monedaDestino = "USD";
                        break;
                    case 3:
                        monedaOrigen = "USD";
                        monedaDestino = "BRL";
                        break;
                    case 4:
                        monedaOrigen = "BRL";
                        monedaDestino = "USD";
                        break;
                    case 5:
                        monedaOrigen = "USD";
                        monedaDestino = "COP";
                        break;
                    case 6:
                        monedaOrigen = "COP";
                        monedaDestino = "USD";
                        break;
                    default:
                        System.out.println("Opción no válida");
                        continue;
                }

                System.out.printf("Ingrese el valor que deseas convertir de %s a %s: ", monedaOrigen, monedaDestino);
                var cantidad = lectura.nextDouble();

                if (cantidad <= 0) {
                    System.out.println("La cantidad debe ser mayor a 0");
                    continue;
                }

                String direccion = "https://v6.exchangerate-api.com/v6/" + apiKey + "/latest/" + monedaOrigen;

                try {
                    HttpClient client = HttpClient.newHttpClient();
                    HttpRequest request = HttpRequest.newBuilder()
                            .uri(URI.create(direccion))
                            .build();
                    HttpResponse<String> response = client
                            .send(request, HttpResponse.BodyHandlers.ofString());

                    String json = response.body();
                    System.out.println("Respuesta de la API recibida...");

                    RespuestaAPI miRespuestaAPI = gson.fromJson(json, RespuestaAPI.class);

                    if (!miRespuestaAPI.result().equals("success")) {
                        throw new ErrorEnConversionException("Error en la API: " + miRespuestaAPI.result());
                    }

                    Double tasaDeCambio = miRespuestaAPI.conversion_rates().get(monedaDestino);
                    if (tasaDeCambio == null) {
                        throw new ErrorEnConversionException("Moneda no encontrada: " + monedaDestino);
                    }

                    double resultado = cantidad * tasaDeCambio;

                    System.out.printf("El valor %.1f [%s] corresponde al valor final de =>>> %.2f [%s]\n",
                            cantidad, monedaOrigen, resultado, monedaDestino);

                    System.out.println("*".repeat(60));

                    String conversion = String.format("%.1f %s = %.2f %s",
                            cantidad, monedaOrigen, resultado, monedaDestino);
                    historialConversiones.add(conversion);

                } catch (NumberFormatException e) {
                    System.out.println("Ocurrió un error: ");
                    System.out.println(e.getMessage());
                } catch (IllegalArgumentException e) {
                    System.out.println("Error en la URI, verifique la dirección.");
                } catch (ErrorEnConversionException e) {
                    System.out.println(e.getMessage());
                }

            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                lectura.nextLine(); // Limpiar buffer
            }
        }

        System.out.println("\nHistorial de conversiones:");
        System.out.println(historialConversiones);

        FileWriter escritura = new FileWriter("conversiones.json");
        escritura.write(gson.toJson(historialConversiones));
        escritura.close();
        System.out.println("Finalizó la ejecución del programa!");
    }
}