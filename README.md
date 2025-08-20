# 💱 Conversor de Monedas

Aplicación de consola desarrollada en Java que permite realizar conversiones entre diferentes monedas utilizando tasas de cambio en tiempo real.

## 🚀 Tecnologías Utilizadas

- **Java 11+** (HttpClient, HttpRequest)
- **Gson** - Manejo de JSON
- **ExchangeRate-API** - API de tasas de cambio

## ⚙️ Configuración

1. **Obtener API Key:**
   - Registrarse en [ExchangeRate-API](https://www.exchangerate-api.com/)
   - Obtener tu API key gratuita

2. **Configurar el proyecto:**
   - Clonar el repositorio
   - Reemplazar `TU_API_KEY_AQUI` en `ConversorApp.java` con tu API key
   - Añadir dependencia de Gson a tu proyecto

## 💰 Conversiones Disponibles

- USD ↔ Peso Argentino (ARS)
- USD ↔ Real Brasileño (BRL)  
- USD ↔ Peso Colombiano (COP)

## 🖥️ Uso

```bash
java com.alura.conversor.principal.ConversorApp
```

### Ejemplo de ejecución:
```
************************************************************
Sea bienvenido/a al Conversor de Moneda =]
************************************************************

1) Dólar =>> Peso argentino
2) Peso argentino =>> Dólar
3) Dólar =>> Real brasileño
4) Real brasileño =>> Dólar
5) Dólar =>> Peso colombiano
6) Peso colombiano =>> Dólar
7) Salir
Elija una opción válida:
************************************************************

1
Ingrese el valor que deseas convertir de USD a ARS: 25
El valor 25.0 [USD] corresponde al valor final de =>>> 20293.75 [ARS]
```

## 📁 Estructura del Proyecto

```
src/
└── com/alura/conversor/
    ├── modelos/
    │   └── RespuestaAPI.java
    ├── principal/
    │   └── ConversorApp.java
    └── excepcion/
        └── ErrorEnConversionException.java
```

## 💾 Funcionalidades

- ✅ Conversiones en tiempo real
- ✅ Historial de conversiones guardado en JSON
- ✅ Manejo de errores y excepciones
- ✅ Interfaz de consola intuitiva
- ✅ Validación de entrada de datos

---

**Desarrollado como parte del challenge de Alura - ONE**
